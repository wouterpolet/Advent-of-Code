class Node:
    el = None
    next = None
    prev = None

    def __init__(self, el, next, prev):
        self.el = el
        self.next = next
        self.prev = prev

    def __eq__(self, other):
        return isinstance(other, Node) and other.el == self.el

class DLL:
    h = None
    t = None

    def addFirst(self, el):
        n = Node(el, self.h, self.t)
        if self.h is None:
            self.h = n
            self.t = n
        else:
            self.h.prev = n
            self.h = n
        return n

    def addLast(self, el):
        n = Node(el, self.h, self.t)
        if self.t is None:
            self.t = n
            self.h = n
        else:
            self.t.next = n
            self.t = n
        return n

    def move(self, toAdd, p):
        if toAdd.next is not None:
            toAdd.next.prev = toAdd.prev
        if toAdd.prev is not None:
            toAdd.prev.next = toAdd.next
        if p.next is not None:
            p.next.prev = toAdd
        p.next = toAdd
        toAdd.next = p.next
        toAdd.prev = p

# with open('test.txt', 'r') as f:
with open('input.txt', 'r') as f:
    og_cups = [int(x) for x in list(f.readline())]
    size = 1000000
    pos = [Node(None, None, None)] * size
    cups = DLL()

    for cup in og_cups:
        n = cups.addLast(cup)
        pos[cup - 1] = n

    for i in range(len(og_cups)+1, size+1):
        n = cups.addLast(i)
        pos[i-1] = n

    c = cups.h
    mini = 1
    maxi = size
    for i in range(10000000):
        p = c.next
        picked = [c.next, c.next.next, c.next.next.next]

        k = 1
        if c.el - k < mini:
            k = c.el - maxi
        while pos[c.el - k - 1] in picked:
            k += 1
            if c.el - k < mini:
                k = c.el - maxi
        dest = pos[c.el - k - 1]

        picked[2].next.prev = picked[0].prev
        picked[0].prev.next = picked[2].next

        dest.next.prev = picked[2]
        picked[2].next = dest.next
        dest.next = picked[0]
        picked[0].prev = dest

        c = c.next

    one = pos[0]
    res1 = one.next.el
    res2 = one.next.next.el

    print(str(res1 * res2))