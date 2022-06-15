// It's the 3n + 1 thing. Recap in case you live under a rock:
  // If number is odd, multiply it by 3 and add 1
  // Else if number is even, divide it by 2
// Collatz Conjecture (aka many other names) states number should eventually go to 1
  // Infamously hard to prove or disprove

import java.util.Scanner;

public class CollatzConjecture {
  public static void printNumbers(long n) {
    int steps = 0;
    long max = 1;
    while (n != 1) {
      System.out.print(n + " ");
      max = Math.max(n, max);   // Sets max
      n = n % 2 == 1 ? 3*n+1 : n/2;   // Condition operator here is just a condensed if-else statement
      steps++;  // Increments steps
    }
    System.out.println(n);  // Prints last number aka 1
    System.out.println("Highest: " + max);  // Prints highest number in sequence
    System.out.println("Steps: " + steps);  // Prints steps until sequence reaches 1
    System.out.println();
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String input;
    long num;
    System.out.println("Input number: ");
    while (true) {
      input = scan.nextLine();
      // Checks if first String is an int
        // Don't know how it works, I just modified what I saw from stackoverflow
      if (input.matches("-?\\d+(\\d+)?")) {
        num = Long.parseLong(input);
        printNumbers(num);
      } else {
        break;
      }
    }
    scan.close();
  }
}
