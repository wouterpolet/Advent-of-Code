total = 0

with open("input.txt", "r") as f:
    for line in f:
        total += int(int(line.rstrip()) / 3) - 2

print(total)

