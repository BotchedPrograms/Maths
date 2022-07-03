import java.util.ArrayList;

// About math behind theorem: https://youtu.be/zIFehsBHB8o

public class ChineseRemainderTheorem {
  // Gets smallest positive number that have certain remainders when divided by certain numbers
    // 3 mod 5, 1 mod 7, and 6 mod 8 --> 78 b/c it's the smallest number under those parameters
  // Explanation at the bottom
  // Prerequisite: All mods are relatively prime
  public static int getAnswer(int[] remainders, int[] mods) {
    if (!hasAnswer(remainders, mods)) {
      return -1;
    }
    // N in video
    int product = 1;
    for (int mod : mods) {
      product *= mod;
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

  // Returns true if all the numbers are relatively prime, false otherwise
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
    ArrayList<Integer> factorsA = factor(a);
    ArrayList<Integer> factorsB = factor(b);
    ArrayList<Integer> inCommon = inCommon(factorsA, factorsB);
    int product = 1;
    for (Integer anInt : inCommon) {
      product *= anInt;
    }
    return product;
  }
  
  public static ArrayList<Integer> factor(int num) {
    return factor(num, 3, new ArrayList<>());
  }

  public static ArrayList<Integer> factor(int num, int i, ArrayList<Integer> factors) {
    if (num % 2 == 0) {
      if (num == 0) {
        return factors;
      }
      factors.add(2);
      return factor(num/2, i, factors);
    }
    int sqrt = (int) Math.sqrt(num);
    for (; i <= sqrt; i += 2) {
      if (num % i == 0) {
        factors.add(i);
        return factor(num/i, i, factors);
      }
    }
    if (num != 1) {
      factors.add(num);
    }
    return factors;
  }

  public static ArrayList<Integer> inCommon(ArrayList<Integer> arr, ArrayList<Integer> arr2) {
    ArrayList<Integer> inCommon = new ArrayList<>();
    for (int i = arr.size() - 1; i >= 0; i--) {
      for (int j = arr2.size() - 1; j >= 0; j--) {
        if (arr.get(i).equals(arr2.get(j))) {
          inCommon.add(arr.get(i));
          arr.remove(i);
          arr2.remove(j);
          break;
        }
      }
    }
    return inCommon;
  }

  public static void main(String[] args) {
    int[] remainders = {3, 1, 6};
    int[] mods = {5, 7, 8};
    System.out.println(getAnswer(remainders, mods));
  }
}

/* 3 mod 5, 1 mod 7, 6 mod 8
b mod n

bi   Ni   xi   bi * Ni * xi
3    56    1       168
1    40    3       120
6    35    3       210

Ni is the product of all the n's except ni
  For i = 1, it means that N1 = something mod n1, 0 mod n2, and 0 mod n3
  Technically N1 is congruent to something mod n1... not equal
    I'm lazy though, so that's what we're going with
xi, by definition, is some number such that Ni * xi = 1 mod n1
  For i = 1, it means bi * Ni * xi = b1 mod n1, 0 mod n2, and 0 mod n3
All the bi * Ni * xi now look something like
  i = 1:  b1 mod n1 = 0 mod n2  = 0 mod n3
  i = 2:  0 mod n1  = b2 mod n2 = 0 mod n3
  i = 3:  0 mod n1  = 0 mod n2  = b3 mod n3
The sum mod n1 = (b1 mod n1) + (0 mod n1) + (0 mod n1) = b1 mod n1
  sum mod n2 = (0 mod n2) + (b2 mod n2) + (0 mod n2) = b2 mod n2
  sum mod n3 = (0 mod n3) + (0 mod n3) + (b3 mod n3) = b3 mod n3
Moding whole thing by n1 * n2 * n3 preserves the mods
*/
