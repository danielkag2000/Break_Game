package objects;

import interfaces.Collidable;

/**
 * the information class of the Collision.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-04-13
 */
public class CollisionInfo {

    private Point collisionP;  // the collision Point
    private Collidable collisionO;  // the collision Object

    /**
     * the constructor of the CollisionInfo.
     *
     * @param collisionObj the collision Object
     * @param collisionP the collision Point
     */
    public CollisionInfo(Collidable collisionObj, Point collisionP) {
        this.collisionO = collisionObj;
        this.collisionP = collisionP;
    }

    /**
     * get the collision Point.
     *
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionP;
    }

    /**
     * get the collision Point.
     *
     * @return the point at which the collision occurs.
     */
    public Collidable collisionObject() {
        return this.collisionO;
    }
}
