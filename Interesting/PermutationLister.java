import java.util.ArrayList;

// 0 1 2 can be rearranged in 6 ways: 0 1 2, 0 2 1, 1 0 2, 1 2 0, 2 0 1, 2 1 0
// Program lists permutations from start-end, 0-2 in this case
  // Explanation down below as per usual

public class PermutationLister {
  // Does the listing part mentioned above
  public static int[][] listPermutations(int start, int end) {
    int diff = end-start+1;
    int[][] arr = new int[(int) factorial(diff)][diff];
    for (int i = 0; i < arr.length; i++) {
      String fac = toFac(i);
      int[] digits = toIntArr(fac, diff);
      ArrayList<Integer> avail = new ArrayList<>();
      for (int j = 0; j < diff; j++) {
        avail.add(j + start);
      }
      for (int j = 0; j < diff; j++) {
        int index2 = digits[j];
        arr[i][j] = avail.get(index2);
        avail.remove(index2);
      }
    }
    return arr;
  }

  // Turns String to int[] of given length
    // Length 6 makes "32210" to [0, 3, 2, 2, 1, 0]
  public static int[] toIntArr(String str, int length) {
    int[] digits = new int[length];
    for (int i = 0; i < str.length(); i++) {
      // 48-57, 65-90, 97-122
      char current = str.charAt(i);
      int num = -1;
      if (bounded(current, 48, 57)) {
        num = current - 48;
      } else if (bounded(current, 65, 90)) {
        num = current - 29;
      } else if (bounded(current, 97, 122)) {
        num = current - 87;
      }
      digits[length - str.length() + i] = num;
    }
    return digits;
  }

  // Returns true if target is in between start and end inclusive
  public static boolean bounded(int target, int start, int end) {
    return target >= start && target <= end;
  }

  // Gets factorial
  public static long factorial(int num) {
    long fac = 1;
    for (int i = num; i > 1; i--) {
      fac *= i;
    }
    return fac;
  }

  // Converts Strings to numbers
  public static long overboard(String digit) {
    String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    return (long) alphabet.indexOf(digit)+10;
  }

  // Converts decimal to base-factorial
  public static String toFac(long num) {
    StringBuilder sb = new StringBuilder();
    if (num == 0) return "0";
    for (int i = 2; num > 0; i++) {
      sb.insert(0, num % i);
      num /= i;
    }
    sb.append("0");
    return sb.toString();
  }

  // Converts base-factorial to decimal
  public static long toDec(String str) {
    long num = 0;
    for (int i = 0; i < str.length(); i++) {
      num += overboard(Character.toString(str.charAt(i))) * factorial(str.length() - i - 1);
    }
    return num;
  }

  public static void print(long num) {
    System.out.println(num);
  }

  public static void print(String num) {
    System.out.println(num);
  }

  public static void print(int[] nums) {
    for (int num : nums) {
      System.out.print(num + " ");
    }
    System.out.println();
  }

  public static void print(int[][] numses) {
    for (int[] nums : numses) {
      print(nums);
    }
  }

  public static void main(String[] args) {
    print(listPermutations(3, 7));
  }

  /*
  How to get all the permutations of 2, 3, 4, 5?
  Let's say we have the permutation 3, 4, 2, 5.
  One way we can think about this is taking the element at index 1 (3)
    Then removing it from the possible digits to get 3, 2, 5
  Then we take the next element at index 1 (4)
    Remove it from possible digits again
    Repeat for the remaining digits -- index 0 (2) and then index 0 (5)
  With this, we can turn any permutation into a unique combination of 0, 1, 2, 3 -- in this case 1, 1, 0, 0
  How many possibilities are there? There are 4 digits possible for the first (ranging from 0 to 3),
    3 for the next (from 0 to 2), then 2 (from 0 to 1), and 1 (just 0).
  Thus, the number of possibilities is 4 x 3 x 2 x 1 = 4!
  You notice something else cool though? There being 4 digits, then 3 digits, 2, and 1 is directly related to base-factorial!
    It's covered in a different folder if you haven't seen it yet
    What this means is that the numbers from [0, 4!) translated to base-factorial, give us all the combinations we want!
    0 -> 0000, 1 -> 0010, 2 -> 0100, 3 -> 0110, 4 -> 0200, 5 -> 0210, 6 -> 1000, 7 -> 1010, etc.
    See, I told you it was practical ;)
   */
}
