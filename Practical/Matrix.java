import java.util.LinkedList;
import java.util.List;
import java.util.Random;

// Assumes inputs are rectangular arrays
public class Matrix {
    private final double[][] mat;

    /**
     * Constructs a Matrix with the given 2D double array
     * Assumes the given array is rectangular
     */
    public Matrix(double[][] mat) {
        this.mat = mat;
    }

    // Used for lambda stuff
    private interface Function {
        double func(double num1, double num2);
    }

    // Used to store two objects simultaneously
    private static class Tuple<T1,T2> {
        T1 thing1;
        T2 thing2;
        private Tuple(T1 thing1, T2 thing2) {
            this.thing1 = thing1;
            this.thing2 = thing2;
        }
    }

    // If this = a, other = b, returned matrix = c, and function = f, c[i][j] = f(a[i][j], b[i][j]) for all i,j
    private Matrix iterate(double[][] other, Function function) {
        if (mat.length != other.length || mat[0].length != other[0].length) {
            throw new IllegalArgumentException();
        }
        int rows = mat.length;
        int cols = mat[0].length;
        double[][] answer = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                answer[i][j] = function.func(mat[i][j], other[i][j]);
            }
        }
        return new Matrix(answer);
    }

    /**
     * Returns the sum of this Matrix and a given Matrix
     */
    public Matrix add(double[][] other) {
        Function add = Double::sum;
        return iterate(other, add);
    }

    /**
     * Returns the difference between this Matrix and a given Matrix
     */
    public Matrix subtract(double[][] other) {
        Function subtract = (num1, num2) -> num1 - num2;
        return iterate(other, subtract);
    }

    /**
     * Returns the product of this Matrix multiplied by a given scalar
     */
    public Matrix multiply(double scalar) {
        int rows = mat.length;
        int cols = mat[0].length;
        double[][] answer = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                answer[i][j] = mat[i][j] * scalar;
            }
        }
        return new Matrix(answer);
    }

    // Returns dot product of this mat's row and other's column
    private double dotRowCol(double[][] mat, int row, double[][] other, int col) {
        double sum = 0;
        for (int i = 0; i < other.length; i++) {
            sum += mat[row][i] * other[i][col];
        }
        return sum;
    }

    /**
     * Returns the Matrix product of this Matrix multiplied to the right by a given Matrix
     */
    public Matrix multiply(Matrix other) {
        if (mat[0].length != other.mat.length) {
            throw new IllegalArgumentException();
        }
        int rows = mat.length;
        int cols = other.mat[0].length;
        double[][] answer = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                answer[i][j] = dotRowCol(mat, i, other.mat, j);
            }
        }
        return new Matrix(answer);
    }

    // Multiplies other to the right of this, given the indices where this has nonzero elements
    private Matrix multiply(Matrix other, List<Integer> nonZeroIndices) {
        if (mat[0].length != other.mat.length) {
            throw new IllegalArgumentException();
        }
        int rows = mat.length;
        int thisCols = mat[0].length;
        int otherCols = other.mat[0].length;
        double[][] answer = new double[rows][otherCols];
        int nonZeroIndex = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < otherCols; j++) {
                double sum = 0;
                for (; nonZeroIndex < nonZeroIndices.size() && nonZeroIndices.get(nonZeroIndex) / thisCols <= i;
                     nonZeroIndex++) {
                    sum += mat[i][nonZeroIndex % thisCols] * other.mat[nonZeroIndex % thisCols][j];
                }
                answer[i][j] = sum;
            }
        }
        return new Matrix(answer);
    }

    // Returns dot product of this mat's col1 column and other's col2 column
    private double dotColCol(double[][] mat, int col1, double[][] other, int col2) {
        double sum = 0;
        for (int i = 0; i < other.length; i++) {
            sum += mat[i][col1] * other[i][col2];
        }
        return sum;
    }

    // Normalizes a given column of a given matrix
    private static void normalizeCol(double[][] mat, int col) {
        double length = 0;
        for (double[] rowVectors : mat) {
            length += rowVectors[col] * rowVectors[col];
        }
        length = Math.sqrt(length);
        for (int i = 0; i < mat.length; i++) {
            mat[i][col] /= length;
        }
    }

    /**
     * Returns Matrix whose columns are orthonormal vectors with the same span as the columns of this Matrix
     */
    public Matrix gramSchmidt() {
        int rows = mat.length;
        int cols = mat[0].length;
        double[][] answer = new double[rows][cols];
        // Very inconvenient to work with column vectors with arrays
        for (int i = 0; i < rows; i++) {
            answer[i][0] = mat[i][0];
        }
        double[] dots = new double[cols];
        dots[0] = dotColCol(mat, 0, mat, 0);
        for (int i = 1; i < cols; i++) {
            // Makes ith column vector of answer equal that of mat
            for (int j = 0; j < rows; j++) {
                answer[j][i] = mat[j][i];
            }
            // Subtracts ith column vector of answer by that of mat's projection onto previous column vectors
            for (int j = 0; j < i; j++) {
                double factor = dotColCol(mat, i, answer, j);
                if (!equals(dots[j], 0)) {
                    factor /= dots[j];
                }
                for (int k = 0; k < rows; k++) {
                    answer[k][i] -= factor * answer[k][j];
                }
            }
            dots[i] = dotColCol(answer, i, answer, i);
        }

        // Normalizes column vectors
        for (int i = 0; i < cols; i++) {
            normalizeCol(answer, i);
        }
        return new Matrix(answer);
    }

    /**
     * Returns this Matrix's transpose
     */
    public Matrix transpose() {
        int rows = mat.length;
        int cols = mat[0].length;
        double[][] answer = new double[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                answer[i][j] = mat[j][i];
            }
        }
        return new Matrix(answer);
    }

    // Returns true if two given doubles are practically equal
    private static boolean equals(double num, double target) {
        return Math.abs(num - target) < 0.000001;
    }

    // Swaps row1 and row2 for mat
    private void swapRows(int row1, int row2) {
        double[] sub = mat[row1];
        mat[row1] = mat[row2];
        mat[row2] = sub;
    }

    // Returns a copy of this Matrix
    private Matrix copy() {
        double[][] newMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            System.arraycopy(mat[i], 0, newMat[i], 0, mat[0].length);
        }
        return new Matrix(newMat);
    }

    // Reduces the matrix to rref while simultaneously calculating the determinant
    private Tuple<Matrix, Double> rrefAndDetHelper(int col, int rowToPlace, double currDet) {
        int rows = mat.length;
        int cols = mat[0].length;
        if (col == cols || rowToPlace == rows) {
            return new Tuple<>(this, currDet);
        }
        for (int i = rowToPlace; i < rows; i++) {
            double leadingDigit = mat[i][col];
            if (equals(leadingDigit, 0)) {
                continue;
            }
            for (int j = col; j < cols; j++) {
                mat[i][j] /= leadingDigit;
            }
            currDet *= leadingDigit;
            for (int j = 0; j < rows; j++) {
                if (i == j) {
                    continue;
                }
                double otherLeading = mat[j][col];
                if (equals(otherLeading, 0)) {
                    continue;
                }
                for (int k = col; k < cols; k++) {
                    mat[j][k] -= otherLeading * mat[i][k];
                }
            }
            if (i != rowToPlace) {
                swapRows(i, rowToPlace);
                currDet *= -1;
            }
            return rrefAndDetHelper(col + 1, rowToPlace + 1, currDet);
        }
        return rrefAndDetHelper(col + 1, rowToPlace, currDet);
    }

    /**
     * Returns this Matrix's reduced row echelon form
     */
    public Matrix rref() {
        Matrix copy = copy();
        return copy.rrefAndDetHelper(0, 0, 1).thing1;
    }

    /**
     * Returns this Matrix's determinant
     *
     * @throws IllegalArgumentException if this Matrix isn't square
     */
    public double det() {
        if (mat.length != mat[0].length) {
            throw new IllegalArgumentException();
        }
        Matrix copy = copy();
        double currDet = copy.rrefAndDetHelper(0, 0, 1).thing2;
        if (equals(copy.mat[mat.length - 1][mat.length - 1], 0)) {
            return 0.0;
        }
        return currDet;
    }

    /**
     * Returns this Matrix's rank, the number of leading 1s when this Matrix is reduced
     */
    public int rank() {
        return rank(rref());
    }

    private static int rank(Matrix rref) {
        for (int i = rref.mat.length - 1; i >= 0; i--) {
            for (int j = i; j < rref.mat[0].length; j++) {
                if (!equals(rref.mat[i][j], 0)) {
                    return i + 1;
                }
            }
        }
        return 0;
    }

    /**
     * Consider the equation Ax = y where A is this Matrix and y is product
     * This prints out the possible solutions for x
     *
     * @throws IllegalArgumentException if A and product don't have the same length
     */
    public void printSolutions(double[] product) {
        if (mat.length != product.length) {
            throw new IllegalArgumentException();
        }
        int rows = mat.length;
        int cols = mat[0].length;
        double[][] appended = new double[rows][cols + 1];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(mat[i], 0, appended[i], 0, cols);
            appended[i][cols] = product[i];
        }
        Matrix rref = new Matrix(appended).rref();
        int rank = rank();
        if (rank != rank(rref)) {
            System.out.println("No solutions");
            return;
        }
        List<double[]> varColVectors = new LinkedList<>();
        for (int i = 1; i < cols + 1; i++) {
            for (int j = 0; j < i && j < rows; j++) {
                if (equals(rref.mat[j][i], 0) || equals(rref.mat[j][i], 1)) {
                    continue;
                }
                double[] column = new double[rows];
                for (int k = 0; k < rows; k++) {
                    column[k] = rref.mat[k][i];
                }
                varColVectors.add(column);
                break;
            }
        }
        if (varColVectors.size() == 1) {
            print(varColVectors.get(0));
            return;
        }
        for (int i = 0; i < varColVectors.size() - 1; i++) {
            double[] column = varColVectors.get(i);
            System.out.printf("a_%d(", i);
            for (double v : column) {
                System.out.printf("% .10f ", v);
            }
            System.out.println(") + ");
        }
        print(varColVectors.get(varColVectors.size() - 1));
    }

    /**
     * Returns this Matrix's inverse Matrix if it has one, null otherwise
     */
    public Matrix inverse() {
        if (mat.length != mat[0].length) {
            return null;
        }
        int length = mat.length;
        // Appends this matrix by the identity matrix
        double[][] appended = new double[length][2 * length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(mat[i], 0, appended[i], 0, length);
            appended[i][length + i] = 1;
        }
        // Finds rref of appended matrix and returns the transformed identity matrix
        Matrix rref = new Matrix(appended).rref();
        if (!equals(rref.mat[length - 1][length - 1], 1)) {
            return null;
        }
        double[][] answer = new double[length][length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(rref.mat[i], length, answer[i], 0, length);
        }
        return new Matrix(answer);
    }

    /**
     * Returns this Matrix's largest eigenvalue
     * Assumes this Matrix is diagonalizable
     * Works well for matrices with most of its elements equal to zero
     *
     * @throws IllegalArgumentException if this Matrix is not square
     */
    // https://en.wikipedia.org/wiki/Power_iteration
    public double powerIteration() {
        if (mat.length != mat[0].length) {
            throw new IllegalArgumentException();
        }
        int length = mat.length;
        List<Integer> nonZeroIndices = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (!equals(mat[i][j], 0)) {
                    nonZeroIndices.add(i * length + j);
                }
            }
        }

        Matrix bVector = new Matrix(new double[mat.length][1]);
        Random rand = new Random();
        for (int i = 0; i < mat.length; i++) {
            bVector.mat[i][0] = rand.nextDouble();
        }
        normalizeCol(bVector.mat, 0);

        // More iterations, more accuracy
        int iterations = 100;
        for (int i = 0; i < iterations; i++) {
            bVector = multiply(bVector, nonZeroIndices);
            normalizeCol(bVector.mat, 0);
        }
        Matrix nextBVector = multiply(bVector);
        return dotColCol(nextBVector.mat, 0, bVector.mat, 0)
            / dotColCol(bVector.mat, 0, bVector.mat, 0);
    }

    /**
     * Prints this Matrix
     */
    public void print() {
        print(mat);
    }

    // Prints out the given double array
    private static void print(double[] arr) {
        for (double v : arr) {
            System.out.printf("% .10f ", v);
        }
        System.out.println();
    }

    // Prints out the given 2d double array
    private static void print(double[][] arrs) {
        for (double[] arr : arrs) {
            print(arr);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Matrix example = new Matrix(new double[][]{
            {1, -1, -2, 4},
            {2, -1, -1, 2},
            {2, 1, 4, 16},
            {1, 2, 3, 4}
        });
        Matrix example2 = new Matrix(new double[][] {
            {4, 0, -3, -17, 1, -5, 0},
            {5, 0, 2, -4, -3, 48, 0},
            {3, 0, 3, 3, 3, 9, 0},
            {-6, 0, 4, 24, -1, 2, 0}
        });
        Matrix example3 = new Matrix(new double[][] {
            {11, 899, 988, 199, 911},
            {972, 53, 938, 843, 136},
            {11, 919, 263, 177, 0},
            {299, 792, 458, 998, 600},
            {0, 867, 530, 9, 39}
        });
        System.out.println(example.det());
        System.out.println(example3.det());
        System.out.println(example.powerIteration());
    }
}
