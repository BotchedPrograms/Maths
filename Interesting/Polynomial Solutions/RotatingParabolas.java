import java.util.ArrayList;

// I'll add explanations, quality-of-life changes, and more later
  // For now, just run this command, ctrl c+v the equation to desmos, and go "woah that's so cool"
// Parabola math at the bottom

public class RotatingParabolas {
  public static void printEquation(double angle) {

  }

  // Input directrix of form y = mx + k1 and focal point of coords (xf, yf) to get corresponding parabola
  public static void printEquation(double m, double k1, double xf, double yf) {
    double k2 = (m*yf+xf)/m;
    double xi = m*(k2-k1)/(m*m+1);
    double yi = m*xi+k1;
    double d = Math.sqrt((xf-xi)*(xf-xi)+(yf-yi)*(yf-yi))/2;
    double a = 1/(4*d);
    double theta = Math.acos((yf-yi)/(2*d))*Math.abs(xf-xi)/(xf-xi);
    double sin = Math.sin(theta);
    double cos = Math.cos(theta);
    double c1 = a*cos*cos;
    double c2 = -2*a*sin*cos;
    double c3 = a*sin*sin;
    double c4 = -sin + 2*a*(-xi*cos*cos + yi*sin*cos);
    double c5 = -cos + 2*a*(xi*sin*cos - yi*sin*sin);
    double c6 = d + yi*cos + xi*sin + a*(Math.pow(xi*cos - yi*sin, 2));
    System.out.printf("%fx^2 + %fxy + %fy^2 + %fx + %fy + %f = 0\n", c1, c2, c3, c4, c5, c6);
  }

  // Input directrix of form y = mx + k1 and focal point of coords (xf, yf) to get corresponding parabola
    // To fact-check parabola:
      // Ctrl c+v to desmos
      // Graph y = mx + k1 and (xf,yf)
      // Graph y = (-1/m)x + k2 which should be the line perpendicular to the directrix that goes through the focal point
      // First of all, new line should seem to split parabola in half
      // Relatively notably, intersection point btwn new line and parabola should be right btwn the intersection of the two lines and the focal point
        // That's b/c one way to define a parabola is as the set of points with the same distance from a line and a point
  public static void printEquation(int m, int k1, int xf, int yf) {
    double k2 = (double) (m*yf+xf)/m;
    double xi = m*(k2-k1)/(m*m+1);
    double yi = m*xi+k1;
    double d = Math.sqrt((xf-xi)*(xf-xi)+(yf-yi)*(yf-yi));
    double[] coeffs = new double[6];
    coeffs[0] = Math.pow(yf-yi, 2);
    coeffs[1] = -2*(yf-yi)*(xf-xi);
    coeffs[2] = Math.pow(xf-xi, 2);
    coeffs[3] = 2*yi*(yf-yi)*(xf-xi) - 2*xi*(yf-yi)*(yf-yi) - 2*d*d*(xf-xi);
    coeffs[4] = 2*xi*(yf-yi)*(xf-xi) - 2*yi*(xf-xi)*(xf-xi) - 2*d*d*(yf-yi);
    coeffs[5] = Math.pow(yi*(xf-xi)-xi*(yf-yi), 2) + 2*d*d*yi*(yf-yi) + 2*d*d*xi*(xf-xi) + d*d*d*d;
    double lcd = Math.pow(m*m+1, 2);
    long[] coeffs2 = new long[6];
    for (int i = 0; i < 6; i++) {
      // Math.round b/c round-off errors; coeffs[i]*lcd should be a whole number
        // should, as in I did a few tests, it worked, and I'm praying it's true in general
        // my math seems to tell me that lcd should be Math.pow(m*m+1, 3) but it seems to work perfectly without it, so I'm not pushing
      coeffs2[i] = Math.round(coeffs[i]*lcd);
    }
    long gcf = gcf(coeffs2);
    for (int i = 0; i < 6; i++) {
      coeffs2[i] /= gcf;
    }
    System.out.printf("%dx^2 + %dxy + %dy^2 + %dx + %dy + %d = 0\n", coeffs2[0], coeffs2[1], coeffs2[2], coeffs2[3], coeffs2[4], coeffs2[5]);
  }

