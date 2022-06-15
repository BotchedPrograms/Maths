import java.util.ArrayList;

// Gets polynomial that goes through given points
  // Does same thing as PolynomialSolution but w/ linear algebra
    // x can now be anything instead of x = 0, 1, 2, 3...
public class PolynomialSolutionButBetter {
  // Multiples matrix by matrix
  public static long[][] multiply(long[][] arr, long[][] arr2) {
    if (arr == null || arr2 == null) {
      return null;
    }
    long[][] newArray = new long[arr.length][arr2[0].length];
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

  public static double[][] multiply(double[][] arr, double[][] arr2) {
    if (arr == null || arr2 == null) {
      return null;
    }
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

  // Gets determinant of square matrix
  public static long getDeterminant(long[][] arr) {
    if (arr.length == 1) {
      return arr[0][0];
    }
    long answer = 0;
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

  public static double getDeterminant(double[][] arr) {
    if (arr.length == 1) {
      return arr[0][0];
    }
    double answer = 0;
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
  public static int rowWithMostZeros(long[][] arr) {
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

  public static int rowWithMostZeros(double[][] arr) {
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
  public static long[][] newArray(long[][] arr, int x, int y) {
    long[][] newArr = new long[arr.length-1][arr.length-1];
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

  public static double[][] newArray(double[][] arr, int x, int y) {
    double[][] newArr = new double[arr.length-1][arr.length-1];
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

  // Gets transpose matrix
  public static long[][] getTranspose(long[][] arr) {
    long[][] transpose = new long[arr.length][arr.length];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        transpose[i][j] = arr[j][i];
      }
    }
    return transpose;
  }

  public static double[][] getTranspose(double[][] arr) {
    double[][] transpose = new double[arr.length][arr.length];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        transpose[i][j] = arr[j][i];
      }
    }
    return transpose;
  }

  // Gets adjugate matrix
  public static long[][] getAdjugate(long[][] arr) {
    long[][] transpose = getTranspose(arr);
    long[][] adjugate = new long[arr.length][arr.length];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        adjugate[i][j] = ((i+j)%2*(-2)+1) * getDeterminant(newArray(transpose, i, j));
      }
    }
    return adjugate;
  }

  public static double[][] getAdjugate(double[][] arr) {
    double[][] transpose = getTranspose(arr);
    double[][] adjugate = new double[arr.length][arr.length];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        adjugate[i][j] = ((i+j)%2*(-2)+1) * getDeterminant(newArray(transpose, i, j));
      }
    }
    return adjugate;
  }

  // Prints solution from int points
  public static void printSolution(int[][] points) {
    long[][] arr1 = new long[points.length][points.length];
    long[] arr2 = new long[points.length];
    for (int i = 0; i < points.length; i++) {
      for (int j = 0; j < points.length; j++) {
        arr1[i][j] = (long) Math.pow(points[i][0], j);
      }
      arr2[i] = points[i][1];
    }
    printSolution(arr1, arr2);
  }

  // Prints solution from long points
  public static void printSolution(long[][] points) {
    long[][] arr1 = new long[points.length][points.length];
    long[] arr2 = new long[points.length];
    for (int i = 0; i < points.length; i++) {
      for (int j = 0; j < points.length; j++) {
        arr1[i][j] = (long) Math.pow(points[i][0], j);
      }
      arr2[i] = points[i][1];
    }
    printSolution(arr1, arr2);
  }

  // Prints solution from double points
  public static void printSolution(double[][] points) {
    double[][] arr1 = new double[points.length][points.length];
    double[] arr2 = new double[points.length];
    for (int i = 0; i < points.length; i++) {
      for (int j = 0; j < points.length; j++) {
        arr1[i][j] = Math.pow(points[i][0], j);
      }
      arr2[i] = points[i][1];
    }
    printSolution(arr1, arr2);
  }

