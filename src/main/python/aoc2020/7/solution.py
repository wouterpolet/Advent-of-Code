def find_upper(acc, start, mapping):
    if start in mapping:
        for n in mapping[start]:
            if n not in acc:
                acc |= {n}
                find_upper(acc, n, mapping)

with open('input.txt', 'r') as f:
    mapping = {}
    for line in f.readlines():
        source = line.rstrip().split(' bags contain')[0]
        target = line.rstrip().split('bags contain ')[1].split(', ')
        for bag in target:
            b = ' '.join(bag.split(' ')[1:3])
            print(b)
            if b != 'other bags.':
                if b not in mapping:
                    mapping[b] = [source]
                else:
                    mapping[b].append(source)
    res = set()
    print(mapping)
    find_upper(res, 'shiny gold', mapping)
    print(res)
    print(len(res))

