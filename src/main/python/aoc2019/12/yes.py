import re
import numpy as np

moons = []
time_steps = 1000

with open("input.txt", "r") as f:
    for line in f.readlines():
        moon = [int(x.group()) for x in re.finditer(r'-?\d+', line.rstrip())]
        moons.append(np.asarray(moon))

print(moons)
velocity = [np.zeros(3, dtype=int) for x in moons]
print(velocity)

for i in range(time_steps):
    gravity = [np.zeros(3, dtype=int) for x in moons]
    moons_aligned = moons
    for i in range(len(moons)):
        moons_aligned = np.roll(moons_aligned, 3)
        gravity += moons < moons_aligned
        gravity -= moons > moons_aligned
    velocity += gravity
    moons += velocity
    print("moons ", moons)
    print("velocity", velocity)

total_energy = 0
print(moons)
print(velocity)
for pnt in range(len(moons)):
    moon = moons[pnt]
    vel = velocity[pnt]
    pot = np.absolute(moon).sum()
    kin = np.absolute(vel).sum()
    total_energy += pot * kin

print("total energy is {}".format(total_energy))




