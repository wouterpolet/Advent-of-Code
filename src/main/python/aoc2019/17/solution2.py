from intcode.intcode import IntCode

with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]

tiles = {}
coordinate = [0, 0]
max_coordinate = [0, 0]
final = [0]


def handle_output(output):
    final[0] = output
    if coordinate[0] > max_coordinate[0]:
        max_coordinate[0] = coordinate[0]
    if coordinate[1] > max_coordinate[1]:
        max_coordinate[1] = coordinate[1]
    char = chr(output)
    if char == '\n':
        coordinate[0] = 0
        coordinate[1] += 1
        print("")
        return
    tiles[coordinate[0], coordinate[1]] = char
    coordinate[0] += 1
    print(char, end="")


inputs = []


def give_input():
    if len(inputs) == 0:
        inp = input("Waiting for input:\n").rstrip()
        inputs.extend(list(inp))
        inputs.append('\n')
    return ord(inputs.pop(0))


comp = IntCode(op, input_provider=give_input, output_callback=handle_output)
comp.run()

# Through experimentation
max_size = [50, 30]

print("Amount of dust: {}".format(final[0]))
