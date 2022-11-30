import re
import math

size = 119315717514047
iterations = 101741582076661
start_diff = 113638587256714
step_factor = 108372218693556
start = 0
step = 1

end_start = (start_diff * (1 - pow(step_factor, iterations, size)) * pow(1 - step_factor, -1, size)) % size
end_step = pow(step_factor, iterations, size)
answer = (end_start + 2020 * end_step) % size

print("Final start = {}".format(end_start))
print("Final step = {}".format(end_step))
print("Answer = {}".format(answer))

with open("input.txt", "r") as f:
    lines = [line.rstrip() for line in f.readlines()]

for i in range(100):
    for line in lines:
        if line == "deal into new stack":
            start = (start - step) % size
            step = (step * -1) % size
        elif re.match(r"cut -?\d+", line):
            num = int(re.findall(r"-?\d+", line)[0])
            start = (start + num * step) % size
        elif re.match(r"deal with increment \d+", line):
            num = int(re.findall(r"\d+", line)[0])
            step = pow(num, -1, size) * step % size
    print("{}: start = {} and step = {}".format(i + 1, start, step))
    # print("Diff start {}, factor step {}".format())
    old_start = start
    old_step = step


