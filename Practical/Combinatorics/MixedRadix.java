import java.util.ArrayList;

public class MixedRadix {
  private int[] radices;

  /**
   * Creates a MixedRadix with the provided int array as radices (i.e. bases).
   *
   * @param radices int array of radices
   */
  public MixedRadix(int[] radices) {
    this.radices = radices;
  }

  /**
   * Creates a MixedRadix with the provided ArrayList as radices (i.e. bases).
   *
   * @param arrList Integer ArrayList of radices
   */
  public MixedRadix(ArrayList<Integer> arrList) {
    this.radices = arrListToArr(arrList);
  }

  /**
   * Creates a MixedRadix with the provided String as radices (i.e. bases).
   * <p>
   *   "7 24 60" is treated the same as {7, 24 ,60}
   * </p>
   *
   * @param line String of radices
   */
  public MixedRadix(String line) {
    setRadices(line);
  }

  /**
   * Returns an int array representation of the "mixed radix numeral" of num
   * using this MixedRadix's radices.
   * <p>
   *   For example, 10000 minutes = 0 weeks, 6 days, 22 hours, and 40 minutes.
   *   Thus, toRadix(10000) = {0, 6, 22, 40} (if radices are 7, 24, 60).
   * </p>
   *
   * @param num int whose mixed radix numeral is returned
   * @return an int array representing the mixed radix numeral of num
   */
  public int[] toRadix(int num) {
    int[] digits = new int[radices.length + 1];
    int quotient = num;
    for (int i = 0; i < radices.length; i++) {
      int radix = radices[radices.length - i - 1];
      // Use of modifiedMod and euclideanDivision allows it to work with negative radices
      digits[digits.length - i -1] = modifiedMod(quotient, radix);
      quotient = euclideanDivision(quotient, radix);
    }
    digits[0] = quotient;

    return digits;
  }

  /**
   * Returns a String representation of the "mixed radix numeral" of num
   * using this MixedRadix's radices.
   * <p>
   *   For example, 10000 minutes = 0 weeks, 6 days, 22 hours, and 40 minutes.
   *   Thus, toRadix(10000) = "0 6 22 40" (if radices are 7, 24, 60).
   * </p>
   *
   * @param num int whose mixed radix numeral is returned
   * @return a String representing the mixed radix numeral of num
   */
  public String toRadixString(int num) {
    int[] digits = toRadix(num);

    StringBuilder sb = new StringBuilder();
    sb.append(digits[0]);
    for (int i = 1; i < digits.length; i++) {
      sb.append(" ");
      sb.append(digits[i]);
    }

    return sb.toString();
  }

  /**
   * Returns the int value of an int array using this MixedRadix's radices.
   * <p>
   *   For example, 0 weeks, 6 days, 22 hours, and 40 minutes = 10000 minutes.
   *   Thus, fromRadix("0 6 22 40") = 10000 (if radices are 7, 24, 60).
   * </p>
   *
   * @param digits the int array representation of a mixed radix numeral whose value is returned
   * @return the int value of tab using this MixedRadix's radices
   */
  public int fromRadix(int[] digits) {
    int sum = 0;
    int product = 1;
    for (int i = 0; i < digits.length - 1; i++) {
      int digit = digits[digits.length - i - 1];
      sum += digit * product;
      /* Unfortunate edge case caused by how there can be more numbers
        in tab than there are radices. 1 more to be exact. Why they
        aren't equal is because the first radix should always be infinity,
        but that sure doesn't fit in an int array nicely. Besides, excluding
        it isn't that a big deal.
       */
      if (radices.length - i - 1 >= 0) {
        product *= radices[radices.length - i - 1];
      }
    }
    sum += digits[0] * product;
    return sum;
  }

  /**
   * Returns the int value of a String using this MixedRadix's radices.
   * <p>
   *   For example, 0 weeks, 6 days, 22 hours, and 40 minutes = 10000 minutes.
   *   Thus, fromRadix("0 6 22 40") = 10000 (if radices are 7, 24, 60).
   * </p>
   *
   * @param tab the String representation of a mixed radix numeral whose value is returned
   * @return the int value of tab using this MixedRadix's radices
   */
  public int fromRadix(String tab) {
    String[] digits = tab.split(" ");
    int[] digitsInts = new int[digits.length];
    for (int i = 0; i < digits.length; i++) {
      digitsInts[i] = Integer.parseInt(digits[i]);
    }
    return fromRadix(digitsInts);
  }

