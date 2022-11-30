with open('input.txt', 'r') as f:
# with open('test.txt', 'r') as f:
    next_num = 0
    start = [int(x) for x in f.readline().split(',')]
    prev = start[-1]
    mem = {}
    for i in range(30000000):
        if i % 1000000 == 0:
            print('Done {}'.format(i))
        if i < len(start):
            mem[start[i]] = (-1, i)
        else:
            current = -1
            if mem[prev][0] > -1:
                current = mem[prev][1] - mem[prev][0]
            else:
                current = 0
            if current in mem:
                mem[current] = (mem[current][1], i)
            else:
                mem[current] = (-1, i)
            prev = current
    print(prev)
