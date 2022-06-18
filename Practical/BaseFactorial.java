// Base-10 to Base-Factorial and vice versa
  // "Factorial base representation has applications in combinatorics and crpytography" so that's cool

public class BaseFactorial {
  // Gets factorial
    // Seems to be generally faster than recursion; point taken
  public static long factorial(int nb) {
    long fac = 1;
    for (int i = 1; i <= nb; i++) {
      fac *= i;
    }
    return fac;
  }

  // Converts numbers to characters
  public static char overboard(long nb) {
    // nb short for number, huh?; damn I was lazy
      // Nevermind, it was given
    // Converts nb to letter 10-35 -> (A-Z)
    if (nb > 9) {
      return (char) (nb+55);
    }
    // Converts nb to digit 0-9 -> (0-9)
    return (char) (nb+48);
  }

  // Converts Strings to numbers
  public static long overboard(String digit) {
    String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    return (long) alphabet.indexOf(digit)+10;
  }

  // Converts decimal to base-factorial
  public static String dec2FactString(long nb) {
    long i = 1;
    long pro = 1;
    while (nb > i*pro) {
      i++;
      pro *= i;
    }
    String digits = "";
    for (long k = i; k > 0; k--) {
      digits += overboard(nb/pro);
      nb %= pro;
      pro /= k;
    }
    digits += "0";
    return digits;
  }

  // Converts base-factorial to decimal
  public static long factString2Dec(String str) {
    long nb = 0;
    for (int i = 0; i < str.length(); i++) {
      nb += overboard(Character.toString(str.charAt(i)))*factorial(str.length()-i-1);
    }
    return nb;
  }
}
