with open("input.txt", "r") as f:
    count_valid = 0
    for line in f.readlines():
        r, letter, seq = line.split(' ')
        low, high = r.split('-')
        letter = letter[0]
        amount = len(list(filter(lambda x: x == letter, seq)))
        if amount <= int(high) and amount >= int(low):
            count_valid += 1

    print(str(count_valid))


