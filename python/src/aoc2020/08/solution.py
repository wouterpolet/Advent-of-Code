with open('input.txt', 'r') as f:
    done = []
    acc = 0
    p = 0
    i = [line.rstrip() for line in f.readlines()]
    while p not in done:
        done.append(p)
        op, a = i[p].split(' ')
        arg = int(a[1:])
        if a[0] == '-':
            arg *= -1
        if op == 'acc':
            acc += arg
        elif op == 'jmp':
            p += arg - 1
        p += 1

    print(acc)