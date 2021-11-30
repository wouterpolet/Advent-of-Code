# with open('test1.txt', 'r') as f:
with open('input.txt', 'r') as f:
    ranges = {}
    data = f.read().split('\n\n')

    for rule in data[0].split('\n'):
        ranges[rule.split(': ')[0]] = [[int(a) for a in x.split('-')] for x in rule.split(': ')[1].split(' or ')]

    count = 0
    tickets = data[2].split('\n')[1:]
    to_remove = []
    for ticket in data[2].split('\n')[1:]:
        for val in ticket.rstrip().split(','):
            val = int(val)
            valid = False
            for r in ranges.values():
                if (val >= r[0][0] and val <= r[0][1]) or (val >= r[1][0] and val <= r[1][1]):
                    valid = True
                    break
            if not valid and ticket not in to_remove:
                to_remove.append(ticket)
    for t in to_remove:
        tickets.remove(t)

    tickets = [[int(x) for x in t.split(',')] for t in tickets]

    my_ticket = [int(x) for x in data[1].split('\n')[1].split(',')]
    tickets.append(my_ticket)

    possible_mapping = []
    for i in range(len(data[0].split('\n'))):
        possible_mapping.append(list(ranges.copy()))

    for t in tickets:
        i = 0
        while i < len(possible_mapping):
            keys = possible_mapping[i]
            j = 0
            while j < len(keys):
                if t[i] < ranges[keys[j]][0][0] or (t[i] > ranges[keys[j]][0][1] and t[i] < ranges[keys[j]][1][0]) or t[i] > ranges[keys[j]][1][1]:
                    keys.pop(j)
                else:
                    j += 1
            i += 1


    reserved = []
    def not_done(mapping):
        for m in mapping:
            if len(m) > 1:
                return True
        return False

    while not_done(possible_mapping):
        for m in possible_mapping:
            if len(m) == 1:
                reserved.append(m[0])
        for m in possible_mapping:
            if len(m) > 1:
                for r in reserved:
                    if r in m:
                        m.remove(r)
        reserved = []

    i = 0
    indices = []
    for m in possible_mapping:
        if 'departure' in m[0]:
            indices.append(i)
        i += 1

    res = 1
    for i in indices:
        res *= my_ticket[i]
    print(res)

