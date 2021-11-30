from intcode.intcode import IntCode


with open("input.txt", "r") as f:
    op = [int(x) for x in f.readlines()[0].rstrip().split(",")]
    print(op)
    IntCode(op).run()





