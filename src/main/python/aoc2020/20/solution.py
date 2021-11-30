import numpy as np

def can_be_adjacent(row, rows):
    for r in rows:
        if np.array_equal(row, r) or np.array_equal(row, np.flip(r)):
            return True


# with open('test.txt', 'r') as f:
with open('input.txt', 'r') as f:
    data = [x.split('\n') for x in f.read().split('\n\n')]
    tiles = {}
    for tile in data:
        id = int(tile[0].split(' ')[1][:-1])
        tiles[id] = np.asarray([list(x) for x in tile[1:]])
    corners = []
    for id, tile in tiles.items():
        l = False
        r = False
        t = False
        b = False
        for o_id, o_tile in tiles.items():
            if id != o_id:
                if not b and can_be_adjacent(tile[0], [o_tile[0], o_tile[-1], o_tile.T[0], o_tile.T[-1]]):
                    b = True
                if not t and can_be_adjacent(tile[-1], [o_tile[0], o_tile[-1], o_tile.T[0], o_tile.T[-1]]):
                    t = True
                if not r and can_be_adjacent(tile.T[-1], [o_tile[0], o_tile[-1], o_tile.T[0], o_tile.T[-1]]):
                    r = True
                if not l and can_be_adjacent(tile.T[0], [o_tile[0], o_tile[-1], o_tile.T[0], o_tile.T[-1]]):
                    l = True
                # if np.array_equal(o_tile[-1], tile[0]):
                #     lb_pos = False
                #     rb_pos = False
                # if np.array_equal(o_tile[0], tile[-1]):
                #     lt_pos = False
                #     rt_pos = False
                # if np.array_equal(o_tile.T[-1], tile.T[0]):
                #     lt_pos = False
                #     lb_pos = False
                # if np.array_equal(o_tile.T[0], tile.T[-1]):
                #     rt_pos = False
                #     rb_pos = False
        if sum([l, r, t, b]) <= 2:
            corners.append(id)

    print(corners)
    res = 1
    for corner in corners:
        res *= corner
    print(res)
