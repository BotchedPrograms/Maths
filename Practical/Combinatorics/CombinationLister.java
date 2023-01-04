import java.util.ArrayList;

public class CombinationLister {
  private final int[] arr;

  /**
   * Creates a CombinationLister with ints 0 to length-1.
   *
   * @param length length of whole numbers for CombinationLister
   */
  public CombinationLister(int length) {
    int[] arr = new int[length];
    for (int i = 1; i < arr.length; i++) {
      arr[i] = i;
    }
    this.arr = arr;
  }

  /**
   * Creates a CombinationLister with the provided int array.
   *
   * @param arr int array for CombinationLister
   */
  public CombinationLister(int[] arr) {
    this.arr = arr;
  }

  /**
   * Returns an int array representation of the ArrayList.
   * <p>
   *   It also transforms the ArrayList in the following way.
   * </p>
   * <p>
   *   Let's say you have an arr = 0 1 2 3 4 5 6 7
   *   Each combination (in the combinatorics sense) can be thought of as a subset
   *   of arr.
   *   We take the permutation of it that's in increasing order.
   *   Since these permutations all always increase, we can write these permutations
   *   as a sequence of numbers showing how much these numbers increase by.
   * </p>
   * <p>
   *   Let's consider one of these permutations: 0 1 4 5 7
   *   This can be written as 0 1 3 1 2, where the 0 is just the first element,
   *   the 1 shows much is increased from the previous element (0), 3 shows how much
   *   is increased from the previous element (1), and so on.
   * </p>
   * <p>
   *   Furthermore, because it is strictly increasing, we can go further to write this
   *   as 0 0 2 0 1, where each element can be thought of as the index of the array
   *   of the numbers after the previous one.
   * </p>
   * <p>
   *   I made this program with this second way in mind, but, not that I think about it,
   *   the first way seems possible. I'm not changing it now though since it'd be much
   *   more trouble than it's worth.
   * </p>
   * <p>
   *   Anyways, this program turns the 0 0 2 0 1 from ArrayList to 0 1 4 5 7 in an
   *   int array.
   * </p>
   *
   * @param al ArrayList to be transformed into an int array
   * @return an int array transformation of al
   */
  private int[] transformToIntArr(ArrayList<Integer> al) {
    int[] arr = new int[al.size()];
    // Using the example in this method's description, the -1 here
    // turns 0 0 2 0 1 -> 0 1 4 5 7 instead of 1 2 5 6 8
    int sum = -1;
    for (int i = 0; i < al.size(); i++) {
      sum += al.get(i) + 1;
      arr[i] = this.arr[sum];
      /* This sets arr to al
       Using the example in the description, this sets arr to the 0 0 2 0 1 from al
       This comment is kept in case the program still needs debugging
       */
      // arr[i] = al.get(i);
    }
    return arr;
  }

  /**
   * Returns a 2d int array representation of the provided 2d ArrayList.
   *
   * @param als 2d ArrayList whose 2d int array representation is returned
   * @return a 2d int array representation of als
   */
  private int[][] toIntArrs(ArrayList<ArrayList<Integer>> als) {
    int[][] arr = new int[als.size()][];
    for (int i = 0; i < als.size(); i++) {
      arr[i] = transformToIntArr(als.get(i));
    }
    return arr;
  }

  /**
   * Tacks on the provided value to all ArrayLists in the provided 2d ArrayList.
   * <p>
   *   For example, using pseudocode,
   *   tackOn(2, [[0,0], [0,1], [1,0]]) = [[2,0,0], [2,0,1], [2,1,0]].
   * </p>
   *
   * @param value value to be tacked on
   * @param current 2d ArrayList to be tacked onto
   * @return the tacked-on 2d ArrayList
   */
  private static ArrayList<ArrayList<Integer>> tackOn(int value, ArrayList<ArrayList<Integer>> current) {
    for (ArrayList<Integer> al : current) {
      al.add(0, value);
    }
    return current;
  }

  /**
   * Returns the concatenation of all ArrayLists in each 2d ArrayList in the provided 3d ArrayList.
   *
   * @param alses the ArrayList of 2d ArrayLists to be concatenated
   * @return the concatenation of all ArrayLists in each 2d ArrayList in alses
   */
  private static ArrayList<ArrayList<Integer>> concatenate(ArrayList<ArrayList<ArrayList<Integer>>> alses) {
    ArrayList<ArrayList<Integer>> als = new ArrayList<>();
    for (ArrayList<ArrayList<Integer>> alsi : alses) {
      als.addAll(alsi);
    }
    return als;
  }

  /**
   * Returns all combinations with the provided number of things from arr
   * (whose values are set in the Constructor)
   *
   * @param r the number of things in the desired combinations
   * @return all combinations of r things from arr
   */
  public int[][] getCombinations(int r) {
    if (r < 0 || r > arr.length) {
      return null;
    }
    if (r == 0) {
      return new int[][] {};
    }
    return toIntArrs(doAllTheWork(arr.length - r + 1, r));
  }

  /**
   * Returns the ArrayList of indices of combinations.
   * <p>
   *   See the description in transformToIntArr for more. Using the example from there,
   *   this returns the ArrayList with 0 0 2 0 1 (among other sequences).
   * </p>
   *
   * @param n the number of 2d ArrayLists to concatenate in this instance
   * @param r the remaining number of times to call this function
   * @return an ArrayList of indices of combinations
   */
  private ArrayList<ArrayList<Integer>> doAllTheWork(int n, int r) {
    if (r == 1) {
      ArrayList<ArrayList<Integer>> als = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        ArrayList<Integer> al = new ArrayList<>();
        al.add(i);
        als.add(al);
      }
      return als;
    }
    ArrayList<ArrayList<ArrayList<Integer>>> alses = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      ArrayList<ArrayList<Integer>> section = tackOn(i, doAllTheWork(n - i, r - 1));
      alses.add(section);
    }
    return concatenate(alses);
  }

  /**
   * Prints an int array.
   *
   * @param arr int array to be printed
   */
  private static void print(int[] arr) {
    for (int num : arr) {
      System.out.print(num + " ");
    }
    System.out.println();
  }

  /**
   * Prints a 2d int array.
   *
   * @param arrs 2d int array to be printed
   */
  public static void print(int[][] arrs) {
    for (int[] arr : arrs) {
      print(arr);
    }
  }

  public static void main(String[] args) {
    CombinationLister cl = new CombinationLister(new int[] {4, 5, 6, 7, 8});
    print(cl.getCombinations(3));
  }
}
