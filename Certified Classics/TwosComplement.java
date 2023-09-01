import java.util.Arrays;
import java.util.Random;

// How negative numbers are usually represented in binary
// If x > 0, -x is said to be the bitwise negation of +x then plus 1
// The benefit of doing this over other methods is that it handles adding and subtracting well with negative numbers
    // and it has only 1 representation for 0
// A brief explanation at the bottom
    // That doesn't mention sign-magnitude or ones' complement for better or worse

public class TwosComplement {
    // Converts an int x to its binary representation in 32 bits
    public static int[] convertTo(int x) {
        if (x >= 0) {
            int[] bits = new int[32];
            for (int i = 0; i < 31; i++) {
                bits[bits.length - i - 1] = x % 2;
                x /= 2;
                if (x == 0) {
                    return bits;
                }
            }
            return bits;
        }
        // Deals with unfortunate edge case
        if (x == Integer.MIN_VALUE) {
            int[] bits = new int[32];
            bits[0] = 1;
            return bits;
        }
        return addOne(bitwiseNegation(convertTo(-x)));
    }

    // Assumes bits contains only 0s and 1s
    // Converts bits from two's complement to a normal number
    public static int convertFrom(int[] bits) {
        if (bits[0] == 0) {
            int sum = 0;
            long twoPow = 1;
            for (int i = 0; i < bits.length; i++) {
                sum += bits[bits.length - i - 1] * twoPow;
                twoPow *= 2;
            }
            return sum;
        }
        // Handles unfortunate edge case
        if (Arrays.equals(bits, new int[] {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0})) {
            return Integer.MIN_VALUE;
        }
        // Note that convertFrom(x) returns the value of x if x > 0 (bits[0] == 0)
        // In the bottom, we define -x = ~x + 1
            // Note that -x = ~x + 1 doesn't strictly depend on x > 0
                // We defined x >= 0 and found x < 0 based on that, but we don't have to
                    // Which implies the procedure to turn positive integers to negative ones works the other way too
            // If x < 0 (bits[0] != 0), convertFrom(~x + 1) = convertFrom(-x) = -x since -x > 0
            // To get the actual x < 0, we multiply the value returned from convertFrom by -1
        return -1 * convertFrom(addOne(bitwiseNegation(bits)));
    }

    // Assumes bits contains only 0s and 1s
    // Returns new int[] with bits's 0s and 1s swapped
    private static int[] bitwiseNegation(int[] bits) {
        int[] newBits = new int[bits.length];
        for (int i = 0; i < bits.length; i++) {
            newBits[i] = bits[i] == 0 ? 1 : 0;
        }
        return newBits;
    }

    // Assumes bits contains only 0s and 1s
    // Returns new int[] = (bits if it was a binary number) + 1
    private static int[] addOne(int[] bits) {
        int[] newBits = new int[bits.length];
        System.arraycopy(bits, 0, newBits, 0, bits.length);
        newBits[bits.length - 1]++;
        for (int i = newBits.length - 1; i > 0; i--) {
            if (newBits[i] == 2) {
                newBits[i] = 0;
                newBits[i - 1]++;
            } else {
                return newBits;
            }
        }
        if (newBits[0] == 2) {
            newBits[0] = 0;
        }
        return newBits;
    }

    // Prints a given int[]
    private static void print(int[] nums) {
        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // -3
        System.out.println(convertFrom(new int[] {1,1,1,1,1,1,1,0,1}));
        // -20
        System.out.println(convertFrom(new int[] {1,0,1,1,0,0}));
        // -2147483648
        System.out.println(convertFrom(new int[] {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}));

        Random rand = new Random();
        int bigNum = 100000000;
        for (int i = 0; i < 10000; i++) {
            int randInt = rand.nextInt(2 * bigNum) - bigNum;
            if (convertFrom(convertTo(randInt)) != randInt) {
                System.out.println("uh oh " + randInt);
            }
        }
    }
}

/*
One of the advantages of Two's Complement over other representations of negative integers is facilitating arithmetic
    i.e. adding and subtracting with negative numbers works
Addition with positive integers in binary works the same way as in decimal
However, if we're representing our ints with n bits, after adding the last bits, we may end up with an extra 1
    This is known as integer overflow. This is usually handled by simply discarding the 1
We would like to be able to apply this for negative numbers as well
Put mathematically, discarding an extra 1 is the same as modding by 2^n

A bitwise negation of a string of 1s and 0s is that same string with the 1s replaced with 0s and vice versa
For example,
    0110 1110 --> 1001 0001
    1010 0100 --> 0101 1011
The bitwise negation of a string of bits x is written as ~x
Note that x + ~x = 1111...
And that 2^n - 1 = 1111...
If a == b (mod n) means a mod n = b mod n
x + ~x = 2^n - 1 == -1 (mod 2^n)
Thus, x == -~x - 1 (mod 2^n)
Thus, -x == ~x + 1 (mod 2^n)
Thus, when we add -x and mod by 2^n, it's the same as adding ~x + 1 and modding by 2^n
Thus, we can define -x to be ~x + 1 to make addition with negative numbers work
    Where the base case x >= 0 is represented in binary as it usually is
    Multiplication with negative numbers also works for similar reasons
    Note the edge case x = 1000... which corresponds with -2^(n-1) in Two's complement
        All the math done here still holds, though -x isn't contained in the n bits
        Fascinatingly, x = 2^(n-1) == -2^(n-1) (mod 2^n) also has this property
        I'm assuming the reason why 1000... isn't give that value is so the first bit indicates if it's positive or not
This is how Two's complement represents negative numbers in binary
*/
