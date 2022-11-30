def try_instr(i, p, done, acc, made_change):
    while p < len(i):
        if p in done:
            return None
        done.append(p)
        op, a = i[p].split(' ')
        arg = int(a[1:])
        if a[0] == '-':
            arg *= -1
        if op == 'acc':
            acc += arg
        elif op == 'jmp':
            if not made_change:
                new_i = i.copy()
                new_i[p] = 'nop ' + a
                new_done = done.copy()
                res = try_instr(new_i, p + 1, new_done, acc, True)
                if res != None:
                    return res
            p += arg - 1
        elif op == 'nop':
            if not made_change:
                new_i = i.copy()
                new_i[p] = 'jmp ' + a
                new_done = done.copy()
                res = try_instr(new_i, p + arg, new_done, acc, True)
                if res != None:
                    return res
        p += 1
    return acc


with open('input.txt', 'r') as f:
    i = [line.rstrip() for line in f.readlines()]
    res = try_instr(i, 0, [], 0, False)

    print(res)
