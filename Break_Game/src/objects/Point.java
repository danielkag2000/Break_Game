package objects;

/**
 * A point in a 2D which represented
 * by two values, x and y in a Cartesian axes.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-03-20
 */
public class Point {

  private double x1;  // the x value
  private double y1;  // the y value

  /**
   * constructor for x, y values that it's gets.
   *
   * @param x the x value
   * @param y the y value
   */
  public Point(double x, double y) {
    this.x1 = x;
    this.y1 = y;
  }

  /**
   * the function calculate the distance
   * between two points and return the distance.
   *
   * @param other
   *             a Point
   * @return the distance of this point to the other point
   */
  public double distance(Point other) {
    // the sum of the (delta x)^2 + (delta y)^2
    double powCalc = Math.pow(this.x1 - other.x1, 2) + Math.pow(this.y1 - other.y1, 2);

    return Math.sqrt(powCalc);
  }

  /**
   * the function check if to points are the same.
   *
   * @param other a point to check equality
   * @return true if it's the same point
   *         else, return false
   */
  public boolean equals(Point other) {
    return ((this.x1 == other.x1) && (this.y1 == other.y1));
  }

  /**
   * get function of this x value.
   *
   * @return the x values of this point
   */
  public double getX() {
    return this.x1;
  }

  /**
   * get function of this y value.
   *
   * @return the y values of this point
   */
  public double getY() {
    return this.y1;
  }

  /**
   * the toString of Point,
   * represent the point as a x and y values on the [X,Y] axes.
   *
   * @return a string from the form of (x,y)
   */
  public String toString() {
    return "(" + this.x1 + "," + this.y1 + ")";
  }
}
