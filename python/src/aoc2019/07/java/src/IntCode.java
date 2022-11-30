import java.util.ArrayList;
import java.util.List;

public class IntCode {

    private int[] op;
    private int isp;

    public IntCode(int[] operations) {
        op = operations;
        isp = 0;
    }

    public int run(int in) {
        boolean read = false;
        while (op[isp] % 100 != 99) {
            int operation = op[isp] % 100;
            int[] paramModes = new int[]{(op[isp] / 10000) % 1000, (op[isp] / 1000) % 100, (op[isp] / 100) % 10};
            int[] params = new int[3];
            for (int i = 1; i < getRequiredParameters(operation) + 1; i++) {
                if (isp + i < op.length) {
                    if (paramModes[3 - i] == 1) {
                        params[i - 1] = op[isp + i];
                    } else {
                        params[i - 1] = op[op[isp + i]];
                    }
                }
            }
            switch (operation) {
                case 1:
                    op[op[isp + 3]] = params[0] + params[1];
                    isp += 4;
                    break;
                case 2:
                    op[op[isp + 3]] = params[0] * params[1];
                    isp += 4;
                    break;
                case 3:
                    if (read)
                        return -1;
                    op[op[isp + 1]] = in;
                    read = true;

                    isp += 2;
                    break;
                case 4:
                    isp += 2;
                    return params[0];
                case 5:
                    if (params[0] != 0) {
                        isp = params[1];
                    } else {
                        isp += 3;
                    }
                    break;
                case 6:
                    if (params[0] == 0) {
                        isp = params[1];
                    } else {
                        isp += 3;
                    }
                    break;
                case 7:
                    op[op[isp + 3]] = params[0] < params[1] ? 1 : 0;
                    isp += 4;
                    break;
                case 8:
                    op[op[isp + 3]] = params[0] == params[1] ? 1 : 0;
                    isp += 4;
                    break;
                default:
                    System.out.println("Unknown operation");
            }
        }
        return -2;
    }


    public int getRequiredParameters(int op) {
        switch(op) {
            case 1:
            case 2:
            case 7:
            case 8:
                return 3;
            case 5:
            case 6:
                return 2;
            case 4:
            case 3:
                return 1;
            default:
                return 0;
        }
    }
}
