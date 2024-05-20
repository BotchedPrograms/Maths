// Note that when m is used as a parameter, the method has to do with something modulo m
// Mathematical explanations and other stuff at the bottom

import java.util.Arrays;
import java.util.Map;

public class MultiplicativeOrder {
    // Returns the smallest positive int k such that a^k mod m = 1
    public static long order(long a, long m) {
        if (LCMandGCF.gcf(a, m) != 1) {
            return -1;
        }
        long lambda = Lambda.lambda(m);
        Map<Long, Integer> factorMap = PrimeFactorization.factorAsMap(lambda);
        return checkFactor(a, m, lambda, factorMap);
    }

    // Checks if any of the factors f of the given factor of lambda satisfies a^f mod m = 1
        // If so, updates factor to be f
        // Works for a similar reason modifiedOrder does
    private static long checkFactor(long a, long m, long factor, Map<Long, Integer> factorMap) {
        for (Map.Entry<Long, Integer> primePower : factorMap.entrySet()) {
            long prime = primePower.getKey();
            int power = primePower.getValue();

            for (int i = 0; i < power; i++) {
                if (pow(a, factor / prime, m) != 1) {
                    break;
                }
                factor /= prime;
            }
        }
        return factor;
    }

    // Returns a^b % mod
    private static long pow(long a, long b, long mod) {
        if (b == 0) {
            return 1;
        }
        long smallPow = pow(a, b / 2, mod);
        if (b % 2 == 0) {
            return (smallPow * smallPow) % mod;
        }
        return ((a * smallPow) % mod * smallPow) % mod;
    }



    // Corollaries I didn't feel like putting somewhere else

