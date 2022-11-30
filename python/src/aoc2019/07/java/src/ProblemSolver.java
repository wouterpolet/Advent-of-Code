import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProblemSolver {

    public static void main(String[] args) {
        List<int[]> configs = generatePermutations(new int[]{5, 6, 7, 8, 9});
        int maxOutput = -1;
        int[] bestConfig = null;
        for (int[] config : configs) {
            AmplifierSolver solver = new AmplifierSolver("input.txt", config);
            int result = solver.solve();
            if (result > maxOutput) {
                maxOutput = result;
                bestConfig = config;
            }
        }
        System.out.println("Got max output: " + maxOutput + ", with config: " + Arrays.toString(bestConfig));
    }

    private static List<int[]> generatePermutations(int[] in) {
        List<int[]> result = new ArrayList<>();
        heapPermutation(in, in.length, in.length, result);
        return result;
    }

    private static void heapPermutation(int[] a, int size, int n, List<int[]> list) {
        // if size becomes 1 then prints the obtained
        // permutation
        if (size == 1)
            list.add(Arrays.copyOf(a, n));

        for (int i=0; i<size; i++)
        {
            heapPermutation(a, size-1, n, list);

            // if size is odd, swap first and last
            // element
            if (size % 2 == 1)
            {
                int temp = a[0];
                a[0] = a[size-1];
                a[size-1] = temp;
            }

            // If size is even, swap ith and last
            // element
            else
            {
                int temp = a[i];
                a[i] = a[size-1];
                a[size-1] = temp;
            }
        }
    }
}
