from anytree import Node, RenderTree, PreOrderIter, Walker

data = []
nodes = {}

with open("input.txt", "r") as f:
    data = f.readlines()
    data = [x.rstrip().split(")") for x in data]

roots = []

for orbit in data:
    parent = None
    if orbit[0] in nodes:
        parent = nodes[orbit[0]]
    else:
        parent = Node(orbit[0])
        nodes[orbit[0]] = parent
        roots.append(parent)
    if orbit[1] in nodes:
        child = nodes[orbit[1]]
        if child in roots:
            roots.remove(child)
        child.parent = parent
    else:
        child = Node(orbit[1], parent=parent)
        nodes[orbit[1]] = child

w = Walker()
you = nodes["YOU"]
santa = nodes["SAN"]
(upward, common, downward) = w.walk(you, santa)
amount = you.depth - common.depth - 1 + santa.depth - common.depth - 1
print("Amount = {}".format(amount))

