import sys

pos_x = 0
pos_y = 0

wires = None
intersections = []

with open("input.txt", "r") as f:
    wires = f.readlines()
    wires = [x.rstrip().split(",") for x in wires]
    print(wires)

boards = [{} for x in wires]

for j in range(len(wires)):
    wire = wires[j]
    board = boards[j]
    for op in wire:
        di = op[0]
        amount = int(op[1:])
        for i in range(amount):
            if di == 'U':
                pos_y += 1
            elif di == 'D':
                pos_y -= 1
            elif di == 'R':
                pos_x += 1
            elif di == 'L':
                pos_x -= 1
            for z in range(len(boards)):
                other = boards[z]
                if j != z and other.get((pos_x, pos_y), 0) > 0:
                    intersections.append([pos_x, pos_y])
            board[pos_x, pos_y] = board.get((pos_x, pos_y), 0) + 1

    pos_x = 0
    pos_y = 0

minimum = sys.maxsize
print(intersections)

for inter in intersections:
    distance = abs(inter[0]) + abs(inter[1])
    print(distance)
    if distance < minimum:
        minimum = distance

print(minimum)

