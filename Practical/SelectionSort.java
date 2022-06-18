import java.util.ArrayList;
import java.util.Scanner;

// Sorts int[] into increasing order using selection sort

public class SelectionSort {
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

  public static ArrayList<Integer> sort(ArrayList<Integer> nums) {
    int temp;
    // Didn't know this was a thing until IntelliJ recommended it for this one too
    ArrayList<Integer> newNums = new ArrayList<>(nums);
    for (int i = 0; i < newNums.size()-1; i++) {
      for (int j = i + 1; j < newNums.size(); j++) {
        if (newNums.get(i) > newNums.get(j)) {
          temp = newNums.get(i);
          newNums.set(i, newNums.get(j));
          newNums.set(j, temp);
        }
      }
    }
    return newNums;
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String[] input;
    ArrayList<Integer> numbers;
    System.out.println("Enter numbers: ");
    while (true) {
      input = scan.nextLine().split(" ");
      // Checks if first String is an int
        // Don't know how it works, I just modified what I saw from stackoverflow
      if (input[0].matches("-?\\d+(\\d+)?")) {
        numbers = new ArrayList<>();
        for (String s : input) {
          numbers.add(Integer.parseInt(s));
        }
        System.out.println(sort(numbers));
      } else {
        break;
      }
    }
    scan.close();
  }
}
