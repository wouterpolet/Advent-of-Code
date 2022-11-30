def is_key(character):
    return 97 <= ord(character) <= 122


def is_door(character):
    return 65 <= ord(character) <= 90


def find_keys(maze, player):
    available = []
    q = [(0, player)]
    visited = []
    while len(q) > 0:
        current = q.pop(0)
        loc = current[1]
        depth = current[0]
        visited.append((loc[0], loc[1]))
        if is_key(maze[loc[1]][loc[0]]):
            available.append((depth, current[1]))
        if maze[loc[1] + 1][loc[0]] != '#' \
                and not is_door(maze[loc[1] + 1][loc[0]]) \
                and (loc[0], loc[1] + 1) not in visited:
            q.append((depth + 1, [loc[0], loc[1] + 1]))
        if maze[loc[1] - 1][loc[0]] != '#' \
                and not is_door(maze[loc[1] - 1][loc[0]]) \
                and (loc[0], loc[1] - 1) not in visited:
            q.append((depth + 1, [loc[0], loc[1] - 1]))
        if maze[loc[1]][loc[0] + 1] != '#' \
                and not is_door(maze[loc[1]][loc[0] + 1]) \
                and (loc[0] + 1, loc[1]) not in visited:
            q.append((depth + 1, [loc[0] + 1, loc[1]]))
        if maze[loc[1]][loc[0] - 1] != '#' \
                and not is_door(maze[loc[1]][loc[0] - 1]) \
                and (loc[0] - 1, loc[1]) not in visited:
            q.append((depth + 1, [loc[0] - 1, loc[1]]))
    return available

count = [0]

def find_shortest_path(player, maze, key_doors, length, minimal):
    available_keys = find_keys(maze, player)
    if length > minimal:
        return minimal
    if len(available_keys) == 0:
        count[0] += 1
        if count[0] % 100 == 0:
            print("Done {} counts".format(count[0]))
        return length
    minimal = 999999
    for depth_key in available_keys:
        distance = depth_key[0]
        new_loc = depth_key[1]
        key = maze[new_loc[1]][new_loc[0]]
        new_maze = [a.copy() for a in maze]
        new_maze[new_loc[1]][new_loc[0]] = '.'
        if key.upper() in key_doors:
            door_loc = key_doors[key.upper()]
            new_maze[door_loc[1]][door_loc[0]] = '.'
        result = find_shortest_path(new_loc, new_maze, key_doors, length + distance, minimal)
        if result < minimal:
            minimal = result
    return minimal


maze = []
key_doors = {}

with open("input.txt", "r") as f:
    for line in f.readlines():
        row = []
        for char in list(line.rstrip()):
            row.append(char)
        maze.append(row)

x = 0
y = 0
player = [-1, -1]
for row in maze:
    x = 0
    for character in row:
        if 65 <= ord(character) <= 122:
            key_doors[character] = [x, y]
        if character == '@':
            player = [x, y]
        x += 1
    y += 1

path_length = find_shortest_path(player, maze, key_doors, 0, 99999)
print("Shortest path length to find all keys is: {}".format(path_length))
