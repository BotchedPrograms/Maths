import java.util.ArrayList;
import java.util.Scanner;

// Put in number, gives prime factorization
  // 12 -> 2 2 3 b/c 2 x 2 x 3 = 12
public class PrimeFactorization {
	public static void factor(long num, ArrayList<Long> smallPrimes, int i, StringBuilder factors) {
		if (num % 2 == 0) {
			factors.append(2 + " ");
			factor(num/2, smallPrimes, i, factors);
			return;
		}
		int sqrt = (int) Math.sqrt(num);
		// l1 short for loop 1; not the best name but I use it an awful lot
			// Useful for continue-ing
		// int i = 0 omitted b/c I don't want to keep resetting it to 0
			// Everything in a for loop parentheses can be omitted to be for(;;) which would effectively be the same as while (true)
		l1: for (; i <= sqrt; i += 2) {
			for (int j = 0; j < smallPrimes.size() - 1; j++) {
				if (i % smallPrimes.get(j) == 0) {
					continue l1;
				} else if (Math.sqrt(i) < smallPrimes.get(j)) {
					break;
				}
			}
			smallPrimes.add((long) i);
			if (num % i == 0) {
				factors.append(i + " ");
				factor(num/i, smallPrimes, i, factors);
				return;
			}
		}
		/* Condensed version of 
		if (num != 1 || factors.length() == 0) {
			System.out.println(num);
		} else {
			System.out.println();
		}
		 */
		factors.append((num != 1 || factors.length() == 0) ? num : "");
		System.out.println(factors);
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String input;
		long num;
		while (true) {
			input = scan.nextLine();
			// Checks if String is int
				// Don't know how it works, I just modified what I saw from stackoverflow
			if (!input.matches("-?\\d+(\\d+)?")) {
				break;
			}
			num = Long.parseLong(input);
			factor(num, new ArrayList<Long>(), 3, new StringBuilder());
		}
	}
}
