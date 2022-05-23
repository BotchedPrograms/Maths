// Finds determinant of square matrix
class FindDeterminant {
  // Worked first try yoooooooooooooooooo; that's like actually unheard of
  public static int determinant(int[][] arr) {
    if (arr.length == 1) {
      return arr[0][0];
    }
    int answer = 0;
    for (int i = 0; i < arr.length; i++) {
      answer += arr[0][i]*Math.pow(-1, i)*determinant(newArray(i, arr));
    }
    return answer;
  }

  public static int[][] newArray(int n, int[][] arr) {
    int[][] newArr = new int[arr.length-1][arr.length-1];
    for (int i = 1; i < arr.length; i++) {
      for (int j = 0; j < arr.length; j++) {
        if (j < n) {
          newArr[i-1][j] = arr[i][j];
        } else if (j > n) {
          newArr[i-1][j-1] = arr[i][j];
        }
      }
    }
    return newArr;
  }
}
