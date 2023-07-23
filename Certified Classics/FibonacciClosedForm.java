// In 3Blue1Brown's linear algebra series episode 14 end, he challenges the viewer to solve a problem,
// which (spoiler) ultimately yields a closed-form expression for the Fibonacci series
// I used those methods to make a formula for what I'll call fibonacci-like sequences

public class FibonacciClosedForm {
    public static void printLatexEquation(int a, int b, int s0, int s1) {
        int delta = a * a + 4 * b;
        String sqrt = String.format("\\sqrt{%d}", delta);
        // (a + sqrt) / 2
        String lambda1 = String.format("\\left(\\frac{%d + %s}{2}\\right)", a, sqrt);
        // (a - sqrt) / 2
        String lambda2 = String.format("\\left(\\frac{%d - %s}{2}\\right)", a, sqrt);
        StringBuilder equation = new StringBuilder();
        // eq = n0((-a + sqrt)*lambda1^n + (a + sqrt)*lambda2^n)/(2sqrt) + n1(lambda1^n - lambda2^n)/sqrt
        if (s0 == 0) {
            if (s1 == 0) equation.append("0");
        } else {
            if (s0 != 1) equation.append(s0);
            equation.append(String.format("\\frac{\\left(-%d + %s\\right)%s^n + \\left(%d + %s\\right)%s^n}{2%s}",
                a, sqrt, lambda1, a, sqrt, lambda2, sqrt));
        }
        if (s1 != 0) {
            if (s0 != 0) equation.append(" + ");
            if (s1 != 1) equation.append(s1);
            equation.append(String.format("\\frac{%s^n - %s^n}{%s}", lambda1, lambda2, sqrt));
        }
        System.out.print("f(n) = " + equation);
    }

    public static void main(String[] args) {
        // If s is a "fibonacci-like" sequence, in particular,
        // If s_n = a*s_n-1 + b*s_n-2
        int a = 1;
        int b = 1;
        int s0 = 0;
        int s1 = 1;
        // This gives a closed-form expression for a fibonacci-like sequence with those arguments
        printLatexEquation(a, b, s0, s1);
    }
}
