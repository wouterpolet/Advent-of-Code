def valid(exp, rules, terms):
    if len(exp) == 0 or len(terms) == 0:
        return len(exp) == len(terms)
    if isinstance(terms[0], str):
        return exp[0] == terms[0] and valid(exp[1:], rules, terms[1:])
    for pos in rules[terms[0]]:
        new_terms = pos.copy()
        for t in terms[1:]:
            new_terms.append(t)
        if valid(exp, rules, new_terms):
            return True
    return False



# with open('test2.txt', 'r') as f:
with open('input2.txt', 'r') as f:
    data = f.read().split('\n\n')
    rules = {}
    for l in data[0].splitlines():
        rules[int(l.split(': ')[0])] = [[int(y) if "\"" not in y else y[1] for y in x.split(' ')] for x in l.split(': ')[1].split(' | ')]
    count = 0
    for exp in data[1].splitlines():
        if valid(exp, rules, [0]):
            print(exp)
            count += 1
    print(count)
