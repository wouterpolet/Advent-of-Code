def valid(exp, rules, rule):
    for pos in rules[rule]:
        skip = 0
        res = True
        for term in pos:
            if isinstance(term, int):
                check = valid(exp[skip:], rules, term)
                if check >= 0:
                    skip += check
                else:
                    res = False
            else:
                if exp[skip] == term:
                    skip += 1
                else:
                    res = False
        if res:
            return skip
    return -1


# with open('test.txt', 'r') as f:
with open('input.txt', 'r') as f:
    data = f.read().split('\n\n')
    rules = {}
    for l in data[0].splitlines():
        print(l)
        rules[int(l.split(': ')[0])] = [[int(y) if "\"" not in y else y[1] for y in x.split(' ')] for x in l.split(': ')[1].split(' | ')]
    print(rules)
    count = 0
    for exp in data[1].splitlines():
        if valid(exp, rules, 0) == len(exp):
            print(exp)
            count += 1
    print(count)
