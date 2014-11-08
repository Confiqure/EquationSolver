package equations;

/**
 *
 * Implementation of Gaussian Elimination. Adapted from Princeton University.
 * 
 * @author Princeton University
 */
public class Gaussian {
    
    private static final double EPSILON = 1e-10;
    
    /**
     *
     * Splits matrix into two separate matrices and solves.
     * 
     * @param matrix matrix of coefficients and solutions
     * @return an array of solutions
     */
    public static double[] solve(final double[][] matrix) {
        final double[][] variables = new double[matrix.length][matrix[0].length - 1];
        final double[] solutions = new double[matrix.length];
        for (int x = 0; x < matrix.length; x ++) {
            for (int y = 0; y < matrix[x].length; y ++) {
                if (y == matrix[x].length - 1) {
                    solutions[x] = matrix[x][y];
                } else {
                    variables[x][y] = matrix[x][y];
                }
            }
        }
        return solve(variables, solutions);
    }
    
    private static double[] solve(final double[][] variables, final double[] solutions) {
        for (int p = 0; p < solutions.length; p ++) {
            int max = p;
            for (int i = p + 1; i < solutions.length; i ++) {
                if (Math.abs(variables[i][p]) > Math.abs(variables[max][p])) {
                    max = i;
                }
            }
            final double[] temp = variables[p];
            variables[p] = variables[max];
            variables[max] = temp;
            final double t = solutions[p];
            solutions[p] = solutions[max];
            solutions[max] = t;
            if (Math.abs(variables[p][p]) <= EPSILON) {
                throw new RuntimeException("Matrix is singular or nearly singular");
            }
            for (int i = p + 1; i < solutions.length; i ++) {
                final double alpha = variables[i][p] / variables[p][p];
                solutions[i] -= alpha * solutions[p];
                for (int j = p; j < solutions.length; j ++) {
                    variables[i][j] -= alpha * variables[p][j];
                }
            }
        }
        final double[] x = new double[solutions.length];
        for (int i = solutions.length - 1; i >= 0; i --) {
            double sum = 0.0;
            for (int j = i + 1; j < solutions.length; j ++) {
                sum += variables[i][j] * x[j];
            }
            x[i] = (solutions[i] - sum) / variables[i][i];
        }
        return x;
    }
    
}
