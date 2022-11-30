with open('input.txt', 'r') as f:
    mem = {}
    mask = {}
    for l in f.readlines():
        l = l.rstrip()
        if l[0:4] == 'mask':
            mask_s = list(l.split(' = ')[1])
            for i in range(len(mask_s)):
                if mask_s[len(mask_s) - i - 1] != 'X':
                    mask[i] = int(mask_s[len(mask_s) - i - 1])
        else:
            loc = int(l[4:].split(']')[0])
            arg = int(l.split(' = ')[1])
            binary = "{0:b}".format(arg)
            binary = '0' * (max(mask.keys()) - len(binary) + 1) + binary
            binary = list(binary)
            for i, v in mask.items():
                binary[len(binary) - i - 1] = str(v)
            binary = ''.join(binary)
            result = int(binary, 2)
            mem[loc] = result
    print(sum(mem.values()))
