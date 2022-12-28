import java.util.ArrayList;

public class NegativeBases {
  public static String toNegative(int num, int base) {
    ArrayList<Integer> arr = new ArrayList<>();
    int radix = -1;
    String answer = null;
    while (!(radix >= 0 && radix < Math.abs(base))) {
      MixedRadix mr = new MixedRadix(arr);
      answer = mr.toRadix(num);

      String[] toRadix = answer.split(" ");
      radix = Integer.parseInt(toRadix[0]);
      arr.add(base);
    }
    return clean(answer);
  }

  public static int fromNegative(String num, int base) {
    int sum = 0;
    int product = 1;
    for (int i = num.length() - 1; i >= 0; i--) {
      sum += Integer.parseInt(num.substring(i, i + 1)) * product;
      product *= base;
    }
    return sum;
  }

  public static String clean(String toClean) {
    if (toClean == null) {
      return null;
    }

    StringBuilder sb = new StringBuilder();
    char[] charArr = toClean.toCharArray();
    for (char letter : charArr) {
      if (letter != ' ') {
        sb.append(letter);
      }
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    System.out.println(toNegative(15532, -8));
    System.out.println(fromNegative("42334", -8));
  }
}
