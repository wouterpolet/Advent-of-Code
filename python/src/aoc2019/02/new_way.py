from intcode.intcode import IntCode

with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]
    intcode = IntCode(op)
    intcode.run()
    print(op)
