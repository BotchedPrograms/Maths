// Makes fibonacci-esque sequence
  // i.e. next number in sequence is sum of previous 2
    // 0 1 --> 0 1 1 --> 0 1 1 2 --> 0 1 1 2 3 --> 0 1 1 2 3 5 --> 0 1 1 2 3 5 8 --> ...
  // Program notably doesn't do previous 2 but as many as inputted
    // 1 1 1 --> 1 1 1 3 --> 1 1 1 3 5 --> 1 1 1 3 5 9 --> ...
import java.util.Scanner;

public class FibonacciMaker {
  public static void makeSequence(long[] nums) {
    makeSequence(nums, 32);
  }

  public static void makeSequence(long[] nums, int times) {
    int length = nums.length;
    long sum = 0;
    // Prints all the numbers before adding numbers to sequence
    for (long i : nums) {
      sum += i;
      System.out.print(i + " ");
    }
    int count = 0;
    long temp;
    double ratio;
    loop: while (true) {
      for (int i = 0; i < length; i++) {
        temp = nums[count % length];
        nums[count % length] = sum;
        sum += sum - temp;    // Sum is sum of previous numbers
        System.out.print(nums[count % length] + " ");
        if (count >= times - length - 1) {
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
