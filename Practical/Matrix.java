// Does stuff with matrices
  // Add and subtract matrices
  // Multiply and divide matrix by number
  // Multiply matrices
  // Get determinant of matrix
  // Get transpose and adjugate matrices
  // Get inverse matrix
  // Solve sets of solutions i.e.
    // 3x + 2y - z = 1
    // -2x + 3y + z = -4
    // 5x - 2y -3z = 3
// It's for linear algebra
  // Might sound like algebra 1 stuff but it's actually college level
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

  // Divides matrix by n
  public static double[][] divide(int[][] arr, double n) {
    double oneOverN = 1/n;
    return multiply(arr, oneOverN);
  }

  // Multiples matrix by matrix
  public static int[][] multiply(int[][] arr, int[][] arr2) {
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
    double[][] newArray = new double[arr.length][arr2[0].length];
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

  // You get the idea
  public static double[][] multiply(int[][] arr, double[][] arr2) {
    return multiply(arr2, arr);
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
    l1: for (int i = 0; i < arr.length; i++) {
      iUsed = i;
      if (i > x) {
        iUsed--;
      } else if (i == x) {
        continue l1;
      }
      for (int j = 0; j < arr.length; j++) {
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
    int[][] newArray = new int[n][n];
    for (int i = 0; i < n; i++) {
      newArray[i][i] = 1;
    }
    return newArray;
  }

  // Gets transpose matrix
  public static int[][] getTranspose(int[][] arr) {
    int[][] transpose = new int[arr.length][arr.length];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr.length; j++) {
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
      for (int j = 0; j < arr.length; j++) {
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
    System.out.println("1/" + determinant + " * ");
    print(adjugate);
  }

  // Gets solution to set of equations
  public static double[] getSolution(int[][] arr, int[] arr2) {
    int[][] newArray = new int[arr.length][1];
    for (int i = 0; i < arr.length; i++) {
      newArray[i][0] = arr2[i];
    }
    double[][] product = multiply(getInverse(arr), newArray);
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
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        System.out.print(arr[i][j] + " ");
      }
      System.out.println();
    }
  }

  // Prints 2d double array
  public static void print(double[][] arr) {
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        System.out.print(arr[i][j] + " ");
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
      {3, 2, -1},
      {-2, 3, 1},
      {5, -2, -3}
    };
    int[] outputs = {1, -4, 3};
    printSolution(inputs, outputs);
  }
}
