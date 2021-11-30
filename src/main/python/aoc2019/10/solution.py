asteroids = []
amount = {}

with open("input.txt", "r") as f:
    row = 0 
    for line in f.readlines():
        column = 0
        for char in list(line.rstrip()):
            if char == '#':
                asteroids.append((column, row))
            column += 1
        row += 1

print(asteroids)

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
    return False
        
best_station = (-1, -1)
max_count = 0

for coordinates in asteroids:
    count = 0
    for other_coordinates in asteroids:
        if coordinates == other_coordinates:
            continue
        if in_line_of_sight(coordinates[0], other_coordinates[0], coordinates[1], other_coordinates[1]):
            count += 1
    if count > max_count:
        max_count = count
        best_station = coordinates

print("Station at {}, with {} asteroids".format(best_station, max_count))


