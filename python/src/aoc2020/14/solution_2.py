with open('input.txt', 'r') as f:
    mem = {}
    mask = ''
    for l in f.readlines():
        l = l.rstrip()
        if l[0:4] == 'mask':
            mask = l.split(' = ')[1]
        else:
            loc = int(l[4:].split(']')[0])
            arg = int(l.split(' = ')[1])
            binary = "{0:b}".format(arg)
            binary = '0' * (36 - len(binary)) + binary
            res = [b if m == 'X' else m for (b, m) in zip(binary, mask)]
            res = ''.join(res)
            result = int(res, 2)
            mem[loc] = result
    print(sum(mem.values()))
