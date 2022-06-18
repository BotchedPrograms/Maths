import java.util.ArrayList;

// 60 has 12 factors: 1, 2, 3, 4, 5, 6, 10, 12, 15, 20, 30, and 60
  // 60 = 2^2 * 3 * 5
  // Each factor of 60 is a unique combination of 2^0, 2^1, 2^2 ; 3^0, 3^1 ; and 5^0, 5^1
  // The number of unique combinations for 60 is 3 x 2 x 2 = 12
public class NumberOfFactors {
  public static int getNumberOfFactors(long num) {
    ArrayList<Long> factors = factor(num);
    ArrayList<Integer> powers = new ArrayList<>();
    for (int i = 0; i < factors.size(); i++) {
      // Checking for powers.isEmpty() here instead of powers.add(1) before for loop prevents giving 1 a powers of 1 and saying it has 2 factors when it has 1
      if (powers.isEmpty() || !factors.get(i).equals(factors.get(i - 1))) {
        powers.add(1);
      } else {
        powers.set(powers.size() - 1, powers.get(powers.size() - 1) + 1);
      }
    }
    int product = 1;
    for (Integer power : powers) {
      product *= power + 1;
    }
    return product;
  }

  public static ArrayList<Long> factor(long num) {
    return factor(num, new ArrayList<>(), 3, new ArrayList<>());
  }

  public static ArrayList<Long> factor(long num, ArrayList<Long> smallPrimes, int i, ArrayList<Long> factors) {
    if (num % 2 == 0) {
      factors.add(2L);
      factor(num/2, smallPrimes, i, factors);
      return factors;
    }
    int sqrt = (int) Math.sqrt(num);
    l1: for (; i <= sqrt; i += 2) {
      for (int j = 0; j < smallPrimes.size(); j++) {
        if (i % smallPrimes.get(j) == 0 && i != smallPrimes.get(j)) {
          continue l1;
        } else if (Math.sqrt(i) > smallPrimes.get(j)) {
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
    if (num != 1) {
      factors.add(num);
    }
    return factors;
  }

  public static void main(String[] args) {
    for (int i = 1; i <= 100; i++) {
      System.out.printf("%2d ", getNumberOfFactors(i));
      if (i % 10 == 0) {
        System.out.println();
      }
    }
  }
}
