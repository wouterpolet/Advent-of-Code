def compute_gcd(x, y):

    while (y):
        x, y = y, x % y
    return x

def lcm(x, y):
    lcm = (x*y)//compute_gcd(x,y)
    return lcm


def find_offset(p, inc_p, b):
    n_off = ((p // b) + 1) * b
    p_acc = p
    while (n_off - 1) % p_acc != 0:
        n_off += ((inc_p // b) + 1) * b
        p_acc += ((n_off - p_acc - 1) // inc_p) * inc_p

    return n_off

with open('input.txt', 'r') as f:
    _ = int(f.readline())
    bs = [int(x) if x != 'x' else -1 for x in f.readline().rstrip().split(',')]

    # p_off = bs[0] # find_offset(bs[0], bs[0], bs[1], bs[1])
    # p_inc = bs[0] # lcm(bs[0], bs[1])
    # for i in range(1, len(bs)):
    #     if bs[i] < 0:
    #         p_off += 1
    #     else:
    #         p_off = find_offset(p_off, p_inc, bs[i])
    #         p_inc = lcm(p_inc, bs[i])

    start = bs[0]
    inc = bs[0]
    for i in range(1, len(bs)):
        if bs[i] > 0:
            while (start + i) % bs[i] != 0:
                start += inc
            inc = lcm(inc, bs[i])

    print(start)



