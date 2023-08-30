// Note that when m is used as a parameter, the method has to do with something modulo m
// Mathematical explanations and other stuff at the bottom

public class MultiplicativeOrder {
    // Returns the smallest positive int k such that a^k mod m = 1
        // Gets by finding factors of lambda and seeing if a^factor mod m = 1
    public static long order(long a, long m) {
        if (LCMandGCF.gcf(a, m) != 1) {
            return -1;
        }
        long lambda = Lambda.lambda(m);
        long[] factors = NumberOfFactors.getFactors(lambda);
        for (long factor : factors) {
            if (pow(a, factor, m) == 1) {
                return factor;
            }
        }
        return lambda;
    }

    // Returns a^b % mod
    private static long pow(long a, long b, long mod) {
        if (b == 0) {
            return 1;
        }
        long smallPow = pow(a, b / 2, mod) % mod;
        if (b % 2 == 0) {
            return (smallPow * smallPow) % mod;
        }
        return ((a * smallPow) % mod * smallPow) % mod;
    }



    // Corollaries I didn't feel like putting somewhere else

    // Returns true iff a is a primitive root mod m, that is if order(a, m) == totient(m)
        // That's just a definition of a primitive root
    private static boolean primitiveRoot(long a, long m) {
        return order(a, m) == Totient.totient(m);
    }

    // Returns true iff floor(1/n * 10^(n-1)) gives a cyclic number
    // For example, isCyclic(7, 10) is true
        // x = floor(1/7 * 10^6) = 142857
        // 2x = 285714
        // 3x = 428571
        // 4x = 571428
        // 5x = 714285
        // 6x = 857142
        // Note that these are all the same sequence of letters but starting at a different digit, so 142857 is cyclic
    private static boolean isCyclic(long n, long base) {
        if (n <= 2) {
            throw new IllegalArgumentException();
        }
        if (LCMandGCF.gcf(n, base) != 1) {
            return false;
        }
        if (!NumberOfFactors.isPrime(n)) {
            return false;
        }
        return order(base, n) == n - 1;
    }

    public static void main(String[] args) {
        System.out.println(order(10, 7)); // 6
        System.out.println(order(10, 13)); // 6
        System.out.println(order(10, 17)); // 16
        System.out.println(order(10, 19)); // 18
        System.out.println(order(34, 23)); // 22
        System.out.println(order(14, 93884313611L)); // 118,120,080

        // https://en.wikipedia.org/wiki/Primitive_root_modulo_n#Table_of_primitive_roots
        System.out.println();
        for (int i = 1; i <= 31; i++) {
            System.out.print(i + ": ");
            for (int j = 1; j < i; j++) {
                if (primitiveRoot(j, i)) {
                    System.out.print(j + " ");
                }
            }
            System.out.println();
        }

        System.out.println();
        for (int i = 3; i < 1000; i++) {
            if (isCyclic(i, 10)) {
                System.out.print(i + ", ");
            }
        }
    }
}

