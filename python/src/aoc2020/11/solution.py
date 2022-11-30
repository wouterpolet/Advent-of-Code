with open('input.txt', 'r') as f:
    grid = []
    for l in f.readlines():
        grid.append(list(l.rstrip()))

    def update():
        new_grid = [line.copy() for line in grid]
        c = False
        for i in range(len(grid)):
            for j in range(len(grid[0])):
                around = ''
                if i > 0:
                    around += grid[i-1][j]
                    if j > 0:
                        around += grid[i-1][j-1]
                if j > 0:
                    around += grid[i][j-1]
                if i < len(grid) - 1:
                    around += grid[i+1][j]
                    if j < len(grid[0]) - 1:
                        around += grid[i+1][j+1]
                if j < len(grid[0]) - 1:
                    around += grid[i][j+1]
                if i > 0 and j <  len(grid[0]) - 1:
                    around += grid[i-1][j+1]
                if i < len(grid) - 1 and j > 0:
                    around += grid[i+1][j-1]
                if around.count('#') == 0 and grid[i][j] == 'L':
                    new_grid[i][j] = '#'
                    c = True
                elif around.count('#') >= 4 and grid[i][j] == '#':
                    new_grid[i][j] = 'L'
                    c = True
        return c, new_grid

    going = True
    while(going):
        c, n_g = update()
        grid = n_g
        going = c

    count = 0
    for line in grid:
        for c in line:
            if c == '#':
                count += 1

    print(count)

