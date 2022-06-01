// Gets lowest common multiple (lcm) and greatest common factor (gcf)
  // Not too hard to recreate but again, it's more trouble than it's worth
public class LCMandGCF {
  // Gets lcm of 2 numbers
  public static int lcm(int a, int b) {
    return Math.abs(a*b/gcf(a, b));
  }

  // Gets lcm of numbers in int[]
  public static int lcm(int[] nums) {
    if (nums.length == 0) {
      return -1;
    }
    int lcm = Math.abs(nums[0]);
    for (int i = 0; i < nums.length - 1; i++) {
      lcm = lcm(lcm, nums[i+1]);
    }
    return lcm;
  }

  // Gets gcf of 2 numbers
  public static int gcf(int a, int b) {
    for (int i = Math.min(Math.abs(a), Math.abs(b)); i > 0; i--) {
      if (a % i == 0 && b % i == 0) {
        return i;
      }
    }
    return -1;
  }

  // Gets gcf of numbers in int[]
  public static int gcf(int[] nums) {
    l1: for (int i = minAbs(nums); i > 0; i--) {
      for (int j = 0; j < nums.length; j++) {
        if (nums[j] % i != 0) {
          continue l1;
        }
      }
      return i;
    }
    return -1;
  }

  // Gets min of absolute value of numbers
  public static int minAbs(int[] nums) {
    int minAbs = Math.abs(nums[0]);
    for (int i = 1; i < nums.length; i++) {
      if (Math.abs(nums[i]) < minAbs) {
        minAbs = Math.abs(nums[i]);
      }
    }
    return minAbs;
  }

  public static void main(String[] args) {
    int[] nums = {-44, 52, 40, 20, -16, -16, 48, 60, 48};
    System.out.println(lcm(nums));
    System.out.println(gcf(nums));
  }
}
