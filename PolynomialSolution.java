import java.util.ArrayList;

// Put in some numbers, and it will give you a polynomial with y = those numbers for x = 0, 1, 2...
    // For example, {0, 1, 4, 9} gives y = x^2
    // Not just any polynomial mind you, but the simplest one
// As long as you don't go too ham on the numbers, it should be fine
// More info at the bottom; I don't wanna clutter up the top too much
public class PolynomialSolution {
    // Gets the greatest common factor for two numbers
    public static long gcf(long a, long b) {
        // Math.min better but troublesome for 0 and negative numbers
        for (long i = Math.max(a,b); i > 0; i--) {
            if (a % i == 0 && b % i == 0) {
                return i;
            }
        }
        return -1;
    }

    // Gets the greatest common factor for an array
    public static long gcf(long[] numbers) {
        l1: for (long i = maxAbs(numbers); i > 0; i--) {
            for (int j = 0; j < numbers.length; j++) {
                if (numbers[j] % i != 0) {
                    continue l1;
                }
            }
            return i;
        }
        return -1;
    }

    // In case you're wondering, I made this term up
    public static long[] ultimateDerivative(long[] numbers) {
        ArrayList<Long> numbers1 = new ArrayList<Long>();
        ArrayList<Long> numbers2 = new ArrayList<Long>();
        // Number 1 is the actual value, number 2 is how many steps it took
        long[] answer = new long[2];
        for (int i = 0; i < numbers.length; i++) {
            numbers1.add(numbers[i]);
        }
        for (int i = 0; i < numbers.length; i++) {
            if (i % 2 == 0) {
                if (allTheSame(numbers1)) {
                    answer[0] = numbers1.get(0);
                    answer[1] = i;
                    return answer;
                }
                numbers2 = differences(numbers1);
            } else {
                if (allTheSame(numbers2)) {
                    answer[0] = numbers2.get(0);
                    answer[1] = i;
                    return answer;
                }
                numbers1 = differences(numbers2);
            }
        }
        answer[0] = -1;
        answer[1] = 1;
        return answer;
    }

    // Gets differences between consecutives numbers
    public static ArrayList<Long> differences(ArrayList<Long> numbers) {
        ArrayList<Long> numbers1 = new ArrayList<Long>();
        for (int i = 1; i < numbers.size(); i++) {
            numbers1.add(numbers.get(i) - numbers.get(i - 1));
        }
        return numbers1;
    }

    // Returns true if all the numbers are the same, false otherwise
    public static boolean allTheSame(ArrayList<Long> numbers) {
        for (int i = 1; i < numbers.size(); i++) {
            if (!numbers.get(i).equals(numbers.get(i - 1))) {
                return false;
            }
        }
        return true;
    }

    // Gets factorial of a number
    public static long factorial(int n) {
        if (n == 0) {
            return 1;
        }
        return n*factorial(n - 1);
    }

    // Gets coefficients of polynomial
    public static long[] getCoefficients(long[] numbers) {
        long[] newNumbers = new long[numbers.length];
        long[] newNumbers2 = new long[numbers.length];
        long[] coefficients = new long[numbers.length];
        long[] ultDerive;
        int steps;
        int totalSteps = (int) ultimateDerivative(numbers)[1];
        long denominator = factorial(totalSteps);
        for (int i = 0; i < numbers.length; i++) {
            newNumbers[i] = numbers[i] * denominator;
        }
        for (int i = 0; i <= totalSteps; i++) {
            if (i % 2 == 0) {
                ultDerive = ultimateDerivative(newNumbers);
                steps = (int) ultDerive[1];
                coefficients[steps] = ultDerive[0] / factorial(steps);
                for (int j = 0; j < numbers.length; j++) {
                    newNumbers2[j] = newNumbers[j] - coefficients[steps] * (int) Math.pow(j, steps);
                }
            } else {
                ultDerive = ultimateDerivative(newNumbers2);
                steps = (int) ultDerive[1];
                coefficients[steps] = ultDerive[0] / factorial(steps);
                for (int j = 0; j < numbers.length; j++) {
                    newNumbers[j] = newNumbers2[j] - coefficients[steps] * (int) Math.pow(j, steps);
                }
            }
            if (steps == 0) {
                break;
            }
        }
        return coefficients;
    }

