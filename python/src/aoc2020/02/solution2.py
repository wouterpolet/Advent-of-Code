with open("input.txt", "r") as f:
    count_valid = 0
    for line in f.readlines():
        r, letter, seq = line.split(' ')
        low, high = r.split('-')
        letter = letter[0]
        if (seq[int(low)-1] == letter) ^ (seq[int(high)-1] == letter):
            count_valid += 1
    print(str(count_valid))