  // All the methods from here on are just here to make the equation look better
    // All the "rotating parabolas" part is above

  // Gets gcf of 2 numbers
    // Gets prime factors and returns the product of the ones they have in common
  public static long gcf(long a, long b) {
    ArrayList<Long> factorsA = factor(a);
    ArrayList<Long> factorsB = factor(b);
    ArrayList<Long> inCommon = inCommon(factorsA, factorsB);
    long product = 1;
    for (Long aLong : inCommon) {
      product *= aLong;
    }
    return product;
  }

  // Gets gcf of numbers in long[]
  public static long gcf(long[] nums) {
    if (nums.length == 0) {
      return -1;
    }
    if (nums.length == 1) {
      return nums[0];
    }
    long gcf = gcf(nums[0], nums[1]);
    for (int i = 1; i < nums.length; i++) {
      if (gcf == 1) {
        break;
      }
      gcf = gcf(gcf, nums[i]);
    }
    return gcf;
  }

  public static ArrayList<Long> factor(long num) {
    return factor(Math.abs(num), new ArrayList<>(), 3, new ArrayList<>());
  }

  public static ArrayList<Long> factor(long num, ArrayList<Long> smallPrimes, int i, ArrayList<Long> factors) {
    if (num % 2 == 0) {
      factors.add(2L);
      return factor(num/2, smallPrimes, i, factors);
    }
    int sqrt = (int) Math.sqrt(num);
    l1: for (; i <= sqrt; i += 2) {
      for (Long smallPrime : smallPrimes) {
        if (i % smallPrime == 0 && i != smallPrime) {
          continue l1;
        } else if (Math.sqrt(i) > smallPrime) {
          break;
        }
      }
      smallPrimes.add((long) i);
      if (num % i == 0) {
        factors.add((long) i);
        return factor(num/i, smallPrimes, i, factors);
      }
    }
    if (num != 1 || factors.size() == 0) {
      factors.add(num);
    }
    return factors;
  }

  public static ArrayList<Long> inCommon(ArrayList<Long> arr, ArrayList<Long> arr2) {
    ArrayList<Long> inCommon = new ArrayList<>();
    for (int i = arr.size() - 1; i >= 0; i--) {
      for (int j = arr2.size() - 1; j >= 0; j--) {
        if (arr.get(i).equals(arr2.get(j))) {
          inCommon.add(arr.get(i));
          arr.remove(i);
          arr2.remove(j);
          break;
        }
      }
    }
    return inCommon;
  }

  public static void main(String[] args) {
    printEquation(2, -5, 4, -4);
  }
}

/*
Focal points
  Background info: https://youtu.be/hoh4TmPzu1w
  To find the focal point of any parabola, let's replace the 2x with f'(x)
    If we use f(x) = ax^2 + bx + c, things get messy with b
    Instead we'll use f(x) = a(x-h)^2 + k, which replaces b with a vertical and horizontal transformation of y = ax^2
      aka vertex form
      Why you can do that: https://youtu.be/hoh4TmPzu1w
  I'll use 0 for theta for convenience sake
  tan(0) = 1/f'(x) = 1/(2a(x-h))
  tan(20) =  2tan(0)        2 * 1/(2a(x-h))      (2a(x-h))^2       2(2a(x-h))             4a(x-h)         x-h
           ------------ = ------------------- * ------------- = ----------------- = ------------------ = -----
            1-tan^2(0)     1 - 1/(2a(x-h))^2     (2a(x-h))^2     (2a(x-h))^2 - 1     4a^2*(x-h)^2 - 1      d
    tan(20) = sin(20)       2sin(0)cos(0)       cos^2(0)       2sin(0)/cos(0)         2tan(0)
             --------- = ------------------- / ---------- = --------------------- = ------------
              cos(20)     cos^2(0)-sin^2(0)     cos^2(0)     1-sin^2(0)/cos^2(0)     1-tan^2(0)
    d      4a^2*(x-h)^2 - 1                 1                x-h       1
  ----- = ------------------ = a(x-h) - ---------     d = --------- = ----
   x-h          4a(x-h)                  4a(x-h)           4a(x-h)     4a
*/
