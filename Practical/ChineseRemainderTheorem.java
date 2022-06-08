// About math behind theorem: https://youtu.be/zIFehsBHB8o
public class ChineseRemainderTheorem {
  // Gets smallest positive number that have certain remainders when divided by certain numbers
    // 3 mod 5, 1 mod 7, and 6 mod 8 --> 78 b/c it's the smallest number under those parameters
  // Prerequisite: All mods are relatively coprime
  public static int getAnswer(int[] remainders, int[] mods) {
    // N in video
    int product = 1;
    for (int i = 0; i < mods.length; i++) {
      product *= mods[i];
    }
    // Ni in video
    int[] semiProducts = new int[mods.length];
    for (int i = 0; i < mods.length; i++) {
      semiProducts[i] = product/mods[i];
    }
    // xi in video
    int[] inverses = new int[mods.length];
    for (int i = 0; i < mods.length; i++) {
      for (int j = 0; j < mods[i]; j++) {
        if (semiProducts[i] * j % mods[i] == 1) {
          inverses[i] = j;
          break;
        }
      }
    }
    // Summation in video
    int sum = 0;
    for (int i = 0; i < mods.length; i++) {
      sum += remainders[i]*semiProducts[i]*inverses[i];
    }
    // Final mod in video
    return sum % product;
  }
  
  // Returns true if all the numbers are relatively coprime, false otherwise
    // Not necessarily that there wouldn't be an answer, but that the theorem wouldn't work
  public static boolean hasAnswer(int[] remainders, int[] mods) {
    if (remainders.length != mods.length) {
      return false;
    }
    int gcf;
    for (int i = 0; i < remainders.length - 1; i++) {
      for (int j = i + 1; j < remainders.length; j++) {
        gcf = gcf(mods[i], mods[j]);
        if (gcf > 1) {
          return false;
        }
      }
    }
    return true;
  }

  public static int gcf(int a, int b) {
    for (int i = Math.min(Math.abs(a), Math.abs(b)); i > 0; i--) {
      if (a % i == 0 && b % i == 0) {
        return i;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    int[] remainders = {3, 1, 6};
    int[] mods = {5, 7, 8};
    System.out.println(getAnswer(remainders, mods));
  }
}
