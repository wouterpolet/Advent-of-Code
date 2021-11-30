def single_params(exp):
    if exp[0] != '(' or exp[-1] != ')':
        return False
    paren_count = 1
    for c in exp[1:]:
        if paren_count == 0:
            return False
        if c == '(':
            paren_count += 1
        elif c == ')':
            paren_count -= 1
    return True


def find_op(exp):
    paren_count = 0
    indices = []
    for i in range(len(exp)):
        if (exp[i] == '*' or exp[i] == '+') and paren_count == 0:
            indices.append(i)
        if exp[i] == '(':
            paren_count += 1
        if exp[i] == ')':
            paren_count -= 1
    return indices

def parse(exp):
    if single_params(exp):
        exp = exp[1:-1]
    indices = find_op(exp)
    if len(indices) > 0:
        res = []
        indices.insert(0, -1)
        indices.append(len(exp))
        i = 1
        while i < len(indices):
            to_parse = exp[indices[i-1]+1:indices[i]]
            res.append(parse(to_parse))
            if indices[i] < len(exp):
                res.append(exp[indices[i]])
            i += 1
        return res
    return int(exp.replace('(','').replace(')',''))


def compute(exp):
    if isinstance(exp, int):
        return exp
    acc = compute(exp[0])
    for i in range(1, len(exp), 2):
        next = compute(exp[i+1])
        if exp[i] == '+':
            acc += next
        elif exp[i] == '*':
            acc *= next
    return acc

# with open('test.txt', 'r') as f:
with open('input.txt', 'r') as f:
    res = 0
    for l in f.readlines():
        terms = ''.join(l.rstrip().split(' '))
        print("doing {}".format(terms))
        exp = parse(terms)
        curr = compute(exp)
        # print(curr)
        res += curr
    print(res)

