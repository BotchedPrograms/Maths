import java.util.ArrayList;

// Gets different types of averages:
  // Type of average                                 Application
  // Arithmetic mean                                 Everywhere
  // Geometric mean                                  Finding rate of growth
  // Weighted mean                                   Grades
  // Root mean square (RMS)                          Alternating current
  // Harmonic mean                                   Presumably why Apple uses squircles w/ n = 4
  // Arithmetic-geometric mean (AGM)                 Hell if I know, I don't even know what I'm talking about
  // Also median and mode(s), those too              For statistics anal-y-sis
    // You'll never unsee that last one, and I don't regret a thing
// Methods repeated to work for int[] and double[]
public class Averages {
  // (2 + 3 + 5 + 7) / 4 = 4.25
  public static double arithmeticMean(int[] nums) {
    // Sum = sum of all numbers in nums
    long sum = 0;
    for (int num : nums) {
      sum += num;
    }
    return sum / (double) nums.length;
  }

  public static double arithmeticMean(double[] nums) {
    double sum = 0;
    for (double num : nums) {
      sum += num;
    }
    return sum / nums.length;
  }

  // (2 * 3 * 5 * 7) ^ (1/4) = 3.807
  // Commonly in the form of √(a*b)
  public static double geometricMean(int[] nums) {
    // Product = product of all numbers in nums
    long product = 1;
    for (int num : nums) {
      product *= num;
    }
    return Math.pow(product, 1.0/nums.length);
  }

  public static double geometricMean(double[] nums) {
    double product = 1;
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
    return sumNums / (double) sumWeights;
  }

  public static double weightedMean(int[] nums, double[] weights) {
    long sumNums = 0;
    double sumWeights = 0;
    for (int i = 0; i < nums.length; i++) {
      sumNums += nums[i]*weights[i];
      sumWeights += weights[i];
    }
    return sumNums / sumWeights;
  }

  public static double weightedMean(double[] nums, int[] weights) {
    double sumNums = 0;
    long sumWeights = 0;
    for (int i = 0; i < nums.length; i++) {
      sumNums += nums[i]*weights[i];
      sumWeights += weights[i];
    }
    return sumNums / (double) sumWeights;
  }

