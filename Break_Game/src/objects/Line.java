package objects;

/**
 * A line in a 2D which represented
 * by two points, start and end.
 *
 * @author Daniel Kaganovich
 * @version 1.1
 * @since 2018-03-20
 */
public class Line {

  private Point start;  // the start point
  private Point end;  // the first point
  private Double slopeLine;  // the slope of the line
  private Double freeP;  // the intersection value with y axes

  /**
   * constructor for p1, p2 values that it's gets
   * by start to end points.
   *
   * @param start the start point value
   * @param end the end point value
   */
  public Line(Point start, Point end) {
    this.start = start;
    this.end = end;

    if (start.equals(end)) {
      this.slopeLine = null;
      this.freeP = null;

    // if it's a vertical line
    } else if (start.getX() == end.getX()) {
      this.slopeLine = null;
      this.freeP = null;

    }  else {
      this.slopeLine = (start.getY() - end.getY()) / (start.getX() - end.getX());
      this.freeP = start.getY() - this.slopeLine * start.getX();
    }
  }

  /**
   * constructor for p1, p2 values that it's gets
   * the x, y values for each point.
   *
   * @param x1 the x value of the start point
   * @param y1 the y value of the start point
   * @param x2 the x value of the end point
   * @param y2 the y value of the end point
   */
  public Line(double x1, double y1, double x2, double y2) {
    this (new Point(x1, y1), new Point(x2, y2));
  }

  /**
   * the function calculate the distance
   * between the start point to the end point.
   *
   * @return the length of the line
   */
  public double length() {
    return start.distance(end);
  }

  /**
   * the function calculate the middle point of the line
   * and returns it.
   *
   * @return the middle point of the line
   */
  public Point middle() {
    // the x value of the middle point
    double midX = (this.start.getX() + this.end.getX()) / 2.0;
    // the x value of the middle point
    double midY = (this.start.getY() + this.end.getY()) / 2.0;

    return new Point(midX, midY);
  }

  /**
   * get function of this start point.
   *
   * @return the start point of this line
   */
  public Point start() {
    return this.start;
  }

  /**
   * get function of this end point.
   *
   * @return the end point of this line
   */
  public Point end() {
    return this.end;
  }

  /**
   * the function checks if the points are intersecting.
   *
   * @param other a line
   * @return true if the lines intersect, false otherwise
   */
  public boolean isIntersecting(Line other) {
    if  (intersectionWith(other) == null) {
      return false;
    }
    return true;
  }

  /**
   * the function checks and return the intersection point.
   *
   * @param other a line
   * @return the intersection point if the lines intersect,
   *         and null otherwise.
   */
  public Point intersectionWith(Line other) {

    // if at least one of the lines is a point line
    if (this.start.equals(this.end) || other.start.equals(other.end)) {
      return this.intersectionWithForPointLine(other);
    }

    // if both of the lines are vertical
    if (this.slopeLine == null && other.slopeLine == null) {
      return this.intersectionWithWhenBothVerticalLines(other);
    }

    // if one of the lines is vertical
    if (this.slopeLine == null || other.slopeLine == null) {
      return this.intersectionWithWhenOnlyOneLineVertical(other);
    }

    // if the lines are parallel
    if (this.slopeLine == other.slopeLine) {
      return this.intersectionWithWhenParallelLines(other);
    }

    // if the lines are different
    // the x value of the meeting point
    double meetX = (double) (other.freeP - this.freeP) / (this.slopeLine - other.slopeLine);
    // the y value of the meeting point
    double meetY = this.slopeLine * meetX + this.freeP;
    Point meetPoint = new Point(meetX, meetY);  // the meeting point

    if (other.isPointInLine(meetPoint) && this.isPointInLine(meetPoint)) {
      return meetPoint;
    }

    return null;  // there is no intersection point
  }

  /**
   * the function if the lines are the same
   * both in direction and length.
   *
   * @param other a line
   * @return return true is the lines are equal, false otherwise
   */
  public boolean equals(Line other) {
    // check start to start and end to end
    if (this.start.getX() == other.start.getX()
         && this.end.getX() == other.end.getX()) {
      return true;
    }

    // check end to end and start to start
    if (this.end.getX() == other.end.getX()
         && this.start.getX() == other.start.getX()) {
      return true;
    }

    // check start to end and end to start
    if (this.start.getX() == other.end.getX()
         && this.end.getX() == other.start.getX()) {
      return true;
    }

    // check end to start and start to end
    if (this.end.getX() == other.start.getX()
         && this.start.getX() == other.end.getX()) {
      return true;
    }

    return false;
  }

