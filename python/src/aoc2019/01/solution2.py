total = 0
added = 0

with open("input.txt", "r") as f:
    for line in f:
        added = int(int(line.rstrip()) / 3) - 2
        while (added > 0):
            total += added
            added = int(added / 3) - 2

print(total)


