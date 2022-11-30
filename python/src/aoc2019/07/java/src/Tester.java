import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tester {

    public static void main(String[] args) {
        int[] program = readProgram("test3.txt");
        IntCode comp = new IntCode(program);
        Scanner sc = new Scanner(System.in);
        System.out.println(comp.run(sc.nextInt()));
        sc.close();
    }


    private static int[] readProgram(String filename) {
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
