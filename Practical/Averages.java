import java.util.ArrayList;

// Gets different kinds of averages:
  // Arithmetic mean
  // Geometric mean
  // Weighted mean
  // Root mean square (RMS)
  // Harmonic mean
  // Also median and mode(s) for y'all stat nerds
// Methods repeated to work for int[] and double[]
public class Averages {
  // (2 + 3 + 5 + 7) / 4 = 4.25
  public static double arithmeticMean(int[] nums) {
    long sum = 0;
    for (int num : nums) {
      sum += num;
    }
    return sum / (double) nums.length;
  }

  public static double arithmeticMean(double[] nums) {
    long sum = 0;
    for (double num : nums) {
      sum += num;
    }
    return sum / (double) nums.length;
  }

  // (2 * 3 * 5 * 7)^(1/4) = 3.807
    // Commonly in the form of √(a*b)
  public static double geometricMean(int[] nums) {
    long product = 1;
    for (int num : nums) {
      product *= num;
    }
    return Math.pow(product, 1.0/nums.length);
  }

  public static double geometricMean(double[] nums) {
    long product = 1;
    for (double num : nums) {
      product *= num;
    }
    return Math.pow(product, 1.0/nums.length);
  }

  // (2*2 + 3*10 + 5*4 + 7*3) / (2+10+4+3) = 3.947
  public static double weightedMean(int[] nums, int[] weights) {
    long sumNums = 0;
    long sumWeights = 0;
    for (int i = 0; i < nums.length; i++) {
      sumNums += nums[i]*weights[i];
      sumWeights += weights[i];
    }
    return sumNums/ (double) sumWeights;
  }

  public static double weightedMean(int[] nums, double[] weights) {
    long sumNums = 0;
    long sumWeights = 0;
    for (int i = 0; i < nums.length; i++) {
      sumNums += nums[i]*weights[i];
      sumWeights += weights[i];
    }
    return sumNums/ (double) sumWeights;
  }

  public static double weightedMean(double[] nums, int[] weights) {
    long sumNums = 0;
    long sumWeights = 0;
    for (int i = 0; i < nums.length; i++) {
      sumNums += nums[i]*weights[i];
      sumWeights += weights[i];
    }
    return sumNums/ (double) sumWeights;
  }

  public static double weightedMean(double[] nums, double[] weights) {
    long sumNums = 0;
    long sumWeights = 0;
    for (int i = 0; i < nums.length; i++) {
      sumNums += nums[i]*weights[i];
      sumWeights += weights[i];
    }
    return sumNums/ (double) sumWeights;
  }

  //  _____________________________
  // √ (2^2 + 5^2 + 3^2 + 7^2) / 4  = 4.583
  public static double rootMeanSquare(int[] nums) {
    long sum = 0;
    for (int i = 0; i < nums.length; i++) {
      // Square
      sum += nums[i] * nums[i];
    }
    // Mean
    sum /= (double) nums.length;
    // Root
    return Math.sqrt(sum);
  }

