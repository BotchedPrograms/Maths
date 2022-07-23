import java.util.Scanner;

// 2694
  // -> 2*6*9*4 = 432
  // -> 4*3*2 = 24
  // -> 2*4 = 8
  // 3 times
// Smallest number with highest times that has been found so far (as far as I know) is 277777788888899
// Not much to write home about here

public class MultiplicativePersistence {
  public static void multiplyOut(String number) {
    multiplyOut(number, 0);
  }
  
  public static void multiplyOut(String number, int times) {
    long product = 1;
    if (number.length() != 1) {
      for (int i = 0; i < number.length(); i++) {
        product *= Integer.parseInt(number.substring(i, i+1));
      }
      System.out.println(product);
      multiplyOut(String.valueOf(product), times + 1);
    } else {
      System.out.println(times + " times");
    }
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String input;
    System.out.println("Enter a number: ");
    while (true) {
      input = scan.nextLine();
      // Checks if String is integer
      // Don't know how it works, I just modified what I saw from stackoverflow
      if (!input.matches("-?\\d+(\\d+)?")) {
        break;
      }
      multiplyOut(input);
    }
    scan.close();
  }
}