  // Prints solution to set of equations
  public static void printSolution(long[][] arr, long[] arr2) {
    long[][] adjugate = getAdjugate(arr);
    long determinant = getDeterminant(arr);
    if (determinant == 0) {
      System.out.print("none");
      return;
    }
    long[][] newArray = new long[arr2.length][1];
    for (int i = 0; i < arr2.length; i++) {
      newArray[i][0] = arr2[i];
    }
    long[][] product = multiply(adjugate, newArray);
    long[] solution = new long[arr2.length];
    for (int i = 0; i < product.length; i++) {
      solution[i] = product[i][0];
    }
    long gcf = gcf(solution);
    gcf = gcf(gcf, determinant);
    if (determinant < 0) {
      gcf *= -1;
    }
    if (Math.abs(gcf) > 1) {
      for (int i = 0; i < solution.length; i++) {
        solution[i] /= gcf;
      }
      determinant /= gcf;
    }
    printPolynomial(determinant, solution);
  }

  public static void printSolution(double[][] arr, double[] arr2) {
    double[][] adjugate = getAdjugate(arr);

    print(adjugate);

    double determinant = getDeterminant(arr);
    if (determinant == 0) {
      System.out.print("none");
      return;
    }
    double[][] newArray = new double[arr2.length][1];
    for (int i = 0; i < arr2.length; i++) {
      newArray[i][0] = arr2[i];
    }
    double[][] product = multiply(adjugate, newArray);
    long[] nums = new long[product.length];
    long[] dens = new long[product.length];
    long[] fraction;
    double quotient;
    for (int i = 0; i < product.length; i++) {
      quotient = product[i][0] / determinant;
      fraction = approximate(Math.abs(quotient), Math.pow(10, -12));
      nums[i] = fraction[0];
      dens[i] = fraction[1];
      if (quotient < 0) {
        nums[i] *= -1;
      }
    }
    long[][] fractions = toSameDenominator(nums, dens);
    nums = fractions[0];
    dens = fractions[1];
    printPolynomial(dens[0], nums);
  }

  // Don't look at this, this is embarrassing
  public static void printPolynomial(long denominator, long[] coefficients) {
    StringBuilder polynomial = new StringBuilder();
    long gcf = gcf(coefficients);
    if (gcf > 1 && denominator % gcf == 0) {
      denominator /= gcf;
      for (int i = 0; i < coefficients.length; i++) {
        coefficients[i] /= gcf;
      }
    }
    ArrayList<Integer> indices = nonZeroIndices(coefficients);
    polynomial.append("y = ");
    if (indices.isEmpty()) {
      polynomial.append("0");
      return;
    }
    if (denominator != 1) {
      polynomial.append("(");
    }
    for (int i = indices.size() - 1; i >= 0; i--) {
      if (indices.get(i) == 0) {
        polynomial.append(coefficients[indices.get(i)]);
      }
      else {
        if (coefficients[indices.get(i)] != 1) {
          if (coefficients[indices.get(i)] == -1) {
            polynomial.append("-");
          }
          else {
            polynomial.append(coefficients[indices.get(i)]);
          }
        }
      }
      if (indices.get(i) == 1) {
        polynomial.append("x");
      }
      else if (indices.get(i) != 0) {
        polynomial.append("x^");
        polynomial.append(indices.get(i));
      }
      if (i != 0) {
        if (coefficients[indices.get(i - 1)] < 0) {
          coefficients[indices.get(i - 1)] *= -1;
          polynomial.append(" - ");
        }
        else {
          polynomial.append(" + ");
        }
      }
    }
    if (denominator != 1) {
      polynomial.append(") / ");
      polynomial.append(denominator);
    }
    System.out.println(polynomial);
  }

  // Approximates decimal as fraction
  public static long[] approximate(double num, double error) {
    long n1 = 0;
    long d1 = 1;
    long n2 = 1;
    long d2 = 1;
    long numerator = 0;
    long denominator = 1;
    // newNum doesn't need to be set, but it's set to 0, so while loop stops early if num is an int
    double newNum = 0;
    double decimalPart = num - (long) num;
    while (Math.abs(newNum - decimalPart) > error) {
      numerator = n1+n2;
      denominator = d1+d2;
      newNum = (double) numerator/denominator;
      if (newNum < decimalPart) {
        n1 += n2;
        d1 += d2;
      } else if (newNum > decimalPart) {
        n2 += n1;
        d2 += d1;
      }
    }
    // Adds int part of num back into newly acquired fraction
    return new long[] {(long) num * denominator + numerator, denominator};
  }

