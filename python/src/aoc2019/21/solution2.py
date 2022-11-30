from intcode.intcode import IntCode


inputs = []


def give_input():
    if len(inputs) == 0:
        inp = input("Waiting for input:\n").rstrip()
        inputs.extend(list(inp))
        inputs.append('\n')
    return ord(inputs.pop(0))


def handle_output(output):
    if output > 127:
        print(output)
    else:
        print(chr(output), end="")


with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]

with open("program2.txt", "r") as p:
    for line in p.readlines():
        if line[0] == '/':
            continue
        inputs.extend(list(line))

comp = IntCode(op, input_provider=give_input, output_callback=handle_output)
comp.run()