  /**
   * the function checks if a line contains the point.
   *
   * @param p a point
   * @return true if the point in the line,
   *         else, false
   */
  public boolean isPointInLine(Point p) {

    if (start.equals(end)) {
      return p.equals(start);
    }

    if (p.equals(this.start) || p.equals(this.end)) {
        return true;
    }

    if (this.start.getX() <= p.getX() && p.getX() <= this.end.getX()) {

      if (this.start.getY() <= p.getY() && p.getY() <= this.end.getY()) {
        return true;

      } else if (this.end.getY() <= p.getY() && p.getY() <= this.start.getY()) {
          return true;
      }

    } else if (this.end.getX() <= p.getX() && p.getX() <= this.start.getX()) {

        if (this.start.getY() <= p.getY() && p.getY() <= this.end.getY()) {
          return true;

        } else if (this.end.getY() <= p.getY() && p.getY() <= this.start.getY()) {
          return true;
        }
    }

    return false;
  }

  /**
   * the function checks if a line intersect with a line
   * when one of the line is a point.
   *
   * @param other a line
   * @return the intersection point if the lines intersect,
   *         and null otherwise.
   */
  private Point intersectionWithForPointLine(Line other) {
    if (this.start.equals(this.end)) {
      if (other.isPointInLine(this.start)) {
        return new Point(this.start.getX(), this.start.getY());
      }
      return null;
    }

    if (other.start.equals(other.end)) {
      if (this.isPointInLine(other.start)) {
        return new Point(other.start.getX(), other.start.getY());
      }
      return null;
    }
    return null;  // should not get here
  }

  /**
   * the function checks if a line intersect with a line
   * when both of the lines are vertical.
   *
   * @param other a line
   * @return the intersection point if the lines intersect,
   *         and null otherwise.
   */
  private Point intersectionWithWhenBothVerticalLines(Line other) {
    return this.getPointInterectionForSameLines(other);
  }

  /**
   * the function checks if a line intersect with a line
   * when one of the lines is vertical.
   *
   * @param other a line
   * @return the intersection point if the lines intersect,
   *         and null otherwise.
   */
  private Point intersectionWithWhenOnlyOneLineVertical(Line other) {
    double meetingY;  // the y value of the meeting
    Point meetP;  // the meeting point

    if (this.start.getX() == this.end.getX()) {
      meetingY = other.slopeLine * this.start.getX() + other.freeP;
      meetP = new Point(this.start.getX(), meetingY);

      if (this.isPointInLine(meetP) && other.isPointInLine(meetP)) {
        return meetP;
      }
      return null;
    }

    // the other is vertical
    meetingY = this.slopeLine * other.start.getX() + this.freeP;
    meetP = new Point(other.start.getX(), meetingY);

    if (this.isPointInLine(meetP) && other.isPointInLine(meetP)) {
      return meetP;
    }

    return null;
  }

  /**
   * the function checks if a line intersect with a line
   * when the lines are parallel.
   *
   * @param other a line
   * @return the intersection point if the lines intersect,
   *         and null otherwise.
   */
  private Point intersectionWithWhenParallelLines(Line other) {
    if (this.freeP != other.freeP) {
      return null;
    }

    return this.getPointInterectionForSameLines(other);
  }

  /**
   * the function checks if a line intersect with a line
   * when the lines are the same (with no connection to the length).
   *
   * @param other a line
   * @return the intersection point if the lines intersect,
   *         and null otherwise.
   */
  private Point getPointInterectionForSameLines(Line other) {

    // check start to start
    if (this.start.getY() == other.start.getY()) {

      if (other.isPointInLine(this.end)) {
        return null;
      }
      return new Point(this.start.getX(), this.start.getY());
    }

    // check end to end
    if (this.end.getY() == other.end.getY()) {
      if (other.isPointInLine(this.start)) {
        return null;
      }
      return new Point(this.end.getX(), this.end.getY());
    }

    // check start to end
    if (this.start.getY() == other.end.getY()) {
      if (other.isPointInLine(this.end)) {
        return null;
      }
      return new Point(this.start.getX(), this.start.getY());
    }

    // check end to start
    if (this.end.getY() == other.start.getY()) {
      if (other.isPointInLine(this.start)) {
        return null;
      }
      return new Point(this.end.getX(), this.end.getY());
    }

    // if there are no connection point or infinity connection points
    return null;
  }

  /**
   * get the closest intersection point of the rectangle with the line
   * to the start point of the line.
   *
   * @param rect a rectangle
   * @return If this line does not intersect with the rectangle, return null.
   *         Otherwise, return the closest intersection point to the
   *         tart of the line.
   */
  public Point closestIntersectionToStartOfLine(Rectangle rect) {
    java.util.List<Point> intersectionPointsList = rect.intersectionPoints(this);

    if (intersectionPointsList.isEmpty()) {
      return null;
    }

    Point closestPoint = null;  // the closest point
    double minDistace = this.length();  // the minimum distance

    for (int i = 0; i < intersectionPointsList.size(); i++) {
      double dis = this.start.distance(intersectionPointsList.get(i));  // the distance between the points
      if (minDistace >= dis) {
        minDistace = dis;
        closestPoint = intersectionPointsList.get(i);
      }
    }
    return closestPoint;
  }

  /**
   * the toString,
   * represent the line with two Points, start and end.
   *
   * @return a string from the form of ((x1,y1),(x2,y2))
   */
  public String toString() {
    return "(" + this.start + "," + this.end + ")";
  }
}
