from intcode.exceptions import NeedNewInputError


class IntCode:
    def __init__(self, op,
                 input_provider=lambda: int(input("Waiting for input\n")),
                 output_callback=print,
                 continuous=True,
                 given_id="IntCode"):
        self.isp = 0
        self.op = op
        self.relative = 0
        self.input_provider = input_provider
        self.output_callback = output_callback
        self.continuous = continuous
        self.read = False
        self.done = False
        self.id = given_id

    def get_value(self, index):
        self.grow_memory(index)
        return self.op[index]

    def set_value(self, index, value):
        self.grow_memory(index)
        self.op[index] = value

    def grow_memory(self, index):
        if index >= len(self.op):
            for i in range(index - len(self.op) + 1):
                self.op.append(0)

    def get_operation(self):
        return self.get_value(self.isp) % 100

    def get_param(self, index):
        mode = int(self.get_value(self.isp) / 10**(index + 2)) % 10
        if mode == 1:
            return self.get_value(self.isp + index + 1)
        if mode == 2:
            return self.get_value(self.get_value(self.isp + index + 1) + self.relative)
        if mode == 0:
            return self.get_value(self.get_value(self.isp + index + 1))
        return None

    def get_pointer(self, index):
        mode = int(self.get_value(self.isp) / 10**(index + 2)) % 10**(index + 1)
        if mode == 2:
            return self.get_value(self.isp + index + 1) + self.relative
        else:
            return self.get_value(self.isp + index + 1)

    def addition(self):
        self.set_value(self.get_pointer(2), self.get_param(0) + self.get_param(1))
        self.isp += 4

    def multiplication(self):
        self.set_value(self.get_pointer(2), self.get_param(0) * self.get_param(1))
        self.isp += 4

    def input(self):
        if self.read and not self.continuous:
            self.read = False
            raise NeedNewInputError
        self.set_value(self.get_pointer(0), self.input_provider())
        self.read = True
        self.isp += 2

    def output(self):
        value = self.get_param(0)
        self.isp += 2
        self.output_callback(value)

    def jump_if_true(self):
        self.isp = self.get_param(1) if self.get_param(0) else self.isp + 3

    def jump_if_false(self):
        self.isp = self.get_param(1) if not self.get_param(0) else self.isp + 3

    def less_than(self):
        self.set_value(self.get_pointer(2), 1 if self.get_param(0) < self.get_param(1) else 0)
        self.isp += 4

    def equal_to(self):
        self.set_value(self.get_pointer(2), 1 if self.get_param(0) == self.get_param(1) else 0)
        self.isp += 4

    def relative_offset(self):
        self.relative += self.get_param(0)
        self.isp += 2

    def stop(self):
        self.done = True

    def perform_operation(self):
        operations = {
            1: self.addition,
            2: self.multiplication,
            3: self.input,
            4: self.output,
            5: self.jump_if_true,
            6: self.jump_if_false,
            7: self.less_than,
            8: self.equal_to,
            9: self.relative_offset,
            99: self.stop
        }

        operations[self.get_operation()]()

    def run(self):
        try:
            while not self.done:
                self.perform_operation()
        except NeedNewInputError:
            return
