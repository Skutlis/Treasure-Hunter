package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import environment.GameMap;
import environment.TileType;
import logic.CollisionArea;

import java.util.ArrayList;

public class Projectile {

    private float range;
    private int damage;
    private Vector2 position;
    private Vector2 start;
    private boolean exist;
    private GameMap map;
    private ItemType type;
    private Vector2 xySpeed;
    private Entity holder;


    /**
     * Initiates another projectile
     * @param entity - The one shooting the projectile
     * @param range - The maximum range the projectile can travel
     * @param damage - The damage the projectile inflicts
     * @param speed - Speed of the projectile
     * @param map - The map where the projectile is fired
     * @param type - Which itemType the projectile belongs to
     */
    public Projectile(Entity entity, float range, int damage, float speed,
                       GameMap map, ItemType type, Vector2 mouseLoc) {
        this.holder = entity;
        this.position = (new Vector2(entity.getX(), entity.getY()+ TileType.TILE_SIZE*1/2)).cpy();
        this.start = position.cpy();
        this.range = range;
        this.damage = damage;
        this.map = map;
        this.type = type;
        Vector2 copyMouseLoc = mouseLoc.cpy();
        this.exist = true;

        //Creates a vector from player to mouseLocation and sets the length of the vector equal to the speed,
        //such that the (x, y) pair is the speed in x and y direction.
        xySpeed = (new Vector2(copyMouseLoc.x - start.x, copyMouseLoc.y - start.y)).setLength(speed);
    }

    /**
     * Updates the position of the projectile. It cease to exist if it crashes in anything or move to maximum range.
     * @param entities - Array of all entities located on the map
     * @param delta - DeltaTime: time since last update.
     */
    public void update(ArrayList<Entity> entities, float delta) {
        float newX = position.x + xySpeed.x * delta;
        float newY = position.y + xySpeed.y * delta;


        float distance = (new Vector2(newX-start.x, newY-start.y)).len();
        System.out.println("DISTANCE");
        System.out.println(distance);
        System.out.println("RANGE");
        System.out.println(range+start.x);
        //A projectile cease to exist if it moves out of its initial range
        if ((range + start.x) - distance <= 0) {
            exist = false;
            return;
        }
        //The collision area
        CollisionArea colArea = new CollisionArea(newX, newY,
                type.getWidth(), type.getHeight());
        //A projectile cease to exist if it hits an enemy
        for (Entity entity: entities) {
            if (entity == holder) {continue;}
            if (colArea.collidesWith(entity.getCollisionArea())) {
                entity.takesDamage(damage);
                exist = false;
                return;
            }
        }
        //Projectile moves further if it doesn't collide with the map
        if (!map.rectCollidesWithMap(newX, newY, type.getWidth(), type.getHeight())) {
            position.x = newX;
            position.y = newY;
        }
        //A projectile cease to exist if it collides with the map
        else {
            exist = false;
        }
    }

    /**
     * Renders the projectile to the screen if it still exist
     * @param batch - Batch to draw the texture to the screen
     */
    public void render(Batch batch) {
        if (exist){
            batch.draw(type.getTexture(), position.x, position.y, type.getWidth(), type.getHeight());
        }

    }


    /**
     *
     * @return true if the projectile still exist, false otherwise
     */
    public boolean exists() {
        return exist;
    }
}
