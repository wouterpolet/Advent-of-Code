with open('input.txt', 'r') as f:
    d = sorted([int(x.rstrip()) for x in f.readlines()])
    d.insert(0, 0)
    d.append(d[-1] + 3)
    mem = [1, 1, 1]
    for i in range(1, len(d)):
        res = 0
        if i >= 1 and d[i] - d[i-1] <= 3:
            res += mem[0]
            if i >= 2 and d[i] - d[i-2] <= 3:
                res += mem[1]
                if i >= 3 and d[i] - d[i-3] <= 3:
                    res += mem[2]
        mem[2] = mem[1]
        mem[1] = mem[0]
        mem[0] = res
        print(mem)
    print(mem[0])
