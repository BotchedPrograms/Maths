import java.util.ArrayList;
import java.util.Scanner;

// Gets prime factorization of number
  // 12 = 2 2 3 b/c 2 x 2 x 3 = 12

public class PrimeFactorization {
  public static ArrayList<Long> factor(long num) {
    return factor(num, 3, new ArrayList<>());
  }

  public static ArrayList<Long> factor(long num, long i, ArrayList<Long> factors) {
    if (num % 2 == 0) {
      if (num == 0) {
        return factors;
      }
      factors.add(2L);
      return factor(num/2, i, factors);
    }
    long sqrt = (long) Math.sqrt(num);
    for (; i <= sqrt; i += 2) {
      if (num % i == 0) {
        factors.add(i);
        return factor(num/i, i, factors);
      }
    }
    if (num != 1) {
      factors.add(num);
    }
    return factors;
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String input;
    long num;
    System.out.println("Enter numbers:");
    while (true) {
      input = scan.nextLine();
      // Checks if String is integer
        // Don't know how it works, I just modified what I saw from stackoverflow
      if (!input.matches("-?\\d+(\\d+)?")) {
        break;
      }
      num = Long.parseLong(input);
      System.out.println(factor(num));
    }
  }
}
