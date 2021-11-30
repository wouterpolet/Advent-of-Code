from intcode.intcode import IntCode

with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]

size = 2
coordinate = [0, 0]
index = [0]
count = [0]
value = [0]
map = {}

max_x = 10000
max_y = 10000

def give_input():
    value = coordinate[index[0]]
    index[0] += 1
    return value


def handle_output(output):
    value[0] = output


def get_coordinate(x, y):
    if (x, y) in map:
        return map[x, y]
    coordinate[0] = x
    coordinate[1] = y
    index[0] = 0
    comp = IntCode(op.copy(), input_provider=give_input, output_callback=handle_output)
    comp.run()
    map[x, y] = value[0]
    return value[0]


def has_force(x_low, x_high, y_low, y_high):
    while x_low < x_high and y_low < y_high:
        if get_coordinate(x_low, y_low) == 1:
            return True
        if x_low > y_low:
            y_low += 1
        else:
            x_low += 1


# HIGH IS EXCLUSIVE
# LOW IS INCLUSIVE
def divide_and_conquer(x_low, x_high, y_low, y_high):
    #
    # Base Cases
    #
    # Edges are lower than size: no solution
    if x_high - x_low < size and y_high - y_low < size:
        return -1, -1
    # If the edges are the size: solution if everything is force:
    if x_high - x_low == size and y_high - y_low == size:
        if get_coordinate(x_low, y_low) == 1 \
                and get_coordinate(x_high - 1, y_low) == 1 \
                and get_coordinate(x_low, y_high - 1) == 1:
            return x_low, y_low
        return -1, -1
    # If no force: no solution
    if not has_force(x_low, x_high, y_low, y_high):
        return -1, -1

    #
    # Divide
    #
    x_mid = int((x_high + x_low + 1) / 2)
    y_mid = int((y_high + y_low + 1) / 2)

    left_top = divide_and_conquer(x_low, x_mid, y_low, y_mid)
    # If solution in left-top: that's the solution
    if sum(left_top) >= 0:
        return left_top

    # Check y-axis
    y_current = y_low
    x_current = x_low
    while y_current < y_high:
        for i in range(size):
            if get_coordinate(x_current + i, y_current) == 0:
                continue
            if get_coordinate(x_current + i, y_current) == 1 and get_coordinate(x_current + i + size - 1, y_current) == 1 and get_coordinate(x_current + i, y_current + size - 1):
                return
        y_current += 1

    # Check x-axis

    left_bottom = divide_and_conquer(x_low, x_mid, y_mid, y_high)

    right_top = divide_and_conquer(x_mid, x_high, y_low, y_mid)



    right_bottom = divide_and_conquer(x_mid, x_high, y_mid, y_high)



answer = get_answer(0, max_x, 0, max_y)

print("Left top coordinate: {}, {}".format(answer[0], answer[1]))
print("Answer: {}".format(answer[0] * 10000 + answer[1]))
