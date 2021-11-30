def get_amount_of_neighbours(x, y, mazes, level):
    maze = mazes[level]
    count = 0
    if x > 0:
        if maze[y][x-1] == '#':
            count += 1
        elif maze[y][x-1] == "?" and level + 1 in mazes:
            inner = mazes[level + 1]
            for i in range(len(maze)):
                if inner[i][len(maze) - 1] == "#":
                    count += 1
    elif level - 1 in mazes:
        if mazes[level-1][2][1] == "#":
            count += 1
    if y > 0:
        if maze[y-1][x] == '#':
            count += 1
        elif maze[y-1][x] == "?" and level + 1 in mazes:
            inner = mazes[level + 1]
            for i in range(len(maze)):
                if inner[len(maze) - 1][i] == "#":
                    count += 1
    elif level - 1 in mazes:
        if mazes[level-1][1][2] == "#":
            count += 1
    if x < len(maze[0]) - 1:
        if maze[y][x+1] == '#':
            count += 1
        elif maze[y][x+1] == "?" and level + 1 in mazes:
            inner = mazes[level + 1]
            for i in range(len(maze)):
                if inner[i][0] == "#":
                    count += 1
    elif level - 1 in mazes:
        if mazes[level-1][2][3] == "#":
            count += 1
    if y < len(maze) - 1:
        if maze[y+1][x] == '#':
            count += 1
        elif maze[y+1][x] == "?" and level + 1 in mazes:
            inner = mazes[level + 1]
            for i in range(len(maze)):
                if inner[0][i] == "#":
                    count += 1
    elif level - 1 in mazes:
        if mazes[level-1][3][2] == "#":
            count += 1
    return count


def new_generation(mazes, level):
    maze = mazes[level]
    result = [line.copy() for line in maze]
    for y in range(len(maze)):
        for x in range(len(maze[y])):
            adjacent = get_amount_of_neighbours(x, y, mazes, level)
            if adjacent != 1 and maze[y][x] == "#":
                result[y][x] = "."
            if (adjacent == 1 or adjacent == 2) and maze[y][x] == ".":
                result[y][x] = "#"
    return result


def count_bugs(maze):
    count = 0
    for y in range(len(maze)):
        for x in range(len(maze[y])):
            if maze[y][x] == "#":
                count += 1
    return count


with open("input2.txt", "r") as f:
    grid = [list(line.rstrip()) for line in f.readlines()]

periods = 200
levels = 2
empty = [["." for j in range(5)] for i in range(5)]
empty[2][2] = "?"
grids = {
    -1: [line.copy() for line in empty],
    0: grid,
    1: [line.copy() for line in empty]
}

for i in range(200):
    new_dict = {}
    for level in range(levels):
        if level != 0:
            new_dict[-level] = new_generation(grids, -level)
            new_dict[level] = new_generation(grids, level)
        else:
            new_dict[level] = new_generation(grids, level)
    new_dict[-levels] = [line.copy() for line in empty]
    new_dict[levels] = [line.copy() for line in empty]
    grids = new_dict
    levels += 1

num_of_bugs = 0
for (_, grid) in grids.items():
    num_of_bugs += count_bugs(grid)

print("Total amount of bugs: {}".format(num_of_bugs))

