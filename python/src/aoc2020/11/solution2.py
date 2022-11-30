def find_seats(grid, i, j):
    adj = 0
    a = i
    while a < len(grid):
        if a != i  and grid[a][j] == '#':
            adj += 1
            break
        if a != i and grid[a][j] == 'L':
            break
        a += 1
    a = i
    while a >= 0:
        if a != i and grid[a][j] == '#':
            adj += 1
            break
        if a != i  and grid[a][j] == 'L':
            break
        a -= 1
    b = j
    while b >= 0:
        if b != j and grid[i][b] == '#':
            adj += 1
            break
        if b != j and grid[i][b] == 'L':
            break
        b -= 1
    b = j
    while b < len(grid[0]):
        if b != j  and grid[i][b] == '#':
            adj += 1
            break
        if b != j and grid[i][b] == 'L':
            break
        b += 1
    a = i
    b = j
    while a < len(grid) and b < len(grid[0]):
        if (a != i or b != j) and grid[a][b] == '#':
            adj += 1
            break
        if (a != i or b != j) and grid[a][b] == 'L':
            break
        a += 1
        b += 1
    a = i
    b = j
    while a < len(grid) and b >= 0:
        if (a != i or b != j) and grid[a][b] == '#':
            adj += 1
            break
        if (a != i or b != j) and grid[a][b] == 'L':
            break
        a += 1
        b -= 1
    a = i
    b = j
    while a >= 0 and b >= 0:
        if (a != i or b != j) and grid[a][b] == '#':
            adj += 1
            break
        if (a != i or b != j) and grid[a][b] == 'L':
            break
        a -= 1
        b -= 1
    a = i
    b = j
    while a >= 0 and b < len(grid[0]):
        if(a != i or b != j) and grid[a][b] == '#':
            adj += 1
            break
        if (a != i or b != j) and grid[a][b] == 'L':
            break
        a -= 1
        b += 1
    return adj




with open('input.txt', 'r') as f:
    grid = []
    for l in f.readlines():
        grid.append(list(l.rstrip()))

    def update():
        new_grid = [line.copy() for line in grid]
        c = False
        for i in range(len(grid)):
            for j in range(len(grid[0])):
                if grid[i][j] != '.':
                    around = find_seats(grid, i, j)
                    if around == 0 and grid[i][j] == 'L':
                        new_grid[i][j] = '#'
                        c = True
                    elif around >= 5 and grid[i][j] == '#':
                        new_grid[i][j] = 'L'
                        c = True
        return c, new_grid

    going = True
    while(going):
        c, n_g = update()
        grid = n_g
        going = c

    print(grid)

    count = 0
    for line in grid:
        for c in line:
            if c == '#':
                count += 1

    print(count)

