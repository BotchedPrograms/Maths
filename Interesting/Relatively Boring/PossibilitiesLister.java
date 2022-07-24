import java.util.Scanner;

// Given array of times they appear, returns corresponding list of possible... things
  // sorry if im not being the clearest rn, this program drank all my brain juice. how rude
  // uhh... so like if you give 2 2 3 then it'll return an int[][] that looks like
    /* 0 0 0
       0 0 1
       0 0 2
       0 1 0
       0 1 1
       0 1 2
       1 0 0
       1 0 1
       1 0 2
       1 1 0
       1 1 1
       1 1 2
     */
  // where each column has 2 2 3 different values respectively and this gives all the things they can have
    // note that it wouldn't normally end up looking like this b/c of how the math works
      // one has to do some reversing to make it into this nice neat arrangement
      // it works the same way that the one binary conversion trick works if you ever heard of it

public class PossibilitiesLister {
  public static int[][] listThings(int[] nums) {
    int product = 1;
    int[] reversedNums = reverseCopy(nums);
    for (int num : nums) {
      product *= num;
    }
    int[][] things = new int[product][];
    int[] thing;
    int tempI;
    for (int i = 0; i < product; i++) {
      tempI = i;
      thing = new int[reversedNums.length];
      for (int j = 0; j < reversedNums.length; j++) {
        thing[j] = tempI % reversedNums[j];
        tempI /= reversedNums[j];
      }
      things[i] = reverseCopy(thing);
    }
    return things;
  }

  public static int[] reverseCopy(int[] nums) {
    int[] reverseCopy = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
      reverseCopy[nums.length - i - 1] = nums[i];
    }
    return reverseCopy;
  }

  public static void print(int[][] arr) {
    for (int[] ints : arr) {
      for (int anInt : ints) {
        System.out.print(anInt + " ");
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String[] input;
    int[] numbers;
    System.out.println("Enter numbers: ");
    while (true) {
      input = scan.nextLine().split(" ");
      // Checks if first String is an int
        // Don't know how it works, I just modified what I saw from stackoverflow
      if (input[0].matches("-?\\d+(\\d+)?")) {
        numbers = new int[input.length];
        for (int i = 0; i < numbers.length; i++) {
          numbers[i] = Integer.parseInt(input[i]);
        }
        print(listThings(numbers));
      } else {
        break;
      }
    }
    scan.close();
  }
}
