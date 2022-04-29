package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import environment.GameMap;
import environment.TileType;

import java.util.ArrayList;

public class Weapon extends Item {
    private final float range;
    private final int damage;
    private final float speed;
    private final long cooldown;
    private long lastShot; //Last shot fired(used to compare to cooldown)

    private Sound fireballSFX; // Fireball sfx

    private ArrayList<Projectile> projectiles; //Active projectiles in the "air" fired from this Weapon

    /**
     * creates a new Weapon
     * @param x - x-position of the Weapon
     * @param y - y-position of the Weapon
     * @param t - itemType
     * @param range - The range of the Weapon
     * @param damage - The damage to the Weapon
     * @param speed - Speed of one projectile (when Weapon fired)
     * @param cooldown - Minimum time between each projectile the Weapon can fire
     * @param isOnGround - True if the item is initiated on the map, false if it is initiated in inventory
     */
    public Weapon(float x, float y, ItemType t, float range, int damage, float speed, long cooldown, boolean isOnGround) {
        super(new Vector2(x, y), t, isOnGround);
        this.range = range;
        this.damage = damage;
        this.speed = speed;
        projectiles = new ArrayList<>();
        this.cooldown = cooldown;
        lastShot = System.currentTimeMillis();

        // Getting fireball audio
        fireballSFX = Gdx.audio.newSound(Gdx.files.internal("fireball2.wav"));
    }

    /**
     * Renders the Weapon to the screen if it is not picked up yet. Also renders all projectiles
     * the Weapon has fired yet(which is none unless it has been picked up at some point)
     * @param batch - The batch which draws the texture to the screen
     */
    @Override
    public void render(Batch batch) {
        if (!isPickedUp) {
            batch.draw(type.getTexture(), position.x, position.y, type.getWidth(), type.getHeight());
        }
        for (Projectile p : projectiles){
            p.render(batch);
        }
    }

    /**
     * Fires the Weapon! Does so by adding another projectile to the projectiles array, which is continuously
     * updated and rendered.
     * @param entity - The player who fired the Weapon
     * @param map - The map of which the Weapon was fired
     */
    public void fire(Entity entity, GameMap map, Vector2 direction) {
        if (System.currentTimeMillis() - lastShot >= cooldown){
            projectiles.add(new Projectile(entity, range, damage, speed,
                    map, type, direction));
            lastShot = System.currentTimeMillis();

            // Play fireball sfx
            long id = fireballSFX.play();
            fireballSFX.setVolume(id, 0.2f);
        }
    }

    /**
     * We only update when the Weapon is picked up. Updates all projectiles which this gun has fired.
     * If a projectile doesn't exist anymore, then we remove it from our projectile list
     * "projectiles".
     * @param player - The player who holds the Weapon
     * @param delta - Deltatime(time since last update)
     */
    public void update(Player player, float delta) {
        super.update(player, delta);
        if (isPickedUp) {
            ArrayList<Entity> entities = player.map.getEntities();
            ArrayList<Projectile> toRemove = new ArrayList<>();

            for (Projectile p : projectiles) {
                if (p.exists()) {
                    p.update(entities, delta);
                } else {
                    toRemove.add(p);
                }
            }
            projectiles.removeAll(toRemove);
        }
    }

    /**
     * Makes a player equip the Weapon.
     * @param entity - The player which equips the Weapon
     */
    @Override
    public void pickUp(Entity entity) {
        entity.equip(this);
        isPickedUp = true;
    }

    public void forcePickedUp() {
        isPickedUp = true;
    }




    public float getRange() {
        return range;
    }
}
