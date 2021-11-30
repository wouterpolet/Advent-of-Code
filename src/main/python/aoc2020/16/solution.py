# with open('test.txt', 'r') as f:
with open('input.txt', 'r') as f:
    ranges = {}
    data = f.read().split('\n\n')

    for rule in data[0].split('\n'):
        ranges[rule.split(': ')[0]] = [[int(a) for a in x.split('-')] for x in rule.split(': ')[1].split(' or ')]

    count = 0
    for ticket in data[2].split('\n')[1:]:
        print(ticket)
        for val in ticket.rstrip().split(','):
            val = int(val)
            valid = False
            for range in ranges.values():
                if (val >= range[0][0] and val <= range[0][1]) or (val >= range[1][0] and val <= range[1][1]):
                    valid = True
                    break
            if not valid:
                count += val

    print(data)
    print(ranges)
    print(count)