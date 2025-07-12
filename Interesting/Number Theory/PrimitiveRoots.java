import java.util.*;

/*
See https://en.wikipedia.org/wiki/Primitive_root_modulo_n#Finding_primitive_roots for the theorems being used
    Also note the fact that a primitive root modulo m exists iff m = 1,2,4,p,p^k or 2p^k where p is an odd prime
My proofs for them are long and disorganized, and I don't want to spend any more time dealing with them
Here's a rough outline for the proofs (hopefully they were correct in the first place):
    https://drive.google.com/file/d/1inkLUxrfLTQmOz2FnlGIw1iWrUUvCd_l/view?usp=sharing
And here's the "sheet before this one" from the notes
    https://drive.google.com/file/d/1kcuxiB7de54v5lbZFdbwyqeriT4vVx57/view?usp=sharing
 */

public class PrimitiveRoots {
    /**
     * Returns whether a is a primitive root modulo m
     */
    public static boolean isPrimitiveRoot(long a, long m) {
        return MultiplicativeOrder.primitiveRoot(a, m);
    }

    /**
     * Returns whether a is a primitive root modulo m, with more info as parameters to help with efficiency
     */
    private static boolean isPrimitiveRoot(long a, long m, long totient, Map<Long, Integer> totientFactors) {
        if (LCMandGCF.gcf(a, m) != 1) {
            return false;
        }
        for (long primeFactor : totientFactors.keySet()) {
            if (pow(a, totient / primeFactor, m) == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a primitive root modulo p, which is known to exist for all primes p
     */
    private static long getPrimitiveRootPrime(long p) {
        Map<Long, Integer> totientFactors = PrimeFactorization.factorAsMap(p - 1);
        for (long i = 2; i < p; i++) {
            if (isPrimitiveRoot(i, p, p - 1, totientFactors)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns a primitive root modulo p^k. Because of math, the exact value of k doesn't matter.
     */
    private static long getPrimitiveRootPrimePower(long p) {
        // p = 2 should be dealt with in getPrimitiveRoot
        if (p == 2) {
            return -1;
        }
        long g = getPrimitiveRootPrime(p);
        if (pow(g, p - 1, p * p) != 1) {
            return g;
        }
        return g + p;
    }

    /**
     * Returns a primitive root modulo 2p^k.
     */
    private static long getPrimitiveRootTwoPrimePower(long p, long twopk) {
        if (p == 2) {
            return -1;
        }
        long g = getPrimitiveRootPrimePower(p);
        if (g % 2 == 1) {
            return g;
        }
        return g + twopk / 2;
    }

    /**
     * Returns a primitive root modulo m, -1 if there is none
     */
    public static long getPrimitiveRoot(long m) {
        if (m == 1 || m == 2) {
            return 1;
        }
        if (m == 4) {
            return 3;
        }

        Map<Long, Integer> mFactors = PrimeFactorization.factorAsMap(m);
        // If m == p or m == p^k
        if (mFactors.size() == 1) {
            long p = mFactors.keySet().iterator().next();
            return getPrimitiveRootPrimePower(p);
        }
        if (mFactors.size() == 2) {
            // If m != 2p^k
            if (m % 4 != 2) {
                return -1;
            }
            // Otherwise (if m == 2p^k)
            Iterator<Long> iter =  mFactors.keySet().iterator();
            long p = iter.next();
            if (p == 2) {
                p = iter.next();
            }
            return getPrimitiveRootTwoPrimePower(p, m);
        }
        return -1;
    }

    /**
     * Returns an unsorted list of primitive roots modulo m
     */
    public static List<Long> getPrimitiveRoots(long m) {
        if (m < 0) {
            return null;
        }
        long primitiveRoot = getPrimitiveRoot(m);
        if (primitiveRoot == -1) {
            return new LinkedList<>();
        }

        List<Long> primitiveRoots = new LinkedList<>();
        List<Long> primitivesResidues = getPrimitiveResidues(Totient.totient(m));
        for (long residue : primitivesResidues) {
            primitiveRoots.add(pow(primitiveRoot, residue, m));
        }
        return primitiveRoots;
    }

    /**
     * Returns integers from 1 to m that are relatively prime to m
     */
    private static List<Long> getPrimitiveResidues(long m) {
        List<Long> primitiveResidues = new LinkedList<>();
        Set<Long> primeFactors = PrimeFactorization.factorAsMap(m).keySet();
        l0: for (long i = 1; i <= m; i++) {
            for (long primeFactor : primeFactors) {
                if (i % primeFactor == 0) {
                    continue l0;
                }
            }
            primitiveResidues.add(i);
        }
        return primitiveResidues;
    }

    /**
     * Returns a^b % mod
     */
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

    public static void main(String[] args) {
        for (int N = 2; N <= 1000; N++) {
            List<Long> primRoots = getPrimitiveRoots(N);
            Collections.sort(primRoots);
            System.out.println(N + " " + primRoots);
        }
    }
}
