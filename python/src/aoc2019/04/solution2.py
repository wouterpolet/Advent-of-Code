def check(num):
    num_str = str(num)
    prev_digit = -1
    double = False
    sequence_count = 1
    for j in range(6):
        digit = int(num_str[j])
        if digit == prev_digit:
            sequence_count += 1
        elif digit < prev_digit:
            return False
        else:
            if sequence_count == 2:
                double = True
            sequence_count = 1
        prev_digit = digit
    if sequence_count == 2:
        double = True
    return double


count = 0
for i in range(387638, 919123):
    if check(i):
        count += 1

print("123444 = {}".format(check(123444)))
print("Total count: {}".format(count))

