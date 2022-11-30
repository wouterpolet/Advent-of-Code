from intcode.intcode import IntCode

tiles = {}
current_tile = []

def handle_output(tiles, current_tile, output):
    current_tile.append(output)
    if len(current_tile) == 3:
        tiles[current_tile[0], current_tile[1]] = current_tile[2]
        current_tile.clear()


with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]

comp = IntCode(op, output_callback=lambda x: handle_output(tiles, current_tile, x))
comp.run()

count = 0
for key in tiles:
    if tiles[key] == 2:
        count += 1
print("We have {} tiles".format(count))