  public static double rootMeanSquare(double[] nums) {
    long sum = 0;
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i] * nums[i];
    }
    sum /= (double) nums.length;
    return Math.sqrt(sum);
  }

  // 4 / (1/2 + 1/3 + 1/5 + 1/7) = 3.401
  public static double harmonicMean(int[] nums) {
    double sum = 0;
    for (int num : nums) {
      sum += 1.0/num;
    }
    return nums.length/sum;
  }

  public static double harmonicMean(double[] nums) {
    double sum = 0;
    for (double num : nums) {
      sum += 1/num;
    }
    return nums.length/sum;
  }

  // 2 3 5 7 --> (3 + 5) / 2.0 = 4.0
  public static double median(int[] nums) {
    if (nums.length % 2 == 1) {
      return nums[nums.length/2];
    } else {
      return (nums[nums.length/2-1] + nums[nums.length/2])/2.0;
    }
  }

  public static double median(double[] nums) {
    if (nums.length % 2 == 1) {
      return nums[nums.length/2];
    } else {
      return (nums[nums.length/2-1] + nums[nums.length/2])/2;
    }
  }

  // 2 3 3 5 7 7 --> 3
  public static int mode(int[] nums) {
    ArrayList<Integer> times = new ArrayList<Integer>();
    ArrayList<Integer> uniqueNumbers = new ArrayList<Integer>();
    int index;
    for (int i = 0; i < nums.length; i++) {
      index = uniqueNumbers.indexOf(nums[i]);
      if (index == -1) {
        uniqueNumbers.add(nums[i]);
        times.add(0);
      } else {
        times.set(index, times.get(index) + 1);
      }
    }
    int max = 0;
    for (int i = 0; i < times.size(); i++) {
      if (times.get(i) > max) {
        max = times.get(i);
      }
    }
    index = times.indexOf(max);
    return uniqueNumbers.get(index);
  }

  public static double mode(double[] nums) {
    ArrayList<Integer> times = new ArrayList<Integer>();
    ArrayList<Double> uniqueNumbers = new ArrayList<Double>();
    int index;
    for (int i = 0; i < nums.length; i++) {
      index = uniqueNumbers.indexOf(nums[i]);
      if (index == -1) {
        uniqueNumbers.add(nums[i]);
        times.add(0);
      } else {
        times.set(index, times.get(index) + 1);
      }
    }
    int max = 0;
    for (int i = 0; i < times.size(); i++) {
      if (times.get(i) > max) {
        max = times.get(i);
      }
    }
    index = times.indexOf(max);
    return uniqueNumbers.get(index);
  }

  // 2 3 3 5 7 7 --> 3, 7
  public static ArrayList<Integer> modes(int[] nums) {
    ArrayList<Integer> times = new ArrayList<Integer>();
    ArrayList<Integer> uniqueNumbers = new ArrayList<Integer>();
    int index;
    for (int i = 0; i < nums.length; i++) {
      index = uniqueNumbers.indexOf(nums[i]);
      if (index == -1) {
        uniqueNumbers.add(nums[i]);
        times.add(0);
      } else {
        times.set(index, times.get(index) + 1);
      }
    }
    int max = 0;
    for (int i = 0; i < times.size(); i++) {
      if (times.get(i) > max) {
        max = times.get(i);
      }
    }
    ArrayList<Integer> modes = new ArrayList<Integer>();
    for (int i = 0; i < times.size(); i++) {
      if (times.get(i) == max) {
        modes.add(uniqueNumbers.get(i));
      }
    }
    return modes;
  }

  public static ArrayList<Double> modes(double[] nums) {
    ArrayList<Integer> times = new ArrayList<Integer>();
    ArrayList<Double> uniqueNumbers = new ArrayList<Double>();
    int index;
    for (int i = 0; i < nums.length; i++) {
      index = uniqueNumbers.indexOf(nums[i]);
      if (index == -1) {
        uniqueNumbers.add(nums[i]);
        times.add(0);
      } else {
        times.set(index, times.get(index) + 1);
      }
    }
    int max = 0;
    for (int i = 0; i < times.size(); i++) {
      if (times.get(i) > max) {
        max = times.get(i);
      }
    }
    ArrayList<Double> modes = new ArrayList<Double>();
    for (int i = 0; i < times.size(); i++) {
      if (times.get(i) == max) {
        modes.add(uniqueNumbers.get(i));
      }
    }
    return modes;
  }

  public static void main(String[] args) {
    int[] nums = {2, 3, 5, 7};
    int[] weights = {2, 10, 4, 3};
    System.out.println("Arithmetic mean:  " + arithmeticMean(nums));
    System.out.println("Geometric mean:   " + geometricMean(nums));
    System.out.println("Weighted mean:    " + weightedMean(nums, weights));
    System.out.println("Root mean square: " + rootMeanSquare(nums));
    System.out.println("Harmonic mean:    " + harmonicMean(nums));
    System.out.println("Median:           " + median(nums));
    System.out.println("Mode:             " + mode(nums));
  }
}
