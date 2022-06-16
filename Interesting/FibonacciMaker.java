// Makes fibonacci-esque sequence
  // i.e. next number in sequence is sum of previous 2
    // 0 1 --> 0 1 1 --> 0 1 1 2 --> 0 1 1 2 3 --> 0 1 1 2 3 5 --> 0 1 1 2 3 5 8 --> ...
  // Program notably doesn't do previous 2 but as many as inputted
    // 1 1 1 --> 1 1 1 3 --> 1 1 1 3 5 --> 1 1 1 3 5 9 --> ...
// More info at the bottom
import java.util.Scanner;

public class FibonacciMaker {
  public static void makeSequence(long[] nums) {
    makeSequence(nums, 32, 1);
  }

  // rat (short for ratio, but I already used that) means next number = the latest number * rat + previous numbers
    // rat = 2 ==> 0 1 --> 0 1 2 --> 0 1 2 5 --> 0 1 2 5 12
    // Can't change rat in command line b/c I thought it'd be awkward to implement, so if you want to change it, do it manually
  public static void makeSequence(long[] nums, int times, int rat) {
    int length = nums.length;
    long sum = 0;
    // Prints all the numbers before adding numbers to sequence
    for (long i : nums) {
      sum += i;
      System.out.print(i + " ");
    }
    int count = 0;
    double ratio;
    loop: while (true) {
      for (int i = 0; i < length; i++) {
        sum -= nums[count % length];
        nums[count % length] += rat * sum;
        sum += nums[count % length];
        System.out.print(nums[count % length] + " ");
        if (count > times - length - 2) {
          // Ratio gets last number divided by second-to-last number
          ratio = (double) nums[count % length] / nums[(count+length-1) % length];
          break loop;
        }
        count++;
      }
    }
    System.out.println("\nRatio: " + ratio);
    System.out.println();
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String[] input;
    long[] numbers;
    System.out.println("Enter numbers: ");
    while (true) {
      input = scan.nextLine().split(" ");
      // Checks if first String is an int
       // Don't know how it works, I just modified what I saw from stackoverflow
      if (input[0].matches("-?\\d+(\\d+)?")) {
        numbers = new long[input.length];
        for (int i = 0; i < numbers.length; i++) {
          numbers[i] = Long.parseLong(input[i]);
        }
        makeSequence(numbers);
      } else {
        break;
      }
    }
    scan.close();
  }
}
/*
Most famous of these sequences is the Fibonacci's whom this program is named after
  Famed especially b/c of how the ratio between consecutive numbers tends towards the golden ratio which appears throughout nature
  * Not the only one though, in fact the same property appears in any sequence where you add the last 2 numbers to get the next
      Let's say that r is the golden ratio from here on just because
      x    + x  = x    = rx         x    + x  = rx         r = ( x    + x  ) / x  = x    / x  + 1 = 1/r + 1
       n-1    n    n+1     n         n-1    n     n               n-1    n      n    n-1    n           (These n's are supposed to be subscripts btw)
      (r = 1/r + 1) * r             r^2 = 1 + r            r^2 - r -1 = 0        r = (1 +- √5)/2
      (1 + √5)/2 > 0 while (1 - √5)/2 < 0, so r = (1 + √5)/2
  * There are other fibonacci-esque sequences out there, notably the Lucas numbers which start with 2 1
      In fact, they're arguably cooler b/c it has a stronger relationship with the golden ratio
      If we say that 2 is the 0th number, the n-th term is approximately r^n for all n != 0
  * There are other metallic ratios besides the golden one
      Can be made in this program by changing rat, and the ratio would tend towards the corresponding metallic ratio
        rat = 1 is golden, 2 is silver, 3 is bronze,...
      Can be used in construction and some other stuff probably
  * Example of natural appearance is with flowers whose seeds rotate in a way that has to do with the golden ratio
      I'll try my best to explain this one, but if it doesn't make sense, you can always go watch this video: https://www.youtube.com/watch?v=sj8Sg8qnjOg
        You are a flower, and you want a pattern to pack your seeds efficiently.
        Packing your seeds in a circle is the best choice, the shape defined by the most area for the least perimeter
        You also want a pattern that builds upon itself; that makes a circle and can quickly make it grow bigger
        This can be done by placing a seed, rotating a bit, planting another seed, rotating a bit, and so forth
            0 turns:             1/2 turns:         1/3 turns:       1/4 turns:      3/4 turns:     5/4 turns:
                                                    o                    o                             o
                                                      o                  o              o              o
          x o o o o o o        o o o x o o o            x o o          o x o o        o x o o        o x o o
          ^ \         /                               o                  o              o              o
      Start    Seeds                                o                                   o
        Numerator doesn't change # of spokes as shown in 1/4 and 3/4 turns
        You want to make n turns but you don't want n to be a fraction b/c denominator = spokes
        To get a nice spiral, you'd want an irrational number like pi. Pi would look like something like this
                                  o o o o o o o                        o
                             o o                o o                    o
                         o o                        o                  o
                     o o                              o                o
                  o o                 o o o o           o              o
                o                 o o          o        o              o
             o                 o                 o      o             o
           o                 o        o o         o     o             o
         o                 o        o     o      o     o             o
       o                  o        o       x o o      o             o
     o                    o        o     o          o              o
                          o        o      o       o              o
                          o          o       o o               o
                           o           o                   o o
                             o           o o         o o o
                               o             o o o o
                                 o
                                    o
                                       o o
                                            o o
        Graph's inaccurate, but point is that it would spiral for a little bit before forming straight lines for a while
          It'd be more than 3 straight lines, but making any more would be torture
        This is because pi can actually be written as 3 +       1
                                                         ---------------
                                                            7   +   1
                                                                 -------
                                                                 15 + ...
        Because 1/15 is fairly close to 0, pi is close to 3 + 1 / 7 = 22/7, which is a fraction so it has spokes
          It's only until later down the line does the 1/(15 + ...) start to matter and the lines spiral again
        With that in mind, the most irrational number (i.e. the number we want) would be 1 + 1 / ( 1 + 1 / ( 1 + 1 / 1 + ... ))
          i.e. the same thing as the pi thing but instead of 3, 7, 15, 1, 292,..., it's all 1s
          r = 1 + 1 / ( 1 + 1 / 1 ...) = 1 + 1/r    (r = 1 + 1/r) * r    r^2 = r + 1    r^2 - r - 1 = 0
          It's the same thing as before, r = (1 + √5)/2
        Actually, in the same way that 5/4 turns equals 1/4 turns, (1 + √5)/2 turns = (1 + √5)/2 - 1 turns
          r = 1 + 1/r so r - 1 = 1/r, which is about 0.618...
        Making r turns anti-clockwise is also similar to making 2 - r turns clockwise, which is about 0.382...
        Turn this into angles by doing 0.382 * 360 degrees, and you get 137.5 degrees, often dubbed the golden angle
          Fudge that part was hard to reverse engineer

        If we divide the circle into 2 parts, the part rotated and the rest, the ratio between the bigger and smaller is 1/(r-1)
          Who on Earth would put it this way? Google search results. Made explaining why r appears so counterintuitive
          r - 1 = 1/r so 1/(r-1) = 1/(1/r) = r
        The ratio is the same as the ratio between the number of turns turned and not turned
        We said we make r turns anti-clockwise so that's 1.618 turns, and the rest is 2 - r turns or 0.382, the aforementioned clockwise turns
          Actually, because 1.618 turns is really 0.618 turns, let's use that
        We get that the ratio is (r-1)/(2-r) which we expand as
          1 + √5
          -------  -  1      1 + √5 - 2
             2                              √5 - 1    3 + √5   3√5 + 5 - 3 - √5    2√5 + 2    √5 + 1
          --------------- = ------------ = -------- * ------ = ---------------- = --------- = ------- = r
                   1 + √5                   3 - √5    3 + √5       9   -   5          4          2
            2  -  -------   4 - (1 + √5)
                     2
          Also, (r-1)/(2-r) = r   r-1 = r(2-r)   r-1 = 2r - r^2   r^2 - r - 1 = 0   r = golden ratio
 */
