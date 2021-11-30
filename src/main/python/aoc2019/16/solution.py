
with open("input.txt", "r") as f:
    line = f.readlines()[0].rstrip()
    data = [int(x) for x in line]

pattern = [0, 1, 0, -1]

new_list = []
current_list = data

for pattern_amount in range(100):
    new_list.clear()
    for index in range(len(current_list)):
        result = 0
        for i in range(len(current_list)):
            pattern_index = int((i + 1) / (index + 1)) % len(pattern)
            result += pattern[pattern_index] * current_list[i]
        new_list.append(abs(result) % 10)
    current_list = new_list.copy()

for el in current_list:
    print(el, end="")
print("")
