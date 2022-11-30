import math

q = []
width = 0
asteroids = []
amount = {}
station = (17, 22)

with open("input.txt", "r") as f:
    row = 0
    height = 0
    for line in f.readlines():
        column = 0
        width = 0
        for char in list(line.rstrip()):
            if char == '#':
                asteroids.append((column, row))
            column += 1
            width += 1
        row += 1
        height += 1

def in_line_of_sight(x1, x2, y1, y2):
    if abs(x1 - x2) > abs(y1 - y2):
        if abs(x1 - x2) == 0:
            step_size = 0
        else:
            step_size = abs(y1 - y2) / abs(x1 - x2)
        for i in range(1, abs(x1 - x2)):
            if x1 < x2:
                x = x1 + i
            else:
                x = x1 - i
            if y1 < y2:
                y = y1 + step_size * i
            else:
                y = y1 - step_size * i
            if y % 1 == 0:
                new_point = (int(x), int(y))
                if new_point in asteroids:
                    return False
        return True
    else:
        if abs(y1 - y2) == 0:
            step_size = 0
        else:
            step_size = abs(x1 - x2) / abs(y1 - y2)
        for i in range(1, abs(y1 - y2)):
            if y1 < y2:
                y = y1 + i
            else:
                y = y1 - i
            if x1 < x2:
                x = x1 + step_size * i
            else:
                x = x1 - step_size * i
            if x % 1 == 0:
                new_point = (int(x), int(y))
                if new_point in asteroids:
                    return False
        return True

for asteroid in asteroids:
    x = asteroid[0]
    y = asteroid[1]
    if (x, y) == station:
        continue
    dy = station[1] - y
    dx = x - station[0]
    angle = math.atan2(dy, dx) * -1 + 0.5 * math.pi
    if angle < 0:
        angle += math.pi * 2
    q.append((angle, (x, y)))

q.sort(reverse=True)

queue = q.copy()
destroyed = 0
target = (-1, -1)

while len(asteroids) != 0:
    to_destroy = []
    while len(queue) != 0:
        coordinate = queue.pop()[1]
        if coordinate not in asteroids:
            continue
        if in_line_of_sight(station[0], coordinate[0], station[1], coordinate[1]):
            to_destroy.append(coordinate)
            print("Destroyed {}, as number {}".format(coordinate, destroyed + 1))
            destroyed += 1
            if destroyed == 200:
                target = coordinate
    queue = q.copy()
    for destroy in to_destroy:
        asteroids.remove(destroy)

print("200th asteroid is: {}".format(target))
print("Asnwer is: {}".format(target[0]*100 + target[1]))


print(asteroids)



