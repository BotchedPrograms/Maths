import java.util.Scanner;

// Let's say we have 3 6-sided dice and they are: 4, 1, and 4
  // The max in this case is 4
// On average, the max is approximately whatever this program pops out: 4.958333333333333
// Idea taken from here: https://www.youtube.com/watch?v=X_DdGRjtwAo

public class AverageMaxRoll {
  // Crunches the numbers
  public static double getAvgMax(int sides, int dice) {
    double sum = 0;
    for (double x = 1; x <= sides; x++) {
      // Another approach would be ( x^dice - (x-1)^dice ) / sides^dice.
        // Would presumably be better for avoiding round-off errors but also prone to having numerator and denominator being too unwieldy
      sum += (Math.pow(x/sides, dice) - Math.pow((x-1)/sides, dice)) * x;
    }
    return sum;
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String[] inputs;
    System.out.println("Enter number of sides and dice: ");
    while (true) {
      inputs = scan.nextLine().split(" ");
      // Checks if String is integer
        // Don't know how it works, I just modified what I saw from stackoverflow
      if (inputs.length != 2) {
        break;
      }
      if (!inputs[0].matches("-?\\d+(\\d+)?") && !inputs[1].matches("-?\\d+(\\d+)?")) {
        break;
      }
      System.out.println(getAvgMax(Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1])));
    }
    scan.close();
  }
}
