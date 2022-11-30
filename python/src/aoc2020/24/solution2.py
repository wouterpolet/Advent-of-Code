def count_neighbours(x, y, tiles):
    c = 0
    dirs = [[-2, 0], [2, 0], [-1, -1], [1, 1], [-1, 1], [1, -1]]
    for dir in dirs:
        coor = (x + dir[0], y + dir[1])
        if coor in tiles and tiles[coor]:
            c += 1
    return c


# with open('test.txt', 'r') as f:
with open('input.txt', 'r') as f:
    tiles = {}
    instructions = [x.rstrip() for x in f.readlines()]
    for instr in instructions:
        i = 0
        x = 0
        y = 0
        while i < len(instr):
            down = 0
            right = 0
            if instr[i] == 'n' or instr[i] == 's':
                down = -1 if instr[i] == 'n' else 1
                right = 1 if instr[i + 1] == 'e' else -1
                i += 2
            else:
                right = 2 if instr[i] == 'e' else -2
                i += 1
            x += right
            y += down
        if (x, y) in tiles:
            tiles[(x, y)] = not tiles[(x, y)]
        else:
            tiles[(x, y)] = True


    for i in range(100):
        c = 0
        new_tiles = tiles.copy()
        for (x, y) in new_tiles.keys():
            dirs = [[-2, 0], [2, 0], [-1, -1], [1, 1], [-1, 1], [1, -1]]
            for dir in dirs:
                coor = (x + dir[0], y + dir[1])
                if coor not in tiles:
                    tiles[coor] = False

        new_tiles = tiles.copy()
        for (x, y), black in tiles.items():
            neighbours = count_neighbours(x, y, tiles)
            if black and (neighbours == 0 or neighbours > 2):
                new_tiles[(x, y)] = False
            elif not black and neighbours == 2:
                new_tiles[(x, y)] = True
        tiles = new_tiles
        c = 0
        for tile in tiles.values():
            if tile:
                c += 1
        print("Day {}, count: {}".format(i+1, c))


