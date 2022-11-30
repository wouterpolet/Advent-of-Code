from intcode.intcode import IntCode


class Amplifier:
    def __init__(self, op, phase, amps, output_index):
        self.signal = phase
        self.intcode = IntCode(op,
                               input_provider=lambda: self.signal,
                               output_callback=lambda x: amps[output_index].receive_signal(x),
                               continuous=False,
                               given_id="AMP{}".format(phase))
        self.intcode.run()

    def receive_signal(self, signal):
        self.signal = signal
        self.intcode.run()
