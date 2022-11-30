pre = 25

def find_num(num, n):
    print(num)
    for i in range(len(n)):
        s = 0
        j = 0
        while s < num:
            s += n[i+j]
            j += 1
        if s == num:
            return min(n[i:i+j]) + max(n[i:i+j])

with open('input.txt', 'r') as f:
    n = []
    total = [int(line.rstrip()) for line in open('input.txt', 'r').readlines()]
    print(sum(total[516:533]))
    count = 0
    for line in f.readlines():
        if count < pre:
            n.append(int(line.rstrip()))
            count += 1
        else:
            combi_exists = False
            for i in range(pre):
                for j in range(pre):
                    if i != j:
                        if n[-pre:][i] + n[-pre:][j] == int(line.rstrip()):
                            combi_exists = True
            if not combi_exists:
                print(str(find_num(int(line.rstrip()), total)))
                break
            else:
                n.append(int(line.rstrip()))
