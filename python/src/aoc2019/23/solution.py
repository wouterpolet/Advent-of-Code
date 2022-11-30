import sys

from intcode.network_intcode import NetworkIntCode

queue = {}
initialized = []
NICs = []

NAT = None
NAT_mem = []

output_index = 0
input_index = 0
packet = [-1, -1, -1]


def give_input(comp_id):
    if comp_id not in initialized:
        initialized.append(comp_id)
        return comp_id
    q = queue.get(comp_id, [])
    if len(q) == 0:
        return -1
    else:
        return q.pop(0)


def handle_output(output):
    global output_index
    packet[output_index] = output
    output_index += 1
    if output_index > 2:
        if packet[0] == 255:
            print("First Y value is {}".format(packet[2]))
            sys.exit(0)
        output_index = 0
        if packet[0] not in queue:
            queue[packet[0]] = []
        queue[packet[0]].extend([packet[1], packet[2]])


with open("input.txt", "r") as f:
    operations = f.readlines()[0].rstrip().split(",")
    op = [int(x) for x in operations]


for i in range(50):
    comp = NetworkIntCode(op.copy(), input_provider=give_input, output_callback=handle_output, continuous=False, given_id=i)
    comp.run()
    NICs.append(comp)

while True:
    for nic in NICs:
        nic.run()

print(queue)
