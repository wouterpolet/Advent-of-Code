import re


size = 10
deck = [x for x in range(size)]
print(deck)

with open("test4.txt", "r") as f:
    count = 0
    for line in f.readlines():
        line = line.rstrip()
        if line == "deal into new stack":
            deck.reverse()
        elif re.match(r"cut -?\d+", line):
            num = int(re.findall(r"-?\d+", line)[0])
            deck = deck[num:] + deck[:num]
        elif re.match(r"deal with increment \d+", line):
            num = int(re.findall(r"-?\d+", line)[0])
            new_deck = deck.copy()
            for i in range(size):
                index = (i * num) % size
                new_deck[index] = deck[i]
            deck = new_deck
        print(line + ": ", end="")
        print(deck)
        count += 1
        if count % 10 == 0:
            print("Done {} operations".format(count))

answer = -1
for i in range(size):
    if deck[i] == 2019:
        answer = i
        break

print("The index with y2019 is {}".format(answer))