    // Don't look at this, this is embarrassing
    public static void printPolynomial(long[] numbers) {
        // StringBuilder used to print polynomial all at once
            // Also, it's more efficient at appending Strings
        StringBuilder polynomial = new StringBuilder();
        long[] coefficients = getCoefficients(numbers);
        long gcf = gcf(coefficients);
        long denominator = factorial((int) ultimateDerivative(numbers)[1]);
        if (gcf > 1 && denominator % gcf == 0) {
            denominator /= gcf;
            for (int i = 0; i < coefficients.length; i++) {
                coefficients[i] /= gcf;
            }
        }
        ArrayList<Integer> indices = nonZeroIndices(coefficients);
        polynomial.append("y = ");
        if (indices.isEmpty()) {
            polynomial.append("0");
            return;
        }
        if (denominator != 1) {
            polynomial.append("(");
        }
        for (int i = indices.size() - 1; i >= 0; i--) {
            if (indices.get(i) == 0) {
                polynomial.append(coefficients[indices.get(i)]);
            } else {
                if (coefficients[indices.get(i)] != 1) {
                    if (coefficients[indices.get(i)] == -1) {
                        polynomial.append("-");
                    } else {
                        polynomial.append(coefficients[indices.get(i)]);
                    }
                }
            }
            if (indices.get(i) == 1) {
                polynomial.append("x");
            } else if (indices.get(i) != 0) {
                polynomial.append("x^");
                polynomial.append(indices.get(i));
            }
            if (i != 0) {
                if (coefficients[indices.get(i-1)] < 0) {
                    coefficients[indices.get(i-1)] *= -1;
                    polynomial.append(" - ");
                } else {
                    polynomial.append(" + ");
                }
            }
        }
        if (denominator != 1) {
            polynomial.append(") / ");
            polynomial.append(denominator);
        }
        System.out.println(polynomial);
    }

    // Avoids 3x^2 + 5x + 0 becoming 3x^2 + 5x +
    public static ArrayList<Integer> nonZeroIndices(long[] numbers) {
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] != 0) {
                indices.add(i);
            }
        }
        return indices;
    }

    // Gets max of absolute value of numbers
    public static long maxAbs(long[] numbers) {
        long maxAbs = Math.abs(numbers[0]);
        for (int i = 1; i < numbers.length; i++) {
            if (Math.abs(numbers[i]) > maxAbs) {
                maxAbs = Math.abs(numbers[i]);
            }
        }
        return maxAbs;
    }

    // Prints numbers in array
    public static void print(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
    }

    // Prints numbers in arraylist
    public static void print(ArrayList<Integer> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            System.out.print(numbers.get(i) + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        long[] numbers = {93, 884, 313, 611};
        printPolynomial(numbers);
    }
}

// Where did this come from: Me
    // Someone else likely discovered this earlier though; I wouldn't know
// How this works: x^n derived = n*x^(n-1)
    // Repeating until exponent = 0, it becomes n!
    // Some polynomial a*x^n + bx^(n-1) ultimately derivates into a*n!
    // a is found, a*x^n is removed from starting numbers, and process repeated to find rest of polynomial
// When is this useful: Never really, it's just kinda cool
    // Most numbers irl are like linear or exponential but not polynomials of order 3-5 which this would actually be useful for
    // Held back by x having to be 0, 1, 2...
        // Linear x can be substituted though
        // So like you could do {0, 1, 4, 9} -> y = x^2 for x = 0, 1, 2... <=> y = (-1/2x+4)^2 for x = 8, 6, 4... if you really want
            // x -> -1/2x + 4   y = -1/2x + 4   x = -2(y-4)   0, 1, 2... -> 8, 6, 4...
    // Doing approximations with this is impractical since these polynomials diverge extremely quickly
