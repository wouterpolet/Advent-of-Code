def find_upper(amount, start, mapping):
    res = amount
    if len(mapping[start]) == 0:
        return amount
    for (b, a) in mapping[start]:
        res += find_upper(amount * a, b, mapping)
    return res

with open('input.txt', 'r') as f:
    mapping = {}
    for line in f.readlines():
        source = line.rstrip().split(' bags contain')[0]
        target = line.rstrip().split('bags contain ')[1].split(', ')
        if not source in mapping:
            mapping[source] = []
            for bag in target:
                b = ' '.join(bag.split(' ')[1:3])
                if b != 'other bags.':
                    a = int(bag.split(' ')[0])
                    mapping[source].append((b, a))
    res = find_upper(1, 'shiny gold', mapping) - 1
    print(res)

