import itertools

op = None

def try_configs(program):
    configs = list(itertools.permutations([0, 1, 2, 3, 4]))
    max_output = -1
    max_config = []
    for config in configs:
        last_output = 0
        for i in config:
            last_output = solve_program(program.copy(), last_output, i)
        if last_output > max_output:
            max_output = last_output
            max_config = config
    print("Found max-output {} with config {}".format(max_output, max_config))


def solve_program(op, input_value, phase):
    isp = 0
    read = False
    while op[isp] % 100 != 99:
        operation = op[isp] % 100
        params = [int(op[isp] / 10000) % 1000, int(op[isp] / 1000) % 100, int(op[isp] / 100) % 10]
        if operation == 1 or operation == 2 or operation == 7 or operation == 8:
            C = op[isp + 1] if params[2] == 1 else op[op[isp + 1]]
            B = op[isp + 2] if params[1] == 1 else op[op[isp + 2]]
            A = op[isp + 3] if params[0] == 1 else op[op[isp + 3]]
            if operation == 1:
                op[op[isp + 3]] = C + B
            elif operation == 2:
                op[op[isp + 3]] = C * B
            elif operation == 7:
                op[op[isp + 3]] = 1 if C < B else 0
            elif operation == 8:
                op[op[isp + 3]] = 1 if C == B else 0
        elif operation == 5 or operation == 6:
            C = op[isp + 1] if params[2] == 1 else op[op[isp + 1]]
            B = op[isp + 2] if params[1] == 1 else op[op[isp + 2]]
            if operation == 5:
                if C != 0:
                    isp = B - 4
                else:
                    isp -= 1
            elif operation == 6:
                if C == 0:
                    isp = B - 4
                else:
                    isp -= 1
        elif operation == 3:
            #print("Waiting for input")
            #value = int(input("prompt"))
            op[op[isp + 1]] = input_value if read else phase
            read = True
            isp -= 2
        elif operation == 4:
            value = op[isp + 1] if params[2] == 1 else op[op[isp + 1]]
            #print(value)
            return value
            isp -= 2
        else:
            print("Something went wrong: unknown operation {}".format(operation))
        isp += 4

with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]

try_configs(op)

