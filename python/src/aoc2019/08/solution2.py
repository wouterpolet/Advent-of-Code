import numpy as np
from PIL import Image

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

image = np.full(width * height, 2)

for layer in layers:
    for i in range(len(image)):
        if image[i] == 2:
            image[i] = layer[i]

im = Image.fromarray(image.reshape((height, width)).astype('uint8') * 255)

im.show()




