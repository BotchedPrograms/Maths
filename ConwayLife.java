// Plays Conway's Game of Life
public class ConwayLife {
  public static void playGame(int[][] cells, int generations) {
    // your code goes here
    int[][] bigScreen = new int[cells.length+2*generations+2][cells[0].length+2*generations+2];
    int[][] neighbors = new int[cells.length+2*generations+2][cells[0].length+2*generations+2];
    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[0].length; j++) {
        bigScreen[i + generations + 1][j + generations + 1] = cells[i][j];
      }
    }
    for (int gens = generations; gens > 0; gens--) {
      for (int i = gens; i < bigScreen.length - gens; i++) {
        for (int j = gens; j < bigScreen[0].length - gens; j++) {
          neighbors[i][j] = getNeighbors(bigScreen, i, j);
        }
      }
      for (int i = gens; i < bigScreen.length - gens; i++) {
        for (int j = gens; j < bigScreen[0].length - gens; j++) {
          if (bigScreen[i][j] == 1) {
            if (neighbors[i][j] < 2 || neighbors[i][j] > 3) {
              bigScreen[i][j] = 0;
            }
          } else {
            if (neighbors[i][j] == 3) {
              bigScreen[i][j] = 1;
            }
          }
        }
      }
      try {
        Thread.sleep(400);
        printScreen(crop(bigScreen, 5));
      } catch (InterruptedException ex) {
        System.out.println("Something somehow went wrong");
      }
    }
  }

  public static int getNeighbors(int[][] cells, int x, int y) {
    int neighbors = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (cells[x + i - 1][y + j - 1] == 1) {
          neighbors++;
        }
      }
    }
    if (cells[x][y] == 1) {
      neighbors--;
    }
    return neighbors;
  }

  public static void printScreen(int[][] screen) {
    StringBuilder newScreen = new StringBuilder();
    for (int i = 0; i < screen.length; i++) {
      for (int j = 0; j < screen[0].length; j++) {
        newScreen.append(screen[i][j] == 0 ? "." : "#");
        newScreen.append(" ");
      }
      newScreen.append('\n');
    }
    System.out.println(newScreen);
  }

  public static int[][] crop(int[][] bigScreen) {
    return crop(bigScreen, 0);
  }

  public static int[][] crop(int[][] bigScreen, int gap) {
    int minX = bigScreen.length;
    int minY = bigScreen.length;
    int maxX = 0;
    int maxY = 0;
    for (int i = 1; i < bigScreen.length - 1; i++) {
      for (int j = 1; j < bigScreen.length - 1; j++) {
        if (bigScreen[i][j] == 1) {
          if (i < minX) {
            minX = i;
          }
          if (i > maxX) {
            maxX = i;
          }
          if (j < minY) {
            minY = j;
          }
          if (j > maxY) {
            maxY = j;
          }
        }
      }
    }
    if (maxX - minX + 1 < 0 || maxY - minY + 1 < 0) {
      return new int[0][0];
    }
    int[][] croppedScreen = new int[maxX - minX + 2*gap + 1][maxY - minY + 2*gap + 1];
    for (int i = gap; i < croppedScreen.length - gap; i++) {
      for (int j = gap; j < croppedScreen[0].length - gap; j++) {
        croppedScreen[i][j] = bigScreen[i + minX - gap][j + minY - gap];
      }
    }
    return croppedScreen;
  }

  public static void main(String[] args) {

    // Neat repeating pattern
        /*
        int[][] cells = {
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
            {0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0},
            {0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        */

    // Gosper glider gun
    int[][] cells = {
        //           5              0              5              0              5              0              5
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
        {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    playGame(cells, 100);
  }
}
