import java.util.LinkedList;
import java.util.List;

// Assumes inputs are rectangular arrays
public class Matrix {
    double[][] mat;

    public Matrix(double[][] mat) {
        this.mat = mat;
    }

    private interface Function {
        double func(double num1, double num2);
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

    public Matrix add(double[][] other) {
        Function add = Double::sum;
        return iterate(other, add);
    }

    public Matrix subtract(double[][] other) {
        Function subtract = (num1, num2) -> num1 - num2;
        return iterate(other, subtract);
    }

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

    public Matrix multiply(Matrix other) {
        if (mat[0].length != other.mat.length) {
            throw new IllegalArgumentException();
        }
        int rows = mat.length;
        int cols = mat[0].length;
        double[][] answer = new double[rows][other.mat[0].length];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                answer[i][j] = dotRowCol(mat, i, other.mat, j);
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

    // Returns set of orthonormal vectors that has the same span as the column vectors of mat
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
            double length = 0;
            for (int j = 0; j < rows; j++) {
                length += answer[j][i] * answer[j][i];
            }
            length = Math.sqrt(length);
            if (!equals(length, 0)) {
                for (int j = 0; j < rows; j++) {
                    answer[j][i] /= length;
                }
            }
        }
        return new Matrix(answer);
    }

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

    private static boolean equals(double num, double target) {
        return Math.abs(num - target) < 0.000001;
    }

    private void swapRows(int row1, int row2) {
        double[] sub = mat[row1];
        mat[row1] = mat[row2];
        mat[row2] = sub;
    }

    private Matrix copy() {
        double[][] newMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            System.arraycopy(mat[i], 0, newMat[i], 0, mat[0].length);
        }
        return new Matrix(newMat);
    }

    private Matrix rrefHelper(int col, int rowToPlace) {
        if (col == mat[0].length || rowToPlace == mat.length) {
            return this;
        }
        for (int i = rowToPlace; i < mat.length; i++) {
            double leadingDigit = mat[i][col];
            if (equals(leadingDigit, 0)) {
                continue;
            }
            for (int j = col; j < mat[0].length; j++) {
                mat[i][j] /= leadingDigit;
            }
            for (int j = 0; j < mat.length; j++) {
                if (i == j) {
                    continue;
                }
                double otherLeading = mat[j][col];
                if (equals(otherLeading, 0)) {
                    continue;
                }
                for (int k = col; k < mat[0].length; k++) {
                    mat[j][k] -= otherLeading * mat[i][k];
                }
            }
            swapRows(i, rowToPlace);
            if (col == mat[0].length - 1 || rowToPlace == mat.length - 1) {
                return this;
            }
            return rrefHelper(col + 1, rowToPlace + 1);
        }
        return rrefHelper(col + 1, rowToPlace);
    }

    // Returns matrix in reduced row echelon form
    public Matrix rref() {
        Matrix copy = copy();
        return copy.rrefHelper(0, 0);
    }

    public int rank() {
        Matrix rref = rref();
        for (int i = mat.length - 1; i >= 0; i--) {
            for (int j = i; j < mat[0].length; j++) {
                if (!equals(rref.mat[i][j], 0)) {
                    return i + 1;
                }
            }
        }
        return 0;
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

    // where mat is coeffs
    public void printSolutions(double[] product) {
        if (mat.length != product.length) {
            throw new IllegalArgumentException();
        }
        double[][] appended = new double[mat.length][mat[0].length + 1];
        for (int i = 0; i < mat.length; i++) {
            System.arraycopy(mat[i], 0, appended[i], 0, mat[0].length);
            appended[i][mat[0].length] = product[i];
        }
        Matrix rref = new Matrix(appended).rref();
        int rank = rank();
        if (rank != rank(rref)) {
            System.out.println("No solutions");
            return;
        }
        List<double[]> varColVectors = new LinkedList<>();
        for (int i = 1; i < mat[0].length + 1; i++) {
            for (int j = 0; j < i && j < mat.length; j++) {
                if (equals(rref.mat[j][i], 0) || equals(rref.mat[j][i], 1)) {
                    continue;
                }
                double[] column = new double[mat.length];
                for (int k = 0; k < mat.length; k++) {
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
                System.out.printf("% .5f ", v);
            }
            System.out.println(") + ");
        }
        print(varColVectors.get(varColVectors.size() - 1));
    }

    // Returns rref of this matrix appended by identity matrix if inverse exists, null otherwise
    public Matrix inverse() {
        if (mat.length != mat[0].length) {
            return null;
        }
        int length = mat.length;
        double[][] appended = new double[length][2 * length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(mat[i], 0, appended[i], 0, length);
            appended[i][length + i] = 1;
        }
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

    private void print() {
        print(mat);
    }

    private static void print(double[] arr) {
        for (double v : arr) {
            System.out.printf("% .10f ", v);
        }
        System.out.println();
    }

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
        example.rref().print();
        example.inverse().print();
        example.multiply(example.inverse()).print();
    }
}
