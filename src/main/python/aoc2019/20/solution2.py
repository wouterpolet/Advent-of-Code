import sys

def is_outer(pos, maze):
    outer = pos[0] < 3 or pos[1] < 3 or pos[0] > len(maze[2]) - 2 or pos[1] > len(maze) - 4
    print("Returning outer {} for {}".format(outer, pos))
    return outer


def bfs(maze, start, finish):
    q = [(start, 0)]
    visited = []
    while len(q) > 0:
        current = q.pop(len(q) - 1)
        loc = current[0]
        length = current[1]
        if loc == finish:
            return length
        if loc in visited:
            continue
        visited.append(loc)
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
    return None

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

connections = {}

for portal in portals:
    for other in portals:
        if portal == other:
            continue
        res = bfs(maze, portals[portal][0], portals[other][0])
        if res:
            connections[portals[portal][0], portals[other][0]] = res
        if len(portals[other]) > 1:
            res = bfs(maze, portals[portal][0], portals[other][1])
            if res:
                connections[portals[portal][0], portals[other][1]] = res
        if len(portals[portal]) > 1:
            res = bfs(maze, portals[portal][1], portals[other][0])
            if res:
                connections[portals[portal][1], portals[other][0]] = res
        if len(portals[portal]) > 1 and len(portals[other]) > 1:
            res = bfs(maze, portals[portal][1], portals[other][1])
            if res:
                connections[portals[portal][1], portals[other][1]] = res

print(connections)


max_level = (len(portals) - 1) * 2

# Position, length, level
q = [(portals['AA'][0], 0, 0)]
visited = {(portals['AA'][0], 0): 0}
answer = None
while len(q) > 0:
    current = q.pop(0)
    print("Doing {}".format(current))
    current_location = current[0]
    current_length = current[1]
    current_level = current[2]
    if visited[current_location, current_level] != current_length:
        continue

    # Try walking around
    for portal in portals:
        for new_location in portals[portal]:
            if (current_location, new_location) in connections:
                new_length = current_length + connections[current_location, new_location]
                if (new_location, current_level) not in visited or visited[new_location, current_level] > new_length:
                    visited[new_location, current_level] = new_length
                    q.append((new_location, new_length, current_level))

    # Try going up or down
    if current_location != portals['AA'][0] and current_location != portals['ZZ'][0]:
        new_location = None
        new_length = current_length + 1
        new_level = None
        # Go up
        if is_outer(current_location, maze) and current_level > 0:
            new_level = current_level - 1
            for portal in portals:
                if portal == 'AA' or portal == 'ZZ':
                    continue
                if portals[portal][0] == current_location:
                    new_location = portals[portal][1]
                elif portals[portal][1] == current_location:
                    new_location = portals[portal][0]
        # Go down
        elif not is_outer(current_location, maze) and current_level < max_level:
            new_level = current_level + 1
            for portal in portals:
                if portal == 'AA' or portal == 'ZZ':
                    continue
                if portals[portal][0] == current_location:
                    new_location = portals[portal][1]
                elif portals[portal][1] == current_location:
                    new_location = portals[portal][0]

        if new_location and \
                ((new_location, new_level) not in visited or visited[new_location, new_level] > new_length):
            visited[new_location, new_level] = new_length
            q.append((new_location, new_length, new_level))

print("")
print(visited)
answer = visited[portals['ZZ'][0], 0]
print("Amount of steps is {}".format(answer))
