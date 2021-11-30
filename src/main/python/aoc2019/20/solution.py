
with open("input.txt", "r") as f:
    maze = []
    for line in f.readlines():
        maze.append(list(line.rstrip()))
    print(maze)
    portals = {}
    for y in range(len(maze)):
        for x in range(len(maze[y])):
            if maze[y][x].isupper():
                entrance = None
                name = None
                try:
                    if x > 0 and maze[y][x-1] == '.':
                        entrance = (x - 1, y)
                        other_letter = maze[y][x + 1]
                        name = maze[y][x] + other_letter
                except IndexError:
                    pass
                try:
                    if x < len(maze[y]) - 1 and maze[y][x+1] == '.':
                        entrance = (x + 1, y)
                        other_letter = maze[y][x - 1]
                        name = other_letter + maze[y][x]
                except IndexError:
                    pass
                try:
                    if y > 0 and maze[y-1][x] == '.':
                        entrance = (x, y - 1)
                        other_letter = maze[y + 1][x]
                        name = maze[y][x] + other_letter
                except IndexError:
                    pass
                try:
                    if y < len(maze) - 1 and maze[y+1][x] == '.':
                        entrance = (x, y + 1)
                        other_letter = maze[y - 1][x]
                        name = other_letter + maze[y][x]
                except IndexError:
                    pass
                if not entrance:
                    continue
                current_coordinates = portals.get(name, [])
                current_coordinates.append(entrance)
                portals[name] = current_coordinates


print(portals)

q = [(portals['AA'][0], 0)]
visited = []
answer = None
while True:
    print(q)
    current = q.pop(0)
    loc = current[0]
    length = current[1]
    if loc in visited:
        continue
    visited.append(loc)

    teleportal = None
    if maze[loc[1]][loc[0] - 1].isupper():
        teleportal = maze[loc[1]][loc[0] - 2] + maze[loc[1]][loc[0] - 1]
    if maze[loc[1] + 1][loc[0]].isupper():
        teleportal = maze[loc[1] + 1][loc[0]] + maze[loc[1] + 2][loc[0]]
    if maze[loc[1] - 1][loc[0]].isupper():
        teleportal = maze[loc[1] - 2][loc[0]] + maze[loc[1] - 1][loc[0]]
    if maze[loc[1]][loc[0] + 1].isupper():
        teleportal = maze[loc[1]][loc[0] + 1] + maze[loc[1]][loc[0] + 2]

    if teleportal:
        if teleportal == 'ZZ':
            answer = length
            break
        if teleportal != 'AA':
            pair = portals[teleportal]
            print("Teleportal = {}".format(teleportal))
            if pair[0] != loc:
                q.append((pair[0], length + 1))
            else:
                q.append((pair[1], length + 1))

    if maze[loc[1] + 1][loc[0]] == '.' \
            and (loc[0], loc[1] + 1) not in visited:
        q.append(((loc[0], loc[1] + 1), length + 1))
    if maze[loc[1] - 1][loc[0]] == '.' \
            and (loc[0], loc[1] - 1) not in visited:
        q.append(((loc[0], loc[1] - 1), length + 1))
    if maze[loc[1]][loc[0] + 1] == '.' \
            and (loc[0] + 1, loc[1]) not in visited:
        q.append(((loc[0] + 1, loc[1]), length + 1))
    if maze[loc[1]][loc[0] - 1] == '.' \
            and (loc[0] - 1, loc[1]) not in visited:
        q.append(((loc[0] - 1, loc[1]), length + 1))

print("Amount of steps is {}".format(answer))
