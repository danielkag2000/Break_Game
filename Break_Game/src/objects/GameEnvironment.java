package objects;

import interfaces.Collidable;

/**
 * the game environment.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-04-13
 */
public class GameEnvironment {

    // the list of collidable objects
    private java.util.List<Collidable> collisionList;

    /**
     * Create new Game Environment.
     */
    public GameEnvironment() {
        this.collisionList = new java.util.ArrayList<Collidable>();
    }

    /**
     * add the given Collidable to the environment.
     *
     * @param c the collision object
     */
    public void addCollidable(Collidable c) {
        collisionList.add(c);
    }

    /**
     * remove the given Collidable to the environment.
     *
     * @param c the collision object to remove
     */
    public void removeCollidable(Collidable c) {
        collisionList.remove(c);
    }

    /**
     * get the i index object.
     *
     * @param i the index
     * @return the object in the i index
     */
    public Collidable getIndexI(int i) {
        return this.collisionList.get(i);
    }

    /**
     * get the number of objects.
     *
     * @return the number of objects
     */
    public int getSize() {
        return this.collisionList.size();
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * get the information of the closest Collidable object
     * @param trajectory a line
     * @return If this object will not collide with any of the collidables
     *         in this collection, return null. Else, return the information
     *         about the closest collision that is going to occur.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {

        if (collisionList.isEmpty()) {
            return null;
        }

        //Collidable closestCollision = collisionList.get(0);  // the closest Collidable object
        // the closest Collidable Points of the closest Collidable object
        //Point closestPoint = trajectory.closestIntersectionToStartOfLine(closestCollision.getCollisionRectangle());

        Collidable closestCollision = null;
        Point closestPoint = null;
        double minDis = trajectory.length();  // the minimum distance

        for (int i = 0; i < collisionList.size(); i++) {
            Collidable objI = collisionList.get(i);  // the Collidable in i place
            // the closest Collidable Points of the closest Collidable object in i place
            Point objectIPoint = trajectory.closestIntersectionToStartOfLine(objI.getCollisionRectangle());
            double distance = minDis;  // the distance

            if (objectIPoint != null) {  // there is an obstacle
                 distance = objectIPoint.distance(trajectory.start());
            }

            if (objectIPoint != null && minDis >= distance) {

                minDis = distance;
                closestCollision = objI;
                closestPoint = objectIPoint;
            }
        }
        if (closestCollision == null || closestPoint == null) {
            return null;
        }
        return new CollisionInfo(closestCollision, closestPoint);
    }
}
