import java.util.ArrayList;

// 1/7 is cyclic b/c all its multiples have the same repeating part, but they start in different places
  // 1/7 = 0.142857...
  // 2/7 = 0.285714...
  // 3/7 = 0.428571...
  // 4/7 = 0.571428...
  // 5/7 = 0.714285...
  // 6/7 = 0.857142...
// Divides numbers and lists cyclic denominators like 7

public class CyclicFractions {
  // Prints a/b in base 10
  public static void divide(int a, int b) {
    divide(a, b, 10);
  }

  // Prints a/b in given base
  public static void divide(int a, int b, int base) {
    StringBuilder sb = new StringBuilder();
    sb.append(a/b);
    sb.append(".");
    a %= b;
    ArrayList<Integer> digits = getRepeatingDigits(a, b, base);
    for (Integer digit : digits) {
      sb.append(overload(digit));
    }
    System.out.println(sb);
  }

  // Gets repeating part of 1/b in base 10
  public static ArrayList<Integer> getRepeatingDigits(int b) {
    return getRepeatingDigits(1, b, 10);
  }

  // Gets repeating part of a/b in base 10
  public static ArrayList<Integer> getRepeatingDigits(int a, int b) {
    return getRepeatingDigits(a, b, 10);
  }

  // Gets repeating part of a/b
  public static ArrayList<Integer> getRepeatingDigits(int a, int b, int base) {
    ArrayList<Integer> digits = new ArrayList<>();
    // b-1 b/c there are only that many numbers from [1, b) that the remainder can be
    int[] remainders = new int[b-1];
    int index = 0;
    for (int i = 0; i < b-1; i++) {
      a *= base;
      index = helper(remainders, a % b, digits, a / b, i);
      if (index == -1) {
        digits.add(a/b);
        remainders[i] = a%b;
      } else {
        break;
      }
      a %= b;
    }
    if (index > 0) {
      digits.subList(0, index).clear();
    }
    return digits;
  }

  // Gets decimal of 1/b in base 10 before it repeats
    // 1/70 = 0.0142857
      // 142857 is the repeating bit
      // 0142857 is the decimal before it repeats
  public static ArrayList<Integer> getDigits(int b) {
    return getDigits(1, b, 10);
  }

  // Gets decimal of a/b in base 10 before it repeats
  public static ArrayList<Integer> getDigits(int a, int b) {
    return getDigits(a, b, 10);
  }

  // Gets decimal of a/b in give base before it repeats
  public static ArrayList<Integer> getDigits(int a, int b, int base) {
    ArrayList<Integer> digits = new ArrayList<>();
    // b-1 b/c there are only that many numbers from [1, b) that the remainder can be
    int[] remainders = new int[b-1];
    int index;
    for (int i = 0; i < b-1; i++) {
      a *= base;
      index = helper(remainders, a % b, digits, a / b, i);
      if (index == -1) {
        digits.add(a/b);
        remainders[i] = a%b;
      } else {
        break;
      }
      a %= b;
    }
    return digits;
  }

  public static int helper(int[] remainders, int mod, ArrayList<Integer> digits, int quo, int index) {
    // Made to stop 1/4 = 0.250 being considered cyclic, having a 0 digit and 0 mod makes it return early
    if (mod == 0 && quo == 0) {
      return 0;
    }
    for (int i = 0; i < index; i++) {
      if (remainders[i] == mod && digits.get(i) == quo) {
        return i;
      }
    }
    return -1;
  }

  // Turns n to character
  public static char overload(int n) {
    String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    return characters.charAt(n);
  }

  // Returns cyclic denominator from start to end in base 10
  public static ArrayList<Integer> getCyclics(int start, int end) {
    return getCyclics(start, end, 10);
  }

  // Returns cyclic denominator from start to end in given base
  public static ArrayList<Integer> getCyclics(int start, int end, int base) {
    ArrayList<Integer> cyclics = new ArrayList<>();
    for (int i = start; i <= end; i++) {
      // Number is cyclic if remainders go through all possible values from [1, i)
      // i.e. if repeating part is i-1 digits long
      if (getRepeatingDigits(1, i, base).size() == i-1) {
        cyclics.add(i);
      }
    }
    return cyclics;
  }

  // Prints an int array
  public static void print(int[] arr) {
    StringBuilder sb = new StringBuilder();
    for (int num : arr) {
      sb.append(num);
      sb.append(" ");
    }
    System.out.println(sb);
  }

  public static void main(String[] args) {
    System.out.println(getCyclics(3, 100));
  }
}
