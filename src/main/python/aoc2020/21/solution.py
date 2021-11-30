def del_ingre(food, ingre, aller):
    for f in food:
        if ingre in f[0]:
            f[0] -= {ingre}
        if aller in f[1]:
            f[1] -= {aller}

# with open('test.txt', 'r') as f:
with open('input.txt', 'r') as f:
    data = f.read()
    food = []
    tot_aller = set()
    for l in data.splitlines():
        ingredients = set(l.split(' (contains ')[0].split())
        allergens = set(l.split('(contains ')[1][:-1].split(', '))
        tot_aller |= allergens
        food.append([ingredients, allergens])
    alloc = {}
    # while(len(alloc) != len(tot_aller)):
    for i in range(len(food)):
        for j in range(len(food)):
            aller = food[i][1].intersection(food[j][1])
            ingre = food[i][0].intersection(food[j][0])
            if len(aller) == 1 and len(ingre) == 1:
                alloc[next(iter(ingre))] = next(iter(aller))
                del_ingre(food, next(iter(ingre)), next(iter(aller)))
    # for i in range(len(food)):
    #     for j in range(len(food)):
    #         ingre = food[i][0] - food[j][0]
    #         aller = food[i][1] - food[j][1]
    #         if len(ingre) == 1 and len(aller) == 1:
    #             alloc[next(iter(ingre))] = next(iter(aller))
    #             del_ingre(food, next(iter(ingre)), next(iter(aller)))
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
    print(food)
    print(can_have)
    all_ingre = set()
    for f in food:
        all_ingre |= f[0]
    for f in can_have:
        all_ingre -= {f}
    print(all_ingre)
    count = 0
    for f in food:
        count += len(all_ingre.intersection(f[0]))
    print(count)