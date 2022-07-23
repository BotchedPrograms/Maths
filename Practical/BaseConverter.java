import java.math.BigInteger;
import java.util.Scanner;

// Converts number from one base to another. Who woulda thunk

public class BaseConverter {
  public static String convert(BigInteger num, int base) {
    return convertDecTo(num, base);
  }

  public static String convert(String num, int baseFrom, int baseTo) {
    BigInteger number = convertToDec(num.toCharArray(), baseFrom);
    return convertDecTo(number, baseTo);
  }

  public static BigInteger convertToDec(char[] digits, int baseFrom) {
    BigInteger sum = BigInteger.ZERO;
    BigInteger pow = BigInteger.ONE;
    BigInteger bF = BigInteger.valueOf(baseFrom);
    for (int i = digits.length - 1; i >= 0; i--) {
      sum = sum.add(pow.multiply(BigInteger.valueOf(overload(digits[i]))));
      pow = pow.multiply(bF);
    }
    return sum;
  }

  public static String convertDecTo(BigInteger num, int baseTo) {
    StringBuilder sb = new StringBuilder();
    BigInteger bT = BigInteger.valueOf(baseTo);
    while (num.compareTo(BigInteger.ZERO) > 0) {
      sb.append(overload(num.mod(bT).intValue()));
      num = num.divide(bT);
    }
    return sb.reverse().toString();
  }

  public static int overload(char digit) {
    if (bounded(digit, 48, 57)) return digit-48;
    if (bounded(digit, 97, 122)) return digit-87;
    if (bounded(digit, 65, 90)) return digit-29;
    char[] characters = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '_', '+', '[', ']', '\\', '{', '}', '|', ';', '\'', ':', '"', ',', '.', '/', '<', '>', '?', '`', '~'};
    if (contains(characters, digit)) return indexOf(characters, digit)+62;
    return -1;
  }

  public static char overload(int digit) {
    if (bounded(digit, 0, 9)) return (char) (digit+48);
    if (bounded(digit, 10, 35)) return (char) (digit+87);
    if (bounded(digit, 36, 61)) return (char) (digit+29);
    char[] characters = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '_', '+', '[', ']', '\\', '{', '}', '|', ';', '\'', ':', '"', ',', '.', '/', '<', '>', '?', '`', '~'};
    if (bounded(digit, 62, 93)) return characters[digit-62];
    return ' ';
  }

  public static boolean bounded(int num, int min, int max) {
    return num >= min && num <= max;
  }

  public static int indexOf(char[] arr, char target) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == target) return i;
    }
    return -1;
  }

  public static boolean contains(char[] arr, char target) {
    for (char item : arr) {
      if (item == target) return true;
    }
    return false;
  }

  public static void main(String[] args) {
    // 185426798972741500939003573842745966704138701305310440454 77
    Scanner scan = new Scanner(System.in);
    String[] input;
    System.out.println("Enter a number, base converted from (optional), and base converted to: ");
    program: while (true) {
      input = scan.nextLine().split(" ");
      // Checks if String is integer
        // Don't know how it works, I just modified what I saw from stackoverflow
      if (!(input.length == 2 || input.length == 3)) {
        for (String s : input) {
          if (!s.matches("-?\\d+(\\d+)?")) {
            break program;
          }
        }
      }
      if (input.length == 2) {
        System.out.println(convert(new BigInteger(input[0]), Integer.parseInt(input[1])));
      } else if (input.length == 3) {
        System.out.println(convert(input[0], Integer.parseInt(input[1]), Integer.parseInt(input[2])));
      }
    }
    scan.close();
  }
}
