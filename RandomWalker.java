import java.util.Scanner;

// Random path generator from a long time ago
// Needs stdlib to use, got mine for princeton comp sci course, that's probably where it came from
public class RandomWalker{
	public static String randomMove(boolean[][] passed, int x, int y, int length) {
		int legals = 0;
		boolean up = false;
		boolean right = false;
		boolean down = false;
		boolean left = false;

		// tests up
		// checks if out of bounds first, then if up hasn't been passed
		if (x-1 < 0 || !passed[x-1][y]) {
			legals++;
			up = true;
		}
		// tests right
		if (y+1 > length-1 || !passed[x][y+1]) {
			legals++;
			right = true;
		}
		// tests down
		if (x+1 > length-1 || !passed[x+1][y]) {
			legals++;
			down = true;
		}
		// tests left
		if (y-1 < 0 || !passed[x][y-1]) {
			legals++;
			left = true;
		}

		// creates array of legal moves and randomly picks one
		if (legals == 0) {
			return "None ";
		} else {
			String[] cardinalS = new String[legals];

			int count = 0;
			if (up) {
					cardinalS[count] = "Up   ";
					count++;
			}
			if (right) {
					cardinalS[count] = "Right";
					count++;
			}
			if (down) {
					cardinalS[count] = "Down ";
					count++;
			}
			if (left) {
					cardinalS[count] = "Left ";
			}

			String cardinalR = cardinalS[(int) (Math.random()*legals)];
			return cardinalR;
		}
	}

	public static void movesDraw(String moves, int length) {
		StdDraw.setCanvasSize(800, 800);
		StdDraw.setXscale(0, length-1);
		StdDraw.setYscale(0, length-1);
		int x0 = 0;
		int y0 = 0;
		int count = 0;
		for (int i = 0; i < moves.length(); i++) {
			int x = 0;
			int y = 0;
			boolean pointed = false;
			if (moves.charAt(i) == '(') {
				pointed = true;
				count++;
				int comma = 0;
				int paraR = 0;
				// finds where comma is
				boolean found1 = true;
				boolean found2 = true;
				for (int j = 1; j < Math.log10(length)+3; j++) {
					if (moves.charAt(i+j) == ',' && !found1) {
						comma = i+j;
					}
					found1 = false;
				}
				// finds where right parentheses is
				for (int k = 2; k < Math.log10(length)+4; k++) {
					if (moves.charAt(comma+k) == ')' && !found2) {
						paraR = comma+k;
					}
					found2 = false;
				}
				x = Integer.parseInt(moves.substring(i+1, comma));
				y = Integer.parseInt(moves.substring(comma+2, paraR));
			}
			if (count == 1 && pointed) {
				x0 = x;
				y0 = y;
			}
			if (pointed) {
				StdDraw.setPenRadius(0.004);
				StdDraw.point(x, y);
				StdDraw.line(x0, y0, x, y);
				x0 = x;
				y0 = y;
			}
		}
	}


