# with open('input.txt', 'r') as f:
#     count = 0
#     for group in f.read().split('\n\n'):
#         answers = []
#         start = True
#         for answer in group.splitlines():
#             if start:
#                 answers = [c for c in answer]
#                 start = False
#             else:
#                 for existing in answers:
#                     if existing not in answer:
#                         answers.remove(existing)
#         print(answers)
#         count += len(answers)
#     print(count)

with open('input.txt', 'r') as f:
    count = 0
    for group in f.read().split('\n\n'):
        answers = group.splitlines()
        chars = {}
        for answer in answers:
            for c in answer:
                if c not in chars:
                    chars[c] = 1
                else:
                    chars[c] += 1
        for item in chars.values():
            if item == len(answers):
                count += 1
    print(count)

