import java.util.Scanner;

// Gives the number of 1s in binary forms of 1 to number to another inclusive
  // 5 and 7 would give x because 101, 110, and 111 have 7 1s
public class BitCount {
  // Gives binary number; or at least it did before I modified it
  public static String toBinary(long num) {
    StringBuilder bin = new StringBuilder();
    long log2 = (long) (Math.log(num)/Math.log(2));
    for (int i = 0; i <= log2; i++) {
      if (num % 2 == 1) {
        bin.append(i);
        bin.append(" ");
      }
      num /= 2;
    }
    return bin.toString();
  }

  public static long countOnes(long num) {
    String bin = toBinary(num);
    String[] binStrings = bin.split(" ");
    long totalOnes = 0;
    long num2;
    long num3;
    for (int i = 0; i < binStrings.length; i++) {
      num2 = Long.parseLong(binStrings[i]);
      num3 = (long) Math.pow(2, num2);
      totalOnes += num2 * num3/2 - (i+1) * num3;
    }
    totalOnes += num*binStrings.length;
    return totalOnes;
  }
  
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    while (true) {
      System.out.print("Enter numbers: ");
      long num1 = scan.nextLong();
      long num2 = scan.nextLong();
      System.out.println(countOnes(num2+1)-countOnes(num1));
    }
  }
}
// Might not look like much but math behind this makes this one of the hardest things I've programmed
  // Tried to explain but gave up halfway through
  // More so b/c I've made things unnecessarily complex though

/* Sum of 1s from (2^(n-1), 2^n]   (or [2^(n-1), 2^n) b/c they're both 1)
 = sum of 1s from (1, 2^(n-1)] + the number of numbers there
  // [4, 8) = 100, 101, 110, and 111 which is just 01, 10, 11, and 00 but w/ a 1 in front
 = sum of 1s from (1, 2^(n-1)] + 2^(n-1)
 
 Here's what that looks like
 n | Sum of 1s from (2^(n-1), 2^n]
 1 | 1                             | 1
 2 | 1 + 2                         | 3
 3 | 1 + (1+2) + 4                 | 8
 4 | 1 + (1+2) + (1+1+2+4) + 8     | 20
 
 Sum of 1s from (1, 2^n] = number above + sum of numbers above that
 n | Sum of 1s from (1, 2^n]   (number from above plus all the ones before it)
 1 | 1                          | 1
 2 | 1 + 3                      | 4
 3 | (1+3) + 8                  | 12
 4 | (1+3+8) + 20               | 32
 n | (1+3+8+20+...) + num above | 2^(n-1)*n   just b/c dude idk
   // this is where the num2*num3/2 comes from
 Sum of 1s from [1, 2^n] = 2^(n-1)*n + 1
 
 Sum of 1s from (2^n, k] where k is in [2^n, 2^(n+1)):
 11011: first 1 reappears 1011 times, second 1 reappears 11 times, and third reappears 1 time
 
 Altogether, sum of 1s from say 1 to 27 (11011)
  1     1     0     1     1
 +33   +13         +2    +1
 +11   +3          +1    +0
 = 64
 
 Sum of 1s from [a, b] = sum of 1s from [1, b] - [1,a]
   = (1, b) - (1, a) which I might've done here, i can't tell
 How all this math translates to the code, I'm not entirely sure
*/
