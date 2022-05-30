import java.util.ArrayList;
import java.util.Scanner;

public class PrimeFactorization {
  public static ArrayList<Long> factor(long num, ArrayList<Long> smallPrimes, int i, ArrayList<Long> factors) {
    if (num % 2 == 0) {
      factors.add(2L);
      factor(num/2, smallPrimes, i, factors);
      return factors;
    }
    int sqrt = (int) Math.sqrt(num);
    l1: for (; i <= sqrt; i += 2) {
      for (int j = 0; j < smallPrimes.size() - 1; j++) {
        if (i % smallPrimes.get(j) == 0) {
          continue l1;
        } else if (Math.sqrt(i) < smallPrimes.get(j)) {
          break;
        }
      }
      smallPrimes.add((long) i);
      if (num % i == 0) {
        factors.add((long) i);
        factor(num/i, smallPrimes, i, factors);
        return factors;
      }
    }
        /* Condensed version of
        if (num != 1 || factors.length() == 0) {
            System.out.println(num);
        } else {
            System.out.println();
        }
         */
    if (num != 1 || factors.size() == 0) {
      factors.add(num);
    }
    return factors;
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String input;
    long num;
    while (true) {
      input = scan.nextLine();
      // Checks if String is integer
      // Don't know how it works, I just modified what I saw from stackoverflow
      if (!input.matches("-?\\d+(\\d+)?")) {
        break;
      }
      num = Long.parseLong(input);
      System.out.println(factor(num, new ArrayList<Long>(), 3, new ArrayList<Long>()));
    }
  }
}
