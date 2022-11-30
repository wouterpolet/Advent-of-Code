with open("input.txt", "r") as f:
    count = 0
    p = [0, 0]
    m = list(map(lambda x: x.rstrip(), f.readlines()))
    while (p[0] < len(m)):
        if (m[p[0]][p[1] % len(m[0])] == '#'):
            count += 1
        p[0] += 1
        p[1] += 3
    print(count)