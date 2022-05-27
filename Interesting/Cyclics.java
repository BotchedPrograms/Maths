import java.util.ArrayList;

// 1/7 is cyclic b/c all its multiples have the same repeating part but starting in different places
  // 1/7 = 0.142857
  // 2/7 = 0.285714
  // 3/7 = 0.428571
  // 4/7 = 0.571428
  // 5/7 = 0.714285
  // 6/7 = 0.857142
class Cyclics {
  // Divides a by b in base 10
  public static void divide(int a, int b) {
    divide(a, b, 10);
  }
  
  // Divides a by b in given base
  public static void divide(int a, int b, int base) {
    System.out.print(a/b);
    System.out.print(".");
    a %= b;
    ArrayList<Integer> digits = getDigits(a, b, base);
    for (int i = 0; i < digits.size(); i++) {
      System.out.print(overload(digits.get(i)));
    }
    System.out.println();
  }

  // Gets repeating part of a/b
  public static ArrayList<Integer> getDigits(int a, int b, int base) {
    ArrayList<Integer> digits = new ArrayList<Integer>();
    int[] remainders = new int[b-1];
    for (int i = 0; i < b-1; i++) {
      a *= base;
      if (!inArray(remainders, i, a%b)) {
        digits.add(a/b);
        remainders[i] = a%b;
      } else {
        break;
      }
      a %= b;
    }
    return digits;
  }

  // Returns true if n is arr from index 0 to given, false otherwise
  public static boolean inArray(int[] arr, int index, int n) {
    for (int i = 0; i < index; i++) {
      if (arr[i] == n) {
        return true;
      }
    }
    return false;
  }

  // Turns n to character
  public static char overload(int n) {
    String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    return characters.charAt(n);
  }

  // Returns cyclic denominator from start to end in base 10
  public static ArrayList<Integer> getCyclics(int start, int end) {
    return getCyclics(start, end, 10);
  }

  // Returns cyclic denominator from start to end in given base
  public static ArrayList<Integer> getCyclics(int start, int end, int base) {
    ArrayList<Integer> cyclics = new ArrayList<Integer>();
    for (int i = start; i <= end; i++) {
      if (getDigits(1, i, base).size() == i-1) {
        cyclics.add(i);
      }
    }
    return cyclics;
  }

  // Prints ArrayList
  public static void print(ArrayList<Integer> arr) {
    for (int i = 0; i < arr.size(); i++) {
      System.out.print(arr.get(i));
      System.out.print(" ");
    }
    System.out.println();
  }
  
  public static void main(String[] args) {
    print(getCyclics(1, 100));
  }
}
