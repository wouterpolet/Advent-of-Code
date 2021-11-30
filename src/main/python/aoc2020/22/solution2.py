import sys

sys.setrecursionlimit(10**6)

def play(p1, p2, acc):
    key = (tuple(p1), tuple(p2))
    if key in acc:
        return p1, p2
    if len(p1) == 0 or len(p2) == 0:
        return p1, p2

    acc.add(key)

    left = p1.pop(0)
    right = p2.pop(0)

    if len(p1) >= left and len(p2) >= right:
        res1, res2 = play(p1[:left].copy(), p2[:right].copy(), acc.copy())
        if len(res1) > 0:
            p1.append(left)
            p1.append(right)
            return play(p1.copy(), p2.copy(), acc)
        else:
            p2.append(right)
            p2.append(left)
            return play(p1.copy(), p2.copy(), acc)
    else:
        if left > right:
            p1.append(left)
            p1.append(right)
            return play(p1.copy(), p2.copy(), acc)
        else:
            p2.append(right)
            p2.append(left)
            return play(p1.copy(), p2.copy(), acc)



# with open('test2.txt', 'r') as f:
with open('input.txt', 'r') as f:
    data = f.read()
    players = [[int(y) for y in x.split('\n')[1:] if y != ''] for x in data.split('\n\n')]

    p1 = players[0]
    p2 = players[1]

    p1, p2 = play(p1, p2, set())
    p = p1 if len(p1) > 0 else p2
    print(p)

    count = 0
    index = 1
    for i in reversed(p):
        count += index * i
        index += 1

    print(count)
