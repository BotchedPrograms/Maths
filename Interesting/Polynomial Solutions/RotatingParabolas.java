import java.util.ArrayList;

// Mathematical explanations at the bottom

public class RotatingParabolas {
  // Rotates parabola of form y = a(x-h)^2 + k by angle counter-clockwise around origin
  public static void printEquation(int a, int h, int k, double angle) {
    printEquation(a, h, k, 0, 0, angle);
  }

  // Rotates parabola of form y = a(x-h)^2 + k by angle counter-clockwise around point of coords (xc, yc)
  public static void printEquation(int a, int h, int k, int xc, int yc, double angle) {
    angle *= -1;
    double sin = Math.sin(angle);
    double cos = Math.cos(angle);
    double c1 = a*cos*cos;
    double c2 = -2*a*sin*cos;
    double c3 = a*sin*sin;
    double c4 = -sin + 2*a*(-xc*cos*cos + yc*sin*cos - h*cos + xc*cos);
    double c5 = -cos + 2*a*(xc*sin*cos - yc*sin*sin + h*sin - xc*sin);
    double c6 = yc*cos + xc*sin + a*(Math.pow(-xc*cos + yc*sin - h + xc, 2)) - yc + k;
    System.out.printf("%fx^2 + %fxy + %fy^2 + %fx + %fy + %f = 0\n", c1, c2, c3, c4, c5, c6);
  }

  // Input directrix of form y = mx + k1 and focal point of coords (xf, yf) to get corresponding parabola
  // To fact-check parabola:
    // Ctrl c+v to desmos
    // Graph y = mx + k1 and (xf,yf)
    // Graph y = (-1/m)x + k2 which should be the line perpendicular to the directrix that goes through the focal point
    // First of all, new line should seem to split parabola in half
    // Relatively notably, intersection point btwn new line and parabola should be the midpoint of line made from the intersection and the focal point
      // That's b/c one way to define a parabola is as the set of points with the same distance from a line and a point
      // Could eyeball the midpoint on desmos, or work out the x and y of the intersection manually, find the midpoint between that and the focal point, and see if it's on the parabola using wolframalpha
  public static void printEquation(double m, double k1, double xf, double yf) {
    // Variable names aren't named with much thought for clarity, just what I jot down when I was working this out
    double k2 = (m*yf+xf)/m;  // y-intercept of perpendicular
    double xi = m*(k2-k1)/(m*m+1);  // x of intersection of line and perpendicular
    double yi = m*xi+k1;  // y of intersection of line and perpendicular
    double d = Math.sqrt((xf-xi)*(xf-xi)+(yf-yi)*(yf-yi))/2;  // Distance between vertex and focal point
    double a = 1/(4*d);   // The a in f(x) = a(x-h)^2 + k
    // sin and cos are sin(theta) and cos(theta) where theta is the angle we're rotating
      // We don't need theta by itself though, so we'll just use its sin and cos
      // Real reason's b/c the theta used here is hard to put mathematically; you're much better off just putting it this way
        // Explained in "Solving for 0"
    double sin = (xf-xi)/(2*d);
    double cos = (yf-yi)/(2*d);
    double c1 = a*cos*cos;
    double c2 = -2*a*sin*cos;
    double c3 = a*sin*sin;
    double c4 = -sin + 2*a*(-xi*cos*cos + yi*sin*cos);
    double c5 = -cos + 2*a*(xi*sin*cos - yi*sin*sin);
    double c6 = d + yi*cos + xi*sin + a*(Math.pow(-xi*cos + yi*sin, 2));
    System.out.printf("%fx^2 + %fxy + %fy^2 + %fx + %fy + %f = 0\n", c1, c2, c3, c4, c5, c6);
  }

  // Input directrix of form y = mx + k1 and focal point of coords (xf, yf) to get corresponding parabola
    // Same as previous method but has int inputs and outputs
      // Int outputs is crazy enough, but how it seems to always start with 1x^2 - I don't even know
      // Don't make the numbers too big or it won't work
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
        // "should" as in I did a few tests, it worked, and I'm praying it's true in general
        // My math seems to tell me that lcd should be something else, but it seems to work perfectly fine without it, so I'm not pushing
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
    printEquation(15, 5, 3, 2);
  }
}

