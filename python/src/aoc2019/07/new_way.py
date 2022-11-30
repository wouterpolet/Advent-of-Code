import itertools

from intcode.amplifier import Amplifier

with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]

    max_thrust = -1
    best_config = []

    configs = list(itertools.permutations(range(5, 10)))

    for config in configs:
        thrust = [0]
        amplifiers = []
        for i in range(len(config)):
            new_amp = Amplifier(op.copy(), config[i], amplifiers, i + 1)
            amplifiers.append(new_amp)

        def handle_last(array, x):
            array[0] = x
            amplifiers[0].receive_signal(x)

        amplifiers[len(amplifiers) - 1].intcode.output_callback = lambda x: (
            handle_last(thrust, x)
        )

        amplifiers[0].receive_signal(0)

        if thrust[0] > max_thrust:
            max_thrust = thrust[0]
            best_config = config

    print("Found max thrust: {}".format(max_thrust))
    print("With configuration: {}".format(best_config))
