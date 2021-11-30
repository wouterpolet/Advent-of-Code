from intcode.intcode import IntCode

with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]

tiles = {
    (0, 0): 0
}
location = [0, 0]
given = []


def give_input(location, given, tiles):
    # north
    if (location[0], location[1] - 1) not in tiles:
        given.append(1)
        location[1] -= 1
        return 1
    # south
    if (location[0], location[1] + 1) not in tiles:
        given.append(2)
        location[1] += 1
        return 2
    # west
    if (location[0] - 1, location[1]) not in tiles:
        given.append(3)
        location[0] -= 1
        return 3
    # east
    if (location[0] + 1, location[1]) not in tiles:
        given.append(4)
        location[0] += 1
        return 4
    # Backtrack if no space left
    length_given = len(given)
    if length_given != 0:
        backtrack = given.pop(length_given - 1)
        choice = -1
        if backtrack == 1:
            choice = 2
            location[1] += 1
        elif backtrack == 2:
            choice = 1
            location[1] -= 1
        elif backtrack == 3:
            choice = 4
            location[0] += 1
        elif backtrack == 4:
            choice = 3
            location[0] -= 1
        # print("Backtracking, new loc: {}".format(location))
        return choice
    # print("F in de chat")
    return -1


def handle_output(output, given, tiles, location):
    tiles[(location[0], location[1])] = output
    # print("Found {} at {}".format(output, location))
    if output == 0:
        last_move = given.pop(len(given) - 1)
        if last_move == 1:
            location[1] += 1
        elif last_move == 2:
            location[1] -= 1
        elif last_move == 3:
            location[0] += 1
        elif last_move == 4:
            location[0] -= 1
        # print("Hit wall, new loc: {}".format(location))
    if len(given) == 0 \
        and (location[0], location[1] + 1) in tiles \
        and (location[0], location[1] - 1) in tiles \
        and (location[0] + 1, location[1]) in tiles \
            and (location[0] - 1, location[1]) in tiles:
        return
    comp.run()


comp = IntCode(op,
               input_provider=lambda: give_input(location, given, tiles),
               output_callback=lambda x: handle_output(x, given, tiles, location),
               continuous=False)

comp.run()

queue = [(0, (0, 0))]
visited = []

while len(queue) > 0:
    current = queue.pop()
    if current[1] in visited:
        continue
    visited.append(current[1])
    if tiles.get(current[1], 0) == 2:
        print("Steps = {}".format(current[0]))
        break
    if tiles.get((current[1][0] + 1, current[1][1]), 0) > 0 \
            and (current[1][0] + 1, current[1][1]) not in visited:
        queue.append((current[0] + 1, (current[1][0] + 1, current[1][1])))
    if tiles.get((current[1][0] - 1, current[1][1]), 0) > 0 \
            and (current[1][0] - 1, current[1][1]) not in visited:
        queue.append((current[0] + 1, (current[1][0] - 1, current[1][1])))
    if tiles.get((current[1][0], current[1][1] + 1), 0) > 0 \
            and (current[1][0], current[1][1] + 1) not in visited:
        queue.append((current[0] + 1, (current[1][0], current[1][1] + 1)))
    if tiles.get((current[1][0], current[1][1] - 1), 0) > 0 \
            and (current[1][0], current[1][1] - 1) not in visited:
        queue.append((current[0] + 1, (current[1][0], current[1][1] - 1)))
