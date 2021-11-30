with open('input.txt', 'r') as f:
    current = 0
    res = 0
    d = list(map(lambda x: int(x.rstrip()), f.readlines()))
    rated = max(d) + 3
    ones = 0
    threes = 0
    d.append(rated)
    while len(d) > 0:
        next = min(d)
        if next - current == 1:
            ones += 1
        elif next - current == 3:
            threes += 1
        current = next
        d.remove(next)
    print(ones * threes)
