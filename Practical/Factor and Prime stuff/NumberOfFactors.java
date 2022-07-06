import java.util.ArrayList;
import java.util.Scanner;

// 60 has 12 factors: 1, 2, 3, 4, 5, 6, 10, 12, 15, 20, 30, and 60
  // 60 = 2^2 * 3 * 5
  // Each factor of 60 is a unique combination of 2^0, 2^1, 2^2 ; 3^0, 3^1 ; and 5^0, 5^1
  // The number of unique combinations for 60 is 3 x 2 x 2 = 12
    // i.e. Number of factors = product of (powers + 1)
// Also, the getFactors method here returns a long[] of those factors
  // Not mentioned in file name b/c wasn't sure what it would be called
    // Factors feels too short, Factorization might just be wrong, GetFactors sounds weird, UniqueFactors feels unnecessarily complicated, ...

public class NumberOfFactors {
  public static int getNumberOfFactors(long num) {
    // Powers of prime factors
    ArrayList<Integer> powers = getPowers(num);
    int product = 1;
    for (Integer power : powers) {
      product *= power + 1;
    }
    return product;
  }
  
  public static long[] getFactors(long num) {
    ArrayList<Long> primeFactors = factor(num);
    // Powers of prime factors
    ArrayList<Integer> powers = getPowers(num);
    // Number of factors
      // Comments directly after thing they're describing might be conventional but it sucks if you want multiple comments
      // For consistency, I'll just stick to having comments above
    int product = 1;
    for (Integer power : powers) {
      product *= power + 1;
    }
    int[] indexes;
    int tempI;
    long[] factors = new long[product];
    int index;
    for (int i = 0; i < product; i++) {
      // Made to not change i while working with the value
      tempI = i;
      // Every indexes is a combination of powers
        // If num = 60, indexes would be [0, 0, 0], [1, 0, 0], [2, 0, 0], [0, 1, 0], ...
        // Goes through all combinations of, in this case, of 2s, 3s, and 5s
      indexes = new int[powers.size()];
      factors[i] = 1;
      index = 0;
      for (int j = 0; j < powers.size(); j++) {
        // The thing that makes the combinations
          // Hard to make, harder to explain
          // or maybe im just too tired rn, honest possibility. ima go nap
        indexes[j] = tempI % (powers.get(j) + 1);
        tempI /= powers.get(j) + 1;
        // Factors[i] *= prime factor and number of it in aforementioned combination
        factors[i] *= Math.pow(primeFactors.get(index), indexes[j]);
        index += powers.get(j);
      }
    }
    return sort(factors);
  }

  public static ArrayList<Integer> getPowers(long num) {
    ArrayList<Long> factors = factor(num);
    ArrayList<Integer> powers = new ArrayList<>();
    for (int i = 0; i < factors.size(); i++) {
      // Checking for powers.isEmpty() here instead of powers.add(1) before for loop prevents giving 1 a powers of 1 and saying it has 2 factors when it has 1
      if (powers.isEmpty() || !factors.get(i).equals(factors.get(i - 1))) {
        // If there is a new prime factor, add 1
        powers.add(1);
      } else {
        // Else, increment last number
        powers.set(powers.size() - 1, powers.get(powers.size() - 1) + 1);
      }
    }
    return powers;
  }

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

  public static long[] sort(long[] nums) {
    long temp;
    long[] newNums = new long[nums.length];
    // Copies nums to newNums
      // For the record, I had no idea about this until IntelliJ recommended it
    System.arraycopy(nums, 0, newNums, 0, nums.length);
    for (int i = 0; i < newNums.length-1; i++) {
      for (int j = i + 1; j < newNums.length; j++) {
        if (newNums[i] > newNums[j]) {
          temp = newNums[i];
          newNums[i] = newNums[j];
          newNums[j] = temp;
        }
      }
    }
    return newNums;
  }

  public static void print(long num) {
    System.out.println(num);
  }

  public static void print(long[] arr) {
    for (long l : arr) {
      System.out.print(l + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String input;
    long num;
    System.out.println("Input a number: ");
    while (true) {
      input = scan.nextLine();
      // Checks if String is integer
        // Don't know how it works, I just modified what I saw from stackoverflow
      if (!input.matches("-?\\d+(\\d+)?")) {
        break;
      }
      num = Long.parseLong(input);
      print(getFactors(num));
    }
    scan.close();
  }
}
