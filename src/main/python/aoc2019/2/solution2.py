op = None

def solve(op):
    isp = 0
    while op[isp] != 99:
        if op[isp] == 1:
            op[op[isp + 3]] = op[op[isp + 1]] + op[op[isp + 2]]
        elif op[isp] == 2:
            op[op[isp + 3]] = op[op[isp + 1]] * op[op[isp + 2]]
        else:
            print("Something went wrong")
        isp += 4


with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]

temp_op = [0]
noun = 0
verb = 0

for noun in range(100):
    for verb in range(100):
        temp_op = op.copy()
        temp_op[1] = noun
        temp_op[2] = verb
        print("Trying noun = {} and verb = {}".format(noun, verb))
        solve(temp_op)
        if temp_op[0] == 19690720:
            print("Success with noun = {} and verb = {}".format(noun, verb))
            answer = 100 * noun + verb
            print("Solution is {}".format(answer))
            break

