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
    c = 0
    for tile in tiles.values():
        if tile:
            c += 1
    print(c)