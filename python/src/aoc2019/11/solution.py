from intcode.intcode import IntCode


tiles = [[0, 0]]
all_painting = []
robot = [0, 0]
direction = [0, -1]
outputs = []


def give_input(outputs, robot, tiles, direction, all_painting):
    if len(outputs) == 0:
        return robot in tiles
    paint = outputs[0]
    turn = outputs[1]
    outputs.clear()
    if paint and robot not in tiles:
        tiles.append(robot.copy())
    if not paint and robot in tiles:
        tiles.remove(robot)
    if robot not in all_painting:
        all_painting.append(robot.copy())
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

intcode = IntCode(op, lambda: give_input(outputs, robot, tiles, direction, all_painting), lambda x: outputs.append(x))
intcode.run()

print("The total amount of painted tiles is: {}".format(len(all_painting)))

