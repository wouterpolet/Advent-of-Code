import numpy as np

layers = []

data = []

with open("input.txt", "r") as f:
    data = [int(x) for x in list(f.readlines()[0].rstrip())]

width = 25
height = 6

data = np.asarray(data)

for i in range(int(len(data) / (width * height))):
    layer = data[i * width * height:(i+1) * width * height]
    layers.append(layer)

min_layer = layers[0]
min_amount = width * height

for layer in layers:
    num_zeros = np.count_nonzero(layer==0)
    if num_zeros < min_amount:
        min_layer = layer
        min_amount = num_zeros

print("Got layers: {}".format(layers))
print("Number of zeros: {}".format(min_amount))
print("With layer: {}".format(min_layer))

result = np.count_nonzero(min_layer==1) * np.count_nonzero(min_layer==2)

print("Result: {}".format(result))

