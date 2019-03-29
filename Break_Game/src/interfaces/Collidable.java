package interfaces;

import objects.Ball;
import objects.Point;
import objects.Rectangle;
import objects.Velocity;

/**
 * shapes that can be collided with something.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-04-13
 */
public interface Collidable {

    /**
     * get the limits of the collision shape.
     *
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * checks the hit with the object
     * and return its new velocity after the hit.
     *
     * @param collisionPoint the collision Point with the object
     * @param currentVelocity the current Velocity
     * @param hitter the ball that hit
     * @return new velocity expected after the hit
     *         (based on the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
