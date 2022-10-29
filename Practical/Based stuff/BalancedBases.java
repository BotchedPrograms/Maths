import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

// Converts number from one base to another base *balanced*
// For example, base-7-balanced
  // Instead of 0, 1, 2, 3, 4, 5, 6 -- it's -3, -2, -1, 0, 1, 2, 3
    // But c, b, a, instead of -3, -2, -1
  // 621 in base-10 = 2abb in base-7-balanced
    // 2*7^3 + (-1)*7^2 + (-2)*7^1 + (-2) = 621

public class BalancedBases {
  public static String convert(BigInteger num, int base) {
    return convertDecTo(num, base);
  }

  // Converts number from base-baseFrom balanced to base-baseTo unbalanced
  public static String convert(String num, int baseFrom, int baseTo) {
    // Converts from base-baseFrom to base-10 and then from base-10 to base-baseTo
    BigInteger number = convertToDec(num.toCharArray(), baseFrom);
    System.out.println(number + " ");
    return convertDecToPure(number, baseTo);
  }

  // Converts char[] from base-baseFrom to BigInteger in base-10
    // 551 in base-7 = 5*7^2 + 5*7 + 1 = 281
    // This method does that but backwards since it's easier to get powers from (7^0 to 7^10) than from (7^10 to 7^0)
  public static BigInteger convertToDec(char[] digits, int baseFrom) {
    BigInteger sum = BigInteger.ZERO;
    BigInteger pow = BigInteger.ONE;
    BigInteger bF = BigInteger.valueOf(baseFrom);
    for (int i = digits.length - 1; i >= 0; i--) {
      sum = sum.add(pow.multiply(BigInteger.valueOf(charToInt(digits[i]))));
      pow = pow.multiply(bF);
    }
    return sum;
  }

  // Converts BigInteger from base-10 to String in base-baseTo
    // Way this works is similar to that one binary conversion trick
      // How does it work? *shrugs*
      // If I had to guess, it's just the following method backwards
    // Intuitive way would be to first divide by largest power of 7 smaller than num and going on from there
      // 281 in base-7: highest power of 7 is 49
      // 281/49 = 5.734693...   281 % 49 = 36
      // 36/7 = 5.142857...   36 % 7 = 1
      // 1/1 = 1
        // Quotients: 5.734693, 5.142857, 1 --> 551
      // Reason this isn't used is because finding first power of 7 takes time
  public static String convertDecTo(BigInteger num, int baseTo) {
    StringBuilder sb = new StringBuilder();
    BigInteger bT = BigInteger.valueOf(baseTo);
    BigInteger[] bIs;
    while (num.compareTo(BigInteger.ZERO) > 0) {
      bIs = specialDivide(num, bT);
      sb.append(intToChar(bIs[1].intValue()));
      num = bIs[0];
    }
    return sb.reverse().toString();
  }

  // Base-10 as base-10, not base-10-balanced
  public static String convertDecToPure(BigInteger num, int baseTo) {
    StringBuilder sb = new StringBuilder();
    BigInteger bT = BigInteger.valueOf(baseTo);
    while (num.compareTo(BigInteger.ZERO) > 0) {
      sb.append(intToChar(num.mod(bT).intValue()));
      num = num.divide(bT);
    }
    return sb.reverse().toString();
  }

  // Changes char to int by making use of ascii
  public static int charToInt(char digit) {
    if (bounded(digit, 48, 57)) return digit-48;  // Turns '0'-'9' to 0-9
    char[] characters = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '_', '+', '[', ']', '\\', '{', '}', '|', ';', '\'', ':', '"', ',', '.', '/', '<', '>', '?', '`', '~'};
    if (contains(characters, digit)) return indexOf(characters, digit)+10;  // Turns other characters to other ints
    if (bounded(digit, 97, 122)) return 96-digit;  // Turns 'a'-'z' to 10-35
    if (bounded(digit, 65, 90)) return 38-digit;  // Turns 'A'-'Z' to 36-61
    return -1;
  }

  // Changes int to char
  public static char intToChar(int digit) {
    char[] negs = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    char[] pos = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '_', '+', '[', ']', '\\', '{', '}', '|', ';', '\'', ':', '"', ',', '.', '/', '<', '>', '?', '`', '~'};
    if (bounded(digit, -52, -1)) return negs[-digit-1];
    if (bounded(digit, 0, 42)) return pos[digit];  // Turns other ints to other characters
    return ' ';
  }

  // Returns true if num is in between min and max inclusive, false otherwise
  public static boolean bounded(int num, int min, int max) {
    return num >= min && num <= max;
  }

  // Returns index of char in char[], -1 if it's not there
  public static int indexOf(char[] arr, char target) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == target) return i;
    }
    return -1;
  }

  // Returns true if char[] contains char, false otherwise
  public static boolean contains(char[] arr, char target) {
    for (char item : arr) {
      if (item == target) return true;
    }
    return false;
  }

  public static BigInteger[] specialDivide(BigInteger num, BigInteger den) {
    BigDecimal bd = new BigDecimal(num);
    MathContext mc1 = new MathContext(bd.toString().length()+2, RoundingMode.HALF_EVEN);
    BigDecimal a = bd.divide(new BigDecimal(den), mc1);
    BigInteger a2 = a.setScale(0, RoundingMode.HALF_DOWN).toBigInteger();
    BigInteger b = num.subtract(a2.multiply(den));
    return new BigInteger[] {a2, b};
  }

  public static void main(String[] args) {
    // 185426798972741500939003573842745966704138701305310440454 77
      // BigInteger used to deal with numbers like that
    Scanner scan = new Scanner(System.in);
    String[] input;
    char[][] arr1 = {
      {'e', 'd', 'c', 'b', 'a', '0', '1', '2', '3', '4', '5'},
      {'e', 'd', 'c', 'b', 'a', '0', '1', '2', '3', '4', '5', '6'},
      {'f', 'e', 'd', 'c', 'b', 'a', '0', '1', '2', '3', '4', '5', '6'},
      {'f', 'e', 'd', 'c', 'b', 'a', '0', '1', '2', '3', '4', '5', '6', '7'}
    };
    for (char[] cs : arr1) {
      for (char c : cs) {
        System.out.print(charToInt(c) + " ");
      }
      System.out.println();
    }
    char[] arr2 = {
      '2', 'a', 'b', 'b'
    };
    for (char c : arr2) {
      System.out.print(charToInt(c) + " ");
    }
    System.out.println("Enter a number, base converted from (optional), and base converted to: ");
    program: while (true) {
      input = scan.nextLine().split(" ");
      if (!(input.length == 2 || input.length == 3)) {
        for (String s : input) {
          // Checks if String is integer
            // Don't know how it works, I just modified what I saw from stackoverflow
          if (!s.matches("-?\\d+(\\d+)?")) {
            break program;
          }
        }
      }
      if (input.length == 2) {
        // If base-converted-from isn't given, program assumes it's 10
        System.out.println(convert(new BigInteger(input[0]), Integer.parseInt(input[1])));
      } else if (input.length == 3) {
        // Else, it's set to given value
          // Base converted to is notably unbalanced in this case
        System.out.println(convert(input[0], Integer.parseInt(input[1]), Integer.parseInt(input[2])));
      }
    }
    scan.close();
  }
}
