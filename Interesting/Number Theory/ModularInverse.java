// Algorithm taken from https://brilliant.org/wiki/extended-euclidean-algorithm/
// Math explanations at the bottom

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

    // Prereq: a and m are relatively prime
    // Returns a^-1 modulo m, can be negative
    public static long getInverse(int a, int m) {
        long[] nums = extendedGcf(a, m);
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
        System.out.println(getInverse(199, 181));
    }
}
/*
Let a == b (mod m) be equivalent to the statement a mod m = b mod m.
A modular inverse of a number a mod m is a number, denoted by a^-1, such that a * a^-1 == 1 (mod m).
Modular inverses will be referred to as inverses from this point on.

Existence and modular uniqueness
    Let's show that ka == 1 (mod m) for a k that's unique modulo m iff gcf(a, m) = 1
        Where m >= 2
    gcf(a, m) = 1 ==> ka == 1 (mod m) for a k that's unique modulo m:
        Assume for contradiction that k1a == k2a (mod m) but k1 !== k2 (mod m)
            ==> k1a - k2a == 0 (mod m) ==> a(k1 - k2) == 0 (mod m)
            If a == 0 (mod m), gcf(a, m) = m != 1, a contradiction
            Otherwise, k1 - k2 == 0 (mod m) ==> k1 == k2 (mod m), a contradiction
        Thus, if k1 !== k2 (mod m), k1a !== k2a (mod m)
        Thus, ka mod m has unique values from k = 0 to m-1
        Anything mod m has m possible values, and k does too
        Thus, each possible value of ka mod m is mapped to a unique value of k (from 0 to m-1)
        That is, k = a^-1 such that ka mod m = 1 has a unique value modulo m
    gcf(a, m) != 1 ==> ka !== 1 (mod m) for a k that's unique modulo m:
        It suffices to show that ka mod m != 1 for any k
        Which we do by showing that ka + cm != 1 for any c,k
        Let g = gcf(a, m) != 1
        Let a' = a/g, and let m' = m/g
        ka + cm = kga' + cgm' = g(ka' + cm') != 1 because 1 isn't a multiple of g

a has inverse iff a^p does
    a has inverse iff gcf(a, m) = 1
    gcf(a, m) = 1 iff none of a's prime factors are equal to m's
        In the gray area where a = 1, a == a^p == a^-1 == a^-p == 1 (mod m)
        Also we're still only working with m >= 2
    a^p has the same prime factors as a
    none of a's prime factors are equal to m's iff none of a^p's prime factors are equal to m's
    gcf(a, m) = 1 iff gcf(a^p, m) = 1
    a has inverse iff a^p does

Treating inverses like negative powers
    Here are some properties that we would like negative powers to hold
        Let p,q be positive integers, and let a and m be ints such that a has an inverse
    (a^p)^-1 == (a^-1)^p (mod m)
        <==> a^p [(a^p)^-1 == (a^-1)^p] (mod m) because a^p has an inverse
        LHS (left-hand side) = a^p * (a^p)^-1 == 1 (mod m) because anything times its inverse is 1
        RHS = a^p * (a^-1)^p = a*a*a*... (p times) * a^-1*a^-1*a^-1*... (p times) == 1 (mod m)
        LHS == RHS == 1 (mod m), so (a^p)^-1 == (a^-1)^p (mod m) holds
    We now define a^-p such that a^-p == (a^p)^-1 == (a^-1)^p (mod m)
    a^(p-q) == a^p * a^-q (mod m).
        Assuming p > q, working with b^(q-p) with b = a^-1 otherwise
        LHS = a*a*a*... (p-q times)
        RHS = a*a*a*... (p times) * a^-1*a^-1*a^-1*... (q times)
            == a*a*a*... (p-q times) * a*a*a*... (q times) * a^-1*a^-1*a^-1*... (q times)
            == a*a*a*... (p-q times) * 1 == a*a*a*... (p-q times) (mod m)
        LHS == RHS == a*a*a*... (p-q times) (mod m)
    (a^-1)^-1 == a (mod m)
        a*a^-1 == 1 (mod m) by definition
        a <- a^-1 (i.e. substituting a with a^-1) ==> a^-1 * (a^-1)^-1 == 1 (mod m)
        a*a^-1 == a^-1*(a^-1)^-1 == 1 (mod m)
        a^-1*a - a^-1*(a^-1)^-1 = a^-1 (a - (a^-1)^-1) == 0 (mod m)
        a^-1 !== 0 (mod m) because if it did, a*a^-1 == 0 !== 1 (mod m), a contradiction
        Thus, a - (a^-1)^-1 == 0 (mod m) ==> a == (a^-1)^-1 (mod m)
        Note that (a^-1)^-1 = a is not necessarily true since inverses aren't unique, only unique modulo m
    Note that the aforementioned properties also hold if p = 0 or q = 0, since a^0 == 1 == 1^-1 (mod m)
 */
