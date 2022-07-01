// getItem gets random String from String[] with weights for how likely they pop up from int[]

public class WeightedProbabilities {
  public static String getItem(String[] items, int[] weights) {
    // Catches errors
      // Rare from me, I know
    if (items.length == 0 || weights.length == 0) {
      return null;
    }
    if (items.length != weights.length) {
      return null;
    }
    // Weights of 1 2 3 4 --> 1 3 6 10
      // Where sumWeights[n] = sum of weights of index <= n
    long[] sumWeights = new long[weights.length];
    long sum = 0;
    for (int i = 0; i < weights.length; i++) {
      sumWeights[i] = sum;
      sum += weights[i];
    }
    // Using 1 2 3 4 as example again, gives random number from 0 to 9 inclusive
    long random = (long) (Math.random() * sum);
    int start = 0;
    int end = sumWeights.length;
    int index = -1;
    int times = (int) (Math.log(weights.length) / Math.log(2)) + 1;
    // Binary-ish search where it goes find the index where the current number is <= to it and the next is >
      // Overcomplicating stuff maybe, but now this is fairly efficient
    for (int i = 0; i <= times; i++) {
      index = (start + end) / 2;
      // Breaks if it reaches ends
      if (index == 0 || index == sumWeights.length - 1) {
        break;
      }
      if (sumWeights[index] <= random && sumWeights[index + 1] > random) {
        break;
      }
      if (sumWeights[index] <= random) {
        start = index;
      } else {
        end = index;
      }
    }
    return items[index];
  }

  // Gets index of String in String[] or -1 if it's not there
  public static int getIndexOf(String[] arr, String item) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i].equals(item)) {
        return i;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    String[] items = {"I", "don't", "even", "know", "if", "this", "actually", "works", "or", "not"};
    int[] weights =  {9  , 1      , 9     , 2     , 6   , 3     , 1         , 7      , 7   , 0    };
    int sum = 0;
    for (int weight : weights) {
      sum += weight;
    }
    int[] times = new int[items.length];
    for (int i = 0; i < sum*Math.pow(10, 6); i++) {
      times[getIndexOf(items, getItem(items, weights))]++;
    }
    for (int time : times) {
      System.out.printf("%,9d\n", time);
    }
  }
}
