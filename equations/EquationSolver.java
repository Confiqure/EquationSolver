package equations;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * Equation solver.
 * 
 * @author Dylan Wheeler
 */
public class EquationSolver {
    
    /**
     *
     * Main method.
     * 
     * @param args no arguments necessary
     */
    public static void main(final String[] args) {
        final ArrayList<String> equations = new ArrayList<>();
        System.out.println("Enter your equations below, pressing <enter> between each equation. After equations have been entered, hit <enter> again. To quit, hit <enter> a second time.");
        while (true) {
            final String input = new Scanner(System.in).nextLine().trim().toLowerCase().replace(" ", "");
            if (input.isEmpty()) {
                if (!equations.isEmpty()) {
                    final double[] solution = Gaussian.solve(Matrix.getMatrix(equations.toArray(new String[equations.size()])));
                    System.out.print("Solution: (");
                    for (int i = 0; i < solution.length; i++) {
                        System.out.print(solution[i] + (i == solution.length - 1 ? ")" : ", "));
                    }
                    System.out.println("\n");
                    equations.clear();
                } else {
                    break;
                }
            } else {
                if (!input.contains("=")) {
                    System.out.println("Error adding equation: " + input);
                } else {
                    equations.add(input);
                }
            }
        }
    }
    
}
