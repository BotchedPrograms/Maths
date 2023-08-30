// As in Carmichael's Lambda function, which gives a number such that a^lambda(n) mod n = 1 (if a and n are coprime)
// It's stronger than Euler's theorem, which has a^totient(n) mod n = 1 (if a and n are coprime)
// If n = p1^k1 * p2^k2 * ..., lambda(n) where p1, p2, ... are prime factors of n,
    // lambda(n) = lcm(totient(p1^k1), totient(p2^k2), ...)
// Also there's the edge case where lambda(2^k) = 1/2 * totient(2^k) for k >= 3
// Mathematical explanations are in the bottom (given that a^totient(n) mod n = 1)

import java.util.Map;
import java.util.Scanner;

public class Lambda {
    public static long lambda(long num) {
        if (num < 1) {
            throw new IllegalArgumentException();
        }
        if (num == 1) {
            return 1;
        }
        Map<Long, Integer> primes = PrimeFactorization.factorAsMap(num);
        long lcm = -1;
        for (Map.Entry<Long, Integer> primePower : primes.entrySet()) {
            long modTot = modifiedTotient(primePower.getKey(), primePower.getValue());
            if (lcm == -1) {
                lcm = modTot;
            } else {
                lcm = LCMandGCF.lcm(lcm, modTot);
            }
        }
        return lcm;
    }

    // totient(p^a) = p^a * (1-p) = p^a - p^(a-1)
    private static long modifiedTotient(long prime, int power) {
        long pow = pow(prime, power);
        long totient = pow - pow / prime;
        if (prime == 2 && power >= 3) {
            return totient / 2;
        }
        return totient;
    }

    private static long pow(long a, int b) {
        if (b == 0) {
            return 1;
        }
        long smallPow = pow(a, b / 2);
        // should never be negative, but included to terminate if it happens
        if (b % 2 <= 0) {
            return smallPow * smallPow;
        }
        return a * smallPow * smallPow;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            System.out.printf("%2d ", lambda(i));
            if (i % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println(lambda(93884313611L));

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter numbers:");
        while (true) {
            String input = scan.nextLine();
            // Checks if String is integer
            // Don't know how it works, I just modified what I saw from stackoverflow
            if (!input.matches("-?\\d+(\\d+)?")) {
                break;
            }
            long num = Long.parseLong(input);
            System.out.println(lambda(num));
        }
    }
}

/*
lambda(n) <= totient(n)
    lambda(p^k) <= totient(p^k) (they're equal except in case of p = 2 and k >= 3, where lambda(p^k) = totient(p^k)/2)
    thus, lambda(n) = lcm(lambda(p1^k1), lambda(p2^k2), ...)
                   <= lcm(totient(p1^k1), totient(p2^k2), ...)
                   <= lcm(p1^k - p1^(k-1), p2^k - p2^(k-1), ...)
                   <= (p1^k1 - p1^(k-1)) * (p2^k - p2^(k-1)) * ... (since lcm is less than other common multiples)
                    = totient(n)

a^totient(n) mod n = 1 ==> a^lambda(n) mod n = 1 (ignoring special case with n = 2^k for k >= 3)
    Givens
        n = p1^k1 * p2^k2 * ...
        a | b means a divides b means b is divisible by a
        a == b (mod n) means a mod n = b mod n, or more formally a and b are congruent modulo n
        Euler's Totient Theorem
            a^totient(n) == 1 (mod n)
        Chinese Remainder Theorem
            x == a1 (mod n1), x == a2 (mod n2), ... has a unique solution mod n if n1, n2, ... are all relatively prime
            Good explanation: https://www.youtube.com/watch?v=SU3vouq7lN4
    lambda(n) = lcm(lambda(p1^k1), lambda(p2^k2), ...)
        ==> lambda(pi^ki) = totient(pi^ki) | lambda(n) for 1 <= i <= n
        ==> lambda(n) = totient(pi^ki) * x
    a^totient(pi^ki) == 1 (mod p1^k1)
        ==> a^lambda(n) = a^(totient(pi^ki) * x) = (a^totient(pi^ki))^x == 1^x (mod pi^ki)
        ==> a^lambda(n) == 1 (mod pi^ki)
    Chinese Remainder Theorem tells us that a^lambda(n) == 1 (mod n) is the unique solution

Special case where lambda(2^k) = 1/2 * totient(2^k) for k >= 3
    a^2 == 1 (mod 8) if a and 8 are relatively prime
        a and 8 are relatively prime ==> a is odd ==> a = 2n + 1
        (2n + 1)^2 = 4n^2 + 4n + 1 = 4n(n+1) + 1
        n(n+1) is even
            if n is even, n(n+1) is even
            if n is odd, n + 1 is even ==> n(n+1) is even
        ==> (2n + 1)^2 = 8*something + 1 ==> (2n + 1)^2 == 1 (mod 8)
    ==> Since a^2 == 1 (mod 8), we can just define lambda(8) to be equal to 2
    
    a^x == 1 (mod 2^k) ==> a^(2x) == 1 (mod 2^(k+1))
        a^x == 1 (mod 2^k) ==> a^x == 1 or 2^k + 1 (mod 2^(k+1))
        a^(2x) = (a^x)^2 == 1^2 or (2^k + 1)^2 (mod 2^(k+1))
            (2^k + 1)^2 = 2^(2k) + 2^(k + 1) + 1 = 2^(k + 1) * (2^(k - 1) * 1) + 1
            if k - 1 >= 0, (2^k + 1)^2 == 1 (mod 2^(k+1))
        ==> a^(2x) == 1 (mod 2^(k+1))
    If we set x as lambda(2^k), we find that we can define lambda(2^(k+1)) to be equal to 2 * lambda(2^k)
    We do this for k >= 3 to make use of lambda(8) = 2
*/
