from y2020.gameboy.gameboy import Gameboy
from y2020.gameboy.terminate import Terminate


def run_until_loop(gb: Gameboy, done):
    gb.run_until(cond=lambda x: x.isp not in done,
                 before=lambda x: done.append(x.isp))


def get_before(done):
    def before(gb: Gameboy):
        if gb.get_instruction() == 'nop' or gb.get_instruction() == 'jmp':
            new_instr = gb.instr.copy()
            if gb.get_instruction() == 'nop':
                new_instr[gb.isp] = ' '.join(['jmp', str(gb.get_arg())])
            elif gb.get_instruction() == 'jmp':
                new_instr[gb.isp] = ' '.join(['nop', str(gb.get_arg())])
            new_gb = Gameboy(new_instr)
            new_gb.reg = gb.reg
            new_gb.isp = gb.isp
            run_until_loop(new_gb, done.copy())
            if new_gb.isp == len(new_gb.instr):
                raise Terminate(new_gb.reg)
        done.append(gb.isp)
    return before


with open('input.txt', 'r') as f:
    i = [line.rstrip() for line in f.readlines()]
    gb = Gameboy(i)
    done = []
    res = gb.run_until(cond=lambda x: x.isp not in done,
                       before=get_before(done))
    if isinstance(res, Terminate):
        print(str(res.value))
    else:
        print("Failed")
