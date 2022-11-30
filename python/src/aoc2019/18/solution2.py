import sys

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

done = [0]
def find_optimal(gathered, positions, known, keys, robots, connections):
    if positions in known:
        for (gotten, amount) in known[positions]:
            if gotten == gathered:
                return amount
    # If not known
    min_path_length = sys.maxsize
    if all(key in gathered for key in keys):
        return 0
    for key in keys:
        if key not in gathered:
            robot = robot_to_key(robots, connections, key)
            if all(needed.lower() in gathered for needed in connections[positions[robot], key][1]):
                new_gathered = gathered.copy()
                new_gathered.add(key)
                new_positions = list(positions)
                new_positions[robot] = key
                new_positions = tuple(new_positions)
                result = find_optimal(new_gathered, new_positions, known, keys, robots, connections)
                new_length = result + connections[positions[robot], key][0]
                if new_length < min_path_length:
                    min_path_length = new_length
                    if len(gathered) == 4:
                        print("Current min: {}".format(min_path_length))
    if positions not in known:
        known[positions] = []
    known[positions].append((gathered, min_path_length))
    done[0] += 1
    if done[0] % 10000 == 0:
        print("Known size is {}".format(done[0]))
    return min_path_length


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

known = {}
answer = find_optimal({'0', '1', '2', '3'}, ('0', '1', '2', '3'), known, keys, robots, key_connections)
# print("Known {}".format(known))
print("Minimal amount of steps: {}".format(answer))
print(len(known))
