def is_key(character):
    return 97 <= ord(character) <= 122


def is_door(character):
    return 65 <= ord(character) <= 90


def is_robot(character):
    return 48 <= ord(character) <= 51


def robot_to_key(robots, connections, key):
    for robot in robots:
        if (robot, key) in connections:
            return int(robot)
    return None

def key_to_key_connections(maze, keys, robots):
    connections = {}
    key_robots = list(keys.keys())
    key_robots.extend(robots.keys())

    for key in key_robots:
        q = [(0, keys.get(key, robots.get(key)), [])]
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


def dependencies(keys, robots, connections):
    # list has keys dependent on key of dict
    dependencies = {}
    for key in keys:
        dependencies[key] = []
        for possibly_dependent in keys:
            if is_robot(possibly_dependent) or possibly_dependent == key:
                continue
            doors_needed = []
            for robot in robots:
                if (robot, possibly_dependent) in connections:
                    doors_needed = connections[robot, possibly_dependent][1]
            if key.upper() in doors_needed:
                dependencies[key].append(possibly_dependent)
    return dependencies


maze = []

with open("input2.txt", "r") as f:
    for line in f.readlines():
        row = []
        for char in list(line.rstrip()):
            row.append(char)
        maze.append(row)

x = 0
y = 0
robots = {}
keys = {}
doors = {}
for row in maze:
    x = 0
    for character in row:
        if is_key(character):
            keys[character] = (x, y)
        if is_door(character):
            doors[character] = (x, y)
        if is_robot(character):
            robots[character] = (x, y)
        x += 1
    y += 1

key_connections = key_to_key_connections(maze, keys, robots)
print(key_connections)
key_dependencies = dependencies(keys, robots, key_connections)
print(key_dependencies)

paths = []

for key in keys:
    if len(key_dependencies[key]) == 0:
        tup = list(robots.keys())
        tup[robot_to_key(robots, key_connections, key)] = key
        tup = tuple(tup)
        paths.append(([key], 0, tup))

while len(paths[0][0]) != len(keys):
    print("Added {} letters. Length paths: {}".format(len(paths[0][0]), len(paths)))
    new_paths = []
    visited = []
    path_groups = []
    for path_length in paths:
        if path_length in visited:
            continue
        equivalent_paths = []
        visited.append(path_length)
        path = path_length[0]
        for other in paths:
            other_path = other[0]
            equivalent = True
            for el in path:
                if el not in other_path:
                    equivalent = False
                    break
            if equivalent and path_length[2] == other[2]:
                equivalent_paths.append(other)
                visited.append(other)
        path_groups.append(equivalent_paths)
    for group in path_groups:
        for key in keys:
            path_init = group[0][0]
            if key in path_init:
                continue
            if any(dependency not in path_init for dependency in key_dependencies[key]):
                continue
            min_length = -1
            min_path = None
            min_tup = None
            for path_length in group:
                path = path_length[0]
                length = path_length[1]
                robot_pos = path_length[2]
                current_key = robot_pos[robot_to_key(robots, key_connections, key)]
                new_length = length + key_connections.get((key, current_key), (0, None))[0]
                if new_length < min_length or min_length == -1:
                    min_length = new_length
                    min_path = path.copy()
                    min_tup = robot_pos
            min_path.append(key)
            new_tup = list(min_tup)
            new_tup[robot_to_key(robots, key_connections, key)] = key
            new_tup = tuple(new_tup)
            new_paths.append((min_path, min_length, new_tup))
    paths = new_paths

minimum_steps = -1
minimum_path = None
print("Final paths: {}".format(paths))
for possible_path in paths:
    path = possible_path[0]
    length = possible_path[1]
    robot_pos = possible_path[2]
    last_key = path[len(path) - 1]
    for i in range(len(robot_pos)):
        length += key_connections[str(i), robot_pos[i]][0]
    if minimum_steps == -1 or length < minimum_steps:
        minimum_steps = length
        new_path = path.copy()
        new_path.append('@')
        minimum_path = new_path


print("Min path: {}".format(minimum_path))
print("Min distance: {}".format(minimum_steps))
