import java.util.ArrayList;

// The toitent function counts the number of non-relatively primes from 1 to n
  // For n = 12:
    // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
    // 1, 5, 7, 11 aren't relatively prime to 12 so the totient function gives 4
// Also equals n * (1 - 1/p1) * (1 - 1/p2) * ... where ps are the unique prime factors of n
  // For n = 12:
    // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
    //    2     4     6     8     10      12      removing multiples of 2 / removing half of numbers
    //       3                 9                  removing multiples of 3 / removing remaining third of numbers
    // 1           5     7            11          what's left is thus 12 * (1 - 1/2) * (1 - 1/3) = 4
// Made by Euler (b/c of course) and is used in number theory and encryption

public class Totient {
  public static int totient(long n) {
    if (n < 1) {
      return 0;
    }
    double product = n;
    ArrayList<Long> primes = removeDuplicates(factor(n));
    for (long prime : primes) {
      if (n % prime == 0) {
        product *= 1 - 1.0/prime;
        n /= prime;
      }
    }
    return (int) product;
  }

  // Gets prime factors
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

  // Removes duplicates from arr
  public static ArrayList<Long> removeDuplicates(ArrayList<Long> arr) {
    for (int i = arr.size() - 1; i > 0; i--) {
      if (arr.get(i).equals(arr.get(i - 1))) {
        arr.remove(i);
      }
    }
    return arr;
  }

  public static void main(String[] args) {
    for (int i = 1; i <= 100; i++) {
      System.out.printf("%2d ", totient(i));
      if (i % 10 == 0) {
        System.out.println();
      }
    }
  }
}