    // Returns true iff a is a primitive root mod m, that is if order(a, m) == totient(m)
        // That's just a definition of a primitive root
    // A.k.a. a generator, since if a is a generator, a^k generates all numbers coprime to m for k = 0 to totient(m)
    private static boolean primitiveRoot(long a, long m) {
        if (LCMandGCF.gcf(a, m) != 1) {
            return false;
        }
        // modifiedOrder returns an int x such that a^x == 1 (mod m) and 0 < x < totient(m) if possible
        // Thus, this returns true iff x = totient(m)
        long totient = Totient.totient(m);
        return modifiedOrder(a, m, totient) == totient;
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
    public static boolean isCyclic(long n, long base) {
        if (n <= 2) {
            throw new IllegalArgumentException();
        }
        if (LCMandGCF.gcf(n, base) != 1) {
            return false;
        }
        if (!NumberOfFactors.isPrime(n)) {
            return false;
        }
        // Given that n is prime, lambda(n) = totient(n)
        return modifiedOrder(base, n, n - 1) == n - 1;
    }

    // Doesn't return the multiplicative order, just an int x such that a^x == 1 (mod m)
        // 0 < x < totient if such a value exists
    private static long modifiedOrder(long a, long m, long totient) {
        for (long primeFactor : PrimeFactorization.factorAsMap(totient).keySet()) {
            long bigFactor = totient / primeFactor;
            if (pow(a, bigFactor, m) == 1) {
                return bigFactor;
            }
        }
        return totient;
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
            for (int j = 0; j < i; j++) {
                if (primitiveRoot(j, i)) {
                    System.out.print(j + " ");
                }
            }
            System.out.println();
        }

        // 7, 17, 19, 23, 29, 47, 59, 61, 97, 109, 113, 131, 149, 167, 179, 181, 193, 223, 229, 233, 257, 263, 269, 313, 337, 367, 379, 383, 389, 419, 433, 461, 487, 491, 499, 503, 509, 541, 571, 577, 593, 619, 647, 659, 701, 709, 727, 743, 811, 821, 823, 857, 863, 887, 937, 941, 953, 971, 977, 983
        System.out.println();
        for (int i = 3; i < 1000; i++) {
            if (isCyclic(i, 10)) {
                System.out.print(i + ", ");
            }
        }
        // 3, 5, 17, 47, 59, 89, 97, 113, 127, 131, 137, 149, 167, 179, 181, 223, 229, 281, 293, 307, 311, 337, 347, 389, 401, 421, 433, 439, 443, 457, 487, 491, 499, 521, 547, 557, 569, 587, 599, 607, 617, 641, 647, 661, 677, 683, 709, 719, 733, 739, 769, 773, 797, 811, 823, 863, 881, 883, 887, 947, 953, 977, 991
        System.out.println();
        for (int i = 3; i < 1000; i++) {
            if (isCyclic(i, 23)) {
                System.out.print(i + ", ");
            }
        }
        // 5, 23, 31, 37, 59, 71, 73, 83, 101, 109, 137, 163, 173, 191, 223, 227, 233, 239, 241, 251, 263, 269, 271, 281, 293, 331, 359, 367, 373, 379, 401, 409, 419, 431, 449, 461, 467, 499, 509, 563, 569, 587, 599, 601, 613, 617, 677, 727, 739, 757, 769, 773, 797, 809, 863, 877, 883, 907, 911, 937, 971, 977
        System.out.println();
        for (int i = 3; i < 1000; i++) {
            if (isCyclic(i, 42)) {
                System.out.print(i + ", ");
            }
        }
        System.out.println();
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

Explanations for checkFactor
    In particular, how the way we avoid redundantly recursing the function on f
    Let f = p1^n1 * p2^n2 * ... * pl^nl = the current value of factor. a^f == 1 (mod m) holds
    Let o = p1^k1 * p2^k2 * ... * pl^kl = order(a, m). Note that kj is minimal and <= nj for all j
    Let f' = p1^n1 * p2^n2 * ... * pi^xi * ... * pl^nl satisfy a^f' == 1 (mod m) with xi being minimal
    We want to show that ki = xi
    If xi > ki, let g = o * p1^(n1-k1) * p2^(n2-k2) * ... * pi^0 * ... * pl^(nl-kl) = o * q
        g = p1^n1 * p2^n2 * ... * pi^ki * ... * pl^nl
        a^o == 1 (mod m) ==> a^g = a^(o*q) = (a^o)^q == 1^q == 1 (mod m)
        But a^f == 1 (mod m) too, so xi isn't minimal
    If xi < ki, let h = gcf(f', o) = p1^k1 * p2^k2 * ... * pi^xi * ... * pl^kl
        Let d1 = f'/h, and let d2 = o/h
            Explicitly, d1 = p1^(n1-k1) * p2^(n2-k2) * ... * pi^0 * ... * pl^(nl-kl) and d2 = pi^(ki-xi)
        Note that d1 is a multiple of powers of all p except pi, and d2 is a power of pi
        a^f' == a^o == 1 (mod m) ==> a^(h * d1) == a^(h * d2) == 1 (mod m) ==> (a^h)^d1 == (a^h)^d2 == 1 (mod m)
        Let b = a^h, meaning that b^d1 == b^d2 == 1 (mod m)
        gcf(d1, d2) = 1 ==> there are ints c1, c2 such that c1*d1 + c2*d2 = 1
            This follows from the Extended Euclidean Algorithm, which I don't feel like proving, see here
                https://brilliant.org/wiki/extended-euclidean-algorithm/
        Thus, b^(c1*d1 + c2*d2) = b^1 = b = (b^d1)^c1 * (b^d2)^c2 == 1^c1 * 1^c2 == 1 (mod m)
        b = a^h == 1 (mod m) ==> ki not minimal, a contradiction
        Note that c1 or c2 can be negative. Working with negative powers in modular arithmetic seems a bit sketchy
            But it essentially works because b has a modular multiplicative inverse since gcf(b, m) = 1

Explanations for isCyclic(n, base)
    Observe what happens when we divide 1 by 7
      --------------------------------
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
    More abstractly, 1/n base b generates a cyclic number if the division cycles through all ints from 1 to n-1
    Which corresponds with b^i mod n having unique values for i from 0 to n-2
        (those unique values being an int from 1 to n-1)

    As fun as it may be to contemplate if 1/1 or 1/2 base anything generates a cyclic number, let's now assume n > 2

    (I) If gcf(n, b) != 1, 1/n base b doesn't generate a cyclic number
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

    (II) If there is an int x such that 0 < x < n-1 and b^x == 1 (mod n), 1/n base b doesn't generate a cyclic number
    If there is an int x such that 0 < x < n-1 and b^x == 1 (mod n)
        b^x == b^0 == 1 (mod n)
        Thus, b^i mod n doesn't have unique values for i from 0 to n-2
        Thus, 1/n base b doesn't generate a cyclic number

    (III) If n is not prime, 1/n base b doesn't generate a cyclic number
    If gcf(n, b) != 1
        1/n base b isn't cyclic (by (I))
    Otherwise
        Recall that totient(n) is the number of relatively prime numbers from 1 to n
        1 is considered relatively prime to n, so totient(n) >= 1 > 0
        If n is not prime, n has >1 factor less than n (note that we don't deal with the edge case 1 since n > 2)
            Note that for all such factors f, f and n share a factor, namely f
            Thus, since n has >1 factor less than n, totient(n) < n-1
        Thus, there is an x such that 0 < x = totient(n) < n-1 and b^x == 1 (mod n)
        1/n base b doesn't generate a cyclic number (by (II))

    The last thing we want to show is that 1/n base b generates a cyclic number iff order(b, n) = n-1
    (==>) 1/n base b generates a cyclic number ==> order(b, n) = n-1
        By the contrapositive of (I), if it generates a cyclic number, gcf(b, n) = 1
            Which tells us b^totient(n) == 1 (mod n)
        By the contrapositive of (II), if it generates a cyclic number, b^x !== 1 (mod n) for all 0 < x < n-1
        By the contrapositive of (III), if it generates a cyclic number, n is prime
            Which tells us totient(n) = n-1
            Since all ints from 1 to m-1 are relatively prime to m but m isn't, almost by definition of m being prime
        Altogether, we find that totient(n) = n-1 is the smallest positive int k such that b^k == 1 (mod n)
        Thus, order(b, n) = n-1
    (<==) order(b, n) = n-1 ==> 1/n base b generates a cyclic number
        Assume for contradiction that there is x and y such that 0 <= x < y <= n-2 and b^x == b^y (mod n)
            Thus, there is an int k = y-x such that 0 < k <= n-2 < n-1 = order(b,n) and b^k == 1 (mod n)
            Which contradicts how order(b, n) = n-1 is the smallest positive int z such that b^z == 1 (mod n)
        Thus, b^i mod n is unique for all i from 0 to n-2
        Thus, 1/n base b generates a cyclic number
    Which completes the proof. Fucking finally.
    Shit there's more. Ahem.

Explanations for modifiedOrder(a, m)
    Made to optimize isCyclic
    If the method has to check order(a, m), we know m is prime
        lambda(m) = totient(m) = m-1
        So we don't need to calculate lambda(m) the long way for this
        We also know that gcf(a, m) != 1 ==> a^totient(m) == 1 (mod m)
    As shown earlier, we only need to check the factors of m-1 to find the multiplicative order
    Here though, we're not interested in the smallest int x such that a^x == 1 (mod m) but if there is an x < m-1
    Checking every factor is a bit wasteful since we know a^(kx) !== 1 (mod m) guarantees that a^x !== 1 (mod m)
        Since, by the contrapositive, if a^x == 1 (mod m), a^(kx) = (a^x)^k == 1^k == 1 (mod m)
    Intuitively, we then only need to check the factors that are close to but not exactly m-1
        We don't check m-1 since we already know a^totient(m) = a^(m-1) == 1 (mod m)
    In particular, we only need to check all factors in the form of (m-1)/prime factor
    Proof: Let m-1 = p1^k1 * p2^k2 * p3^k3 * ... * pl^kl
        Addressing notable edge cases
            l can be 1 if m = 3 and this still works
            Don't have to worry about possibly weird case m-1 = 1 since m = n > 2
        We want to show that every factor of m-1 besides itself divides (m-1)/pi for some int i from 1 to l
            Where a divides b iff b is divisible by a. It's just another way of saying it
        Assume for contradiction there is a factor f' of m-1 such that f' != m-1 doesn't divide (m-1)/pi for all i
            Every factor f of m-1 can be written in the form of p1^n1 * p2^n2 * p3^n2 * ... * pl^nl
                Where ni is some int from 0 to ki
            Let f1 = p1^x1 * p2^x2 * ... pl^xl, and let f2 = p1^y1 * p2^y2 * ... * pl^yl
            f1 does not divide f2 iff xi > yi for some i
            Let f' = p1^n1 * p2^n2 * p3^n2 * ... * pl^nl
            Note that (m-1)/pi = p1^k1 * p2^k2 * ... * pi^(ki-1) * ... * pl^kl
            nj <= kj for all j != i, so (m-1)/pi does not divide f' iff ni > ki-1
                ni <= ki, so ni = ki
            We can do this for all i to find that f' = p1^n1 * p2^n2 * ... * pl^nl
            Thus, f' = m-1, which is a contradiction
        Which completes the proof

Addressing known abuses of notation I've used
    order(a, m) is usually written as ord_m(a) or other things
    "isCyclic" is wrong since 1/7 in base 10 isn't considered cyclic, its repeating digits 142857 is
    When I've said a^k mod m = 1, it's usually formally written as a^k is congruent to 1 modulo m
        They're the same thing for all m > 1. If you want to be a smart-ass, yes, they are different for m = 1.
        I write it this way when I can to make things less complicated for the uninitiated
        Not so much here but math jargon has made learning about these kinds of things harder than they should be
 */
