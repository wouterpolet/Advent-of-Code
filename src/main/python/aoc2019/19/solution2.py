from intcode.intcode import IntCode

with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]

size = 100
coordinate = [100, 100]
do_not_touch = [1, 1]
index = [0]
count = [0]
value = [0]
map = {}

max_x = 10000
max_y = 10000


def give_input():
    value = do_not_touch[index[0]]
    index[0] += 1
    return value


def handle_output(output):
    value[0] = output


def get_coordinate(x, y):
    if (x, y) in map:
        return map[x, y]
    do_not_touch[0] = x
    do_not_touch[1] = y
    index[0] = 0
    comp = IntCode(op.copy(), input_provider=give_input, output_callback=handle_output)
    comp.run()
    map[x, y] = value[0]
    return value[0]


add_to_y = 0
max_y_add = 100
found = False
while not found:
    while get_coordinate(coordinate[0], coordinate[1]) == 0:
        coordinate[1] += 1
    index_down = 0
    while get_coordinate(coordinate[0], coordinate[1] + index_down) == 1 and get_coordinate(coordinate[0], coordinate[1] + index_down + size - 1) == 1:
        if get_coordinate(coordinate[0] + size - 1, coordinate[1] + index_down) == 1:
            found = True
            coordinate[0] -= 1
            coordinate[1] += index_down
            break
        index_down += 1
    coordinate[0] += 1
    if coordinate[0] % 100 == 0:
        print("Coordinate {}".format(coordinate))

print("Left top coordinate: {}, {}".format(coordinate[0], coordinate[1]))
print("Answer: {}".format(coordinate[0] * 10000 + coordinate[1]))
