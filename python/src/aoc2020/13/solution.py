with open('input.txt', 'r') as f:
    start = int(f.readline())
    buses = f.readline().rstrip().split(',')

    wait_time = 9999999999999
    min_bus = -1
    for bus in buses:
        if bus != 'x' and int(bus) - (start % int(bus)) < wait_time:
            wait_time = int(bus) - (start % int(bus))
            min_bus = int(bus)

    print(wait_time)
    print(min_bus)
    print(wait_time * min_bus)