  public static double weightedMean(double[] nums, double[] weights) {
    double sumNums = 0;
    double sumWeights = 0;
    for (int i = 0; i < nums.length; i++) {
      sumNums += nums[i]*weights[i];
      sumWeights += weights[i];
    }
    return sumNums / sumWeights;
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
    double sum = 0;
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i] * nums[i];
    }
    sum /= nums.length;
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

  // Takes arithmetic and geometric means recursively
    // 2 3 5 7 --> 4.25 and 3.807 --> 4.028 and 4.022 --> 4.025 and 4.025 --> 4.025
  public static double arithmeticGeometricMean(int[] nums) {
    return arithmeticGeometricMean(nums, Math.pow(10, -16));
  }

  public static double arithmeticGeometricMean(double[] nums) {
    return arithmeticGeometricMean(nums, Math.pow(10, -16));
  }

  public static double arithmeticGeometricMean(int[] nums, double error) {
    double geoMean = geometricMean(nums);
    double arithMean = arithmeticMean(nums);
    return arithmeticGeometricMean(geoMean, arithMean, error);
  }

  public static double arithmeticGeometricMean(double[] nums, double error) {
    double geoMean = geometricMean(nums);
    double arithMean = arithmeticMean(nums);
    return arithmeticGeometricMean(geoMean, arithMean, error);
  }

  public static double arithmeticGeometricMean(double geoMean, double arithMean, double error) {
    if (Math.abs(geoMean - arithMean) <= error) {
      return geoMean;
    }
    double newGeoMean = Math.sqrt(geoMean * arithMean);
    double newArithMean = (geoMean + arithMean)/2;
    return arithmeticGeometricMean(newGeoMean, newArithMean, error);
  }

  // 2 3 5 7 --> (3 + 5) / 2.0 = 4.0
  public static double median(int[] nums) {
    int[] sortedNums = sort(nums);
    if (sortedNums.length % 2 == 1) {
      return sortedNums[sortedNums.length/2];
    } else {
      return (sortedNums[sortedNums.length/2-1] + sortedNums[sortedNums.length/2])/2.0;
    }
  }

  public static double median(double[] nums) {
    double[] sortedNums = sort(nums);
    if (sortedNums.length % 2 == 1) {
      return sortedNums[sortedNums.length/2];
    } else {
      return (sortedNums[sortedNums.length/2-1] + sortedNums[sortedNums.length/2])/2;
    }
  }

  // Sorts numbers in array
  public static int[] sort(int[] nums) {
    int temp;
    int[] newNums = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
      newNums[i] = nums[i];
    }
    for (int i = 0; i < newNums.length-1; i++) {
      for (int j = i + 1; j < newNums.length; j++) {
        if (newNums[i] > newNums[j]) {
          temp = newNums[i];
          newNums[i] = newNums[j];
          newNums[j] = temp;
        }
      }
    }
    return newNums;
  }

  public static double[] sort(double[] nums) {
    double temp;
    double[] newNums = new double[nums.length];
    for (int i = 0; i < nums.length; i++) {
      newNums[i] = nums[i];
    }
    for (int i = 0; i < newNums.length-1; i++) {
      for (int j = i + 1; j < newNums.length; j++) {
        if (newNums[i] > newNums[j]) {
          temp = newNums[i];
          newNums[i] = newNums[j];
          newNums[j] = temp;
        }
      }
    }
    return newNums;
  }

  // 2 3 3 5 7 7 --> 3
  public static int mode(int[] nums) {
    // Gets unique numbers from nums
    ArrayList<Integer> uniqueNumbers = new ArrayList<Integer>();
    // Gets times unique number appear in nums
    ArrayList<Integer> times = new ArrayList<Integer>();
    int index;
    for (int i = 0; i < nums.length; i++) {
      index = uniqueNumbers.indexOf(nums[i]);
      // If new number, add it to uniqueNumbers and add 0 to times
      if (index == -1) {
        uniqueNumbers.add(nums[i]);
        times.add(0);
      // Increase times unique number appeared by 1
      } else {
        times.set(index, times.get(index) + 1);
      }
    }
    // Gets max times
    int max = getMax(times);
    // Gets first number that appears max times
    index = times.indexOf(max);
    return uniqueNumbers.get(index);
  }

  public static double mode(double[] nums) {
    ArrayList<Double> uniqueNumbers = new ArrayList<Double>();
    ArrayList<Integer> times = new ArrayList<Integer>();
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
    int max = getMax(times);
    index = times.indexOf(max);
    return uniqueNumbers.get(index);
  }

  // 2 3 3 5 7 7 --> 3, 7
  public static ArrayList<Integer> modes(int[] nums) {
    ArrayList<Integer> uniqueNumbers = new ArrayList<Integer>();
    ArrayList<Integer> times = new ArrayList<Integer>();
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
    int max = getMax(times);
    // Gets numbers that have appear max times
    ArrayList<Integer> modes = new ArrayList<Integer>();
    for (int i = 0; i < times.size(); i++) {
      if (times.get(i) == max) {
        modes.add(uniqueNumbers.get(i));
      }
    }
    return modes;
  }

  public static ArrayList<Double> modes(double[] nums) {
    ArrayList<Double> uniqueNumbers = new ArrayList<Double>();
    ArrayList<Integer> times = new ArrayList<Integer>();
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
    int max = getMax(times);
    ArrayList<Double> modes = new ArrayList<Double>();
    for (int i = 0; i < times.size(); i++) {
      if (times.get(i) == max) {
        modes.add(uniqueNumbers.get(i));
      }
    }
    return modes;
  }

  // Gets max number in nums
  // Only made for Integer b/c only here to condense mode code... at least a little bit
  public static int getMax(ArrayList<Integer> nums) {
    int max = 0;
    for (int i = 0; i < nums.size(); i++) {
      if (nums.get(i) > max) {
        max = nums.get(i);
      }
    }
    return max;
  }

  public static void main(String[] args) {
    int[] nums = {2, 3, 5, 7};
    int[] weights = {2, 10, 4, 3};
    System.out.println("Arithmetic mean: " + arithmeticMean(nums));
    System.out.println("Geometric mean:  " + geometricMean(nums));
    System.out.println("Weighted mean:   " + weightedMean(nums, weights));
    System.out.println("RMS:             " + rootMeanSquare(nums));
    System.out.println("Harmonic mean:   " + harmonicMean(nums));
    System.out.println("AGM:             " + arithmeticGeometricMean(nums));
    System.out.println("Median:          " + median(nums));
    System.out.println("Mode:            " + mode(nums));
  }
}