  // Puts fractions in sameDenominator
  public static long[][] toSameDenominator(long[] nums, long[] dens) {
    long lcm = lcm(dens);
    for (int i = 0; i < nums.length; i++) {
      nums[i] *= lcm/dens[i];
      dens[i] = lcm;
    }
    return new long[][] {nums, dens};
  }

  // Gets lcm of 2 numbers
  public static long lcm(long a, long b) {
    return Math.abs(a*b/gcf(a, b));
  }

  // Gets lcm of numbers in int[]
  public static long lcm(long[] nums) {
    if (nums.length == 0) {
      return -1;
    }
    long lcm = Math.abs(nums[0]);
    for (int i = 0; i < nums.length - 1; i++) {
      lcm = lcm(lcm, nums[i+1]);
    }
    return lcm;
  }

  // Avoids 3x^2 + 5x + 0 becoming 3x^2 + 5x +
  public static ArrayList<Integer> nonZeroIndices(long[] numbers) {
    ArrayList<Integer> indices = new ArrayList<Integer>();
    for (int i = 0; i < numbers.length; i++) {
      if (numbers[i] != 0) {
        indices.add(i);
      }
    }
    return indices;
  }

  public static ArrayList<Integer> nonZeroIndices(double[] numbers) {
    ArrayList<Integer> indices = new ArrayList<Integer>();
    for (int i = 0; i < numbers.length; i++) {
      if (numbers[i] != 0) {
        indices.add(i);
      }
    }
    return indices;
  }

  public static long gcf(long a, long b) {
    ArrayList<Long> factorsA = factor(a);
    ArrayList<Long> factorsB = factor(b);
    ArrayList<Long> inCommon = inCommon(factorsA, factorsB);
    long product = 1;
    for (int i = 0; i < inCommon.size(); i++) {
      product *= inCommon.get(i);
    }
    return product;
  }

  // Gets gcf of numbers in int[]
  public static long gcf(long[] nums) {
    if (nums.length == 0) {
      return -1;
    }
    if (nums.length == 1) {
      return nums[0];
    }
    long gcf = gcf(nums[0], nums[1]);
    for (int i = 1; i < nums.length; i++) {
      if (gcf == 1) {
        break;
      }
      gcf = gcf(gcf, nums[i]);
    }
    return gcf;
  }

  public static ArrayList<Long> factor(long num) {
    return factor(num, new ArrayList<Long>(), 3, new ArrayList<Long>());
  }

  public static ArrayList<Long> factor(long num, ArrayList<Long> smallPrimes, int i, ArrayList<Long> factors) {
    if (num % 2 == 0) {
      if (num == 0) {
        return new ArrayList<Long>();
      }
      factors.add(2L);
      factor(num/2, smallPrimes, i, factors);
      return factors;
    }
    int sqrt = (int) Math.sqrt(num);
    l1: for (; i <= sqrt; i += 2) {
      for (int j = 0; j < smallPrimes.size() - 1; j++) {
        if (i % smallPrimes.get(j) == 0) {
          continue l1;
        } else if (Math.sqrt(i) < smallPrimes.get(j)) {
          break;
        }
      }
      smallPrimes.add((long) i);
      if (num % i == 0) {
        factors.add((long) i);
        factor(num/i, smallPrimes, i, factors);
        return factors;
      }
    }
    if (num != 1 || factors.size() == 0) {
      factors.add(num);
    }
    return factors;
  }

  public static ArrayList<Long> inCommon(ArrayList<Long> arr, ArrayList<Long> arr2) {
    ArrayList<Long> inCommon = new ArrayList<Long>();
    for (int i = arr.size() - 1; i >= 0; i--) {
      for (int j = arr2.size() - 1; j >= 0; j--) {
        if (arr.get(i).equals(arr2.get(j))) {
          inCommon.add(arr.get(i));
          arr.remove(i);
          arr2.remove(j);
          break;
        }
      }
    }
    return inCommon;
  }

  public static void main(String[] args) {
    double[][] points = {
      {0.1, 0.2},
      {0.7, 1.4},
      {1.2, 2.4}
    };
    printSolution(points);
  }



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
        System.out.print(arr[i][j] + " ");
      }
      System.out.println();
    }
  }



}
