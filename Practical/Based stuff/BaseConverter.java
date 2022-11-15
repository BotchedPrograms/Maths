import java.math.BigInteger;
import java.util.Scanner;

// Converts number from one base to another. Who woulda thunk

public class BaseConverter {
  // Converts number from base-10 to given base
  public static String convert(BigInteger num, int base) {
    return convertDecTo(num, base);
  }

  // Converts number from base-baseFrom to base-baseTo
  public static String convert(String num, int baseFrom, int baseTo) {
    // Converts from base-baseFrom to base-10 and then from base-10 to base-baseTo
    BigInteger number = convertToDec(num.toCharArray(), baseFrom);
    return convertDecTo(number, baseTo);
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
    if (num.equals(BigInteger.ZERO)) return "0";
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
    if (bounded(digit, 97, 122)) return digit-87;  // Turns 'a'-'z' to 10-35
    if (bounded(digit, 65, 90)) return digit-29;  // Turns 'A'-'Z' to 36-61
    char[] characters = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '_', '+', '[', ']', '\\', '{', '}', '|', ';', '\'', ':', '"', ',', '.', '/', '<', '>', '?', '`', '~'};
    if (contains(characters, digit)) return indexOf(characters, digit)+62;  // Turns other characters to other ints
    return -1;
  }

  // Changes int to char
  public static char intToChar(int digit) {
    if (bounded(digit, 0, 9)) return (char) (digit+48);  // Turns 0-9 to '0'-'9'
    if (bounded(digit, 10, 35)) return (char) (digit+87);  // Turns 10-35 to 'a'-'z'
    if (bounded(digit, 36, 61)) return (char) (digit+29);  // Turns 36-61 to 'A'-'Z'
    char[] characters = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '_', '+', '[', ']', '\\', '{', '}', '|', ';', '\'', ':', '"', ',', '.', '/', '<', '>', '?', '`', '~'};
    if (bounded(digit, 62, 93)) return characters[digit-62];  // Turns other ints to other characters
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

  public static void main(String[] args) {
    // 185426798972741500939003573842745966704138701305310440454 77
      // BigInteger used to deal with numbers like that
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
        // If base-converted-from isn't given, program assumes it's 10
        System.out.println(convert(new BigInteger(input[0]), Integer.parseInt(input[1])));
      } else if (input.length == 3) {
        // Else, it's set to given value
        System.out.println(convert(input[0], Integer.parseInt(input[1]), Integer.parseInt(input[2])));
      }
    }
    scan.close();
  }
}
