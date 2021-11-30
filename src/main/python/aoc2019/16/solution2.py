import numpy as np
import math

with open("input.txt", "r") as f:
    line = f.readlines()[0].rstrip()
    data = [int(x) for x in line]


pattern = np.asarray([0, 1, 0, -1])

offset = 0
for i in range(7):
    offset += 10**i * data[6 - i]
print("Result offset: {}".format(offset))

current_list = np.tile(data, 10000)[offset:]
length = len(current_list)
print("Done current_list")

new_list = []

for phase in range(100):
    for index in range(1, length):
        current_pos = length - 1 - index
        current_list[current_pos] += current_list[current_pos + 1]
    for index in range(length):
        current_list[index] = abs(current_list[index]) % 10
    if phase % 10 == 0:
        print("Phase: {}".format(phase))

for el in current_list:
    print(el, end="")
print("")

result = 0
for i in range(8):
    result += 10**i * current_list[7 - i]
print("Result: {}".format(result))




