def get_dirs(tot, acc, n):
    if n <= 0:
        tot.append(tuple(acc))
        return
    new_acc = acc.copy()
    new_acc.append(-1)
    new_acc1 = acc.copy()
    new_acc1.append(0)
    new_acc2 = acc.copy()
    new_acc2.append(1)
    get_dirs(tot, new_acc, n - 1)
    get_dirs(tot, new_acc1, n - 1)
    get_dirs(tot, new_acc2, n - 1)

dirs = []
get_dirs(dirs, [], 4)
dirs.remove((0,0,0,0))

def get_neighbour(grid, pos, dir):
    new_pos = (pos[0] + dir[0], pos[1] + dir[1], pos[2] + dir[2], pos[3] + dir[3])
    if new_pos in grid and grid[new_pos] == '#':
        return 1
    return 0

def neighbour(grid, pos, dir):
    new_pos = (pos[0] + dir[0], pos[1] + dir[1], pos[2] + dir[2], pos[3] +  dir[3])
    if new_pos not in grid:
        grid[new_pos] = '.'

def add_neighbours(grid):
    k = list(grid.keys())
    for pos in k:
        for dir in dirs:
            neighbour(grid, pos, dir)

def get_neighbours(grid, pos):
    count = 0
    for dir in dirs:
        count += get_neighbour(grid, pos, dir)
    return count

# with open('test.txt', 'r') as f:
with open('input.txt', 'r') as f:
    grid = {}
    data = [list(l.rstrip()) for l in f.readlines()]
    for y in range(len(data)):
        for x in range(len(data[0])):
            grid[(x,y,0,0)] = data[y][x]
    for i in range(6):
        add_neighbours(grid)
        new_grid = grid.copy()
        for pos, state in grid.items():
            if state == '#':
                n = get_neighbours(grid, pos)
                if n != 2 and n != 3:
                    new_grid[pos] = '.'
            else:
                if get_neighbours(grid, pos) == 3:
                    new_grid[pos] = '#'
        grid = new_grid
    count = 0
    for value in grid.values():
        if value == '#':
            count += 1
    print(count)
