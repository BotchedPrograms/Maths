// Math explanation in the bottom
public class SquareRootApprox {
    // Returns gcf of 2 numbers
    private static long gcf(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcf(b, a % b);
    }

    // Returns |expected - result|
    private static double error(double expected, double result) {
        return Math.abs(expected - result);
    }

    // Prints info given values
    private static void printInfo(long newNum, long newDen, long n) {
        double quotient = (double) newNum / newDen;
        System.out.println(newNum + "/" + newDen + ":");
        System.out.println("\tquotient   = " + quotient);
        System.out.println("\tquotient^2 = " + (quotient * quotient));
        System.out.println("\terror      = " + error(Math.sqrt(n), quotient));
    }

    public static void approxSqrt(long n) {
        approxSqrt(n, 10);
    }

    // Prints fraction approximations of sqrt(n) after doing the given number of iterations
    public static void approxSqrt(long n, int iterations) {
        // Can replace with any other integer approximation of sqrt(n)
        long num = Math.round(Math.sqrt(n));
        long den = 1;
        for (int i = 0; i < iterations; i++) {
            long newNum = num * num + n * den * den;
            long newDen = 2 * num * den;
            if (newNum < 0 || newDen < 0) {
                System.out.println("integer overflow");
                break;
            }
            long gcf = gcf(newNum, newDen);
            // Simplifies fraction newNum / newDen
            newNum /= gcf;
            newDen /= gcf;
            printInfo(newNum, newDen, n);
            num = newNum;
            den = newDen;
        }
    }

    private static void approxSqrtSlow(long n) {
        approxSqrtSlow(n, 20);
    }

    // Approx but slower. Nice if you want to see more approximations of sqrt(n) before overflowing
    private static void approxSqrtSlow(long n, int iterations) {
        long q = Math.round(Math.sqrt(n));
        long num = q;
        long den = 1;
        for (int i = 0; i < iterations; i++) {
            long newNum = q * num + n * den;
            long newDen = q * den + num;
            if (newNum < 0 || newDen < 0) {
                System.out.println("integer overflow");
                break;
            }
            long gcf = gcf(newNum, newDen);
            newNum /= gcf;
            newDen /= gcf;
            printInfo(newNum, newDen, n);
            num = newNum;
            den = newDen;
        }
    }

    public static void main(String[] args) {
        approxSqrt(333);
        System.out.println();
        approxSqrtSlow(333);
    }
}
/*
Suppose you wanted to find a fraction approximation of sqrt(333) (without calculus). One thing you can do is
    x^2 = 333
==> x^2 + qx = qx + 333 for all q
==> x(x + q) = qx + 333
==> x = (qx + 333) / (x + q)
      = (qx + q^2 - q^2 + 333) / (x + q)
      = q + (333 - q^2) / (q + x)
Doing recursion on x gives us better and better approximations of sqrt(x)
We want to keep track of the numerator and denominator for x. If x = a/b currently,
    a/b = q + (333 - q^2) / (q + a/b)
        = q + (333b - q^2b) / (qb + a)
        = (q^2b + qa) / (qb + a) + (333b - q^2b) / (qb + a)
        = (q^2b + qa + 333b - q^2b) / (qb + a)
        = (qa + 333b) / (qb + a)
Thus, a <-- qa + 333b, b <-- qb + a
This works for all q, but some values work better than others. In particular, the closer it is to an approximation
of sqrt(333), the better it is. I now humbly apologize for having no good explanation for why that is. My intuition
is that x = q + (333 - q^2) / (q + x) gets to hone in on the sqrt(333) faster when q is already really close, but
unfortunately this is not a rigorous proof in the slightest. I will update this explanation when/if I find a
satisfactory explanation.
This is the math behind approxSqrtSlow, which is already really good in its own right. There, we set q to be the
sqrt(333) rounded to the nearest integer. The use of a predetermined sqrt(333) to help approximate sqrt(333) is a bit
dubious, but can be replaced with any halfway decent rough approximation of sqrt(333) if need be.
approxSqrt takes things further by recursing on q too, that is setting q to be the most recent approximation. Setting
q equal to a/b too gives us
     a/b = (a/b*a + 333b) / (a/b*b + a)
         = (a^2 + 333b^2) / (a*b + ab)
         = (a^2 + 333b^2) / (2ab)
which is what approxSqrt does. It is an objectively faster method than approxSqrtSlow for the same reason that
pow(x, 8) = {((x^2)^2)^2} is faster than = {x*x*x*x*x*x*x*x}. approxSqrtSlow remains in case you want to appreciate
a decent amount of approximations before numbers start overflowing.

Now the burning question: Why not calculus?
The original question that led to this program was "How did mathematicians approximate sqrt(2) before calculus?"
Did they do this? Who knows. At the very least, they could've. Besides, an admittedly brief skimming showed me that
trying to approximate sqrt(c) with Taylor series or something would require knowing the value of sqrt(c). Or at least
they're not that good at approximating sqrts. Or further at least they're not nearly as efficient as approxSqrt.
Common calculus L

Okay, I checked the history of this question
I read the Wikipedia article "Methods of computing square roots". There were a number of algorithms that existed before
calculus was invented.
approxSqrtSlow is the "Continued fraction expansion" part. Not a coincidence, continued fractions motivated my algorithm
in the first place. The algorithms aren't derived from the same formula, but empirically get the same answers. The
unfortunately does not discuss the history of the algorithm. It links to a main article that cites a textbook written
in 1948, which is not a very meaningful upper bound.
*/
