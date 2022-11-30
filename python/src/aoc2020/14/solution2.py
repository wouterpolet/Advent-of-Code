def all_locs(loc, acc):
    if loc.count('X') == 0:
        acc.append(loc)
    i = 0
    for c in loc:
        if c == 'X':
            new_l_0 = loc.copy()
            new_l_1 = loc.copy()
            new_l_0[i] = '0'
            new_l_1[i] = '1'
            all_locs(new_l_0, acc)
            all_locs(new_l_1, acc)
        i += 1

def get_addr(addr_mask):
    if "X" in addr_mask:
        for r in ("0", "1"):
            for addr in get_addr(addr_mask.replace("X", r, 1)):
                yield addr
    else:
        yield addr_mask

with open('input.txt', 'r') as f:
    mem = {}
    mask = ''
    for l in f.readlines():
        print('At op: '+ l)
        l = l.rstrip()
        if l[0:4] == 'mask':
            mask = l.split(' = ')[1]
        else:
            loc = "{0:b}".format(int(l[4:].split(']')[0]))
            loc = "0" * (36 - len(loc)) + loc
            loc = ''.join([o if m == '0' else m for (o, m) in zip(loc, mask)])
            locs = get_addr(loc)
            #locs = [int(''.join(x), 2) for x in locs]

            arg = int(l.split(' = ')[1])

            for loc in locs:
                mem[loc] = arg
    print(sum(mem.values()))
