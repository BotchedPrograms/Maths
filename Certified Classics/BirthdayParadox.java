import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// Fun fact: you only need around 23 people for it to be more likely than not that 2 of them share the same birthday
  // Assuming all birthdays are equally likely, no leap years, and no twins
  // https://www.youtube.com/watch?v=KtT_cgMzHx8
// What this program does is if you input 365, it gives you 23
  // If you input a number, it gives you the number of things needed for a match to be likelier than not
// Mathematical explanation for how to work that number out: https://www.reddit.com/r/theydidthemath/comments/w4an7h/request_if_i_try_to_save_a_file_as_crghguhgk_what/

public class BirthdayParadox {
  // https://stackoverflow.com/questions/739532/logarithm-of-a-bigdecimal
  // b is number being logged, dp seems to control how much precision
  public static BigDecimal log10(BigDecimal b, int dp)
  {
    final int NUM_OF_DIGITS = dp+2; // need to add one to get the right number of dp
    //  and then add one again to get the next number
    //  so I can round it correctly.

    MathContext mc = new MathContext(NUM_OF_DIGITS, RoundingMode.HALF_EVEN);

    //special conditions:
    // log(-x) -> exception
    // log(1) == 0 exactly;
    // log of a number lessthan one = -log(1/x)
    if(b.signum() <= 0)
      throw new ArithmeticException("log of a negative number! (or zero)");
    else if(b.compareTo(BigDecimal.ONE) == 0)
      return BigDecimal.ZERO;
    else if(b.compareTo(BigDecimal.ONE) < 0)
      return (log10((BigDecimal.ONE).divide(b,mc),dp)).negate();

    StringBuffer sb = new StringBuffer();
    //number of digits on the left of the decimal point
    int leftDigits = b.precision() - b.scale();

    //so, the first digits of the log10 are:
    sb.append(leftDigits - 1).append(".");

    //this is the algorithm outlined in the webpage
    int n = 0;
    while(n < NUM_OF_DIGITS)
    {
      b = (b.movePointLeft(leftDigits - 1)).pow(10, mc);
      leftDigits = b.precision() - b.scale();
      sb.append(leftDigits - 1);
      n++;
    }

    BigDecimal ans = new BigDecimal(sb.toString());

    //Round the number to the correct number of decimal places.
    ans = ans.round(new MathContext(ans.precision() - ans.scale() + dp, RoundingMode.HALF_EVEN));
    return ans;
  }

  // Lot of stuff here, but you can (and should) just do   log(1/2) / (log(num - 1) - log(num))   in wolfram alpha
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String input;
    int precision = 69;
    System.out.println("Enter a number: ");
    while (true) {
      input = scan.nextLine();
      // Checks if String is integer
        // Don't know how it works, I just modified what I saw from stackoverflow
      if (!input.matches("-?\\d+(\\d+)?")) {
        break;
      }
      BigDecimal number = new BigDecimal(input);
      BigDecimal num1 = log10(number.subtract(BigDecimal.ONE), precision);
      BigDecimal num2 = log10(number, precision);
      BigDecimal numerator = log10(BigDecimal.valueOf(2), precision).multiply(BigDecimal.valueOf(-1));
      BigDecimal answer = numerator.divide(num1.subtract(num2), RoundingMode.HALF_EVEN);
      BigDecimal radicand = answer.multiply(BigDecimal.valueOf(8)).add(BigDecimal.ONE);
      BigDecimal sqrt = radicand.sqrt(new MathContext(precision, RoundingMode.HALF_EVEN));
      BigDecimal top = sqrt.add(BigDecimal.ONE);
      BigDecimal real = top.divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN);
      // https://stackoverflow.com/questions/26102362/round-up-bigdecimal-to-integer-value
      System.out.println(real.setScale(0, RoundingMode.UP));
    }
    scan.close();
    /* One way to work it out; doesn't work for big numbers
    double ratio = 67318966.0/67318967;
    double product = 1;
    for (int i = 0; ; i++) {
      product *= Math.pow(ratio, i);
      if (product < 0.5) {
        System.out.println(i+1);
        break;
      }
    }
    */

    /* Works numbers manually
      // Median of numbers is the number we want
    ArrayList<Integer> arr;
    Random rand = new Random();
    int newNum;
    int size = 6001;
    ArrayList<Integer> sums = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      arr = new ArrayList<>();
      while (true) {
        newNum = rand.nextInt(67318967);
        if (arr.contains(newNum)) {
          break;
        }
        arr.add(newNum);
      }
      sums.add(arr.size());
    }
    System.out.println(sums);
    */
    /* for (int j : array) {
      System.out.print(j + " ");
    }*/

    /* Works it out another way but also doesn't work for big numbers
      // Same way as shown in ted-ed video
    BigInteger num = BigInteger.ONE;
    BigInteger den = BigInteger.ONE;
    int number = 67318967;
    for (int i = 0; ; i++) {
      num = num.multiply(BigInteger.valueOf(number - i));
      den = den.multiply(BigInteger.valueOf(number));
      if (num.multiply(BigInteger.TWO).compareTo(den) < 0) {
        System.out.println(i+1);
        break;
      }
    }
    */

    // long num = 5429503678976L; // 26^9
    /* Used for working it out for 26^6 - 26^12 inclusive
      // Works number out but works well for big numbers
    BigDecimal big = new BigDecimal(1);
    for (int i = 0; i < 5; i++) {
      big = big.multiply(BigDecimal.valueOf(26));
    }
    for (int i = 0; i < 7; i++) {
      big = big.multiply(BigDecimal.valueOf(26));
      BigDecimal num1 = log10(big.subtract(BigDecimal.ONE), 40);
      BigDecimal num2 = log10(big, 40);
      BigDecimal numerator = log10(BigDecimal.valueOf(2), 40).multiply(BigDecimal.valueOf(-1));
      BigDecimal answer = numerator.divide(num1.subtract(num2), RoundingMode.HALF_EVEN);
      BigDecimal radicand = answer.multiply(BigDecimal.valueOf(8)).add(BigDecimal.ONE);
      BigDecimal sqrt = radicand.sqrt(new MathContext(40, RoundingMode.HALF_EVEN));
      BigDecimal top = sqrt.add(BigDecimal.ONE);
      BigDecimal real = top.divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN);
      System.out.println(answer);
      System.out.println(real);
      System.out.println();
    }
    */
  }
}
