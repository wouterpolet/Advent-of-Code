from PIL import Image

from intcode.intcode import IntCode
import numpy as np


tiles = [[0, 0]]
all_painting = []
robot = [0, 0]
direction = [0, -1]
outputs = []


def give_input(outputs, robot, tiles, direction):
    if len(outputs) == 0:
        return robot in tiles
    paint = outputs[0]
    turn = outputs[1]
    outputs.clear()
    if paint and robot not in tiles:
        tiles.append(robot.copy())
    if not paint and robot in tiles:
        tiles.remove(robot)
    if not turn:
        temp = direction[0]
        direction[0] = direction[1]
        direction[1] = -1 * temp
    else:
        temp = direction[0]
        direction[0] = -1 * direction[1]
        direction[1] = temp
    robot[0] += direction[0]
    robot[1] += direction[1]
    return 1 if robot in tiles else 0


with open("input.txt", "r") as f:
    op = [int(x) for x in f.readlines()[0].rstrip().split(",")]

intcode = IntCode(op, lambda: give_input(outputs, robot, tiles, direction), lambda x: outputs.append(x))
intcode.run()

print(len(tiles))
tiles = np.asarray(tiles)
min_x = min(tiles[:, 0])
min_y = min(tiles[:, 1])
tiles[:, 0] += min_x
tiles[:, 1] += min_y
max_x = max(tiles[:, 0])
max_y = max(tiles[:, 1])
tiles = list(tiles)
tiles = [list(x) for x in tiles]
img = []

for i in range(max_y+1):
    # row = []
    for j in range(max_x+1):
        print("█" if [j, i] in tiles else " ", end="")
    #     row.append(255 if [j, i] in tiles else 0)
    # img.append(row)
    print("")

# print(img)
print(tiles)
#
# img = np.asarray(img)
#
# im = Image.fromarray(img.astype('uint8') * 255)

# im.show()
