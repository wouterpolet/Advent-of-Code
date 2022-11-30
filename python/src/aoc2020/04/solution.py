req = ['byr', 'iyr', 'eyr', 'hgt', 'hcl', 'ecl', 'pid']

with open("input.txt", "r") as f:
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
            if pair.split(":")[0] in req:
                password.append(pair)
    if len(password) == len(req):
        count += 1
    print(count)
