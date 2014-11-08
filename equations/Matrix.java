package equations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Class to handle matrices.
 */
public class Matrix {
    
    /**
     *
     * Returns the matrix generated from equations.
     * 
     * @param equations an array of each of the equations being used.
     * @return a matrix of the coefficients and solutions
     */
    public static double[][] getMatrix(final String[] equations) {
        final String[] variables = getVariables(equations);
        Arrays.sort(variables);
        final double[][] matrix = new double[equations.length][variables.length + 1];
        for (int i = 0; i < equations.length; i ++) {
            matrix[i] = extractValues(equations[i], variables);
        }
        return matrix;
    }
    
    private static String[] getVariables(final String[] equations) {
        String[] variables = {};
        for (final String equation : equations) {
            final String[] vars = getVariables(equation);
            if (vars.length > variables.length) {
                variables = vars;
            }
        }
        return variables;
    }
    
    private static String[] getVariables(final String equation) {
        final ArrayList<String> variables = new ArrayList<>();
        for (final char c : equation.toCharArray()) {
            if (Pattern.compile("[a-z]+").matcher(c + "").matches()) {
                variables.add(c + "");
            }
        }
        return variables.toArray(new String[variables.size()]);
    }
    
    private static double[] extractValues(final String equation, final String[] variables) {
        final double[] values = new double[variables.length + 1];
        String[] halves = equation.replace("+-", "-").replace("-+", "-").replace("-", "+-").split("=");
        if (contains(halves[1], variables)) {
            halves = flip(halves, 0, 1);
        }
        if (halves[0].startsWith("+")) {
            halves[0] = halves[0].substring(1);
        }
        if (halves[1].startsWith("+")) {
            halves[1] = halves[1].substring(1);
        }
        for (final String variable : variables) {
            if (!halves[0].contains(variable)) {
                halves[0] += "+0" + variable;
            }
        }
        final String[] terms = halves[0].split("\\+");
        for (int i = 0; i < variables.length - 1; i ++) {
            if (!terms[i].contains(variables[i])) {
                for (int j = 0; j < terms.length; j ++) {
                    if (i == j) {
                        continue;
                    }
                    if (terms[j].contains(variables[i])) {
                        flip(terms, i, j);
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < terms.length; i ++) {
            try {
                values[values.length - 1] += -Double.parseDouble(terms[i]);
            } catch (final Exception e) {
                String cut = terms[i].substring(0, terms[i].length() - 1);
                if (cut.isEmpty() || cut.equals("-")) {
                    cut += "1";
                }
                values[i] = Double.parseDouble(cut);
            }
        }
        values[values.length - 1] += Double.parseDouble(halves[1]);
        return values;
    }
    
    private static boolean contains(final String s, final String[] chars) {
        for (final String c : chars) {
            if (s.contains(c)) {
                return true;
            }
        }
        return false;
    }
    
    private static String[] flip(final String[] array, final int i1, final int i2) {
        final String tmp = array[i1];
        array[i1] = array[i2];
        array[i2] = tmp;
        return array;
    }
    
}
