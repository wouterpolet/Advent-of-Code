def magic(move, m):
    count = 0
    p = [0, 0]
    while (p[0] < len(m)):
        if (m[p[0]][p[1] % len(m[0])] == '#'):
            count += 1
        p[0] += move[0]
        p[1] += move[1]
    return count

with open("input.txt", "r") as f:
    result = 1
    m = list(map(lambda x: x.rstrip(), f.readlines()))
    result *= magic([1, 1], m)
    result *= magic([1, 3], m)
    result *= magic([1, 5], m)
    result *= magic([1, 7], m)
    result *= magic([2, 1], m)
    print(result)