/*
Backstory
  I was reading a textbook and one of the problems involved doing a lot of math, which ultimately spat out an equation like x^2 + 12xy + 36y^2 - 404x - 130x + 659 = 0
    For context, this section was on conics, which includes equations like x^2 + y^2 = 1 which is a circle and other similar equations for ellipses, hyperbolas, and parabolas
  I had trouble finding what this equation would look like, so I went to the answer sheet and it was like "you do some math and you get the equation x^2 + 12xy + ... and here's what it looks like:" and it's damn a rotated parabola
  I was already having a hard time for this whole section, so I've just been nodding my head going "yup, uh huh, that makes sense"
    But this was the breaking point where I said "no, you can't just drop something like that and expect me to accept it; ima go work this out myself"

Focal points
  Background info: https://youtu.be/owVwjr6pTqc
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
    d      4a^2*(x-h)^2 - 1                 1
  ----- = ------------------ = a(x-h) - ---------
   x-h          4a(x-h)                  4a(x-h)
                     x-h                  1
  d = a(x-h)^2 - --------- = a(x-h)^2 - ----
                   4a(x-h)               4a
  distance from focal point = a(x-h)^2 - d = a(x-h)^2 - (a(x-h)^2 - 1/(4a)) = 1/(4a)

Rotating things
  Before we rotate a whole parabola, let's work out what rotating a single point looks like mathematically
         *' (x',y')
        /
     h /      __-* (x,y)
      / b __--
     /__-- a
    C ------------------
    Where * is the original point, *' is the rotated one, a is the angle * is rotated from the horizontal, b is the angle rotated from angle a, C is the center of rotation, and h is the distance between the center and * (or *')
      sin(a) = y/h and cos(a) = x/h, so x = h*cos(a) and y = h*sin(a)
      sin(a+b) = y'/h and cos(a+b) = y'/h, so x' = h*cos(a+b) and y' = h*sin(a+b)
      x' = h*(cos(a)cos(b) - sin(a)sin(b)) = h*cos(a)cos(b) - h*sin(a)sin(b) = x*cos(b) - y*sin(b)
      y' = h*(sin(a)cos(b) + cos(a)sin(b)) = h*sin(a)cos(b) + h*cos(a)sin(b) = y*cos(b) + x*sin(b)
    Thus, if we rotate * by an angle 0, we get the equations
      x' = x*cos(0) - y*sin(0)
      y' = y*cos(0) + x*sin(0)
    If we say that C has coordinates (xc, yc), these equations become
      x'-xc = (x-xc)*cos(0) - (y-yc)*sin(0)
      y'-yc = (y-yc)*cos(0) + (x-xc)*sin(0)
  Now, because we're working with a parabola, we set y = a(x-h)^2 + k
      x' = x*cos(0) - (a(x-h)^2 + k)*sin(0)
      y' = (a(x-h)^2 + k)*cos(0) + x*sin(0)
    What can we do with this? Not much (or at least not easily), so what we actually do is set y' = a(x'-h)^2 + k
      y' = a(x'-h)^2 + k
      y*cos(0) + x*sin(0) = a((x*cos(0) - y*sin(0) - h)^2 + k
    From here, if you know 0, you can simplify this to an equation in the form of ax^2 + bxy + cy^2 + dx + ey + f = 0 (zero this time, not theta)
    Important note: recall how x and y are the old points and x' and y' are the new ones
      By making y' be an upright parabola and solving for x and y, we make the new parabola upright and solve for the old one
      i.e. we don't get an upright parabola rotated by 0, but a parabola rotated by 0 to become upright
      i.e. the parabola is rotated the opposite way, meaning we can solve this issue by just multiplying 0 by -1
        It's why the method where angle is given, we do angle *= -1;
        Not done in the other methods because the math is done with the *= -1 in mind
  If we want to rotate around a point of coordinates (xc, yc), the equation becomes
    (y-yc)*cos(0) + (x-xc)*sin(0) + yc = a((x-xc)*cos(0) - (y-yc)*sin(0) - h + xc)^2 + k

Solving for rotated parabola when given the directrix and focal point
  We use the equation above
    x and y stay in the final equation
    a, 0, xc, yc, h, and k we need to solve for
                    ^       \             _--``              ___ perpendicular
                    |        \         /``focal point ___---
                    |         \      /`     v  ___ ---
                    | theta    \     |  ___ f--
                    | v         \ __ -v-                ____---``
                    | 0    ___---i    ^``---____------``
         y-intercept|__---       ^\  vertex     rotated parabola
             ___--- b  intersection\
      ___---        |               \
                    |                \
   <----------------o-------------------------------------------->
              origin|                  \
                    |                   \
                    v                    \ directrix
  Solving for a
    Distance from focal point (which we'll call d from here on) = 1/(4a) as mentioned in "Focal points"
      d = 1/(4a)
      4a = 1/d
      a = 1/(4d)
    To solve for d, we use the fact that the vertex (along with the whole parabola) is equidistant from the focal point and some line called the directrix
    If we draw a line through the focal point and perpendicular to the directrix, the distance between the intersection and the focal point will be 2*d
    All told, to solve for a:
      Get perpendicular line using point-slope formula
      Use system of equations of perpendicular and normal lines to find intersection
        ohh so that's why it's called a normal line, that's cute
      Find distance between intersection and focal point using distance formula or pythagorean theorem
      d = half that
      a = 1/(4d)
  Solving for 0
    0 is the theta you see on the graph
    We want 0 to be a value from -pi to pi (a full circle)
      sin, cos, or tan inverse are thus out of the question (since they only cover half a circle)
    With that in mind, it's actually easier if we define sin(0) and cos(0) since every theta in a circle has a unique combination of sin and cos
    Ok, I should confess something: the graph's a little misleading
      The theta used in this program isn't the angle from the perpendicular and a vertical line at the y-intercept, but the one at the intersection
      I honestly think the math would still work and I did play around with it, but the first equation that I got to work consistently was the one at the intersection
    With that in mind, we can say sin(0) = o/h = (xf-xi)/(2d)
        xf for focal point, xi for intersection, 2d for distance between them
      cos(0) = a/h = (yf-yi)/(2d)
    One might be worried if this is the 0 we want, considering how we multiplied it by -1 earlier
      Seeing how both sin and cos would be positive in this case, and that we'd need to rotate this parabola by a positive angle to be upright, 0 is not reversed and thus is the same one as our equations
  Solving for xc and yc
    One option is to have the upright parabola rotate around the y-intercept where the 0 in the graph is
    But as mentioned earlier, the equations used here revolve around the intersection, so xc = xi and yc = yi
  Solving for h and k
    h and k are the horizontal and vertical transformations of the upright parabola
    If you imagine rotating the parabola in the graph around the intersection to make it upright, you'll see the upright vertex is at x = xi and y = yi + d (d being the distance between the intersection and the vertex)
    So h = xi and k = yi + d
  The equation can now be simplified to
    (y-yi)*cos(0) + (x-xi)*sin(0) + yi = a((x-xi)*cos(0) - (y-yi)*sin(0) - xi + xi)^2 + yi + d
    (y-yi)*cos(0) + (x-xi)*sin(0) = a((x-xi)*cos(0) - (y-yi)*sin(0))^2 + d
    where a, sin(0), cos(0), xi, yi, and d are blah blah blah

Solving for int coefficients for final equation in form of ax^2 + bxy + cy^2 + dx + ey + f = 0
  To get int coefficients, let's see what's stopping them
  The biggest thing preventing int coefficients is the a(...)^2 part where the sin and cos are over 2d, that 2d is squared, and a is over 4d
    Thus it's something / ((2d)^2*(4d)) = something / (16d^3)
  Another thing is that xi and yi (if you work that out in terms of the given m, k1, a, and b (y = mx+k1 and focal point at (a, b)) is over (m^2+1)
    Because the coefficient of x and y have a combination of 3 xis and yis, we multiply everything by (m^2+1)^3
    Except apparently it works with just (m^2+1)^2
      That part I genuinely can't explain
      More math later and now it seems like it should be m^3(m^2+1)^3
  All told, multiply both sides by 16d^3 and work that mess out
    Then multiply the coefficients by (m^2+1)^2
    Divide them by the gcf of all the coefficients to simplify
      Optionally, you can try to find why the coefficient of x^2 always seems to be 1
      Don't ask me though, can't explain this one either
      Proofs for both of them might be as simple as more algebra 2 working-it-out, but I've had my fill of math
*/
