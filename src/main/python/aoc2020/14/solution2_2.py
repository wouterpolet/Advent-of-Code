
# 3802286150229
# 3805767744034
# 3871932458169
# 3906793757978
# 3803522125700
# 2735146352906
# 2735146352906

# def match(old, new):
#     current_backup = old.copy()
#     x_count = 0
#     m_count = 0
#     if old.count('.') == len(old):
#         return 0
#     for i in range(len(old)):
#         if old[i] == 'X' and new[i] == 'X':
#             m_count += 1
#             x_count += 1
#         elif old[i] == 'X':
#             if new[i] == '1':
#                 old[i] = '0'
#             else:
#                 old[i] = '1'
#             x_count += 1
#         elif new[i] == 'X':
#             continue
#         elif old[i] != new[i]:
#             for j in range(len(old)):
#                 old[j] = current_backup[j]
#             return 0
#     if m_count == x_count:
#         for i in range(len(old)):
#             old[i] = '.'


    # res = ['X' if x == 'X' and y == 'X' else x if x == y or y == 'X' else y if x == 'X' else '.' if x == '.' or y == '.' else '_' for (x, y) in zip(loc1, loc2)]
    # if res.count('_') > 0:
    #     return 0
    # return 2 ** res.count('X')

with open('test2.txt', 'r') as f:
    mem = []
    mask = ''
    for l in f.readlines():
        l = l.rstrip()
        if l[0:4] == 'mask':
            mask = l.split(' = ')[1]
        else:
            loc = "{0:b}".format(int(l[4:].split(']')[0]))
            loc = "0" * (36 - len(loc)) + loc
            loc = [o if m == '0' else m for (o, m) in zip(loc, mask)]

            arg = int(l.split(' = ')[1])

            i = 0
            while i < len(mem):
                j = 0
                if mem[i][0] == loc:
                    mem.pop(i)
                    i -= 1
                else:
                    new_old = mem[i][0].copy()
                    full = True
                    match = True
                    for (j, o_p, n_p) in zip(range(len(loc)), mem[i][0], loc):
                        if o_p == 'X' and n_p != 'X':
                            if n_p == '1':
                                new_old[j] = '0'
                            else:
                                new_old[j] = '1'
                            full = False
                        elif o_p != n_p and n_p != 'X':
                            match = False
                            break
                    if match:
                        if full:
                            mem.pop(i)
                            i -= 1
                        else:
                            mem[i][0] = new_old
                i += 1

            mem.append([loc, arg])

    count = 0
    for store in mem:
        count += 2 ** store[0].count('X') * store[1]


    print(count)
