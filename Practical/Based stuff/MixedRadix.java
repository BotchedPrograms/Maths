import java.util.Scanner;

public class MixedRadix {
  private int[] radices;

  public MixedRadix(int[] radices) {
    this.radices = radices;
  }

  public MixedRadix(String line) {
    setRadices(line);
  }

  public String toRadix(int num) {
    int[] digits = new int[radices.length + 1];
    int product = 1;
    for (int i = 0; i < radices.length; i++) {
      int radix = radices[radices.length - i - 1];
      digits[i] = (num / product) % radix;
      product *= radix;
    }
    digits[digits.length - 1] = num/product;

    StringBuilder sb = new StringBuilder();
    sb.append(digits[digits.length - 1]);
    for (int i = 1; i < digits.length; i++) {
      sb.append(" ");
      sb.append(digits[digits.length - i - 1]);
    }

    return sb.toString();
  }

  public int fromRadix(String tab) {
    String[] digits = tab.split(" ");
    int product = 1;
    int sum = 0;
    for (int i = 0; i < radices.length; i++) {
      int digit = Integer.parseInt(digits[digits.length - i - 1]);
      sum += digit * product;
      product *= radices[radices.length - i - 1];
    }
    int digit = Integer.parseInt(digits[0]);
    sum += digit * product;

    return sum;
  }

  public void setRadices(String input) throws IllegalArgumentException {
    String[] radices = input.split(" ");
    this.radices = new int[radices.length];
    for (int i = 0; i < radices.length; i++) {
      if (radices[i].matches("-?\\d+(\\d+)?")) {
        this.radices[i] = Integer.parseInt(radices[i]);
      } else {
        throw new IllegalArgumentException();
      }
    }
  }

  public int[] getRadices() {
    int[] copy = new int[radices.length];
    System.arraycopy(radices, 0, copy, 0, radices.length);
    return copy;
  }

  public static void print(int[] nums) {
    for (int num : nums) {
      System.out.print(num + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    System.out.println("Enter radices:");

    MixedRadix mr = new MixedRadix("7 24 60");
    //print(mr.getRadices());
    System.out.println(mr.toRadix(15532));
    System.out.println(mr.fromRadix("1 3 18 52"));
  }
}
