import math

with open('input.txt', 'r') as f:
    degrees = 0
    direction = [0, 1]
    pos = [0, 0]
    for line in f.readlines():
        arg = int(line[1:])
        op = line[0]
        print(op)
        print(arg)
        if op == 'N':
            pos[0] += arg
        elif op == 'S':
            pos[0] -= arg
        elif op == 'E':
            pos[1] += arg
        elif op == 'W':
            pos[1] -= arg
        elif op == 'F':
            pos[0] += arg * direction[0]
            pos[1] += arg * direction[1]
        elif op == 'L':
            degrees += arg
            direction[1] = math.cos(math.radians(degrees))
            direction[0] = math.sin(math.radians(degrees))
        elif op == 'R':
            degrees -= arg
            direction[1] = math.cos(math.radians(degrees))
            direction[0] = math.sin(math.radians(degrees))
        print(pos)
    print(pos)
    print(abs(pos[0]) + abs(pos[1]))
