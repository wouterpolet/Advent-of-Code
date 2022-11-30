import math

reactions = {}

with open("input.txt", "r") as f:
    for line in f.readlines():
        line = line.rstrip()
        left_right = line.split(" => ")
        result_chemical = left_right[1].split(" ")[1]
        result_amount = int(left_right[1].split(" ")[0])
        requirements = (result_amount, [])
        for ingredient in left_right[0].split(", "):
            amount_chemical = ingredient.split(" ")
            requirements[1].append((int(amount_chemical[0]), amount_chemical[1]))
        reactions[result_chemical] = requirements


needed = {}
extra = {}
fuel = 0
ore = 1000000000000


def used_by_other(material, needed, reactions):
    if material == "ORE":
        return False
    for need in needed.keys():
        if need == "ORE" or need == material:
            continue
        if uses_material(material, need, reactions):
            return True
    return False


def uses_material(material, ingredient, reactions):
    checking = [(0, ingredient)]
    while len(checking) != 0:
        current = checking.pop()
        if current[1] == material:
            return True
        checking.extend(reactions.get(current[1], (0, []))[1])
    return False


high = 1000000000000
low = 0

while low <= high:
    n = low + int((high - low) / 2)
    print("trying {}".format(n))
    extra.clear()
    needed["FUEL"] = n
    ore = 1000000000000
    while len(needed) != 0:
        keys = list(needed.keys())
        material = ""
        for key in keys:
            if not used_by_other(key, needed, reactions):
                material = key
                break
        if material == "ORE":
            ore -= needed.pop("ORE", 0)
            continue
        amount = needed[material]
        reaction = reactions[material]
        reactions_amount = math.ceil(amount / reaction[0])
        ingredients = reaction[1]
        for ingredient in ingredients:
            amount = ingredient[0] * reactions_amount
            current = needed.get(ingredient[1], 0)
            needed[ingredient[1]] = current + amount
        needed.pop(material, 0)
    if ore < 0:
        high = n - 1
    elif ore == 0:
        fuel = n
        print("Perfect = {}".format(n))
        high = -1
    elif ore > 0:
        low = n + 1
        fuel = n

print("Total fuel: {}".format(fuel))
