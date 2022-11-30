from anytree import Node, RenderTree, PreOrderIter

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



#for pre, fill, node in RenderTree(nodes["4CC"]):
#    print("%s%s,%d" % (pre, node.name, node.depth))


orbits = 0

for root in roots:
    for node in PreOrderIter(root):
        orbits += node.depth

print("Length of roots: {}".format(len(roots)))
print("Number of orbits: {}".format(orbits))


