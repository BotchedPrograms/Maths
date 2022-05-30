// Gets root mean square of numbers; that's it
public class RootMeanSquare {
  public static double getRMS(int[] nums) {
    double mean = 0;
    for (int i = 0; i < nums.length; i++) {
      // Square
      mean += nums[i] * nums[i];
    }
    // Mean
    mean /= nums.length;
    // Root
    return Math.sqrt(mean);
  }

  public static double getRMS(double[] nums) {
    double mean = 0;
    for (int i = 0; i < nums.length; i++) {
      mean += nums[i] * nums[i];
    }
    mean /= nums.length;
    return Math.sqrt(mean);
  }

  public static void main(String[] args) {
    System.out.println(getRMS(new int[] {5, -3, -7}));
  }
}
