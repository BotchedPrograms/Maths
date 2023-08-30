// Returns lowest common multiple (lcm) and greatest common factor (gcf)

public class LCMandGCF {
    // Returns lcm of 2 numbers
    public static long lcm(long a, long b) {
        return a / gcf(a, b) * b;
    }

    // Returns lcm of an array of numbers
    public static long lcm(long[] nums) {
        long currLcm = lcm(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            currLcm = lcm(currLcm, nums[i]);
        }
        return currLcm;
    }

    // Returns gcf of 2 numbers
    public static long gcf(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcf(b, a % b);
    }

    // Returns gcf of an array of numbers
    public static long gcf(long[] nums) {
        long currGcf = gcf(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            if (currGcf == 1) {
                return currGcf;
            }
            currGcf = gcf(currGcf, nums[i]);
        }
        return currGcf;
    }

    public static void main(String[] args) {
        long[] nums = {44, 52, 40, 20, 16, 16, 48, 60, 48};
        System.out.println(lcm(nums));
        System.out.println(gcf(nums));
        System.out.println(lcm(79548996, 11382516));
        System.out.println(gcf(79548996, 11382516));
    }
}
