op = None

def solve(op):
    isp = 0
    while op[isp] % 100 != 99:
        operation = op[isp] % 100
        #print("Doing op: {}".format(operation))
        params = [int(op[isp] / 10000) % 1000, int(op[isp] / 1000) % 100, int(op[isp] / 100) % 10]
        if operation == 1 or operation == 2 or operation == 7 or operation == 8:
            C = op[isp + 1] if params[2] == 1 else op[op[isp + 1]]
            B = op[isp + 2] if params[1] == 1 else op[op[isp + 2]]
            A = op[isp + 3] if params[0] == 1 else op[op[isp + 3]]
            if operation == 1:
                op[op[isp + 3]] = C + B
                #print("Storing {}".format(C + B))
            elif operation == 2:
                op[op[isp + 3]] = C * B
                #print("Storing {}".format(C * B))
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
            print("Waiting for input")
            value = int(input("prompt"))
            op[op[isp + 1]] = value
            #print("Got: {}".format(value))
            isp -= 2
        elif operation == 4:
            value = op[isp + 1] if params[2] == 1 else op[op[isp + 1]]
            print(value)
            isp -= 2
        else:
            print("Something went wrong: unknown operation {}".format(operation))
        isp += 4
        #print(op)

with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]

#print(op)
solve(op)

