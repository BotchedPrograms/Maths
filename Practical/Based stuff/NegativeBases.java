import java.math.BigInteger;

public class NegativeBases {
  public static BigInteger[] divide(BigInteger num, BigInteger den) {
    BigInteger a = num.divide(den);
    BigInteger b = num.subtract(a.multiply(den));
    return new BigInteger[] {a, b};
  }

  public static String convertDecTo(BigInteger num, int baseTo) {
    StringBuilder sb = new StringBuilder();
    BigInteger bT = BigInteger.valueOf(baseTo);
    BigInteger[] bIs;
    while (num.compareTo(BigInteger.ZERO) != 0) {
      bIs = divide(num, bT);
      sb.append(intToChar(Math.abs(bIs[1].intValue())));
      num = bIs[0];
    }
    return sb.reverse().toString();
  }

  public static char intToChar(int digit) {
    if (bounded(digit, 0, 9)) return (char) (digit+48);  // Turns 0-9 to '0'-'9'
    if (bounded(digit, 10, 35)) return (char) (digit+87);  // Turns 10-35 to 'a'-'z'
    if (bounded(digit, 36, 61)) return (char) (digit+29);  // Turns 36-61 to 'A'-'Z'
    char[] characters = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '_', '+', '[', ']', '\\', '{', '}', '|', ';', '\'', ':', '"', ',', '.', '/', '<', '>', '?', '`', '~'};
    if (bounded(digit, 62, 93)) return characters[digit-62];  // Turns other ints to other characters
    return ' ';
  }

  public static boolean bounded(int num, int min, int max) {
    return num >= min && num <= max;
  }

  public static void main(String[] args) {
    for (int i = -42; i < 6; i++) {
      BigInteger b1 = BigInteger.valueOf(i);
      BigInteger b2 = BigInteger.valueOf(-7);
      BigInteger[] arr = divide(b1, b2);
      System.out.println(convertDecTo(b1, -7));
    }
  }
}
