import java.util.ArrayList;

// Gets different kinds of averages:
  // Type of average                                 Application
  // Arithmetic mean                                 Everywhere
  // Geometric mean                                  Finding rate of growth
  // Weighted mean                                   Grades
  // Root mean square (RMS)                          Alternating current
  // Harmonic mean                                   Presumably why Apple uses squircles of shape n = 4
  // Arithmetic-geometric mean (AGM)                 Hell if I know, I don't even know what I'm talking about
  // Metallic means                                  Not even really a mean. Used in construction though
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
    return sum / (double) nums.length;
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
      sumNums += (long) nums[i] *weights[i];
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
    for (int num : nums) {
      // Square
      sum += (long) num * num;
    }
    // Mean
    sum /= (double) nums.length;
    // Root
    return Math.sqrt(sum);
  }

  public static double rootMeanSquare(double[] nums) {
    double sum = 0;
    for (double num : nums) {
      sum += num * num;
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

  // Ratio of consecutive numbers in fibonacci-esque sequence except you do the second-to-last number plus the last number times n
    // n = 2: 0 1 --> 0 1 2 --> 0 1 2 5 --> 0 1 2 5 12 --> 0 1 2 5 12 29 --> ...
    // Ratio in this case approaches 2.4142135... aka the silver ratio
    // n = 1 is golden, 2 is silver, 3 is bronze, ...
  /* x    + nx  = x    = rx     where x is some number, a is supposed to be a subscript, n is the number you multiply by, and r is the ratio
      a-1     a    a+1     a
     r = (x    + nx )/x  = x   /x + nx /x  = 1/r + n
           a-1     a   a    a-1  a    a  a
     (r = 1/r + n) * r/r where r != 0    r^2 = 1 + nr    r^2 - nr - 1 = 0
     Quadratic equation --> r = (n +- √(n^2 + 4))/2
       √(n^2 + 4) > √n^2 = n so (n - √(n^2 + 4))/2 < 0
       (n + √(n^2 + 4))/2 > 0 so the metallic ratio as a function of n is (n + √(n^2 + 4))/2
   */
  public static double metallicMean(int n) {
    return (n + Math.sqrt(n*n+4))/2.0;
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
    // Copies nums to newNums
      // For the record, I had no idea about this until IntelliJ recommended it
    System.arraycopy(nums, 0, newNums, 0, nums.length);
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
    System.arraycopy(nums, 0, newNums, 0, nums.length);
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
    ArrayList<Integer> uniqueNumbers = new ArrayList<>();
    // Gets times unique number appear in nums
    ArrayList<Integer> times = new ArrayList<>();
    int index;
    for (int num : nums) {
      index = uniqueNumbers.indexOf(num);
      // If new number, add it to uniqueNumbers and add 0 to times
      if (index == -1) {
        uniqueNumbers.add(num);
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
    ArrayList<Double> uniqueNumbers = new ArrayList<>();
    ArrayList<Integer> times = new ArrayList<>();
    int index;
    for (double num : nums) {
      index = uniqueNumbers.indexOf(num);
      if (index == -1) {
        uniqueNumbers.add(num);
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
    ArrayList<Integer> uniqueNumbers = new ArrayList<>();
    ArrayList<Integer> times = new ArrayList<>();
    int index;
    for (int num : nums) {
      index = uniqueNumbers.indexOf(num);
      if (index == -1) {
        uniqueNumbers.add(num);
        times.add(0);
      } else {
        times.set(index, times.get(index) + 1);
      }
    }
    int max = getMax(times);
    // Gets numbers that have appear max times
    ArrayList<Integer> modes = new ArrayList<>();
    for (int i = 0; i < times.size(); i++) {
      if (times.get(i) == max) {
        modes.add(uniqueNumbers.get(i));
      }
    }
    return modes;
  }

  public static ArrayList<Double> modes(double[] nums) {
    ArrayList<Double> uniqueNumbers = new ArrayList<>();
    ArrayList<Integer> times = new ArrayList<>();
    int index;
    for (double num : nums) {
      index = uniqueNumbers.indexOf(num);
      if (index == -1) {
        uniqueNumbers.add(num);
        times.add(0);
      } else {
        times.set(index, times.get(index) + 1);
      }
    }
    int max = getMax(times);
    ArrayList<Double> modes = new ArrayList<>();
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
    for (Integer num : nums) {
      if (num > max) {
        max = num;
      }
    }
    return max;
  }

  public static void main(String[] args) {
    int[] nums = {2, 3, 5, 7, 11};
    int[] weights = {2, 10, 4, 3, 1};
    System.out.println("Arithmetic mean: " + arithmeticMean(nums));
    System.out.println("Geometric mean:  " + geometricMean(nums));
    System.out.println("Weighted mean:   " + weightedMean(nums, weights));
    System.out.println("RMS:             " + rootMeanSquare(nums));
    System.out.println("Harmonic mean:   " + harmonicMean(nums));
    System.out.println("AGM:             " + arithmeticGeometricMean(nums));
    System.out.println("Metallic mean:   " + metallicMean(2));
    System.out.println("Median:          " + median(nums));
    System.out.println("Mode:            " + mode(nums));
  }
}
