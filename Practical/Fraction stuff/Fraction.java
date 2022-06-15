import java.util.ArrayList;

// FractionMaths but relatively clean class
// Adds, subtracts, multiplies, and divides fractions; standard stuff
public class Fraction {
  private long num;    // Numerator
  private long den;    // Denominator

  // Default Fraction = 1/1
  public Fraction() {
    this(1, 1);
  }

  // Fraction from String
  public Fraction(String fraction) {
    String[] fractionNums = fraction.split("/");
    num = Integer.parseInt(fractionNums[0]);
    den = Integer.parseInt(fractionNums[1]);
  }

  // Fraction from 1 int
  public Fraction(int numerator) {
    num = numerator;
    den = 1;
  }

  // Fraction from 1 double
  public Fraction(double numerator) {
    int[] fraction = approximate(numerator, Math.pow(10, -12));
    num = fraction[0];
    den = fraction[1];
  }

  // Fraction from 2 ints
  public Fraction(int numerator, int denominator) {
    num = numerator;
    den = denominator;
  }

  // Fraction from int[]
  public Fraction(int[] fraction) {
    num = fraction[0];
    den = fraction[1];
  }

  public Fraction(long numerator, long denominator) {
    num = numerator;
    den = denominator;
  }

  // Adds Fractions
  public Fraction add(Fraction frac) {
    return new Fraction(num*frac.den + frac.num*den, den*frac.den);
  }

  // Subtracts Fractions
  public Fraction subtract(Fraction frac) {
    return new Fraction(num*frac.den - frac.num*den, den*frac.den);
  }

  // Multiplies Fractions
  public Fraction multiply(Fraction frac) {
    return new Fraction(num*frac.num, den*frac.den);
  }

  // Divides Fractions
  public Fraction divide(Fraction frac) {
    return new Fraction(num*frac.den, den*frac.num);
  }

  // Simplifies Fraction
  public Fraction simplify() {
    if (den != 0) {
      long gcf = gcf();
      if (gcf > 1) {
        num /= gcf;
        den /= gcf;
      }
    }
    return new Fraction(num, den);
  }

  // Gets numerator
  public long getNum() {
    return num;
  }

  // Gets denominator
  public long getDen() {
    return den;
  }

  // Gets the greatest common factor of numerator and denominator
  public long gcf() {
    ArrayList<Long> factorsA = factor(num);
    ArrayList<Long> factorsB = factor(den);
    ArrayList<Long> inCommon = inCommon(factorsA, factorsB);
    long product = 1;
    for (int i = 0; i < inCommon.size(); i++) {
      product *= inCommon.get(i);
    }
    return product;
  }

  public static ArrayList<Long> factor(long num) {
    return factor(Math.abs(num), new ArrayList<Long>(), 3, new ArrayList<Long>());
  }

  public static ArrayList<Long> factor(long num, ArrayList<Long> smallPrimes, int i, ArrayList<Long> factors) {
    if (num % 2 == 0) {
      factors.add(2L);
      factor(num/2, smallPrimes, i, factors);
      return factors;
    }
    int sqrt = (int) Math.sqrt(num);
    l1: for (; i <= sqrt; i += 2) {
      for (int j = 0; j < smallPrimes.size(); j++) {
        if (i % smallPrimes.get(j) == 0 && i != smallPrimes.get(j)) {
          continue l1;
        } else if (Math.sqrt(i) > smallPrimes.get(j)) {
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

  // Gets double value of Fraction
  public double value() {
    return (double) num / den;
  }

  // Return true if negative, false otherwise
  public boolean isNegative() {
    if (num * den < 0) {
      return true;
    }
    return false;
  }

  // Compares Fractions
    // Negative if less, 0 if equal, positive if more
  public double compareTo(Fraction frac) {
    Fraction frac2 = new Fraction(num, den);
    return frac2.subtract(frac).value();
  }

  // Returns String value of Fraction
  public String toString() {
    return num + "/" + den;
  }

  // Returns true if Fractions are equal, false otherwise
  public boolean equals(Fraction frac) {
    if (compareTo(frac) == 0) {
      return true;
    }
    return false;
  }

  public boolean equals(int num) {
    if (compareTo(new Fraction(num)) == 0) {
      return true;
    }
    return false;
  }

  public static int[] approximate(double num, double error) {
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
    return new int[] {(int) num * denominator + numerator, denominator};
  }

  public static void main(String[] args) {
    Fraction frac = new Fraction("8/9");
    Fraction frac2 = new Fraction(16, 18);
    System.out.println(frac.equals(frac2));
  }
}
