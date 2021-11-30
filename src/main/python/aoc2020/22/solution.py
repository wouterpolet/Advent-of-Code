# with open('test.txt', 'r') as f:
with open('input.txt', 'r') as f:
    data = f.read()
    players = [[int(y) for y in x.split('\n')[1:] if y != ''] for x in data.split('\n\n')]

    p1 = players[0]
    p2 = players[1]
    while len(players[0]) != 0 and len(players[1]) != 0:
        left = p1.pop(0)
        right = p2.pop(0)
        if left > right:
            p1.append(left)
            p1.append(right)
        else:
            p2.append(right)
            p2.append(left)
        print(p1)
        print(p2)
    p = None
    if len(p1) > 0:
        p = p1
    else:
        p = p2

    print(p)
    count = 0
    index = 1
    for i in reversed(p):
        print(i)
        count += index * i
        index += 1

    print(count)
