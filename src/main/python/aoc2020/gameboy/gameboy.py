from y2020.gameboy.terminate import Terminate

class Gameboy:

    isp = 0
    instr = None
    operations = None
    reg = 0

    def __init__(self, instructions):
        self.instr = instructions
        self.operations = {
            'jmp': self.jump,
            'acc': self.acc,
            'nop': self.nop
        }

    def jump(self, arg):
        self.isp += arg - 1

    def acc(self, arg):
        self.reg += arg

    def nop(self, _):
        pass

    def get_instruction(self):
        return self.instr[self.isp].split()[0]

    def get_arg(self):
        return int(self.instr[self.isp].split()[1])

    def run_iter(self):
        self.operations[self.get_instruction()](self.get_arg())
        self.isp += 1

    def run(self):
        while self.isp < len(self.instr):
            self.run_iter()

    def run_with(self, after):
        while self.isp < len(self.instr):
            self.run_iter()
            after(self)

    def run_until(self, cond, before=lambda x: x, after=lambda x: x):
        try:
            while cond(self) and self.isp < len(self.instr):
                before(self)
                self.run_iter()
                after(self)
            return self.reg
        except Terminate as t:
            return t