  /**
   * Returns the least positive residue of the 2 provided ints.
   * <p>
   *   In other words, this returns num % den but always non-negative.
   *   num % den in Java can be negative if num is negative. This is
   *   presumably because of how Java handles division, which is
   *   elaborated on in the description for euclideanDivision(). For
   *   the purposes of this program, we want it to always be non-negative,
   *   so the program can process negative radices and return non-negative
   *   digits (except for the first digit potentially).
   * </p>
   *
   * @param num the numerator (of a fraction or more formally,
   *            the dividend)
   * @param den the denominator (of a fraction or more formally,
   *            the divisor).
   * @return the least positive residue of num and den
   */
  private static int modifiedMod(int num, int den) {
    int quotient = euclideanDivision(num, den);
    return num - den * quotient;
  }

  /**
   * Returns the quotient of the 2 provided ints using Euclidean division.
   * <p>
   *   Programming languages handle division differently depending on how
   *   the designers see fit.
   * </p>
   * <p>
   *   The way languages like Java and C handle division, the quotient essentially
   *   rounds towards 0, so -43/7 = -6.
   * </p>
   * <p>
   *   This differs from the Euclidean definition (and how languages like
   *   Ruby and Python handle division), which essentially rounds the quotient
   *   to negative infinity, so -43/7 = -7.
   * </p>
   * <p>
   *   This is not to say that Python is better; Python is slow, dangerously
   *   unstructured, and I'm more used to Java.
   * </p>
   * <p>
   *   This method is needed for modifiedMod to work. This implementation
   *   uses the technique promoted by one Raymond T. Boute.
   * </p>
   * <p>
   *   Sources: https://en.wikipedia.org/wiki/Modulo_operation
   * </p>
   * <p>
   *   https://stackoverflow.com/questions/19517868/integer-division-by-negative-number
   * </p>
   *
   * @param num the numerator (of a fraction or more formally,
   *            the dividend)
   * @param den the denominator (of a fraction or more formally,
   *            the divisor).
   * @throws IllegalArgumentException if den is 0
   * @return the quotient of num and den
   */
  private static int euclideanDivision(int num, int den) {
    if (den > 0) {
      return Math.floorDiv(num, den);
    } else if (den < 0) {
      return Math.ceilDiv(num, den);
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Returns an int array representation of the provided ArrayList.
   *
   * @param arrList the ArrayList whose int array representation is returned
   * @return an int array representation of arrList
   */
  private static int[] arrListToArr(ArrayList<Integer> arrList) {
    int[] arr = new int[arrList.size()];
    for (int i = 0; i < arrList.size(); i++) {
      arr[i] = arrList.get(i);
    }
    return arr;
  }

  /**
   * Sets this MixedRadix's radices to the provided int array.
   *
   * @param arr the int array for this MixedRadix's radices to be assigned to
   */
  public void setRadices(int[] arr) {
    this.radices = arr;
  }

  /**
   * Sets the radices using the provided String representation.
   *
   * @param input String representation of the radices for this MixedRadix's radices
   *              to be assigned to
   * @throws IllegalArgumentException if there is a part in input that isn't an int
   * (excluding spaces)
   */
  public void setRadices(String input) throws IllegalArgumentException {
    String[] radices = input.trim().split(" ");
    this.radices = new int[radices.length];
    for (int i = 0; i < radices.length; i++) {
      if (radices[i].matches("-?\\d+(\\d+)?")) {
        this.radices[i] = Integer.parseInt(radices[i]);
      } else {
        throw new IllegalArgumentException();
      }
    }
  }

  /**
   * Returns this MixedRadix's radices.
   *
   * @return this MixedRadix's radices
   */
  public int[] getRadices() {
    int[] copy = new int[radices.length];
    System.arraycopy(radices, 0, copy, 0, radices.length);
    return copy;
  }

  /**
   * Returns the product of this MixedRadix's radices.
   * <p>
   *   When might this be useful?
   * </p>
   * <p>
   *   Let's say you want to get every combinations of days, hours, and minutes (with
   *   values out of 7, 24, and 60 respectively).
   *   Creating a MixedRadix with radices 7, 24, 60 can help give you said combinations.
   *   It may help to know how many unique combinations there are.
   *   There are 7 x 24 x 60 of them.
   *   This method facilitates getting that result.
   * </p>
   *
   * @return the product of this MixedRadix's radices
   */
  public int getRadicesProduct() {
    int product = 1;
    for (int radix : radices) {
      product *= radix;
    }
    return product;
  }

  /**
   * Prints an int array.
   *
   * @param nums int array to be printed
   */
  public static void print(int[] nums) {
    for (int num : nums) {
      System.out.print(num + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    MixedRadix mr = new MixedRadix("7 24 60");
    print(mr.toRadix(10000));
    System.out.println(mr.fromRadix("0 6 22 40"));
    System.out.println();
    MixedRadix mr2 = new MixedRadix("-7 -7 -7 -7 -7 -7");
    print(mr2.toRadix(15532));
    System.out.println(mr2.fromRadix("1 6 0 4 2 1 6"));
    System.out.println(Math.floorDiv(43, 7));
    System.out.println(euclideanDivision(-43, 7));
  }
}
