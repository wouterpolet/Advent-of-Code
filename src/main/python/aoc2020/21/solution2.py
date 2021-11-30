def del_ingre(food, ingre, aller):
    for f in food:
        if ingre in f[0]:
            f[0] -= {ingre}
        if aller in f[1]:
            f[1] -= {aller}

def check_const(const, chosen):
    for c in const:
        if all([x in chosen for x in c[1]]):
            acc = set()
            for x in c[1]:
                acc |= {chosen[x]}
            if len(c[0] - acc) > 0:
                return False
    return True

def find_alloc(pos, const, chosen):
    if not check_const(const, chosen):
        return None
    if len(pos) == 0 and check_const(const, chosen):
        return chosen
    for k, v in pos.items():
        for ing in v:
            new_chosen = chosen.copy()
            new_chosen[ing] = k
            new_pos = pos.copy()
            del new_pos[k]
            for i in new_pos.values():
                i -= {ing}
            new_alloc = find_alloc(new_pos, const, new_chosen)
            if new_alloc is not None:
                return new_alloc


def find_alloc_brut(allerg, ingr, const, chosen):
    if not check_const(const, chosen):
        return None
    if len(allerg) == 0:
        if check_const(const, chosen):
            return chosen
        else:
            return None
    for ing in ingr:
        for aller in allerg:
            new_ing = ingr.copy()
            new_aller = allerg.copy()
            new_ing.remove(ing)
            new_aller.remove(aller)
            new_chosen = chosen.copy()
            new_chosen[ing] = aller
            new_alloc = find_alloc_brut(new_aller, new_ing, const, new_chosen)
            if new_alloc is not None:
                return new_alloc

# with open('test.txt', 'r') as f:
with open('input.txt', 'r') as f:
    data = f.read()
    food = []
    tot_aller = set()
    tot_ingr = set()
    for l in data.splitlines():
        ingredients = set(l.split(' (contains ')[0].split())
        allergens = set(l.split('(contains ')[1][:-1].split(', '))
        tot_aller |= allergens
        tot_ingr |= ingredients
        food.append([ingredients, allergens])
    can_have = set()
    for aller in tot_aller:
        acc = None
        for f in food:
            if aller in f[1]:
                if acc is None:
                    acc = f[0]
                else:
                    acc = acc.intersection(f[0])
        if acc is not None:
            can_have |= acc

    cannot_have = set()
    for f in food:
        cannot_have |= f[0]
    for f in can_have:
        cannot_have -= {f}

    for f in food:
        f[0] -= cannot_have

    alloc = {}
    constraints = []
    for i in range(len(food)):
        for j in range(i, len(food)):
            aller = food[i][1].intersection(food[j][1])
            ingre = food[i][0].intersection(food[j][0])
            if len(aller) > 0:
                constraints.append([aller, ingre])
            if len(aller) == 1:
                alloc[next(iter(aller))] = ingre

    print(alloc)
    print(constraints)
    # for aller in tot_aller:
    #     acc = alloc[aller]
    #     for k, v in alloc.items():
    #         if k != aller:
    #             acc = acc - v
    #     print(len(acc))
    # while len(alloc) != len(tot_aller):
    #     print(alloc)
    #     for c in constraints:
    #         if len(c[0]) == 1 and len(c[1]) == 1:
    #             alloc[next(iter(c[1]))] = next(iter(c[0]))
    #             del_0 = c[0].copy()
    #             del_1 = c[1].copy()
    #             for con in constraints:
    #                 con[0] -= del_0
    #                 con[1] -= del_1

    solution = find_alloc_brut(list(tot_aller), list(tot_ingr - cannot_have), constraints, {})
    print(solution)
    print(','.join(x[1] for x in sorted([tuple(reversed(x)) for x in solution.items()])))
