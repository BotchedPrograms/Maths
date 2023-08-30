// Algorithm taken from https://brilliant.org/wiki/extended-euclidean-algorithm/

public class ModularInverse {
    // Returns {x, y} such that ax + by = gcf(a, b)
    public static long[] bezoutCoeffs(long a, long b) {
        long[] nums = extendedGcf(a, b);
        return new long[] {nums[1], nums[2]};
    }

    // Returns {g, x, y} such that g = gcf(a,b) = ax + by
    public static long[] extendedGcf(long a, long b) {
        long r = b;
        long s = 0;
        long t = 1;
        long oldR = a;
        long oldS = 1;
        long oldT = 0;
        while (r != 0) {
            long quotient = oldR / r;
            long subR = r;
            long subS = s;
            long subT = t;
            r = oldR - quotient * r;
            s = oldS - quotient * s;
            t = oldT - quotient * t;
            oldR = subR;
            oldS = subS;
            oldT = subT;
        }
        return new long[] {oldR, oldS, oldT};
        // t,s is a,b/gcf
    }

    // Prereq: a and b are relatively prime
    public static long getInverse(int a, int b) {
        long[] nums = extendedGcf(a, b);
        if (nums[0] == 1) {
            return nums[1];
        }
        throw new IllegalArgumentException();
    }

    private static void print(long[] nums) {
        for (long num : nums) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        print(bezoutCoeffs(15, 532));
        System.out.println(getInverse(15, 532));
    }
}
