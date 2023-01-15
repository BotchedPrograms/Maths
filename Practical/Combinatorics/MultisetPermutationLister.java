import java.util.ArrayList;

public class MultisetPermutationLister {
  private final int[] set;
  private final int[] weights;
  private final int sum;

  /**
   * Creates a MultisetPermutationLister with the provided set and weights.
   * <p>
   *   A multiset with one 1, three 2s, and one 3 is considered to have a set with 1, 2, 3
   *   and weights 1, 3, 1 respectively.
   * </p>
   *
   * @param set the set of ints for MultisetPermutationLister
   * @param weights the weights of the numbers in set
   * @throws IllegalArgumentException if set and weights don't have the same lengths
   */
  public MultisetPermutationLister(int[] set, int[] weights) {
    if (set.length != weights.length) {
      throw new IllegalArgumentException();
    }
    this.set = set;
    this.weights = weights;
    this.sum = getSumOfWeights();
  }

  /**
   * Creates a MultisetPermutationLister from the provided 2d int array.
   * <p>
   *   A multiset with one 1, three 2s, and one 3 is considered to have a set with 1, 2, 3
   *   and weights 1, 3, 1 respectively.
   *   The multiset is formatted with a set of ints in the first column and their weights
   *   in the second, in this case {{1, 1}, {2, 3}, {3, 1}}.
   * </p>
   *
   * @param multiset the set of numbers with their weights for MultisetPermutationLister
   * @throws IllegalArgumentException if any of the array's rows don't have exactly 2 elements
   */
  public MultisetPermutationLister(int[][] multiset) {
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
   * Creates a MultisetPermutationLister with a set of ints from 0 to length-1 and the
   * provided weights.
   * <p>
   *   A multiset with one 1, three 2s, and one 3 is considered to have a set with 1, 2, 3
   *   and weights 1, 3, 1 respectively.
   * </p>
   *
   * @param weights the weights of the numbers in set (whole numbers) for MultisetPermutationLister
   */
  public MultisetPermutationLister(int[] weights) {
    int[] set = new int[weights.length];
    for (int i = 1; i < weights.length; i++) {
      set[i] = i;
    }
    this.set = set;
    this.weights = weights;
    this.sum = getSumOfWeights();
  }

  /**
   * Returns a 2d int array representation of all permutations of set (whose values are
   * set in the Constructor).
   * <p>
   *   Note: these permutations are unfortunately not sorted.
   * </p>
   *
   * @return all permutations of set
   */
  public int[][] getPermutations() {
    return getPermutations(sum);
  }

  /**
   * Returns a 2d int array representation of all r-permutations of set (whose values are
   * set in the Constructor).
   * <p>
   *   Note: these permutations are unfortunately not sorted.
   * </p>
   *
   * @param r the length of each permutation
   * @return all r-permutations of set
   */
  public int[][] getPermutations(int r) {
    MultisetCombinationLister mcl = new MultisetCombinationLister(weights);
    ArrayList<ArrayList<Integer>> als = new ArrayList<>();
    ArrayList<Integer> al = new ArrayList<>();
    al.add(sum);
    al.add(r);
    als.add(al);
    ArrayList<ArrayList<Integer>> weightsIndices = mcl.doAllTheWork(0, als);

    int[][][] combs = new int[set.length][][];
    ArrayList<ArrayList<Integer>> perms = new ArrayList<>();
    for (ArrayList<Integer> weightsIndex : weightsIndices) {
      int[] chooses = new int[set.length];
      int sub = r;
      for (int j = 0; j < set.length; j++) {
        int weight = weightsIndex.get(j + 2);
        CombinationLister cl = new CombinationLister(sub);
        combs[j] = cl.getCombinations(weight);
        chooses[j] = combs[j].length;
        sub -= weight;
      }
      MixedRadix mr = new MixedRadix(chooses);

      for (int j = 0; j < mr.getRadicesProduct(); j++) {
        ArrayList<Integer> perm = new ArrayList<>();
        ArrayList<Integer> avail = new ArrayList<>();
        for (int k = 0; k < r; k++) {
          perm.add(-1);
          avail.add(k);
        }

        int[] indices = mr.toRadix(j);
        for (int k = 0; k < set.length; k++) {
          int[] chosenIndices = combs[k][indices[k + 1]];
          int[] toRemove = new int[chosenIndices.length];
          for (int l = 0; l < chosenIndices.length; l++) {
            toRemove[l] = avail.get(chosenIndices[l]);
            perm.set(toRemove[l], set[k]);
          }
          for (int value : toRemove) {
            avail.remove((Integer) value);
          }
        }
        perms.add(perm);
      }
    }
    return toIntArrs(perms);
  }

  /**
   * Returns a 2d int array representation of the provided 2d ArrayList.
   *
   * @param als the 2d ArrayList
   * @return als as a 2d int array
   */
  private int[][] toIntArrs(ArrayList<ArrayList<Integer>> als) {
    int[][] arrs = new int[als.size()][];
    for (int i = 0; i < arrs.length; i++) {
      arrs[i] = toIntArr(als.get(i));
    }
    return arrs;
  }

  /**
   * Returns an int array representation of the provided ArrayList.
   *
   * @param al the ArrayList
   * @return al as an int array
   */
  private int[] toIntArr(ArrayList<Integer> al) {
    int[] arr = new int[al.size()];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = al.get(i);
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
    MultisetPermutationLister mpl = new MultisetPermutationLister(set, weights);
    print(mpl.getPermutations(4));
  }
}
