package objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import environment.GameMap;
import logic.CollisionArea;

import java.util.ArrayList;

public abstract class Entity {
    protected Vector2 position;
    protected EntityType type;
    protected float velocityY = 0;
    protected GameMap map;
    protected boolean grounded = false;
    protected String lastDir;
    protected Animation[] animations;
    protected float stateTime;
    protected static final float ANIMATION_SPEED = 0.25f;

    protected int hp;
    protected int damage;
    protected String name;
    protected CollisionArea collisionArea;
    protected boolean isAlive;
    protected Weapon weapon = null;
    protected ArrayList<Item> inventory = new ArrayList<>();

    /**
     * Constructs an entity to serve as a parent class of all Player and Enemy objects.
     * @param x - the position on the x axis
     * @param y - the position on the y axis
     * @param type - the type of the entity (Player, Enemy)
     * @param map - the GameMap the entity is placed on.
     */
    public Entity(float x, float y, EntityType type, GameMap map){
        this.position = new Vector2(x, y);
        this.type = type;
        this.map = map;
        this.collisionArea = new CollisionArea(this.position.x, this.position.y, getWidth(), getHeight());
        this.isAlive = true;
        this.lastDir = "Right";
        this.inventory = new ArrayList<>();
    }

    /**
     *
     * @param deltaTime - used by gdx to synchronize events.
     * @param gravity - gravity constant to represent the gravitational force.
     */
    public void update(float deltaTime, float gravity, Vector2 mouseLoc){
        float newY = position.y;

        this.velocityY += gravity * deltaTime * getWeight();
        newY += this.velocityY * deltaTime;

        if (map.rectCollidesWithMap(position.x, newY, getWidth(), getHeight())) {
            if (velocityY < 0) {
                this.position.y = (float) Math.floor(position.y);
                grounded = true;
            }
            this.velocityY = 0;
        } else {
            this.position.y = newY;
            grounded = false;
        }
        collisionArea.move(position.x, position.y);
    }


    /**
     * Checks collisions for entity in a certain radius
     * @param amount
     */
    public void moveX (float amount){
        float newX = this.position.x + amount;
        if (!map.rectCollidesWithMap(newX, position.y, getWidth(), getHeight()))
            this.position.x = newX;
    }


    /**
     * Renders the SpriteBatch object.
     * @param batch - the SpriteBatch to render
     */
    public abstract void render (SpriteBatch batch);

    public void dispose(SpriteBatch batch) {
        batch.end();
    }

    public void attack(Entity enemy) {
        enemy.takesDamage(damage);
    }

    /**
     * Controls the amount of damage tne Entity takes. If damage taken
     * is more than total HP, damage is simply scaled down to match HP,
     * so that the Entity HP is reduced to 0.
     *
     * @param damage - the incoming damage the Entity takes.
     * @return total damage taken.
     */
    public int takesDamage (int damage) {
        System.out.println("\n\nDMG: " + damage + "\nHP before: " + hp);
        if (damage >= hp) {
            damage = hp;
            hp = 0;
            kill();
        } else {
            hp -= damage;
        }
        System.out.println("HP after: " + hp);
        return damage;
    }

    /**
     * Equips the entity with a Weapon
     * @param w - The Weapon equipped
     */
    public void equip(Weapon w) {
        if (weapon != null){
            weapon.putDown(this);
        }
        this.weapon = w;
        inventory.add(w);
    }

    /**
     * Unequips the entity's Weapon
     * @param w - The Weapon equipped
     */
    public void unEquip(Weapon w) {
        if (weapon != null){
            weapon.putDown(this);
            weapon = null;
        }
    }

    /**
     * Returns the current weapon of the entity
     *
     * @return Weapon
     */
    public Weapon getCurrentWeapon() {
        if(weapon != null)
            return weapon;
        else
            return null;
    }


    /**
     * Returns the x and y position of the entity
     * @return entity position
     */
    public Vector2 getPosition(){
        return position;
    }


    /**
     * Returns What type of entity the entity is
     * @return entity type
     */
    public EntityType getType(){
        return type;
    }


    /**
     * Returns velocity in the y-axis
     * @return y-velocity
     */
    public float getVelocityY(){
        return velocityY;
    }


    /**
     *Boolean to check if entity is on the ground
     * @return True if entity is grounded, false if not
     */
    public boolean isGrounded(){
        return grounded;
    }


    /**
     * Returns the current x position of the entity
     *
     * @return x position
     */
    public float getX(){
        return position.x;
    }


    /**
     * Returns the current y position of the entity
     *
     * @return y position
     */
    public float getY(){
        return position.y;
    }


    /**
     * Returns the pixel width of the entity
     *
     * @return entity width
     */
    public int getWidth() {
        return type.getWidth();
    }


    /**
     * Returns the pixel height of the entity
     *
     * @return entity height
     */
    public int getHeight(){
        return type.getHeight();
    }


    /**
     * An entity has a weight to make gravity work (gravity will be implemented later)
     *
     * @return entity weight
     */
    public float getWeight(){
        return type.getWeight();
    }

    public String getName() {
        return this.name;
    }

    public CollisionArea getCollisionArea() {
        return collisionArea;
    }

    /**
     * Sets the current position (x,y) of the entity
     *
     * @params x
     * @params y
     */
    public void setPosition(float x, float y){
        position.x = x;
        position.y = y;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void kill() {
        damage = 0;
        isAlive = false;
    }



}
