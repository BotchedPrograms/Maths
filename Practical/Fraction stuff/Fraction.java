// FractionMaths but relatively clean class

// Adds, subtracts, multiplies, and divides fractions; standard stuff
public class Fraction {
  private int num;    // Numerator
  private int den;    // Denominator

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

  // Adds Fractions
  public Fraction add(Fraction frac) {
    num = num*frac.den + frac.num*den;
    den = den*frac.den;
    return new Fraction(num, den);
  }

  // Subtracts Fractions
  public Fraction subtract(Fraction frac) {
    num = num*frac.den - frac.num*den;
    den = den*frac.den;
    return new Fraction(num, den);
  }

  // Multiplies Fractions
  public Fraction multiply(Fraction frac) {
    num = num*frac.num;
    den = den*frac.den;
    return new Fraction(num, den);
  }

  // Divides Fractions
  public Fraction divide(Fraction frac) {
    num = num*frac.den;
    den = den*frac.num;
    return new Fraction(num, den);
  }

  // Simplifies Fraction
  public Fraction simplify() {
    if (den != 0) {
      int gcf = gcf();
      if (gcf > 1) {
        num /= gcf;
        den /= gcf;
      }
      return new Fraction(num, den);
    }
    return null;
  }

  // Gets the greatest common factor of numerator and denominator
  public int gcf() {
    for (int i = Math.min(Math.abs(num), Math.abs(den)); i > 0; i--) {
      if (num % i == 0 && den % i == 0) {
        return i;
      }
    }
    return -1;
  }

  // Gets double value of Fraction
  public double value() {
    return (double) num / den;
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

  public static void main(String[] args) {
    Fraction frac = new Fraction("8/9");
    Fraction frac2 = new Fraction(16, 18);
    System.out.println(frac.equals(frac2));
  }
}
