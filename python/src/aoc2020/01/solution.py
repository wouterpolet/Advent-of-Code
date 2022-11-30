with open("input.txt", "r") as f:
    expenses = []
    for line in f:
        expenses.append(int(line.rstrip()))
    for i in range(len(expenses)):
        for j in range(len(expenses)):
            if expenses[i] + expenses[j] == 2020:
                print("{} and {}: result = {}".format(expenses[i], expenses[j], expenses[i] * expenses[j]))
