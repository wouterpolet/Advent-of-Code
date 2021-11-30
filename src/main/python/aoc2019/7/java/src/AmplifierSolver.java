import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class AmplifierSolver {

    private IntCode[] amplifiers;
    private int lastThrust;

    public AmplifierSolver(String filename, int[] phases) {
        lastThrust = 0;

        int[] program = readProgram(filename);
        amplifiers = new IntCode[5];
        for (int i = 0; i < amplifiers.length; i++) {
            assert program != null;
            amplifiers[i] = new IntCode(Arrays.copyOf(program, program.length));
            amplifiers[i].run(phases[i]);
        }

    }

    public int solve() {
        int index = 0;
        int lastReturn = 0;
        while (true) {
            lastReturn = amplifiers[index].run(lastReturn);
            if (lastReturn == -2) {
                return lastThrust;
            }
            if (index == 4) {
                lastThrust = lastReturn;
            }
            index++;
            if (index == 5) {
                index = 0;
            }
        }
    }

    private int[] readProgram(String filename) {
        File f =  new File(filename);
        try {
            Scanner scanner = new Scanner(f);
            String line = scanner.nextLine();
            String[] ops = line.split(",");
            int[] result = new int[ops.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = Integer.parseInt(ops[i]);
            }
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
