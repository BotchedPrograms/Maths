import java.util.ArrayList;
import java.util.Scanner;
/* pi can be written as 3 +                1
                            -------------------------------
                                   7       +       1
                                            ---------------
                                               15  +   1
                                                    -------
                                                     1 + ...
  If you input 3.1415926, this program will give you 3, 7, 15, 1,...
  If you input a double, you get the ...fraction parts? Sure, we'll go with that
    Vice versa too
 */

public class FractionInfinitum {
  // Turns double to a fraction with a numerator and denominator, which we'll work with
  public static ArrayList<Integer> getParts(double num) {
    // length of decimal part = length of double - length of int part - 1
      // - 1 at the end to deal with decimal point
      // Wanted to do length of (double - int) but something like 5.86 would become 0.8600000000000000003, which is not good
    int length = String.valueOf(num).length() - String.valueOf((int) num).length() - 1;
    // length better for longer decimals
    // 4 better for short decimals like 0.1
    long[] fraction = approximate(num, Math.pow(10, -Math.max(length, 4)));
    return getParts(new ArrayList<>(), fraction[0], fraction[1]);
  }

  // Turns double to fraction without approximating
    // 0.142857 becomes 142857/1000000 instead of 1/7
  public static ArrayList<Integer> getPartsNoApprox(double num) {
    int length = String.valueOf(num).length() - String.valueOf((int) num).length() - 1;
    long den = (long) Math.pow(10, length);
    num *= den;
    // Simplifies num / den b/c apparently that's necessary
    while (num % 2 == 0) {
      num /= 2;
      den /= 2;
    }
    while (num % 5 == 0) {
      num /= 5;
      den /= 5;
    }
    return getParts(new ArrayList<>(), (long) num, den);
  }

  // Turns numerator and denominator into fraction parts
  // Beautifully concise, isn't it?
  public static ArrayList<Integer> getParts(ArrayList<Integer> numbers, long num, long den) {
    // Second part prevents it stopping early for 0.5 = 1/2
    if (num == 1 && !numbers.isEmpty()) {
      return numbers;
    }
    numbers.add((int) (num / den));   // Fraction part
    num %= den;
    return getParts(numbers, den, num);
  }

  // Turns fraction parts to double
  public static double getNumber(ArrayList<Integer> numbers) {
    int num = numbers.get(numbers.size() - 1);
    int den = 1;
    int oldNum;
    for (int i = numbers.size() - 2; i >= 0; i--) {
      System.out.println(num + " " + den);
      oldNum = num;
      num = numbers.get(i) * oldNum + den;
      den = oldNum;
    }
    return (double) num / den;
  }

  // Same thing as previous method but int[] instead of ArrayList<Integer>
  public static double getNumber(int[] numbers) {
    int num = numbers[numbers.length - 1];
    int den = 1;
    int oldNum;
    for (int i = numbers.length - 2; i >= 0; i--) {
      oldNum = num;
      num = numbers[i] * oldNum + den;
      den = oldNum;
    }
    return (double) num / den;
  }

  // Approximates double to fraction
    // ints can probably be used instead of longs honestly
  public static long[] approximate(double num, double error) {
    long n1 = 0;
    long d1 = 1;
    long n2 = 1;
    long d2 = 1;
    long numerator = 0;
    long denominator = 1;
    double newNum = 0;
    double decimal = num - (long) num;
    while (Math.abs(newNum - decimal) > error) {
      numerator = n1+n2;
      denominator = d1+d2;
      newNum = (double) numerator/denominator;
      if (newNum < decimal) {
        n1 += n2;
        d1 += d2;
      } else if (newNum > decimal) {
        n2 += n1;
        d2 += d1;
      }
    }
    // Adds int part of num back into newly acquired fraction
    return new long[] {(long) num * denominator + numerator, denominator};
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String input;
    double num;
    System.out.println("Input number: ");
    String[] inputs;
    while (true) {
      input = scan.nextLine();
      inputs = input.split(" ");
      if (inputs.length > 1 && inputs[0].matches("-?\\d+(\\d+)?")) {
        // If there are multiple numbers, turn those fraction parts to a double
        int[] numbers = new int[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
          numbers[i] = Integer.parseInt(inputs[i]);
        }
        System.out.println(getNumber(numbers));
        System.out.println();
      // Checks if first String is a double or an int
        // Don't know how they work, I just modified what I saw from stackoverflow
      } else if (input.matches("([0-9]*)\\.([0-9]*)") || input.matches("-?\\d+(\\d+)?")) {
        // Turns double or int into fraction parts
        num = Double.parseDouble(input);
        System.out.println(getParts(num));
        System.out.println();
      } else {
        break;
      }
    }
    scan.close();
  }
}
