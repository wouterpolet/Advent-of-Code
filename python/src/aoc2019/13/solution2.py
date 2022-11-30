from intcode.intcode import IntCode
import numpy as np

tiles = {}
current_tile = []

displays = {
    0: " ",
    1: "#",
    2: "□",
    3: "-",
    4: "O"
}


def handle_output(tiles, current_tile, output):
    current_tile.append(output)
    if len(current_tile) == 3:
        if current_tile[0] == -1 and current_tile[1] == 0:
            print("Score: {}".format(current_tile[2]))
        tiles[current_tile[0], current_tile[1]] = current_tile[2]
        current_tile.clear()
    # print_tiles(tiles)


def print_tiles(tiles):
    max_x = 0
    max_y = 0
    for key in tiles:
        if key[0] > max_x:
            max_x = key[0] + 1
        if key[1] > max_y:
            max_y = key[1] + 1
    for y in range(max_y):
        for x in range(max_x):
            print(displays[tiles[x, y]], end="")
        print("")
    print("")


def generate_input():
    ball = []
    paddle = []
    for key in tiles:
        if tiles[key] == 4:
            ball = key
        if tiles[key] == 3:
            paddle = key
    if ball[0] > paddle[0]:
        return 1
    if ball[0] < paddle[0]:
        return -1
    return 0



with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]

comp = IntCode(op, input_provider=generate_input, output_callback=lambda x: handle_output(tiles, current_tile, x))
comp.run()


