import java.util.ArrayList;

public class MultisetCombinationLister {
  private final int[] set;
  private final int[] weights;
  private final int sum;

  /**
   * Creates a MultisetCombinationLister with the provided set and weights.
   * <p>
   *   A multiset with one 1, three 2s, and one 3 is considered to have a set with 1, 2, 3
   *   and weights 1, 3, 1 respectively.
   * </p>
   *
   * @param set the set of ints for MultisetCombinationLister
   * @param weights the weights of the numbers in set
   * @throws IllegalArgumentException if set and weights don't have the same lengths
   */
  public MultisetCombinationLister(int[] set, int[] weights) {
    if (set.length != weights.length) {
      throw new IllegalArgumentException();
    }
    this.set = set;
    this.weights = weights;
    this.sum = getSumOfWeights();
  }

  /**
   * Creates a MultisetCombinationLister from the provided 2d int array.
   * <p>
   *   A multiset with one 1, three 2s, and one 3 is considered to have a set with 1, 2, 3
   *   and weights 1, 3, 1 respectively.
   *   The multiset is formatted with a set of ints in the first column and their weights
   *   in the second, in this case {{1, 1}, {2, 3}, {3, 1}}.
   * </p>
   *
   * @param multiset the set of numbers with their weights for MultisetCombinationLister
   * @throws IllegalArgumentException if any of the array's rows don't have exactly 2 elements
   */
  public MultisetCombinationLister(int[][] multiset) {
    set = new int[multiset.length];
    weights = new int[multiset.length];
    for (int i = 0; i < multiset.length; i++) {
      if (multiset[i].length != 2) {
        throw new IllegalArgumentException();
      }
      set[i] = multiset[i][0];
      weights[i] = multiset[i][1];
    }
    this.sum = getSumOfWeights();
  }

  /**
   * Creates a MultisetCombinationLister with a set of ints from 0 to length-1 and the
   * provided weights.
   * <p>
   *   A multiset with one 1, three 2s, and one 3 is considered to have a set with 1, 2, 3
   *   and weights 1, 3, 1 respectively.
   * </p>
   *
   * @param weights the weights of the numbers in set (whole numbers) for MultisetCombinationLister
   */
  public MultisetCombinationLister(int[] weights) {
    int[] set = new int[weights.length];
    for (int i = 1; i < set.length; i++) {
      set[i] = i;
    }
    this.set = set;
    this.weights = weights;
    this.sum = getSumOfWeights();
  }

  /**
   * Returns a 2d int array representation of all r-combinations of set (whose values are
   * set in the Constructor).
   *
   * @param r the number of elements in each combination
   * @return all r-combinations of set
   */
  public int[][] getCombinations(int r) {
    if (r == sum) {
      int[] arr = new int[sum];
      int count = 0;
      for (int i = 0; i < weights.length; i++) {
        for (int j = 0; j < weights[i]; j++) {
          arr[count] = set[i];
          count++;
        }
      }
      return new int[][] {arr};
    }
    if (r == 0) {
      return new int[][] {{}};
    }
    if (r < 0 || r > sum) {
      return null;
    }
    ArrayList<ArrayList<Integer>> als = new ArrayList<>();
    ArrayList<Integer> al = new ArrayList<>();
    al.add(sum);
    al.add(r);
    als.add(al);
    return transformToIntArrs(doAllTheWork(0, als), r);
  }

  /**
   * Returns an ArrayList of the "indices" for all r-combinations.
   *
   * @param index the current index on curr at this iteration.
   * @param curr the current 2d ArrayList
   * @return an ArrayList of the "indices" for all r-combinations
   */
  ArrayList<ArrayList<Integer>> doAllTheWork(int index, ArrayList<ArrayList<Integer>> curr) {
    if (index == set.length) {
      return curr;
    }
    for (int i = curr.size() - 1; i >= 0; i--) {
      int sum = curr.get(i).get(0);
      int acc = curr.get(i).get(1);
      int min = Math.min(acc, weights[index]);
      int max = Math.max(0, acc - sum + weights[index]);
      for (int j = max; j < min; j++) {
        ArrayList<Integer> newLine = new ArrayList<>(curr.get(i));
        newLine.add(j);
        newLine.set(0, sum - weights[index]);
        newLine.set(1, acc - j);
        curr.add(i + 1, newLine);
      }
      curr.get(i).add(min);
      curr.get(i).set(0, sum - weights[index]);
      curr.get(i).set(1, acc - min);
    }
    return doAllTheWork(index + 1, curr);
  }

  /**
   * Returns a 2d int array transformation of the provided 2d ArrayList.
   *
   * @param als 2d ArrayList whose 2d int array representation is returned
   * @param length the length of each 1d int array
   * @return a 2d int array transformation of als
   */
  private int[][] transformToIntArrs(ArrayList<ArrayList<Integer>> als, int length) {
    int[][] arr = new int[als.size()][];
    for (int i = 0; i < als.size(); i++) {
      arr[i] = transformToIntArr(als.get(i), length);
    }
    return arr;
  }

  /**
   * Returns an int array representation of the ArrayList. It also
   * transforms it in a certain way.
   *
   * @param al ArrayList to be transformed into an int array
   * @param length the length of the returned int array
   * @return an int array transformation of al
   */
  /*
    Say we have a set with {0, 1, 2} and doAllTheWork() returns something like 0 3 1.
    Then the array is 0 0s, 3 1s, and 2s.
    Similar to how doAllTheWork in CombinationLister had an extra element in the front,
  this one has two. Reason being essentially the same as in CombinationLister.
   */
  private int[] transformToIntArr(ArrayList<Integer> al, int length) {
    int[] arr = new int[length];
    int count = 0;
    for (int i = 0; i < set.length; i++) {
      for (int j = 0; j < al.get(i + 2); j++) {
        arr[count] = set[i];
        count++;
      }
      /* This sets arr to al
       This comment is kept to facilitate debugging in case it's still needed
       */
      // arr[i] = al.get(i);
    }
    return arr;
  }

  /**
   * Returns the sum of the ints in weights.
   *
   * @return the sum of the ints in weights
   */
  private int getSumOfWeights() {
    int sum = 0;
    for (int i = 0; i < set.length; i++) {
      sum += weights[i];
    }
    return sum;
  }

  /**
   * Prints the provided 2d int array.
   *
   * @param arrs the 2d int array to be printed
   */
  public static void print(int[][] arrs) {
    for (int[] arr : arrs) {
      print(arr);
    }
  }

  /**
   * Prints the provided int array.
   *
   * @param arr the int array to be printed
   */
  private static void print(int[] arr) {
    for (int num : arr) {
      System.out.print(num + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    int[] set = {1, 2, 3};
    int[] weights = {1, 3, 1};
    MultisetCombinationLister mcl = new MultisetCombinationLister(set, weights);
    print(mcl.getCombinations(3));
  }
}
