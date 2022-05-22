import java.util.Scanner;

// 2694
// -> 2*6*9*4 = 432
// -> 4*3*2 = 24
// -> 2*4 = 8
// 3 times
// Not much to write home about here
public class MultiplicativePersistence {
    public static void multiply(String number, int times) {
        long product = 1;
        if (number.length() != 1) {
            for (int i = 0; i < number.length(); i++) {
                product *= Integer.parseInt(number.substring(i, i+1));
            }
            System.out.println(product);
            multiply(String.valueOf(product), times + 1);
        } else {
            System.out.println(times + " times");
        }
    }
}
