import re

req = ['byr', 'iyr', 'eyr', 'hgt', 'hcl', 'ecl', 'pid']

valid = {
    'byr': lambda x: 1920 <= int(x) <= 2002,
    'iyr': lambda x: 2010 <= int(x) <= 2020,
    'eyr': lambda x: 2020 <= int(x) <= 2030,
    'hgt': lambda x: 150 <= int(x[:len(x)-2]) <= 193 if x[len(x)-2:] == 'cm' else 59 <= int(x[:len(x)-2]) <= 76 if x[len(x)-2:] == 'in' else False,
    'hcl': lambda x: x[0] == '#' and re.search('[0-9a-f]*', x[:1]) is not None,
    'ecl': lambda x: x in 'amb blu brn gry grn hzl oth'.split(' '),
    'pid': lambda x: len(x) == 9 and re.search('[0-9]*', x) is not None
}

with open("day04.txt", "r") as f:
    count = 0
    password = None
    for line in f.readlines():
        if line == '\n':
            if len(password) == len(req):
                count += 1
            password = None
            continue
        if password is None:
            password = []
        for pair in line.rstrip().split(' '):
            key, val = pair.split(":")
            if key in req and valid[key](val):
                password.append(pair)
    if len(password) == len(req):
        count += 1
    print(count)
