import java.util.ArrayList;
import java.util.Scanner;

//  C  = the number of combinations of 5 items out of 7 possible ones
// 7 5
  // nCr = n!/((n-r)!r!)
// Program gives you those combinations if you input 7 and 5

public class CombinationLister {
  /* Explanation
    Let's say a = 3 and b = 6
    First gets all numbers from 000 - 333 (length a) in base-4 (b-a+1)
    Removes numbers whose digits are greater than 3 (b-a)
    What's left in this case is 000 001 002 003 010 011 012 020 021 030 100 101 102 110 111 120 200 201 210 300
    We take numbers 0 1 2 3 4 5 6
    000 means index 0 of 0123456 (0), index 0 of 123456 (1), and index 0 of 23456 (2)
    120 means index 1 of 0123456 (1), index 2 of 23456  (4), and index 0 of 56    (5)
    Do that for all the numbers and you get the combinations
  There's probably definitely a better way to do this
  */
  
  // b then a b/c I forgot it's 7C5 not 5C7
  public static int[][] listCombinations(int b, int a) {
    ArrayList<String> nums = new ArrayList<>();
    // Sets end to 300, as in it only does 000-300, not actually 000-333 since 301-333 are evidently too much
    int end = (int) ((b-a)*Math.pow(b-a+1, a-1));
    String length = "%0" + a + "d";
    for (int i = 0; i <= end; i++) {
      // Putting the numbers in base-(b-a+1)
        // String.format part adds 0s
      nums.add(String.format(length, Integer.parseInt(baseA(i, b-a+1))));
    }
    // Gets rid of digits greater than b-a
    for (int i = nums.size() - 1; i >= 0; i--) {
      if (sumOfDigits(nums.get(i)) > b-a) {
        nums.remove(i);
      }
    }
    int[][] perms = new int[nums.size()][a];
    int[] wholes = new int[b];
    // Take the numbers from 0 - b
    for (int i = 0; i < b; i++) {
      wholes[i] = i;
    }
    int index;
    // Does the index # of numbers stuff
    for (int i = 0; i < nums.size(); i++) {
      index = 0;
      for (int j = 0; j < a; j++) {
        index += Integer.parseInt(nums.get(i).substring(j, j+1));
        perms[i][j] = wholes[index];
        index++;
      }
    }
    return perms;
  }

  // Return num in base-a
  public static String baseA(int num, int a) {
    StringBuilder str = new StringBuilder();
    if (num == 0) {
      return "0";
    }
    while (num >= 1) {
      str.append(num % a);
      num /= a;
    }
    return String.valueOf(str.reverse());
  }

  // Returns sum of digits in String
  public static int sumOfDigits(String num) {
    int sum = 0;
    for (int i = 0; i < num.length(); i++) {
      sum += Integer.parseInt(num.substring(i, i+1));
    }
    return sum;
  }

  // Prints out int[][], useful for quickly printing combinations
  public static void print(int[][] arr) {
    for (int[] ints : arr) {
      for (int anInt : ints) {
        System.out.print(anInt + " ");
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String[] input;
    System.out.println("Enter a number: ");
    while (true) {
      input = scan.nextLine().split(" ");
      // Checks if String is integer
        // Don't know how it works, I just modified what I saw from stackoverflow
      if (input.length != 2) break;
      if (!input[0].matches("-?\\d+(\\d+)?") || !input[1].matches("-?\\d+(\\d+)?")) break;
      print(listCombinations(Integer.parseInt(input[0]), Integer.parseInt(input[1])));
    }
    scan.close();
  }
}
