package logic;

/**
 * Keeps track of an objects current position, and its size.
 * CollisionArea is used to check if certain entities collide,
 * such as Enemy and Player, for proper handling of attacking
 * and receiving damage.
 */
public class CollisionArea {

    float x, y;
    int width, height;

    /**
     *
     * @param x - the x position CollisionArea is initiated to.
     * @param y - the y position CollisionArea is initiated to.
     * @param width - the width of the CollisionArea.
     * @param height - the height of the CollisionArea.
     */
    public CollisionArea(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     *
     * @param x - new x position to move the CollisionArea to.
     * @param y - new y position to move the CollisionArea to.
     */
    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param area - the CollisionArea to compare the current CollisionArea with.
     * @return - true if the two CollisionAreas collide, false otherwise.
     */
    public boolean collidesWith(CollisionArea area) {
        return x < area.x + area.width && y < area.y + area.height && x + width > area.x && y + height > area.y;
    }

}
