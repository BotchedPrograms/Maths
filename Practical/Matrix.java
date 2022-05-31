// Does stuff with matrices
  // Add and subtract matrices
  // Multiply and divide matrix by number
  // Multiply matrices
  // "Divides" matrices
  // Get determinant of matrix
  // Get transpose and adjugate matrices
  // Get inverse matrix
  // Solve sets of solutions e.g.
    // 3x + 2y - z = 1
    // -2x + 3y + z = -4
    // 5x - 2y -3z = 3
// It's for linear algebra
  // Might sound like algebra 1 stuff but it's actually college level
  // Used for working with large databases
public class Matrix {
  // Adds matrices
  public static int[][] add(int[][] arr, int[][] arr2) {
    int[][] newArray = new int[arr.length][arr[0].length];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        newArray[i][j] = arr[i][j] + arr2[i][j];
      }
    }
    return newArray;
  }

  // Subtracts matrices
  public static int[][] subtract(int[][] arr, int[][] arr2) {
    int[][] newArray = new int[arr.length][arr[0].length];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        newArray[i][j] = arr[i][j] - arr2[i][j];
      }
    }
    return newArray;
  }

  // Multiplies matrix by int n
  public static int[][] multiply(int[][] arr, int n) {
    int[][] newArray = new int[arr.length][arr[0].length];
    for (int i = 0; i < newArray.length; i++) {
      for (int j = 0; j < newArray[0].length; j++) {
        newArray[i][j] = arr[i][j] * n;
      }
    }
    return newArray;
  }

  // Multiplies matrix by int n but works w/ parameters in reversed order
  public static int[][] multiply(int n, int[][] arr) {
    return multiply(arr, n);
  }

  // Multiplies matrix by double n
  public static double[][] multiply(int[][] arr, double n) {
    double[][] newArray = new double[arr.length][arr[0].length];
    for (int i = 0; i < newArray.length; i++) {
      for (int j = 0; j < newArray[0].length; j++) {
        newArray[i][j] = arr[i][j] * n;
      }
    }
    return newArray;
  }

  // Look, I gotta fill up space somehow, ok?
  public static double[][] multiply(double n, int[][] arr) {
    return multiply(arr, n);
  }

  // Multiples matrix by matrix
  public static int[][] multiply(int[][] arr, int[][] arr2) {
    if (arr == null || arr2 == null) {
      return null;
    }
    int[][] newArray = new int[arr.length][arr2[0].length];
    // Classic triple loop
    for (int i = 0; i < newArray.length; i++) {
      for (int j = 0; j < newArray[0].length; j++) {
        for (int k = 0; k < arr2.length; k++) {
          newArray[i][j] += arr[i][k]*arr2[k][j];
        }
      }
    }
    return newArray;
  }

  // 2d double array instead of 2d int array
  public static double[][] multiply(double[][] arr, int[][] arr2) {
    if (arr == null || arr2 == null) {
      return null;
    }
    double[][] newArray = new double[arr.length][arr2[0].length];
    for (int i = 0; i < newArray.length; i++) {
      for (int j = 0; j < newArray[0].length; j++) {
        for (int k = 0; k < arr2.length; k++) {
          newArray[i][j] += arr[i][k]*arr2[k][j];
        }
      }
    }
    return newArray;
  }

  // You get the idea
  public static double[][] multiply(int[][] arr, double[][] arr2) {
    if (arr == null || arr2 == null) {
      return null;
    }
    double[][] newArray = new double[arr.length][arr2[0].length];
    for (int i = 0; i < newArray.length; i++) {
      for (int j = 0; j < newArray[0].length; j++) {
        for (int k = 0; k < arr2.length; k++) {
          newArray[i][j] += arr[i][k]*arr2[k][j];
        }
      }
    }
    return newArray;
  }

  public static double[][] multiply(double[][] arr, double[][] arr2) {
    if (arr == null || arr2 == null) {
      return null;
    }
    double[][] newArray = new double[arr.length][arr2[0].length];
    for (int i = 0; i < newArray.length; i++) {
      for (int j = 0; j < newArray[0].length; j++) {
        for (int k = 0; k < arr2.length; k++) {
          newArray[i][j] += arr[i][k]*arr2[k][j];
        }
      }
    }
    return newArray;
  }

  // Divides matrix by n
  public static double[][] divide(int[][] arr, double n) {
    double oneOverN = 1/n;
    return multiply(arr, oneOverN);
  }

  public static double[][] divideLeft(int[][] arr, int[][] arr2) {
    double[][] inverse = getInverse(arr);
    return multiply(inverse, arr2);
  }

  public static double[][] divideRight(int[][] arr, int[][] arr2) {
    double[][] inverse = getInverse(arr);
    return multiply(arr2, inverse);
  }

  public static void printDivideLeft(int[][] arr, int[][] arr2) {
    int determinant = getDeterminant(arr);
    if (determinant == 0) {
      System.out.println("none");
      return;
    }
    int[][] adjugate = getAdjugate(arr);
    int[][] main = multiply(adjugate, arr2);
    printDivide(main, determinant);
  }

  public static void printDivideRight(int[][] arr, int[][] arr2) {
    int determinant = getDeterminant(arr);
    if (determinant == 0) {
      System.out.println("none");
      return;
    }
    int[][] adjugate = getAdjugate(arr);
    int[][] main = multiply(arr2, adjugate);
    printDivide(main, determinant);
  }

  public static void printDivide(int[][] main, int determinant) {
    int gcf = gcf(main);
    gcf = gcf(determinant, gcf);
    int num = 1;
    if (gcf > 1) {
      num *= gcf;
      for (int i = 0; i < main.length; i++) {
        for (int j = 0; j < main[0].length; j++) {
          main[i][j] /= gcf;
        }
      }
      int[] fraction = simplify(num, determinant);
      num = fraction[0];
      determinant = fraction[1];
    }
    if (determinant < 0) {
      num *= -1;
      determinant *= -1;
    }
    System.out.println(num + "/" + determinant + " * ");
    print(main);
  }

  public static int gcf(int a, int b) {
    for (int i = Math.min(Math.abs(a), Math.abs(b)); i > 0; i--) {
      if (a % i == 0 && b % i == 0) {
        return i;
      }
    }
    return -1;
  }

  public static int gcf(int[] nums) {
    l1: for (int i = minAbs(nums); i > 0; i--) {
      for (int j = 0; j < nums.length; j++) {
        if (nums[j] % i != 0) {
          continue l1;
        }
      }
      return i;
    }
    return -1;
  }

  public static int gcf(int[][] arr) {
    int[] nums = new int[arr.length*arr[0].length];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        nums[i*arr.length+j] = arr[i][j];
      }
    }
    return gcf(nums);
  }

  // Gets min of absolute value of numbers
  public static int minAbs(int[] numbers) {
    int minAbs = Math.abs(numbers[0]);
    for (int i = 1; i < numbers.length; i++) {
      if (Math.abs(numbers[i]) < minAbs) {
        minAbs = Math.abs(numbers[i]);
      }
    }
    return minAbs;
  }

  public static int[] simplify(int num, int den) {
    int gcf = gcf(num, den);
    if (gcf > 1) {
      num /= gcf;
      den /= gcf;
    }
    return new int[] {num, den};
  }

  // Gets determinant of square matrix
  public static int getDeterminant(int[][] arr) {
    if (arr.length == 1) {
      return arr[0][0];
    }
    int answer = 0;
    // Not necessary, i could be 0 for all we care, it just makes program more efficient b/c more 0s equals less work as explained right below
    int i = rowWithMostZeros(arr);
    for (int j = 0; j < arr.length; j++) {
      // If arr[i][j] == 0, answer is just += 0 and we needn't waste time calculating everything else
      if (arr[i][j] != 0) {
        // Explanation for (i+j)%2*(-2)+1:
        // Imagine a chess board where top left and same-colored tiles stay the same and others multiplied by -1
        answer += arr[i][j]*((i+j)%2*(-2)+1)*getDeterminant(newArray(arr, i, j));
      }
    }
    return answer;
  }

  // Returns the row in 2d array with the most zeros
  public static int rowWithMostZeros(int[][] arr) {
    int rowWithMostZeros = 0;
    int numZeros;
    int currentMaxZeros = 0;
    for (int i = 0; i < arr.length; i++) {
      numZeros = 0;
      for (int j = 0; j < arr[0].length; j++) {
        if (arr[i][j] == 0) {
          numZeros++;
        }
      }
      if (numZeros > currentMaxZeros) {
        currentMaxZeros = numZeros;
        rowWithMostZeros = i;
      }
    }
    return rowWithMostZeros;
  }

  // Makes a new array excluding given row and column
  public static int[][] newArray(int[][] arr, int x, int y) {
    int[][] newArr = new int[arr.length-1][arr.length-1];
    int iUsed;
    int jUsed;
    for (int i = 0; i < arr.length; i++) {
      iUsed = i;
      if (i > x) {
        iUsed--;
      } else if (i == x) {
        continue;
      }
      for (int j = 0; j < arr[0].length; j++) {
        jUsed = j;
        if (j > y) {
          jUsed--;
        }
        // if statement needed for reasons unknown
        if (j != y) {
          newArr[iUsed][jUsed] = arr[i][j];
        }
      }
    }
    return newArr;
  }

  // Returns unit matrix of order n
  public static int[][] unitMatrix(int n) {
    int[][] unitMatrix = new int[n][n];
    for (int i = 0; i < n; i++) {
      unitMatrix[i][i] = 1;
    }
    return unitMatrix;
  }

  // Gets transpose matrix
  public static int[][] getTranspose(int[][] arr) {
    int[][] transpose = new int[arr.length][arr.length];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        transpose[i][j] = arr[j][i];
      }
    }
    return transpose;
  }

  // Gets adjugate matrix
  public static int[][] getAdjugate(int[][] arr) {
    int[][] transpose = getTranspose(arr);
    int[][] adjugate = new int[arr.length][arr.length];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        adjugate[i][j] = ((i+j)%2*(-2)+1) * getDeterminant(newArray(transpose, i, j));
      }
    }
    return adjugate;
  }

  // Gets inverse matrix
  public static double[][] getInverse(int[][] arr) {
    int[][] adjugate = getAdjugate(arr);
    int determinant = getDeterminant(arr);
    if (determinant == 0) {
      return null;
    }
    return divide(adjugate, determinant);
  }

  // Merely prints inverse matrix if you don't like seeing all the doubles
  public static void printInverse(int[][] arr) {
    int[][] adjugate = getAdjugate(arr);
    int determinant = getDeterminant(arr);
    if (determinant == 0) {
      System.out.print("none");
      return;
    }
    int num = 1;
    if (determinant < 0) {
      num *= -1;
      determinant *= -1;
    }
    System.out.println(num + "/" + determinant + " * ");
    print(adjugate);
  }

  // Gets solution to set of equations
  public static double[] getSolution(int[][] arr, int[] arr2) {
    int[][] newArray = new int[arr.length][1];
    for (int i = 0; i < arr.length; i++) {
      newArray[i][0] = arr2[i];
    }
    double[][] product = multiply(getInverse(arr), newArray);
    if (product == null) {
      return null;
    }
    double[] solution = new double[product.length];
    for (int i = 0; i < product.length; i++) {
      solution[i] = product[i][0];
    }
    return solution;
  }

  // Prints solution but better b/c it converts stuff like 3.200000000000000006 to 16/5
  public static void printSolution(int[][] arr, int[] arr2) {
    StringBuilder text = new StringBuilder();
    double[] solution = getSolution(arr, arr2);
    int[] fraction;
    for (int i = 0; i < solution.length; i++) {
      fraction = approximate(solution[i], Math.pow(10, -8));
      if (fraction[1] < 0) {
        fraction[1] *= -1;
        fraction[0] *= -1;
      }
      if (fraction[1] == 1) {
        text.append(fraction[0]);
      } else {
        text.append(fraction[0]);
        text.append("/");
        text.append(fraction[1]);
      }
      if (i != solution.length - 1) {
        text.append(", ");
      }
    }
    System.out.println(text);
  }

  // Approximates decimal to fraction
  // Borrowed from other file here but modified to return numbers in fraction instead of just printing them
  public static int[] approximate(double num, double error) {
    int[] fraction = new int[2];
    int n1 = 0;
    int d1 = 1;
    int n2 = 1;
    int d2 = 1;
    int numerator = 0;
    int denominator = 1;
    // newNum doesn't need to be set, but it's set to 0, so while loop stops early if num is an int
    double newNum = 0;
    double decimal = num - (int) num;
    while (Math.abs(newNum - decimal) > error) {
      numerator = n1+n2;
      denominator = d1+d2;
      newNum = (double) numerator/denominator;
      if (newNum < decimal) {
        n1 += n2;
        d1 += d2;
      } else if (newNum > decimal) {
        n2 += n1;
        d2 += d1;
      }
    }
    // Adds int part of num back into newly acquired fraction
    fraction[0] = ((int) num * (denominator) + numerator);
    fraction[1] = denominator;
    return fraction;
  }

  // Prints n
  // Made so I don't have to worry about changing print(int[][]) or print(int[]) to print(int)
  public static void print(int n) {
    System.out.println(n);
  }

  // Prints int array
  public static void print(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i]);
      System.out.print(" ");
    }
  }

  // Prints double array
  public static void print(double[] arr) {
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i]);
      System.out.print(" ");
    }
  }

  // Prints 2d int array
  public static void print(int[][] arr) {
    int max = 0;
    int length;
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        length = String.valueOf(arr[i][j]).length();
        if (length > max) {
          max = length;
        }
      }
    }
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        System.out.printf("%" + (max+1) + "d", arr[i][j]);
      }
      System.out.println();
    }
  }

  // Prints 2d double array
  public static void print(double[][] arr) {
    int max = 0;
    int length;
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        length = String.valueOf(arr[i][j]).length();
        if (length > max) {
          max = length;
        }
      }
    }
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        System.out.printf("%" + (max+1) + "d", arr[i][j]);
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    int[][] threeByThree = {
        {3, 2, -1},
        {-2, 3, 1},
        {5, -2, -3}
    };
    int[][] twoByTwo = {
        {3, 7},
        {5, -8}
    };
    int[][] inputs = {
        {3, 1, 2},
        {-1, -2, -1},
        {2, 1, 3}
    };
    int[] outputs = {1, -4, 3};
    printDivideLeft(threeByThree, inputs);
  }
}
