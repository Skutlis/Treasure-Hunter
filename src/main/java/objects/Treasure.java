package objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;



public class Treasure extends Item {

    private boolean isCollected;

    /**
     * Initiates a new Treasure
     * @param x - x-position where the item is located on the map
     * @param y - y-position where the item is located on the map
     * @param t - ItemType of the Treasure
     * @param isOnGround - True if the item is initiated on the map, false if it is initiated in inventory
     */
    public Treasure(float x, float y, ItemType t, boolean isOnGround) {
        super(new Vector2(x, y), t, isOnGround);
        isCollected = false;
    }

    /**
     * Updates the Treasure by calling the super.update method
     * @param player - The player of the game
     * @param delta - DeltaTime: Time since last update
     */
    @Override
    public void update(Player player, float delta) {
        super.update(player, delta);
    }

    /**
     * Makes a player pick up the item by adding the item to inventory and
     * giving it coins.
     * @param entity - The player picking the item up
     */
    @Override
    public void pickUp(Entity entity) {
        if (!isCollected && entity instanceof Player){
            ((Player) entity).addCoins(type.getValue());
            entity.inventory.add(this);
            //((Player) entity).hasChest();
            isCollected = true;
        }

    }


    @Override
    public void render(Batch batch) {
        if (!isPickedUp) {
            batch.draw(type.getTexture(), position.x, position.y, type.getWidth(), type.getHeight());
        }
    }
}
