def get_amount_of_neighbours(x, y, maze):
    count = 0
    if x > 0:
        if maze[y][x-1] == '#':
            count += 1
    if y > 0:
        if maze[y-1][x] == '#':
            count += 1
    if x < len(maze[0]) - 1:
        if maze[y][x+1] == '#':
            count += 1
    if y < len(maze) - 1:
        if maze[y+1][x] == '#':
            count += 1
    return count


def new_generation(maze):
    result = [line.copy() for line in maze]
    for y in range(len(maze)):
        for x in range(len(maze[y])):
            adjacent = get_amount_of_neighbours(x, y, maze)
            if adjacent != 1 and maze[y][x] == "#":
                result[y][x] = "."
            if (adjacent == 1 or adjacent == 2) and maze[y][x] == ".":
                result[y][x] = "#"
    return result


with open("input.txt", "r") as f:
    grid = [list(line.rstrip()) for line in f.readlines()]

layouts = set()
while tuple([tuple(line) for line in grid]) not in layouts:
    layouts.add(tuple([tuple(line) for line in grid]))
    grid = new_generation(grid)
    print(grid)

biodiversity = 0
for y in range(len(grid)):
    for x in range(len(grid[y])):
        if grid[y][x] == "#":
            biodiversity += 2 ** (y * len(grid[x]) + x)

print("The biodiversity rating is {}".format(biodiversity))
print("Final grid is {}".format(grid))
