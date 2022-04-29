package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import logic.CollisionArea;

public abstract class Item {
    protected Vector2 position;
    protected ItemType type;
    protected boolean isPickedUp;
    protected CollisionArea collisionArea;
    protected long timeSincePutDown;
    protected String name;

    private Sound pickupSFX; // Pickup sfx

    /**
     * Creates another item instance.
     * @param pos - Position of the item in question
     * @param t - Type of the item
     * @param isOnGround - True if the item is initiated on the map, false if it is initiated in inventory
     */
    public Item(Vector2 pos, ItemType t, boolean isOnGround) {
        this.position = pos;
        this.type = t;
        this.name = t.name();
        this.isPickedUp = !isOnGround;
        this.collisionArea = new CollisionArea(pos.x, pos.y, t.getWidth(), t.getHeight());
        timeSincePutDown = System.currentTimeMillis();

        // Getting pickup audio
        pickupSFX = Gdx.audio.newSound(Gdx.files.internal("pickup.wav"));
    }


    /**
     * Calculates if a given player collides with the item, which results in a pickup of the item
     * @param player
     * @param delta
     */
    public void update(Player player, float delta){
        //Pick up if a player collides and it is at least 1 sec since the item was put down.
        if (!isPickedUp && player.getCollisionArea().collidesWith(collisionArea) && System.currentTimeMillis() - timeSincePutDown > 1000){
            isPickedUp = true;
            pickUp(player);

            // Play pickup sfx
            long id = pickupSFX.play();
            pickupSFX.setVolume(id, 0.15f);
        }
    }

    /**
     * Method which decides what happens with the item when it is picked up
     * @param entity - The player picking the item up
     */
    public abstract void pickUp(Entity entity);


    /**
     * Puts a Weapon back on the ground
     * @param entity - Player which puts the Weapon down
     */
    public void putDown(Entity entity) {
        position.x = entity.getX();
        position.y = entity.getY();
        collisionArea.move(position.x, position.y);
        isPickedUp = false;
    }

    /**
     * Renders a given item to the screen
     * @param batch - Batch which writes the texture to the screen
     */
    public abstract void render(Batch batch);


}
