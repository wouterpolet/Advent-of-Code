import numpy as np

def row_adjacent_tile(row, tile):
    return can_be_adjacent(row, [tile[0], tile[-1], tile.T[0], tile.T[-1]])

def can_be_adjacent(row, rows):
    for r in rows:
        if np.array_equal(row, r) or np.array_equal(row, np.flip(r)):
            return True
    return False

def orientations(tile):
    possible = []
    curr = tile
    for i in range(4):
        possible.append(curr)
        possible.append(np.fliplr(curr))
        curr = np.rot90(curr)
    return possible

def get_right_tile(tile, adjs, tiles):
    for adj in adjs:
        if row_adjacent_tile(tile.T[-1], tiles[adj]):
            return adj

def get_orien_right(tile, left):
    for orien in orientations(tile):
        if np.array_equal(left.T[-1], orien.T[0]):
            return orien

def get_orien_bottom(tile, up):
    for orien in orientations(tile):
        if np.array_equal(up[-1], orien[0]):
            return orien

def determine_left(tile, adj1, adj2):
    for orien in orientations(tile):
        if (row_adjacent_tile(orien[-1], adj1) and row_adjacent_tile(orien.T[-1], adj2)) \
                or (row_adjacent_tile(orien[-1], adj2) and row_adjacent_tile(orien.T[-1], adj1)):
            return orien

def check_monster(grid, monster):
    for (i, j) in monster:
        if grid[i][j] != '#':
            return False
    return True

def find_monster(grid, monster):
    count = 0
    v_len = max([x for (x, _) in monster]) + 1
    h_len = max([x for (_, x) in monster]) + 1
    for i in range(len(grid) - v_len):
        for j in range(len(grid[0]) - h_len):
            if check_monster(grid[i:i+v_len, j:j+h_len], monster):
                count += 1
    return count


# with open('test.txt', 'r') as f:
with open('input.txt', 'r') as f:
    data = [x.split('\n') for x in f.read().split('\n\n')]
    tiles = {}
    for tile in data:
        id = int(tile[0].split(' ')[1][:-1])
        tiles[id] = np.asarray([list(x) for x in tile[1:]])
    adj = {}
    row_len = int(np.sqrt(len(tiles)))
    tile_len = len(list(tiles.values())[0])
    for id, tile in tiles.items():
        a = []
        for o_id, o_tile in tiles.items():
            if id != o_id:
                if can_be_adjacent(tile[0], [o_tile[0], o_tile[-1], o_tile.T[0], o_tile.T[-1]]):
                    a.append(o_id)
                if can_be_adjacent(tile[-1], [o_tile[0], o_tile[-1], o_tile.T[0], o_tile.T[-1]]):
                    a.append(o_id)
                if can_be_adjacent(tile.T[-1], [o_tile[0], o_tile[-1], o_tile.T[0], o_tile.T[-1]]):
                    a.append(o_id)
                if can_be_adjacent(tile.T[0], [o_tile[0], o_tile[-1], o_tile.T[0], o_tile.T[-1]]):
                    a.append(o_id)
        adj[id] = a

    left_top = -1
    for id, a in adj.items():
        if len(a) == 2:
            left_top = id
            break

    grid = []
    placed = {}
    left_id = left_top
    left = determine_left(tiles[left_top], tiles[adj[left_top][0]], tiles[adj[left_top][1]])

    row = [left]
    placed[left_top] = left
    current_id = get_right_tile(left, [x for x in adj[left_top] if x not in placed.keys()], tiles)
    current = get_orien_right(tiles[current_id], left)

    while(current is not None):
        row.append(current)
        placed[current_id] = current
        # Go right
        if len(row) < row_len:
            current_id = get_right_tile(current, [x for x in adj[current_id] if x not in placed.keys()], tiles)
            current = get_orien_right(tiles[current_id], current)
        # Go down
        else:
            grid.append(row)
            row = []
            if len(grid) < row_len:
                filtered = [x for x in adj[left_id] if x not in placed.keys()]
                if len(filtered) > 1:
                    print('OH NO YIKES')
                current_id = filtered[0]
                left_id = filtered[0]
                current = get_orien_bottom(tiles[current_id], left)
                left = current
            else:
                current = None


    for i in range(len(grid)):
        for j in range(len(grid)):
            grid[i][j] = grid[i][j][1:-1,1:-1]

    tile_len -= 2
    total = np.empty((len(grid) * tile_len, len(grid) * tile_len), dtype=object)
    for a in range(tile_len):
        for b in range(tile_len):
            for i in range(len(grid)):
                for j in range(len(grid)):
                    total[i * tile_len + a, j * tile_len + b] = grid[i][j][a][b]


    monster = """
                  # 
#    ##    ##    ###
 #  #  #  #  #  #   
"""

    monster = np.asarray([x for x in monster.splitlines() if x != ''])
    m = []
    for i in range(len(monster)):
        for j in range(len(monster[0])):
            if monster[i][j] == '#':
                m.append((i,j))

    count = -1
    for orien in orientations(total):
        monsters = find_monster(orien, m)
        if monsters > 0:
            count = monsters
            break

    print(count)

    answer = np.count_nonzero(total == '#') - monsters * len(m)

    print(answer)

