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
    double sin = Math.sin(theta);
    double cos = Math.cos(theta);
    double c1 = a*cos*cos;
    double c2 = -2*a*sin*cos;
    double c3 = a*sin*sin;
    double c4 = -sin + 2*a*(-xi*cos*cos + yi*sin*cos);
    double c5 = -cos + 2*a*(xi*sin*cos - yi*sin*sin);
    double c6 = d + yi*cos + xi*sin + a*(Math.pow(xi*cos - yi*sin, 2));
    System.out.printf("%fx^2 + %fxy + %fy^2 + %fx + %fy + %f = 0\n", c1, c2, c3, c4, c5, c6);
  }

  public static void main(String[] args) {
    printEquation(-2, 6, 1, 0);
  }
}
