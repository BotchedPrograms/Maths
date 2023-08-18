// As in Carmichael's Lambda function, which gives a number such that a^lambda(n) mod n = 1 (if a and n are coprime)
// It's stronger than Euler's theorem, which has a^totient(n) mod n = 1 (if a and n are coprime)
// If n = p1^k1 * p2^k2 * ..., lambda(n) where p1, p2, ... are prime factors of n,
    // lambda(n) = lcm(totient(p1^k1), totient(p2^k2), ...)
// Also there's the edge case where lambda(2^k) = 1/2 * totient(2^k) for k >= 3
    // a^lambda(n) mod n = 1 still holds for this case, for reasons I no longer understand

import java.util.Map;

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
    }
}
