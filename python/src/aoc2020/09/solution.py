from y2020.gameboy.gameboy import Gameboy
from y2020.gameboy.terminate import Terminate

with open('input.txt', 'r') as f:
    n = []
    count = 0
    for line in f.readlines():
        if count < 25:
            n.append(int(line.rstrip()))
            count += 1
        else:
            combi_exists = False
            for i in range(25):
                for j in range(25):
                    if i != j:
                        if n[-25:][i] + n[-25:][j] == int(line.rstrip()):
                            combi_exists = True
            if not combi_exists:
                print(line.rstrip())
            else:
                n.append(int(line.rstrip()))
