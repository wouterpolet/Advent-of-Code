# with open('test.txt', 'r') as f:
with open('input.txt', 'r') as f:
    cups = [int(x) for x in list(f.readline())]
    c = 0
    mini = min(cups)
    maxi = max(cups)
    for i in range(10000000):
        # print(cups)
        picked = []
        for j in range(3):
            picked.append(cups[(c+j+1) % len(cups)])
        k = 1
        if cups[c] - k < mini:
            k = cups[c] - maxi
        while cups[c] - k in picked:
            k += 1
            if cups[c] - k < mini:
                k = cups[c] - maxi
        dest = cups.index(cups[c] - k)

        z = (c+1)%len(cups)
        while z != (dest + 1) % len(cups):
            cups[z] = cups[(z+len(picked))%len(cups)]
            z = (z + 1) % len(cups)

        dest -= len(picked) - 1
        added = 0
        while added != len(picked):
            cups[(dest + added) % len(cups)] = picked[added]
            added += 1

        c = (c + 1) % len(cups)

    start = cups.index(1)
    point = start + 1
    res = []
    while point != start:
        res.append(cups[point])
        point = (point + 1) % len(cups)
    print(''.join([str(x) for x in res]))