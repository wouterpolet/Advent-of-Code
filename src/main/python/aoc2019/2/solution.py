index = 0

with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]
    while op[index] != 99:
        if op[index] == 1:
            op[op[index + 3]] = op[op[index + 1]] + op[op[index + 2]]
        elif op[index] == 2:
            op[op[index + 3]] = op[op[index + 1]] * op[op[index + 2]]
        else:
            print("Something went wrong")
        index += 4

    print(op)

