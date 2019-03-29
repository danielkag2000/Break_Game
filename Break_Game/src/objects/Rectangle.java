package objects;

/**
 * A rectangle in a 2D which represented
 * by two vertical line and two horizontal lines.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-04-13
 */
public class Rectangle {

    /* the lines of the rectangle:
    * line 0: the upper horizontal line
    * line 1: the right vertical line
    * line 2: the down horizontal line
    * line 3: the left vertical line */
    private Line[] lineArr = new Line[4];
    private double width;  // the width of the rectangle
    private double height;  // the height of the rectangle

    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft the upper left point of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.width = width;
        this.height = height;
        // the upper horizontal line
        lineArr[0] = new Line(upperLeft, new Point(upperLeft.getX() + width, upperLeft.getY()));
        // the right vertical line
        lineArr[1] = new Line(new Point(upperLeft.getX() + width, upperLeft.getY()),
                              new Point(upperLeft.getX() + width, upperLeft.getY() + height));
        // the down horizontal line
        lineArr[2] = new Line(new Point(upperLeft.getX(), upperLeft.getY() + height),
                              new Point(upperLeft.getX() + width, upperLeft.getY() + height));
        // the left vertical line
        lineArr[3] = new Line(upperLeft, new Point(upperLeft.getX(), upperLeft.getY() + height));
    }

    /**
     * check and returns all the intersection Points
     * with the rectangle.
     *
     * @param line a line
     * @return a (possibly empty) List of intersection points
     *         with the specified line.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        // the list of the the intersection points
        java.util.List<Point> intersectionPointsList = new java.util.ArrayList<Point>();

        for (int i = 0; i < lineArr.length; i++) {
            Point intersectionPoint = lineArr[i].intersectionWith(line);

            if (intersectionPoint != null) {  // if there is an intersection point
                intersectionPointsList.add(intersectionPoint);
            }
        }
        return intersectionPointsList;
    }

    /**
     * get function of this width value.
     *
     * @return the width values of this rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * get function of this height value.
     *
     * @return the height values of this rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * get function of this UpperLeft point.
     *
     * @return the UpperLeft point of this rectangle
     */
    public Point getUpperLeft() {
        return this.lineArr[0].start();
    }

    /**
     * get function of this lines Array.
     *
     * @return the lines Array of this rectangle
     */
    public Line[] getLineArr() {
        return this.lineArr;
    }

    /**
     * chacks if the point in the rectangle.
     *
     * @param p the point to check
     * @return true, if the point in the rectangle
     */
    public boolean inPointInRec(Point p) {
        double pX = p.getX();
        double pY = p.getY();

        double recXStart = this.lineArr[0].start().getX();
        double recYStart = this.lineArr[0].start().getY();
        double recXEnd = this.lineArr[1].end().getX();
        double recYEnd = this.lineArr[1].end().getY();

        if ((recXStart <= pX && pX <= recXEnd) && (recYStart <= pY && pY <= recYEnd)) {
            return true;
        }
        return false;
    }

    /**
     * the toString,
     * represent the rectangle with four Lines.
     *
     * @return a string from the form of (line1,lin2,...)
     */
    public String toString() {
        String s = "";

        s += "[";
        for (int i = 0; i < 4; i++) {
            if (i == 3) {
                s += this.lineArr[3];
            } else {
                s += this.lineArr[i] + ",";
            }
        }
        s += "]";
        return s;
    }
}
