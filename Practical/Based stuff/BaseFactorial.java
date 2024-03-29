// Base-10 to Base-Factorial and vice versa
  // "Factorial base representation has applications in combinatorics and cryptography" so that's cool

public class BaseFactorial {
  // Gets factorial
  public static long factorial(int num) {
    long fac = 1;
    for (int i = 1; i <= num; i++) {
      fac *= i;
    }
    return fac;
  }

  // Converts numbers to characters
  public static char overboard(long num) {
    String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    return alphabet.charAt((int) num);
  }

  // Converts Strings to numbers
  public static long overboard(String digit) {
    String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    return alphabet.indexOf(digit);
  }

  // Converts decimal to base-factorial
  public static String dec2FactString(long num) {
    long i = 1;
    long pro = 1;
    while (num > i*pro) {
      i++;
      pro *= i;
    }
    StringBuilder digits = new StringBuilder();
    for (long k = i; k > 0; k--) {
      digits.append(overboard(num / pro));
      num %= pro;
      pro /= k;
    }
    digits.append("0");
    return digits.toString();
  }

  // Converts base-factorial to decimal
  public static long factString2Dec(String str) {
    long num = 0;
    for (int i = 0; i < str.length(); i++) {
      num += overboard(Character.toString(str.charAt(i)))*factorial(str.length()-i-1);
    }
    return num;
  }
}
