// Adds, subtracts, multiplies, and divides fractions; standard stuff
  // Methods repeated 2-3 times to work for Strings, int[]s, and/or ints
    // String fractions represented like "2/3", "5/6", etc.
  // Made so I don't have to waste time remaking this
public class FractionMaths {
  public static String add(String fraction1, String fraction2) {
    String[] fractionNums1 = fraction1.split("/");
    String[] fractionNums2 = fraction2.split("/");
    int num1 = Integer.parseInt(fractionNums1[0]);
    int den1 = Integer.parseInt(fractionNums1[1]);
    int num2 = Integer.parseInt(fractionNums2[0]);
    int den2 = Integer.parseInt(fractionNums2[1]);
    return simplify("" + (num1 * den2 + num2 * den1) + "/" + (den1 * den2));
  }

  public static int[] add(int[] fraction1, int[] fraction2) {
    return add(fraction1[0], fraction1[1], fraction2[0], fraction2[1]);
  }

  public static int[] add(int num1, int den1, int num2, int den2) {
    int num = num1*den2 + num2*den1;
    int den = den1*den2;
    return simplify(num, den);
  }

  public static String subtract(String fraction1, String fraction2) {
    String[] fractionNums1 = fraction1.split("/");
    String[] fractionNums2 = fraction2.split("/");
    int num1 = Integer.parseInt(fractionNums1[0]);
    int den1 = Integer.parseInt(fractionNums1[1]);
    int num2 = Integer.parseInt(fractionNums2[0]);
    int den2 = Integer.parseInt(fractionNums2[1]);
    return simplify("" + (num1 * den2 - num2 * den1) + "/" + (den1 * den2));
  }

  public static int[] subtract(int[] fraction1, int[] fraction2) {
    return subtract(fraction1[0], fraction1[1], fraction2[0], fraction2[1]);
  }

  public static int[] subtract(int num1, int den1, int num2, int den2) {
    int num = num1*den2 - num2*den1;
    int den = den1*den2;
    return simplify(num, den);
  }

  public static String multiply(String fraction1, String fraction2) {
    String[] fractionNums1 = fraction1.split("/");
    String[] fractionNums2 = fraction2.split("/");
    int num1 = Integer.parseInt(fractionNums1[0]);
    int den1 = Integer.parseInt(fractionNums1[1]);
    int num2 = Integer.parseInt(fractionNums2[0]);
    int den2 = Integer.parseInt(fractionNums2[1]);
    return simplify("" + (num1 * num2) + "/" + (den1 * den2));
  }

  public static int[] multiply(int[] fraction1, int[] fraction2) {
    return multiply(fraction1[0], fraction1[1], fraction2[0], fraction2[1]);
  }

  public static int[] multiply(int num1, int den1, int num2, int den2) {
    int num = num1*num2;
    int den = den1*den2;
    return simplify(num, den);
  }

  public static String divide(String fraction1, String fraction2) {
    String[] fractionNums1 = fraction1.split("/");
    String[] fractionNums2 = fraction2.split("/");
    int num1 = Integer.parseInt(fractionNums1[0]);
    int den1 = Integer.parseInt(fractionNums1[1]);
    int num2 = Integer.parseInt(fractionNums2[0]);
    int den2 = Integer.parseInt(fractionNums2[1]);
    return simplify("" + (num1 * den2) + "/" + (den1 * num2));
  }

  public static int[] divide(int[] fraction1, int[] fraction2) {
    return divide(fraction1[0], fraction1[1], fraction2[0], fraction2[1]);
  }

  public static int[] divide(int num1, int den1, int num2, int den2) {
    int num = num1*den2;
    int den = den1*num2;
    return simplify(num, den);
  }

  public static String average(String fraction1, String fraction2) {
    return divide(add(fraction1, fraction2), "2/1");
  }

  public static int[] average(int[] fraction1, int[] fraction2) {
    return divide(add(fraction1, fraction2), new int[] {2, 1});
  }

  public static int gcf(int a, int b) {
    // Math.min better but troublesome for 0 and negative numbers
    for (int i = Math.max(a,b); i > 0; i--) {
      if (a % i == 0 && b % i == 0) {
        return i;
      }
    }
    return -1;
  }

  public static String simplify(String fraction) {
    String[] fractionNums1 = fraction.split("/");
    int num = Integer.parseInt(fractionNums1[0]);
    int den = Integer.parseInt(fractionNums1[1]);
    if (den == 0) {
      return "undefined";
    } else {
      int gcf = gcf(num, den);
      if (gcf > 1) {
        num /= gcf;
        den /= gcf;
      }
      return "" + num + "/" + den;
    }
  }

  public static int[] simplify(int[] fraction) {
    int num = fraction[0];
    int den = fraction[1];
    return simplify(num, den);
  }

  public static int[] simplify(int num, int den) {
    if (den == 0) {
      return null;
    } else {
      int gcf = gcf(num, den);
      if (gcf > 1) {
        num /= gcf;
        den /= gcf;
      }
      return new int[] {num, den};
    }
  }

  // Groundbreaking, I know
  public static void print(String str) {
    System.out.println(str);
  }

  public static void print(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
      System.out.print(nums[i]);
      System.out.print(" ");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    print(add("5/7", "6/9"));
    print(subtract("5/7", "6/9"));
    print(multiply("5/7", "6/9"));
    print(divide("5/7", "6/9"));
    print(average("5/7", "6/9"));
  }
}
