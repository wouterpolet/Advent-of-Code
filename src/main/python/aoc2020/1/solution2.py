with open("input.txt", "r") as f:
    expenses = []
    for line in f:
        expenses.append(int(line.rstrip()))
    for i in expenses:
        for j in expenses:
            for z in expenses:
                if i + j + z == 2020:
                    print("{}, {}, and {}: result = {}".format(i, j, z, i * j * z))
