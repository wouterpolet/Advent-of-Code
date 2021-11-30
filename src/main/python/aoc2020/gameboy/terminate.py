class Terminate(Exception):
    value = None

    def __init__(self, value=None):
        self.value = value
