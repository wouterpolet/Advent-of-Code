def check(data):
    count = 0
    r = [0, 128]
    c = [0, 8]
    for line in data:
        if count < 7:
            if line == 'F':
                r[1] = int((r[1] + r[0]) / 2)
            elif line == 'B':
                r[0] = int((r[1] + r[0]) / 2)
        else:
            if line == 'L':
                c[1] = int((c[1] + c[0]) / 2)
            elif line == 'R':
                c[0] = int((c[1] + c[0]) / 2)
        count += 1
    return r[0] * 8 + c[0]

with open("input.txt", "r") as f:
    res = [check(line.rstrip()) for line in f.readlines()]
    for row in range(127 + 1):
        for column in range(7 + 1):
            seatID = row * 8 + column
            if seatID not in res and seatID - 1 in res and seatID + 1 in res:
                print(seatID)