/*
Why only need to check factors of lambda for multiplicative orders
    Given:
        a == b (mod n) means a mod n = b mod n, or more formally a and b are congruent modulo n
        a^lambda(m) == 1 (mod m) (See Lambda.java, requires prior knowledge about Euler's theorem unfortunately)
    Assume for contradiction x = order(a, m) but lambda(m) is not divisible by x
    x = order(a, m) ==> a^x == 1
    lambda(m) is not divisible by x ==> lambda(m) = kx + h where k is an int and 0 < h < x
    Thus, a^lambda(m) = a^(kx + h) = (a^x)^k * a^h == 1^k * a^h = a^h == 1 (mod m)
    Thus, there is a positive int h < x such that a^h == 1 (mod m)
    Thus, x is not the multiplicative order

Explanations for isCyclic(n, base)
    Observe what happens when we divide 1 by 7
      -----------------------------
    7 | 1.000000 <---------- 1
        - 7
        ---
          30 <-------------- 3
        - 28
         ---
           20 <------------- 2
         - 14
          ---
            60 <------------ 6
          - 56
           ---
             40 <----------- 4
           - 35
            ---
              50 <---------- 5
            - 49
             ---
               1
    With this in mind, when we divide 2 by 7, we cycle through the same digits (1,3,2,6,4,5 repeat) but starting with 2
    All of 1/7, 2/7, 3/7, ..., 6/7 goes through the same digits since we cycle through all of 1,2,3,...,6
        For this reason, the repeating digits of 1/7 -- 142857 -- is said to be a cyclic number
    More abstractly, 1/n base b yields a cyclic number if the division cycles through all ints from 1 to n-1
    Which corresponds with b^i mod n having unique values for i from 0 to n-2
        (those unique values being an int from 1 to n-1)

    As fun as it may be to contemplate if 1/1 or 1/2 base anything yields a cyclic number, let's now assume n > 2

    (I) If gcf(n, b) != 1, 1/n base b isn't cyclic
    If g = gcf(n, b) != 1
        Let b^k = cn + h where c, h, and k are ints; k > 0; and 0 <= h < n
        Thus, b^k == cn + h (mod g)
        Thus, 0 == h (mod g)
        Thus, h != g-1 (since g != 1)
        Thus, b^k == h != g-1 (mod n)
        Thus, b^k == g-1 (mod n) has no solutions for k > 0
        If k = 0 is not a solution as well
            b^k == g-1 (mod n) has no solutions for k >= 0
            2 <= g <= n ==> 1 <= g-1 <= n-1
            Thus, there is no int k >= 0 such that b^k == g-1 (mod n) where g-1 is in between 1 and n-1 inclusive
        Otherwise (if k = 0 is a solution)
            b^k == 1 == g-1 (mod n) and g-1 < n ==> g = 2 
            n > 2 and g = 2 ==> n >= 4 ==> n-1 >= 3 >= 1
            Consider b^k == 3 (mod n)
                This has no solutions for k > 0 similar to how b^k == g-1 (mod n) didn't
                k = 0 isn't a solution since b^k == 1 !== 3 (mod n)
            Thus, there is no int k >= 0 such that b^k == 3 (mod n) where 3 is in between 1 and n-1 inclusive
        Thus, 1/n base b isn't cyclic

    (II) If there is an int x such that 0 < x < n-1 and b^x == 1 (mod n), 1/n base b doesn't yield a cyclic number
    If there is an int x such that 0 < x < n-1 and b^x == 1 (mod n)
        b^x == b^0 == 1 (mod n)
        Thus, b^i mod n doesn't have unique values for i from 0 to n-2
        Thus, 1/n base b doesn't yield a cyclic number

    (III) If n is not prime, 1/n base b doesn't yield a cyclic number
    If gcf(n, b) != 1
        1/n base b isn't cyclic (by (I))
    Otherwise
        Recall that totient(n) is the number of relatively prime numbers from 1 to n
        1 is considered relatively prime to n, so totient(n) >= 1 > 0
        If n is not prime, it has >1 factor (recall that n > 1)
            Note that for all factors f of n, they share a factor, namely f
            Thus, since n has >1 factor, totient(n) < n-1
        Thus, there is an x such that 0 < x = totient(n) < n-1 and b^x == 1 (mod n)
        1/n base b doesn't yield a cyclic number (by (II))

    The last thing we want to show is that 1/n base b yields a cyclic number iff order(b, n) = n-1
    (==>) 1/n base b yields a cyclic number ==> order(b, n) = n-1
        By the contrapositive of (I), if it yields a cyclic number, gcf(b, n) = 1
            Which tells us b^totient(n) == 1 (mod n)
        By the contrapositive of (II), if it yields a cyclic number, there is no x < n-1 such that b^x == 1 (mod n)
        By the contrapositive of (III), if it yields a cyclic number, n is prime
            Which tells us totient(n) = n-1
        Altogether, we find that totient(n) = n-1 is the smallest positive int k such that b^k == 1 (mod n)
        Thus, order(b, n) = n-1
    (<==) order(b, n) = n-1 ==> 1/n base b yields a cyclic number
        Assume for contradiction that there is x and y such that 0 <= x < y <= n-2 and b^x == b^y (mod n)
            Since there is an int k such that b^k == 1 (mod n) (namely order(b,n)), there is a b^(-1)
                Where b^(-1), the modular multiplicative inverse, is a number such that b * b^(-1) == 1 (mod n)
                And one possible value of b^(-1) is b^(k-1) since b * b^(k-1) = b^k == 1 (mod n)
            (b^x == b^y) * (b^(-1))^x (mod n)
                b^x * (b^(-1))^x == b^(y-x) * b^x * (b^(-1))^x (mod n)
                b^x * (b^(-1))^x = (b * b^(-1))^x == 1 (mod n)
            Thus, 1 == b^(y-x) (mod n)
                y > x ==> y-x > 0
                x >= 0 ==> -x <= 0 ==> y-x <= y <= n-2
            Thus, there is an int k = y-x such that 0 < k <= n-2 < n-1 = order(b,n) and b^k == 1 (mod n)
            Which contradicts how order(b, n) = n-1 is the smallest positive int such that b^(n-1) == 1 (mod n)
        Thus, b^i mod n is unique for all i from 0 to n-2
        Thus, 1/n base b yields a cyclic number
    Which completes the proof. Fucking finally.

Addressing known abuses of notation I've used
    order(a, m) is usually written as ord_m(a) or other things
    "isCyclic" is wrong since 1/7 in base 10 isn't considered cyclic, its repeating digits 142857 is
    When I've said a^k mod m = 1, it's usually formally written as a^k is congruent to 1 modulo m
        They're the same thing for all m > 1. If you want to be a smart-ass, yes, they are different for m = 1.
        I write it this way when I can to make things less complicated for the uninitiated
        Not so much here but math jargon has made learning about these kinds of things harder than they should be
 */
