// I'll add explanations, quality-of-life changes, and more later
  // For now, just run this command and ctrl c+v the equation to desmos
public class RotatingParabolas {
  public static void printEquation(double angle) {

  }

  // Input directrix of form y = mx + k1 and focal point of coords (xf, yf) to get corresponding parabola
  public static void printEquation(int m, int k1, int xf, int yf) {
    double k2 = (double) (m*yf+xf)/m;
    double xi = m*(k2-k1)/(m*m+1);
    double yi = m*xi+k1;
    double d = Math.sqrt((xf-xi)*(xf-xi)+(yf-yi)*(yf-yi))/2;
    double a = 1/(4*d);
    double theta = Math.acos((yf-yi)/(2*d))*Math.abs(xf-xi)/(xf-xi);
    System.out.println("(y-" + yi + ")*" + Math.cos(theta) + " + (x-" + xi + ")*" + Math.sin(theta) + " = " + a + "((x-" + xi + ")*" + Math.cos(theta) + " - (y-" + yi + ")*" + Math.sin(theta) + ")^2 + " + d);
  }

  public static void main(String[] args) {
    // Corresponds to the
    printEquation(-2, 6, 1, 0);
  }
}
