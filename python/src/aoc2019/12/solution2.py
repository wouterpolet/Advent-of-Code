import re
import numpy as np

moons = []

with open("input.txt", "r") as f:
    for line in f.readlines():
        moon = [int(x.group()) for x in re.finditer(r'-?\d+', line.rstrip())]
        moons.append(np.asarray(moon))

moons = np.asarray(moons)
periods = []

for i in range(3):
    time_steps = 0
    velocity = np.zeros(len(moons), dtype=int)
    positions = moons[:, i]
    start_pos = positions.copy()
    start_vel = velocity.copy()
    while time_steps == 0 or not (np.array_equal(positions, start_pos) and np.array_equal(velocity, start_vel)):
        gravity = np.zeros(len(moons), dtype=int)
        moons_aligned = positions
        for j in range(len(moons)):
            moons_aligned = np.roll(moons_aligned, 1)
            gravity += positions < moons_aligned
            gravity -= positions > moons_aligned
        velocity += gravity
        positions += velocity
        time_steps += 1
    print("Found {} steps for dimension {}".format(time_steps, i))
    periods.append(time_steps)

lcm = np.lcm(periods[0], periods[1])

for i in range(1, len(periods)):
    lcm = np.lcm(lcm, periods[i])


print("Total time steps: {}".format(lcm))
