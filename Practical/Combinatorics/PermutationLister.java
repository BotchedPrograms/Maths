import java.util.ArrayList;

// 0 1 2 can be rearranged in 6 ways: 0 1 2, 0 2 1, 1 0 2, 1 2 0, 2 0 1, 2 1 0
// Program lists permutations from start-end, 0-2 in this case
  // Explanation down below as per usual

public class PermutationLister {
  private final int[] arr;

  /**
   * Creates a PermutationLister with ints 0 to length-1.
   *
   * @param length length of whole numbers for PermutationLister
   */
  public PermutationLister(int length) {
    int[] arr = new int[length];
    for (int i = 1; i < arr.length; i++) {
      arr[i] = i;
    }
    this.arr = arr;
  }

  /**
   * Creates a PermutationLister with the provided int array.
   *
   * @param arr int array for PermutationLister
   */
  public PermutationLister(int[] arr) {
    this.arr = arr;
  }

  /**
   * Returns the value of n!.
   *
   * @param n int whose factorial is returned.
   * @return the value of n!
   */
  private static int factorial(int n) {
    return permutation(n, n);
  }

  /**
   * Returns the value of P(n,r).
   *
   * @param n the first half of P(n,r)
   * @param r the second half of P(n,r)
   * @return the value of P(n,r)
   */
  private static int permutation(int n, int r) {
    int product = 1;
    for (int i = n; i > n - r; i--) {
      product *= i;
    }
    return product;
  }

  /**
   * Returns the value of arr as an ArrayList.
   *
   * @return arr as an ArrayList
   */
  private ArrayList<Integer> getArrList() {
    ArrayList<Integer> al = new ArrayList<>();
    for (int num : arr) {
      al.add(num);
    }
    return al;
  }

  /**
   * Returns all permutations of arr (whose values are set in the Constructor).
   *
   * @return an int[][] representing all permutations of arr
   */
  public int[][] getPermutations() {
    return getPermutations(arr.length);
  }

  /**
   * Returns all r-length permutations of arr (whose values are set in the Constructor).
   *
   * @param r length of the permutations
   * @return all r-length permutations of arr
   */
  public int[][] getPermutations(int r) {
    int[][] perms = new int[permutation(arr.length, r)][r];
    int[] radices = new int[r];
    for (int i = 0; i < r; i++) {
      radices[i] = arr.length - i;
    }
    MixedRadix mr = new MixedRadix(radices);
    for (int i = 0; i < mr.getRadicesProduct(); i++) {
      ArrayList<Integer> al = getArrList();
      int[] digits = mr.toRadix(i);
      for (int j = 0; j < r; j++) {
        perms[i][j] = al.remove(digits[j + 1]);
      }
    }
    return perms;
  }

  /**
   * Prints an int array.
   *
   * @param nums int array to be printed
   */
  private static void print(int[] nums) {
    for (int num : nums) {
      System.out.print(num + " ");
    }
    System.out.println();
  }

  /**
   * Prints a 2d int array.
   *
   * @param numses 2d int array to be printed
   */
  public static void print(int[][] numses) {
    for (int[] nums : numses) {
      print(nums);
    }
  }

  public static void main(String[] args) {
    PermutationLister pl = new PermutationLister(new int[] {4, 6 , 8, 10, 12});
    print(pl.getPermutations(2));
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
