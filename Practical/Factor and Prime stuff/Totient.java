// The toitent function counts the number of non-relatively primes from 1 to n
// For n = 12:
// 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
// 1, 5, 7, 11 aren't relatively prime to 12 so the totient function gives 4
// Also equals n * (1 - 1/p1) * (1 - 1/p2) * ... where ps are the unique prime factors of n
// For n = 12:
// 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
//    2     4     6     8     10      12      removing multiples of 2 / removing half of numbers
//       3                 9                  removing multiples of 3 / removing remaining third of numbers
// 1           5     7            11          what's left is thus 12 * (1 - 1/2) * (1 - 1/3) = 4
// Made by Euler (b/c of course) and is used in number theory and encryption

import java.util.List;

public class Totient {
    public static long totient(long num) {
        if (num < 1) {
            throw new IllegalArgumentException();
        }
        // Method returns 1 without this, but included anyways
        if (num == 1) {
            return 1;
        }
        // Uses Euler's product formula, totient(n) = n * product of (1 - 1/p) for all unique primes p that divide n
        long product = num;
        List<Long> primeFactors = PrimeFactorization.factor(num);
        PrimeFactorization.removeDuplicates(primeFactors);
        for (Long primeFactor : primeFactors) {
            product /= primeFactor;
            product *= primeFactor - 1;
        }
        return product;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            System.out.printf("%2d ", totient(i));
            if (i % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println(totient(93884313611L));
    }
}
