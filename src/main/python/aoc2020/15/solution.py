with open('input.txt', 'r') as f:
# with open('test.txt', 'r') as f:
    next_num = 0
    start = [int(x) for x in f.readline().split(',')]
    prev = start[-1]
    mem = {}
    for i in range(2020):
        if i < len(start):
            mem[start[i]] = [i]
        else:
            current = -1
            if len(mem[prev]) > 1:
                current = mem[prev][-1] - mem[prev][-2]
            else:
                current = 0
            if current in mem:
                mem[current].append(i)
            else:
                mem[current] = [i]
            prev = current
    print(prev)