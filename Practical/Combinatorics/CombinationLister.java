import java.util.ArrayList;

public class CombinationLister {
  private final int[] arr;

  /**
   * Creates a CombinationLister with the provided int array.
   *
   * @param arr int array for CombinationLister
   */
  public CombinationLister(int[] arr) {
    this.arr = arr;
  }

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
   * Returns all combinations with the provided number of things from arr
   * (whose values are set in the Constructor).
   *
   * @param r the number of things in the desired combinations
   * @return all combinations of r things from arr
   */
  public int[][] getCombinations(int r) {
    if (r == arr.length) {
      return new int[][] {arr};
    }
    if (r == 0) {
      return new int[][] {{}};
    }
    if (r < 0 || r > arr.length) {
      return null;
    }
    ArrayList<ArrayList<Integer>> als = new ArrayList<>();
    ArrayList<Integer> al = new ArrayList<>();
    al.add(arr.length - r);
    als.add(al);
    return transformToIntArrs(doAllTheWork(r, als));
  }

  /**
   * Returns an ArrayList of the "indices" for all r-combinations.
   *
   * @param r the remaining number of times to call this function
   * @param curr the current 2d ArrayList
   * @return an ArrayList of the "indices" for all r-combinations
   */
  /*
    See the description in transformToIntArr for more. Using the example from there,
  this returns the ArrayList with 0 0 2 0 1 (among other sequences). Except it'd
  actually be 0 0 0 2 0 1 where the first element can be thought of as the sum of
  the next possible elements. As in, before the program outputs 0 0 0 2 0 1, the
  ArrayList would have 1 0 0 2 0, indicating that the next element can be 0 or 1.
    This first element allows each row to be treated independently. This is not the
  only way to do so, as demonstrated by the previous implementation of this program.
  The advantage of this one though is that it's tail recursive.
  */
  private ArrayList<ArrayList<Integer>> doAllTheWork(int r,
                                                     ArrayList<ArrayList<Integer>> curr) {
    if (r == 0) {
      return curr;
    }
    for (int i = curr.size() - 1; i >= 0; i--) {
      int sum = curr.get(i).get(0);
      for (int j = sum; j >= 1; j--) {
        ArrayList<Integer> newLine = new ArrayList<>(curr.get(i));
        newLine.add(j);
        newLine.set(0, sum - j);
        curr.add(i + 1, newLine);
      }
      curr.get(i).add(0);
    }
    return doAllTheWork(r - 1, curr);
  }

  /**
   * Returns a 2d int array transformation of the provided 2d ArrayList.
   *
   * @param als 2d ArrayList whose 2d int array representation is returned
   * @return a 2d int array transformation of als
   */
  private int[][] transformToIntArrs(ArrayList<ArrayList<Integer>> als) {
    int[][] arr = new int[als.size()][];
    for (int i = 0; i < als.size(); i++) {
      arr[i] = transformToIntArr(als.get(i));
    }
    return arr;
  }

  /**
   * Returns an int array representation of the ArrayList. It also
   * transforms it in a certain way.
   *
   * @param al ArrayList to be transformed into an int array
   * @return an int array transformation of al
   */
  /*
    It transforms the ArrayList in the following way.
    Let's say you have an arr = 0 1 2 3 4 5 6 7
    Each combination (in the combinatorics sense) can be thought of as a subset
  of arr.
    We take the permutation of it that's in increasing order.
    Since these permutations all always increase, we can write these permutations
  as a sequence of numbers showing how much these numbers increase by.
    Let's consider one of these permutations: 0 1 4 5 7
    This can be written as 0 1 3 1 2, where the 0 is just the first element,
  the 1 shows much is increased from the previous element (0), 3 shows how much
  is increased from the previous element (1), and so on.
    Furthermore, because it is strictly increasing, we can go further to write this
  as 0 0 2 0 1, where each element can be thought of as the index of the array
  of the numbers after the previous one.
    I made this program with this second way in mind. The first method sounds
  possible, but I'm not going to change this program since it would just be more
  trouble than it's worth.
    Anyway, this program turns the 0 0 2 0 1 from ArrayList to 0 1 4 5 7 in an
  int array. Except the first part's not technically true, as elaborated on in
  the description for doAllTheWork().
  */
  private int[] transformToIntArr(ArrayList<Integer> al) {
    int[] arr = new int[al.size() - 1];
    int sum = -1;
    for (int i = 0; i < arr.length; i++) {
      sum += al.get(i + 1) + 1;
      arr[i] = this.arr[sum];
      /* This sets arr to al
       This comment is kept to facilitate debugging in case it's still needed
       */
      // arr[i] = al.get(i);
    }
    return arr;
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

  public static void main(String[] args) {
    CombinationLister cl = new CombinationLister(new int[] {4, 5, 6, 7, 8});
    print(cl.getCombinations(3));
  }
}
