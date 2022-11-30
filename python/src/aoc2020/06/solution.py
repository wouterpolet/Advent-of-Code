with open('input.txt', 'r') as f:
    count = 0
    for group in f.read().split('\n\n'):
        answers = []
        for answer in group:
            if answer != '\n' and answer not in answers:
                answers.append(answer)
        count += len(answers)
    print(count)

