def check(num):
    num_str = str(num)
    prev_digit = -1
    double = False
    for j in range(6):
        digit = int(num_str[j])
        if digit == prev_digit:
            double = True
        if digit < prev_digit:
            return False
        prev_digit = digit
    return double


count = 0
for i in range(387638, 919123):
    if check(i):
        count += 1

print("Total count: {}".format(count))

