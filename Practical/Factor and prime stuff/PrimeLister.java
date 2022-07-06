import java.util.ArrayList;

// Lists primes, that's it

public class PrimeLister {
  // Lists prime <= max
  public static ArrayList<Long> listPrimes(long max) {
    ArrayList<Long> primes = new ArrayList<>();
    // Goes through odd numbers from 3 to max inclusive
      // Even numbers obviously divisible by 2
        // Except 2 ofc, which is added later so we don't need to check if odd number is divisible by it
    l1: for (long i = 3; i <= max; i += 2) {
      // Checks if number is divisible by known primes
      for (long prime : primes) {
        if (i % prime == 0) {
          continue l1;
        }
        // If it's not a prime, there should be a prime factor <= sqrt
          // Thus breaks and adds it to primes if it can't find one
        if (prime > Math.sqrt(i)) {
          break;
        }
      }
      primes.add(i);
    }
    // Adds 2 to start of primes
    if (max >= 2) {
      primes.set(0, 2L);
    }
    return primes;
  }

  public static void main(String[] args) {
    // Only takes around 1.5 seconds to list primes from 1 - 10,000,000
      // Wow, so fast!
    ArrayList<Long> primes = listPrimes(10000000);
    System.out.println(primes);
  }
}
