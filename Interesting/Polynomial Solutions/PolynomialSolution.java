import java.util.ArrayList;

// Put in some numbers, and it will give you a polynomial with y = those numbers for x = 0, 1, 2...
  // For example, {0, 1, 4, 9} gives y = x^2
  // Not just any polynomial mind you, but the simplest one
// As long as you don't go too ham on the numbers, it should be fine
// More info at the bottom; I don't wanna clutter up the top too much

public class PolynomialSolution {
  // Gets gcf of 2 numbers
    // Gets prime factors and returns the product of the ones they have in common
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

  // Gets gcf of numbers in long[]
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
    return factor(num, 3, new ArrayList<>());
  }

  public static ArrayList<Long> factor(long num, long i, ArrayList<Long> factors) {
    if (num % 2 == 0) {
      if (num == 0) {
        return factors;
      }
      factors.add(2L);
      return factor(num/2, i, factors);
    }
    long sqrt = (long) Math.sqrt(num);
    for (; i <= sqrt; i += 2) {
      if (num % i == 0) {
        factors.add(i);
        return factor(num/i, i, factors);
      }
    }
    if (num != 1) {
      factors.add(num);
    }
    return factors;
  }

  // In case you're wondering, I made this term up
  public static long[] ultimateDerivative(long[] numbers) {
    ArrayList<Long> numbers1 = new ArrayList<Long>();
    ArrayList<Long> numbers2 = new ArrayList<Long>();
    // Number 1 is the actual value, number 2 is how many steps it took
    long[] answer = new long[2];
    for (int i = 0; i < numbers.length; i++) {
      numbers1.add(numbers[i]);
    }
    for (int i = 0; i < numbers.length; i++) {
      if (i % 2 == 0) {
        if (allTheSame(numbers1)) {
          answer[0] = numbers1.get(0);
          answer[1] = i;
          return answer;
        }
        numbers2 = differences(numbers1);
      } else {
        if (allTheSame(numbers2)) {
          answer[0] = numbers2.get(0);
          answer[1] = i;
          return answer;
        }
        numbers1 = differences(numbers2);
      }
    }
    answer[0] = -1;
    answer[1] = 1;
    return answer;
  }

  // Gets differences between consecutives numbers
  public static ArrayList<Long> differences(ArrayList<Long> numbers) {
    ArrayList<Long> numbers1 = new ArrayList<Long>();
    for (int i = 1; i < numbers.size(); i++) {
      numbers1.add(numbers.get(i) - numbers.get(i - 1));
    }
    return numbers1;
  }

  // Returns true if all the numbers are the same, false otherwise
  public static boolean allTheSame(ArrayList<Long> numbers) {
    for (int i = 1; i < numbers.size(); i++) {
      if (!numbers.get(i).equals(numbers.get(i - 1))) {
        return false;
      }
    }
    return true;
  }

  // Gets factorial of a number
  public static long factorial(int n) {
    long fac = 1;
    for (long i = n; i > 1; i--) {
      fac *= i;
    }
    return fac;
  }

  // Gets coefficients of polynomial
  public static long[] getCoefficients(long[] numbers) {
    long[] newNumbers = new long[numbers.length];
    long[] newNumbers2 = new long[numbers.length];
    long[] coefficients = new long[numbers.length];
    long[] ultDerive;
    int steps;
    int totalSteps = (int) ultimateDerivative(numbers)[1];
    long denominator = factorial(totalSteps);
    for (int i = 0; i < numbers.length; i++) {
      newNumbers[i] = numbers[i] * denominator;
    }
    for (int i = 0; i <= totalSteps; i++) {
      if (i % 2 == 0) {
        ultDerive = ultimateDerivative(newNumbers);
        steps = (int) ultDerive[1];
        coefficients[steps] = ultDerive[0] / factorial(steps);
        for (int j = 0; j < numbers.length; j++) {
          newNumbers2[j] = newNumbers[j] - coefficients[steps] * (int) Math.pow(j, steps);
        }
      } else {
        ultDerive = ultimateDerivative(newNumbers2);
        steps = (int) ultDerive[1];
        coefficients[steps] = ultDerive[0] / factorial(steps);
        for (int j = 0; j < numbers.length; j++) {
          newNumbers[j] = newNumbers2[j] - coefficients[steps] * (int) Math.pow(j, steps);
        }
      }
      if (steps == 0) {
        break;
      }
    }
    return coefficients;
  }

  // Don't look at this, this is embarrassing
  public static void printPolynomial(long[] numbers) {
    // StringBuilder used to print polynomial all at once
    // Also, it's more efficient at appending Strings
    StringBuilder polynomial = new StringBuilder();
    long[] coefficients = getCoefficients(numbers);
    long gcf = gcf(coefficients);
    long denominator = factorial((int) ultimateDerivative(numbers)[1]);
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
      } else {
        if (coefficients[indices.get(i)] != 1) {
          if (coefficients[indices.get(i)] == -1) {
            polynomial.append("-");
          } else {
            polynomial.append(coefficients[indices.get(i)]);
          }
        }
      }
      if (indices.get(i) == 1) {
        polynomial.append("x");
      } else if (indices.get(i) != 0) {
        polynomial.append("x^");
        polynomial.append(indices.get(i));
      }
      if (i != 0) {
        if (coefficients[indices.get(i-1)] < 0) {
          coefficients[indices.get(i-1)] *= -1;
          polynomial.append(" - ");
        } else {
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

  // Gets min of absolute value of numbers
  public static long minAbs(long[] numbers) {
    long minAbs = Math.abs(numbers[0]);
    for (int i = 1; i < numbers.length; i++) {
      if (Math.abs(numbers[i]) < minAbs) {
        minAbs = Math.abs(numbers[i]);
      }
    }
    return minAbs;
  }

  // Prints numbers in array
  public static void print(int[] numbers) {
    for (int i = 0; i < numbers.length; i++) {
      System.out.print(numbers[i] + " ");
    }
    System.out.println();
  }

  // Prints numbers in arraylist
  public static void print(ArrayList<Integer> numbers) {
    for (int i = 0; i < numbers.size(); i++) {
      System.out.print(numbers.get(i) + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    long[] numbers = {93, 884, 313, 611};
    printPolynomial(numbers);
  }
}

// Where did this come from: Me
  // Someone else likely discovered this earlier though; I wouldn't know
// How this works: x^n derived = n*x^(n-1)
  // Repeating until exponent = 0, it becomes n!
  // Some polynomial a*x^n + bx^(n-1) ultimately derivates into a*n!
  // a is found, a*x^n is removed from starting numbers, and process repeated to find rest of polynomial
// When is this useful: Never really, it's just kinda cool
  // Most numbers irl are like linear or exponential but not polynomials of order 3-5 which this would actually be useful for
  // Held back by x having to be 0, 1, 2...
  // Linear x can be substituted though
    // So like you could do {0, 1, 4, 9} -> y = x^2 for x = 0, 1, 2... <=> y = (-1/2x+4)^2 for x = 8, 6, 4... if you really want
      // x -> -1/2x + 4   y = -1/2x + 4   x = -2(y-4)   0, 1, 2... -> 8, 6, 4...
  // Doing approximations with this is impractical since these polynomials diverge extremely quickly
