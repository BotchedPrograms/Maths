import java.util.ArrayList;

// Gets lowest common multiple (lcm) and greatest common factor (gcf)
  // Not too hard to recreate but again, it's more trouble than it's worth
public class LCMandGCF {
  // Gets lcm of 2 numbers
  public static long lcm(long a, long b) {
    return Math.abs(a*b/gcf(a, b));
  }

  // Gets lcm of numbers in int[]
  public static long lcm(long[] nums) {
    if (nums.length == 0) {
      return -1;
    }
    long lcm = Math.abs(nums[0]);
    for (int i = 0; i < nums.length - 1; i++) {
      lcm = lcm(lcm, nums[i+1]);
    }
    return lcm;
  }

  // Gets gcf of 2 numbers
    // Gets prime factors and returns the product of the ones they have in common
  public static long gcf(long a, long b) {
    ArrayList<Long> factorsA = factor(a);
    ArrayList<Long> factorsB = factor(b);
    ArrayList<Long> inCommon = inCommon(factorsA, factorsB);
    long product = 1;
    for (int i = 0; i < inCommon.size(); i++) {
      product *= inCommon.get(i);
    }
    return product;
  }

  // Gets gcf of numbers in int[]
  public static long gcf(long[] nums) {
    if (nums.length == 0) {
      return -1;
    }
    if (nums.length == 1) {
      return nums[0];
    }
    long gcf = gcf(nums[0], nums[1]);
    for (int i = 1; i < nums.length; i++) {
      if (gcf == 1) {
        break;
      }
      gcf = gcf(gcf, nums[i]);
    }
    return gcf;
  }

  public static ArrayList<Long> factor(long num) {
    return factor(num, new ArrayList<Long>(), 3, new ArrayList<Long>());
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
    if (num != 1 || factors.size() == 0) {
      factors.add(num);
    }
    return factors;
  }

  public static ArrayList<Long> inCommon(ArrayList<Long> arr, ArrayList<Long> arr2) {
    ArrayList<Long> inCommon = new ArrayList<Long>();
    for (int i = arr.size() - 1; i >= 0; i--) {
      for (int j = arr2.size() - 1; j >= 0; j--) {
        if (arr.get(i).equals(arr2.get(j))) {
          inCommon.add(arr.get(i));
          arr.remove(i);
          arr2.remove(j);
          break;
        }
      }
    }
    return inCommon;
  }

  public static void main(String[] args) {
    long[] nums = {-44, 52, 40, 20, -16, -16, 48, 60, 48};
    System.out.println(lcm(nums));
    System.out.println(gcf(nums));
    System.out.println(gcf(79548996, 11382516));
  }
}
