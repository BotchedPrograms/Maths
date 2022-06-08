// Approximates fraction from decimal using the Farey sequence
public class FractionApproximation {
  public static void approximate(double num, double error) {
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
    System.out.println(((int) num * (denominator) + numerator) + "/" + denominator);
  }

  public static void main(String[] args) {
    approximate(28.074433, 0.00001);
  }
}
