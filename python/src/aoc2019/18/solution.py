def is_key(character):
    return 97 <= ord(character) <= 122


def is_door(character):
    return 65 <= ord(character) <= 90


def key_to_key_connections(maze, keys):
    connections = {}
    for key in keys:
        q = [(0, keys[key], [])]
        visited = []
        while len(q) > 0:
            current = q.pop(0)
            depth = current[0]
            loc = current[1]
            if loc in visited:
                continue
            dependencies = current[2]
            visited.append((loc[0], loc[1]))
            if is_key(maze[loc[1]][loc[0]]) and maze[loc[1]][loc[0]] != key:
                connections[key, maze[loc[1]][loc[0]]] = (depth, dependencies.copy())
            if maze[loc[1] + 1][loc[0]] != '#' \
                    and (loc[0], loc[1] + 1) not in visited:
                dep = dependencies.copy()
                if is_door(maze[loc[1] + 1][loc[0]]):
                    dep.append(maze[loc[1] + 1][loc[0]])
                q.append((depth + 1, (loc[0], loc[1] + 1), dep))
            if maze[loc[1] - 1][loc[0]] != '#' \
                    and (loc[0], loc[1] - 1) not in visited:
                dep = dependencies.copy()
                if is_door(maze[loc[1] - 1][loc[0]]):
                    dep.append(maze[loc[1] - 1][loc[0]])
                q.append((depth + 1, (loc[0], loc[1] - 1), dep))
            if maze[loc[1]][loc[0] + 1] != '#' \
                    and (loc[0] + 1, loc[1]) not in visited:
                dep = dependencies.copy()
                if is_door(maze[loc[1]][loc[0] + 1]):
                    dep.append(maze[loc[1]][loc[0] + 1])
                q.append((depth + 1, (loc[0] + 1, loc[1]), dep))
            if maze[loc[1]][loc[0] - 1] != '#' \
                    and (loc[0] - 1, loc[1]) not in visited:
                dep = dependencies.copy()
                if is_door(maze[loc[1]][loc[0] - 1]):
                    dep.append(maze[loc[1]][loc[0] - 1])
                q.append((depth + 1, (loc[0] - 1, loc[1]), dep))
    return connections


def dependencies(keys, connections):
    # list has keys dependent on key of dict
    dependencies = {}
    for key in keys:
        dependencies[key] = []
        for possibly_dependent in keys:
            if possibly_dependent == '@' or possibly_dependent == key:
                continue
            if key.upper() in connections['@', possibly_dependent][1]:
                dependencies[key].append(possibly_dependent)
    return dependencies


maze = []

with open("input.txt", "r") as f:
    for line in f.readlines():
        row = []
        for char in list(line.rstrip()):
            row.append(char)
        maze.append(row)

x = 0
y = 0
player = (-1, -1)
keys = {}
doors = {}
for row in maze:
    x = 0
    for character in row:
        if is_key(character):
            keys[character] = (x, y)
        if is_door(character):
            doors[character] = (x, y)
        if character == '@':
            player = (x, y)
            keys[character] = (x, y)
        x += 1
    y += 1

key_connections = key_to_key_connections(maze, keys)
print(key_connections)
key_dependencies = dependencies(keys, key_connections)
print(key_dependencies)

paths = []

for key in keys:
    if key == '@':
        continue
    if len(key_dependencies[key]) == 0:
        paths.append(([key], 0))

while len(paths[0][0]) != len(keys) - 1:
    print("Added {} letters. Length paths: {}".format(len(paths[0][0]), len(paths)))
    new_paths = []
    visited = []
    path_groups = []
    for path_length in paths:
        if path_length in visited:
            continue
        equivalent_paths = [path_length]
        visited.append(path_length)
        path = path_length[0]
        for other in paths:
            other_path = other[0]
            equivalent = True
            for el in path:
                if el not in other_path:
                    equivalent = False
                    break
            if equivalent:
                equivalent_paths.append(other)
                visited.append(other)
        path_groups.append(equivalent_paths)
    for group in path_groups:
        for key in keys:
            path_init = group[0][0]
            if key == '@' or key in path_init:
                continue
            if any(dependency not in path_init for dependency in key_dependencies[key]):
                continue
            min_length = -1
            min_path = None
            for path_length in group:
                path = path_length[0]
                length = path_length[1]
                new_length = length + key_connections[key, path[len(path) - 1]][0]
                if new_length < min_length or min_length == -1:
                    min_length = new_length
                    min_path = path.copy()
            min_path.append(key)
            new_paths.append((min_path, min_length))
    paths = new_paths

minimum_steps = -1
minimum_path = None

for possible_path in paths:
    path = possible_path[0]
    length = possible_path[1]
    new_length = length + key_connections['@', path[len(path) - 1]][0]
    if minimum_steps == -1 or new_length < minimum_steps:
        minimum_steps = new_length
        new_path = path.copy()
        new_path.append('@')
        minimum_path = new_path


print("Min path: {}".format(minimum_path))
print("Min distance: {}".format(minimum_steps))
