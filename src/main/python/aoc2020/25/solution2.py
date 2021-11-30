def find_loop(key, s_number):
    l_size = 0
    v = 1
    while v != key:
        v *= s_number
        v %= 20201227
        l_size += 1
    return l_size

def transform(s_number, l_size):
    v = 1
    for i in range(l_size):
        v *= s_number
        v %= 20201227
    return v

# with open('test.txt', 'r') as f:
with open('input.txt', 'r') as f:
    v = 1
    c_key = int(f.readline())
    d_key = int(f.readline())
    c_loop = find_loop(c_key, 7)
    d_loop = find_loop(d_key, 7)

    e_key = transform(d_key, c_loop)
    print(e_key)
