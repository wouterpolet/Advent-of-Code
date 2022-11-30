def check_rule8(exp, rules, lengths, acc):
    check42 = valid(exp, rules, 42)
    if check42 >= 0:
        lengths.append(acc + check42)
        check_rule8(exp[check42:], rules, lengths, acc + check42)
    return lengths

    # for i in range(1, len(exp)):
    #     check = valid(exp[:-i], rules, 42)
    #     if check == i:
    #         if i + acc not in lengths:
    #             lengths.append(i + acc)
    #         # check_remainder = valid(exp[-i:], rules, 8)
    #         check_rule8(exp[-i:], rules, lengths, acc + i)


def valid(exp, rules, rule):
    # print("{} with rule {}, which is {}".format(exp, rule, rules[rule]))
    if len(exp) == 0:
        return -1
    if rule == 11:
        for i in range(1, len(exp)):
            check_left = valid(exp[:i], rules, 42)
            if check_left >= 0:
                check_right = valid(exp[i:], rules, 31)
                if check_right >= 0:
                    return check_left + check_right
        for l_size in range(1, len(exp)):
            check_left = valid(exp[:l_size], rules, 42)
            if check_left >= 0:
                for r_size in range(1, len(exp)):
                    check_right = valid(exp[-r_size:], rules, 31)
                    if check_right >= 0:
                        check_middle = valid(exp[l_size:r_size+1], rules, 11)
                        if check_middle >= 0:
                            return check_left + check_middle + check_right
        return -1
    for pos in rules[rule]:
        skip = 0
        res = True
        for term in pos:
            if isinstance(term, int):
                if term == 8:
                    # print("pos with 8: " + str(pos))
                    sizes = check_rule8(exp, rules, [], 0)
                    print("{} for string {}".format(sizes, exp))
                    for size in sizes:
                        if valid(exp[size:], rules, 11) == len(exp) - size:
                            return len(exp)
                else:
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


with open('test2.txt', 'r') as f:
# with open('input2.txt', 'r') as f:
    data = f.read().split('\n\n')
    rules = {}
    for l in data[0].splitlines():
        rules[int(l.split(': ')[0])] = [[int(y) if "\"" not in y else y[1] for y in x.split(' ')] for x in l.split(': ')[1].split(' | ')]
    count = 0
    for exp in data[1].splitlines():
        if valid(exp, rules, 0) == len(exp):
            print(exp)
            count += 1
    print(count)
