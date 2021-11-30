import math

with open('test.txt', 'r') as f:
    w = [1, 10]
    pos = [0, 0]
    def waypoint():
        return pos + w
    for line in f.readlines():
        arg = int(line[1:])
        op = line[0]
        print(op)
        print(arg)
        if op == 'N':
            w[0] += arg
        elif op == 'S':
            w[0] -= arg
        elif op == 'E':
            w[1] += arg
        elif op == 'W':
            w[1] -= arg
        elif op == 'F':
            pos[0] += arg * w[0]
            pos[1] += arg * w[1]
        elif op == 'L':
            degrees = math.atan2(w[0], w[1])
            s = math.sqrt(w[0] ** 2 + w[1] ** 2)
            w[0] = math.sin(degrees + math.radians(arg)) * s
            w[1] = math.cos(degrees + math.radians(arg)) * s
        elif op == 'R':
            degrees = math.atan2(w[0], w[1])
            s = math.sqrt(w[0] ** 2 + w[1] ** 2)
            w[0] = math.sin(degrees - math.radians(arg)) * s
            w[1] = math.cos(degrees - math.radians(arg)) * s
        print(pos)
        print(w)
    print(pos)
    print(abs(pos[0]) + abs(pos[1]))
