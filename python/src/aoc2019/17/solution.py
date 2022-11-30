from intcode.intcode import IntCode

with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]

tiles = {}
coordinate = [0, 0]
max_coordinate = [0, 0]


def handle_output(output):
    if coordinate[0] > max_coordinate[0]:
        max_coordinate[0] = coordinate[0]
    if coordinate[1] > max_coordinate[1]:
        max_coordinate[1] = coordinate[1]
    char = chr(output)
    if char == '\n':
        coordinate[0] = 0
        coordinate[1] += 1
        return
    tiles[coordinate[0], coordinate[1]] = char
    coordinate[0] += 1
    print(char, end="")


comp = IntCode(op, output_callback=handle_output)
comp.run()
print(tiles)

max_size = [50, 30]

intersections = []

for coordinate in tiles:
    if tiles[coordinate] == '#' and coordinate[0] > 0 and coordinate[1] > 0 \
            and coordinate[0] < max_size[0] - 1 and coordinate[1] < max_size[1] - 1:
        if tiles[coordinate[0] - 1, coordinate[1]] == '#' \
            and tiles[coordinate[0] + 1, coordinate[1]] == '#' \
            and tiles[coordinate[0], coordinate[1] - 1] == '#' \
                and tiles[coordinate[0], coordinate[1] + 1] == '#':
            intersections.append(coordinate)

total = 0

for intersection in intersections:
    total += intersection[0] * intersection[1]

print("Sum of aligned parameters: {}".format(total))
