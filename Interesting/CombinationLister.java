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
    Do that for all the numbers, and you get the combinations
  There's probably definitely a better way to do this
  */

  // b then a b/c I forgot it's 7C5 not 5C7
  public static int[][] listCombinations(int b, int a) {
    if (b < a) {
      return new int[0][0];
    }
    ArrayList<String> nums = new ArrayList<>();
    // Sets end to 300, as in it only does 000-300, not actually 000-333 since 301-333 are evidently too much
    int end = (int) ((b-a)*Math.pow(b-a+1, a-1));
    for (int i = 0; i <= end; i++) {
      // Putting the numbers in base-(b-a+1)
        // String.format part adds 0s
      nums.add(addZeros(baseA(i, b-a+1), a));
    }
    // Gets rid of digits greater than b-a
    for (int i = nums.size() - 1; i >= 0; i--) {
      if (sumOfDigits(nums.get(i)) > b-a) {
        nums.remove(i);
      }
    }
    int[][] perms = new int[nums.size()][a];
    int[] wholes = new int[b];
    for (int i = 0; i < b; i++) {
      wholes[i] = i;
    }
    int index;
    for (int i = 0; i < nums.size(); i++) {
      index = 0;
      for (int j = 0; j < a; j++) {
        index += charToInt(nums.get(i).charAt(j));
        perms[i][j] = wholes[index];
        index++;
      }
    }
    return perms;
  }

  // Returns num in base-a
  public static String baseA(int num, int a) {
    StringBuilder str = new StringBuilder();
    if (num == 0) {
      return "0";
    }
    while (num >= 1) {
      str.append(intToChar(num % a));
      num /= a;
    }
    return String.valueOf(str.reverse());
  }

  // Returns sum of digits in String
  public static int sumOfDigits(String num) {
    int sum = 0;
    for (int i = 0; i < num.length(); i++) {
      sum += charToInt(num.charAt(i));
    }
    return sum;
  }

  // Adds zeros to front of String until String has given length
  public static String addZeros(String str, int length) {
    StringBuilder sb = new StringBuilder(str);
    for (int i = str.length(); i < length; i++) {
      sb.insert(0, 0);
    }
    return sb.toString();
  }

  // Changes char to int by making use of ascii
  public static int charToInt(char digit) {
    if (bounded(digit, 48, 57)) return digit-48;  // Turns '0'-'9' to 0-9
    if (bounded(digit, 97, 122)) return digit-87;  // Turns 'a'-'z' to 10-35
    if (bounded(digit, 65, 90)) return digit-29;  // Turns 'A'-'Z' to 36-61
    char[] characters = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '_', '+', '[', ']', '\\', '{', '}', '|', ';', '\'', ':', '"', ',', '.', '/', '<', '>', '?', '`', '~'};
    if (contains(characters, digit)) return indexOf(characters, digit)+62;  // Turns other characters to other ints
    return -1;
  }

  // Changes int to char
  public static char intToChar(int digit) {
    if (bounded(digit, 0, 9)) return (char) (digit+48);  // Turns 0-9 to '0'-'9'
    if (bounded(digit, 10, 35)) return (char) (digit+87);  // Turns 10-35 to 'a'-'z'
    if (bounded(digit, 36, 61)) return (char) (digit+29);  // Turns 36-61 to 'A'-'Z'
    char[] characters = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '_', '+', '[', ']', '\\', '{', '}', '|', ';', '\'', ':', '"', ',', '.', '/', '<', '>', '?', '`', '~'};
    if (bounded(digit, 62, 93)) return characters[digit-62];  // Turns other ints to other characters
    return ' ';
  }

  // Returns true if num is in between min and max inclusive, false otherwise
  public static boolean bounded(int num, int min, int max) {
    return num >= min && num <= max;
  }

  // Returns index of char in char[], -1 if it's not there
  public static int indexOf(char[] arr, char target) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == target) return i;
    }
    return -1;
  }

  // Returns true if char[] contains char, false otherwise
  public static boolean contains(char[] arr, char target) {
    for (char item : arr) {
      if (item == target) return true;
    }
    return false;
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
