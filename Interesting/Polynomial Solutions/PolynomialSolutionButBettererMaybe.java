import java.util.ArrayList;

// Same as PolynomialSolutionButBetter except this uses Fractions instead of doubles
  // More accurate but whole ordeal uses a lot of memory
    // Tried to get a java profiler to look at it, but gave up

public class PolynomialSolutionButBettererMaybe {
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

  public static Fraction[][] multiply(Fraction[][] arr, Fraction[][] arr2) {
    if (arr == null || arr2 == null) {
      return null;
    }
    Fraction[][] newArray = new Fraction[arr.length][arr2[0].length];
    for (int i = 0; i < newArray.length; i++) {
      for (int j = 0; j < newArray[0].length; j++) {
        newArray[i][j] = new Fraction(0);
      }
    }
    // Classic triple loop
    for (int i = 0; i < newArray.length; i++) {
      for (int j = 0; j < newArray[0].length; j++) {
        for (int k = 0; k < arr2.length; k++) {
          newArray[i][j] = newArray[i][j].add(arr[i][k].multiply(arr2[k][j])).simplify();
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

  public static Fraction getDeterminant(Fraction[][] arr) {
    if (arr.length == 1) {
      return arr[0][0];
    }
    Fraction answer = new Fraction(0);
    Fraction frac;
    // Not necessary, i could be 0 for all we care, it just makes program more efficient b/c more 0s equals less work as explained right below
    int i = rowWithMostZeros(arr);
    for (int j = 0; j < arr.length; j++) {
      // If arr[i][j] == 0, answer is just += 0 and we needn't waste time calculating everything else
      if (!arr[i][j].equals(0)) {
        frac = arr[i][j];
        // Explanation for (i+j)%2*(-2)+1:
        // Imagine a chess board where top left and same-colored tiles stay the same and others multiplied by -1
        answer = answer.add(frac.multiply(new Fraction((i+j)%2*(-2)+1)).multiply(getDeterminant(newArray(arr, i, j))));
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

  public static int rowWithMostZeros(Fraction[][] arr) {
    int rowWithMostZeros = 0;
    int numZeros;
    int currentMaxZeros = 0;
    for (int i = 0; i < arr.length; i++) {
      numZeros = 0;
      for (int j = 0; j < arr[0].length; j++) {
        if (arr[i][j].equals(0)) {
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

  public static Fraction[][] newArray(Fraction[][] arr, int x, int y) {
    Fraction[][] newArr = new Fraction[arr.length-1][arr.length-1];
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

  public static Fraction[][] getTranspose(Fraction[][] arr) {
    Fraction[][] transpose = new Fraction[arr.length][arr.length];
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

  public static Fraction[][] getAdjugate(Fraction[][] arr) {
    Fraction[][] transpose = getTranspose(arr);
    Fraction[][] adjugate = new Fraction[arr.length][arr.length];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        adjugate[i][j] = new Fraction((i+j)%2*(-2)+1).multiply(getDeterminant(newArray(transpose, i, j))).simplify();
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
    Fraction[][] newPoints = new Fraction[points.length][points[0].length];
    for (int i = 0; i < points.length; i++) {
      for (int j = 0; j < points[i].length; j++) {
        newPoints[i][j] = new Fraction(points[i][j]);
      }
    }
    printSolution(newPoints);
  }

  // Prints solution from Fraction points
  public static void printSolution(Fraction[][] points) {
    Fraction[][] arr1 = new Fraction[points.length][points.length];
    Fraction[] arr2 = new Fraction[points.length];
    for (int i = 0; i < points.length; i++) {
      for (int j = 0; j < points.length; j++) {
        arr1[i][j] = new Fraction(1);
        for (int k = 0; k < j; k++) {
          arr1[i][j] = arr1[i][j].multiply(points[i][0]);
        }
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

  public static void printSolution(Fraction[][] arr, Fraction[] arr2) {
    Fraction[][] adjugate = getAdjugate(arr);
    Fraction determinant = getDeterminant(arr).simplify();
    if (determinant.equals(0)) {
      System.out.print("none");
      return;
    }
    Fraction[][] newArray = new Fraction[arr2.length][1];
    for (int i = 0; i < arr2.length; i++) {
      newArray[i][0] = arr2[i];
    }
    Fraction[][] product = multiply(adjugate, newArray);
    long[] nums = new long[product.length];
    long[] dens = new long[product.length];
    for (int i = 0; i < product.length; i++) {
      product[i][0] = product[i][0].divide(determinant).simplify();
      nums[i] = product[i][0].getNum();
      dens[i] = product[i][0].getDen();
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

  // Gets lcm of numbers in long[]
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
    ArrayList<Integer> indices = new ArrayList<>();
    for (int i = 0; i < numbers.length; i++) {
      if (numbers[i] != 0) {
        indices.add(i);
      }
    }
    return indices;
  }

  public static ArrayList<Integer> nonZeroIndices(Fraction[] numbers) {
    ArrayList<Integer> indices = new ArrayList<>();
    for (int i = 0; i < numbers.length; i++) {
      if (!numbers[i].equals(0)) {
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
    for (Long aLong : inCommon) {
      product *= aLong;
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
    return factor(Math.abs(num), new ArrayList<>(), 3, new ArrayList<>());
  }

  public static ArrayList<Long> factor(long num, ArrayList<Long> smallPrimes, int i, ArrayList<Long> factors) {
    if (num % 2 == 0) {
      if (num == 0) {
        return new ArrayList<>();
      }
      factors.add(2L);
      factor(num/2, smallPrimes, i, factors);
      return factors;
    }
    int sqrt = (int) Math.sqrt(num);
    l1: for (; i <= sqrt; i += 2) {
      for (Long smallPrime : smallPrimes) {
        if (i % smallPrime == 0 && i != smallPrime) {
          continue l1;
        } else if (Math.sqrt(i) > smallPrime) {
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
    ArrayList<Long> inCommon = new ArrayList<>();
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
      {0.4, 0.1},
      {0.2, 0.5},
      {0.6, 0.7}
    };
    printSolution(points);
  }
}
