import java.util.ArrayList;

public class ModularInverse {

  // Made to help with the Chinese Remainder Theorem
  // Given a and b, returns n such that a*n mod b = 1
    // No explanations and clarity b/c this program drained my last remaining brain cell
    // Only half true, will show general idea but not how any of it is translated to code here

  // Prerequisite: a and b are relatively prime and a < b
  public static ArrayList<Integer> getQuotients(int a, int b) {
    ArrayList<Integer> arr = new ArrayList<>();
    for (int i = 0; ; i++) {
      if (a == 1) {
        arr.add(0, b+1);
        arr.add(0, 1);
        return arr;
      }
      if (b == 1) {
        arr.add(0, 1);
        arr.add(0, a-1);
        return arr;
      }
      if (i % 2 == 0) {
        arr.add(b/a);
        b %= a;
      } else {
        arr.add(a/b);
        a %= b;
      }
    }
  }

  public static int getInverse(int a, int b) {
    ArrayList<Integer> quotients = getQuotients(a, b);
    int[] arr = new int[2];
    int size = quotients.size();
    arr[(size+1) % 2] = quotients.remove(0);
    arr[size % 2] = quotients.remove(0);
    int j = 0;
    for (int i = size - 3; i >= 0; i--) {
      j = (j + 1) % 2;
      arr[j] = quotients.get(i)*arr[1-j] + arr[j];
    }
    return arr[j] % b;
  }

  public static void main(String[] args) {
    // To verify getInverse(a, b), just do (answer*a-1)/b
      // If it's an integer, then it worked
    System.out.println(getInverse(15, 532));
  }

  /*
  12n mod 17 = 1
  17k + 12n = 1
  12n = 17k + 1 (let's say sign of k doesn't change similar to how +C = -C)
  12(n-k) = 5k + 1
    c0 = n-k
  12c0 = 5k + 1
  2c0 = 5(k-2c0) + 1
    c1 = k-2c0
  2c0 = 5c1 + 1
  2(c0-2c1) = c1 + 1
    c2 = c0-2c1
  2c2 = c1 + 1
  c2 = (c1 + 1)/2
  For c2 to be an int, c1 + 1 must be divisible by 2
    Not hard to just say that c1 can be 1 and c2 thus 1
    Having c1 have a coefficient of 1 makes this possible
      One can't just do that for n = (17k + 1)/12 from earlier
      If you could, then you wouldn't need an algorithm like this in the first place
  One can work backwards and eventually get n
   */
}
