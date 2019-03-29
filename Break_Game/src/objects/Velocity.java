package objects;

/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-03-23
 */
public class Velocity {

    private double dx;  // the change in position on [X] axis
    private double dy;  // the change in position on [Y] axis

    /**
     * constructor
     * for the change in [XY] axes.
     *
     * @param dx the change in position on [X] axis
     * @param dy the change in position on [Y] axis
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * change the position of a point in dx and dy.
     *
     * @param p a point with position (x, y)
     * @return a new point with position (x+dx, y+dy)
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * get the change in position on [X] axis value of the velocity.
     *
     * @return the change in position on [X] axis
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * get the change in position on [Y] axis value of the velocity.
     *
     * @return the change in position on [Y] axis
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * set the dx value to what the function gets.
     *
     * @param dX the change in position on [X] axis
     */
    public void setDx(double dX) {
        this.dx = dX;
    }

    /**
     * set the dy value to what the function gets.
     *
     * @param dY the change in position on [X] axis
     */
    public void setDy(double dY) {
        this.dy = dY;
    }

    /**
     * change the velocity
     * from angle + speed to change in [XY] axes.
     *
     * @param angle the angle of the velocity vector
     * @param speed the size of the velocity vector
     * @return a new velocity with direction of angle and size of speed
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double angleRad = angle;
        while (angleRad < 0) {  // if the angle is negative
            angleRad += 360;
        }

        angleRad = angle % 360;  // if the angle more than 360

        if (angleRad == 0) {  // if vertical and go up
            return new Velocity(0, -1 * speed);
        }

        if (angleRad == 180) {  // if vertical and go down
            return new Velocity(0, speed);
        }

        if (angleRad == 90) {  // if horizontal and go right
            return new Velocity(speed, 0);
        }

        if (angleRad == 270) {  // if horizontal and go left
            return new Velocity(-1 * speed, 0);
        }

        angleRad = Math.toRadians(angleRad);  // degrees to radians

        // 0 angle is up
        return new Velocity(speed * Math.sin(angleRad), -1 * speed * Math.cos(angleRad));
     }

    /**
     * the toString of Velocity,
     * represent the velosity as a vector with dx, dy values on the [X,Y] axes.
     *
     * @return a string from the form of (dx, dy)
     */
    public String toString() {
        return "(" + this.dx + "," + this.dy + ")";
    }

    /**
     * calculate and return the speed.
     *
     * @return the speed
     */
    public double speed() {
        return Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
    }
}
