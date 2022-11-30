from intcode.intcode import IntCode

with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]

size = 50
coordinate = [0, 0]
index = [0]
count = [0]
last_result = [0]
map = []

comp = IntCode(op.copy())
comp.run()

def give_input():
    value = coordinate[index[0]]
    index[0] += 1
    return value


def handle_output(output):
    if output == 1:
        count[0] += 1
    last_result[0] = output


for y in range(50):
    row = []
    for x in range(50):
        coordinate = [x, y]
        index[0] = 0
        comp = IntCode(op.copy(), input_provider=give_input, output_callback=handle_output)
        comp.run()
        row.append(last_result[0])
    map.append(row)

print("Number of effected tiles: {}".format(count[0]))

for y in range(50):
    for x in range(50):
        tile = map[y][x]
        if tile == 1:
            print("#", end="")
        else:
            print(".", end="")
    print("")