	public static void main(String[] args){

	/* Write your code here */

	/* Scanner scan = new Scanner(System.in);

	System.out.println("Type the number of sides:");
	int sides = scan.nextInt();

	System.out.println("Type a side length:");
	double length = scan.nextDouble();

	RegularPolygon poly = new RegularPolygon(sides, length);
	System.out.println(poly);
	*/

		// movesDraw("Start | (100, 100) \nDown  | (100, 99)\nLeft  | (99, 99)\nUp    | (99, 100)\nLeft  | (98, 100)\nUp    | (98, 101)\nUp    | (98, 102)\nLeft  | (97, 102)\nDown  | (97, 101)\nDown  | (97, 100)\nDown  | (97, 99)\nLeft  | (96, 99)\nDown  | (96, 98)\nDown  | (96, 97)\nDown  | (96, 96)\nRight | (97, 96)\nRight | (98, 96)\nDown  | (98, 95)\nRight | (99, 95)\nUp    | (99, 96)\nRight | (100, 96)\nDown  | (100, 95)\nRight | (101, 95)\nRight | (102, 95)\nDown  | (102, 94)\nLeft  | (101, 94)\nLeft  | (100, 94)\nLeft  | (99, 94)\nDown  | (99, 93)\nLeft  | (98, 93)\nLeft  | (97, 93)\nLeft  | (96, 93)\nUp    | (96, 94)\nRight | (97, 94)\nUp    | (97, 95)\nLeft  | (96, 95)\nLeft  | (95, 95)\nUp    | (95, 96)\nLeft  | (94, 96)\nDown  | (94, 95)\nLeft  | (93, 95)\nUp    | (93, 96)\nLeft  | (92, 96)\nUp    | (92, 97)\nLeft  | (91, 97)\nUp    | (91, 98)\nUp    | (91, 99)", 201);

		Scanner scan = new Scanner(System.in);
		int half_length;
		int trials;
		int length;
		if (args.length >= 1) {
			half_length = Integer.parseInt(args[0]);
			length = half_length * 2 + 1;
			if (args.length >= 2) {
				trials = Integer.parseInt(args[1]);
			} else {
				trials = 1;
			}
		} else {
			System.out.println("Enter distance from center:");
			half_length = scan.nextInt();
			length = half_length * 2 + 1;
			System.out.println("Enter the number of trials:");
			trials = scan.nextInt();
		}


		if (trials == 1) {
			int lose = 0;
			int win = 0;
			boolean[][] passed = new boolean[length][length];
			int x = half_length;
			int y = half_length;
			passed[x][y] = true;
			System.out.println("\nStart | (" + half_length + ", " + half_length + ")");

			for (int i = 0; i < length*length; i++) {
				String move = randomMove(passed, x, y, length);
				if (move.equals("None ")) {
					i = length*length;
					lose++;
				} else if (move.equals("Up   ")) {
					x--;
					if (x < 0) {
							i = length*length;
							win++;
					} else {
							passed[x][y] = true;
					}
				} else if (move.equals("Right")) {
					y++;
					if (y > length-1) {
							i = length*length;
							win++;
					} else {
							passed[x][y] = true;
					}
				} else if (move.equals("Down ")) {
					x++;
					if (x > length-1) {
							i = length*length;
							win++;
					} else {
							passed[x][y] = true;
					}
				} else if (move.equals("Left ")) {
					y--;
					if (y < 0) {
							i = length*length;
							win++;
					} else {
							passed[x][y] = true;
					}
				}
				System.out.println(move + " | (" + y + ", " + (length-x-1) + ")");
			}
			// printing both losses and wins is unnecessary because there's only 1 trial
			System.out.println("\nloses = " + lose);
			System.out.println("wins = " + win);


		} else if (trials > 1) {
			int lose = 0;
			int win = 0;
			int stepsL = 0;
			int stepsW = 0;
			int stepsT = 0;
			int stepsLow = length*length;
			int stepsHigh = 0;
			String[] winLog = new String[trials];
			int winLogIndex = 0;
			String stepsLowest = "";
			String stepsHighest = "";
			System.out.println();

			for (int j = 0; j < trials; j++) {
				int x = half_length;
				int y = half_length;
				boolean[][] passed = new boolean[length][length];
				passed[x][y] = true;
				int steps = 0;
				boolean didWin = false;
				StringBuilder str = new StringBuilder();
				str.append("Start | (" + half_length + ", " + half_length + ")        \n");

				for (int i = 0; i < length*length; i++) {
					String move = randomMove(passed, x, y, length);
					if (move.equals("None ")) {
						i = length*length;
						lose++;
						didWin = false;
					} else if (move.equals("Up   ")) {
						x--;
						steps++;
						if (x < 0) {
							i = length*length;
							win++;
							didWin = true;
						} else {
							passed[x][y] = true;
						}
					} else if (move.equals("Right")) {
						y++;
						steps++;
						if (y > length-1) {
							i = length*length;
							win++;
							didWin = true;
						} else {
							passed[x][y] = true;
						}
					} else if (move.equals("Down ")) {
						x++;
						steps++;
						if (x > length-1) {
							i = length*length;
							win++;
							didWin= true;
						} else {
							passed[x][y] = true;
						}
					} else if (move.equals("Left ")) {
						y--;
						steps++;
						if (y < 0) {
							i = length*length;
							win++;
							didWin = true;
						} else {
							passed[x][y] = true;
						}
					} else {
						System.out.println("Error2");
					}
					str.append(move + " | (" + y + ", " + (length-x-1) + ")         \n");
				}
				if (didWin) {
					stepsW += steps;
					winLog[winLogIndex] = str.toString();
					winLogIndex++;
				} else {
					stepsL += steps;
				}
				if (steps < stepsLow) {
					stepsLow = steps;
					stepsLowest = str.toString();
				}
				if (steps > stepsHigh) {
					stepsHigh = steps;
					stepsHighest = str.toString();
				}
				stepsT += steps;
			}

			System.out.println("loses = " + lose);
			System.out.println("wins = " + win);
			System.out.println("percent win = " + (double) win/trials*100 + "%");
			System.out.println("avg win steps = " + (double) stepsW/win);
			System.out.println("avg lose steps = " + (double) stepsL/lose);
			System.out.println("avg steps = " + (double) stepsT/trials);
			System.out.println("lowest steps = " + stepsLow);
			System.out.println("highest steps = " + stepsHigh);

			System.out.println("\nEnter a 1 to see win logs(" + win + ")");
			System.out.println("Enter a 2 to see extrema logs");
			System.out.println("Enter a 3 to see all logs");
			System.out.println("Enter a 4 to watch win logs(" + win + ")");
			System.out.println("Enter a 5 to watch longest log");
			System.out.println("Enter a 6 to watch both logs");
			System.out.println("Enter a 7 to see everything");
			//System.out.println("Enter anything else to do nothing");

			int answer;
			if (args.length >= 3) {
				answer = Integer.parseInt(args[2]);
			} else {
				answer = scan.nextInt();
			}
			if (answer == 1 || answer == 3 || answer == 7) {
				System.out.println("\nWin log (" + win + ")\n----------------");
				for (int i = 0; i < winLogIndex; i++) {
					if (winLog[i] != null) {
						System.out.println(winLog[i]);
					}
				}
			}
			if (answer == 2 || answer == 3 || answer == 7) {
				System.out.println("\nShortest log (" + stepsLow + ")\n----------------");
				System.out.println(stepsLowest);
				System.out.println("\nHighest log (" + stepsHigh + ")\n----------------");
				System.out.println(stepsHighest);
			}
			if (answer == 4 || answer == 6 || answer == 7) {
				for (int i = 0; i < winLogIndex; i++) {
					if (winLog[i] != null) {
						movesDraw(winLog[i], length);
					}
				}
			}
			if (answer == 5 || answer == 6 || answer == 7) {
				movesDraw(stepsHighest, length);
			}
		} else {
			System.out.println("Enter a positive number");
		}
		// 69 10,000,000 = 0.03103%

	}
}
