from y2020.gameboy.gameboy import Gameboy

with open('input.txt', 'r') as f:
    i = [line.rstrip() for line in f.readlines()]
    gb = Gameboy(i)
    done = []
    gb.run_until(cond=lambda x: x.isp not in done,
                 before=lambda x: done.append(x.isp))
    print(str(gb.reg))
