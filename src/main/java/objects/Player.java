package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Vector2;
import environment.GameMap;

import java.util.ArrayList;

/**
 * Representation of the Player in the game. This class decides all information and Textures for Player,
 * and controls Player movement and damage.
 * It is responsible for correct updating and rendering of the Player object.
 */
public class Player extends Entity {

    private static final int SPEED = 300;
    private static final int JUMP_VELOCITY = 4;
    private static final int HEALTH = 100;
    private Texture healthBar;
    final float DMG_INTERVAL = .5f;
    float dmg_time = 0;
    private int coins;

    public Player (float x, float y, GameMap map) {
        super(x, y, EntityType.PLAYER, map);
        name = "Player";
        hp = HEALTH;
        damage = 15;
        healthBar = new Texture("healthbar.png");
        coins = 0;

        animations = new Animation[6];

        TextureRegion[][] spriteSheet = TextureRegion.split(new Texture("PlayerAnimations.png"), 300, 300);

        for (int i = 0; i < animations.length; i++) {
            animations[i] = new Animation<>(ANIMATION_SPEED, spriteSheet[i]);
        }
    }
    

    /**
     * Extention of update method in Entity.
     * Player has its own set of rules for movement on the map, based on
     * keyboard input.
     *
     * @param deltaTime - used by gdx to synchronize events.
     * @param gravity - gravity constant to represent the gravitational force.
     */
    public void update(float deltaTime, float gravity, Vector2 mouseLoc) {

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && grounded) {
            this.velocityY += JUMP_VELOCITY * getWeight();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !grounded && this.velocityY > 0) {
            this.velocityY += JUMP_VELOCITY * getWeight()* deltaTime;
        }

        super.update(deltaTime,gravity, mouseLoc);

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            lastDir = "Left";
            moveX(-SPEED * deltaTime);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            lastDir = "Right";
            moveX(SPEED * deltaTime);
        }

        // For each DMG_INTERVAL Player is colliding with an Enemy object, Player takes damage.
        dmg_time += Gdx.graphics.getDeltaTime();
        if (dmg_time > DMG_INTERVAL) {
            collidesWithEnemy();
            dmg_time -= DMG_INTERVAL;
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            if (weapon != null){
                weapon.fire(this, map, mouseLoc);
            }
        }
    }


    @Override
    public void render(SpriteBatch batch) {
        drawHealthBar(batch);
        drawPoints(batch);

        if (hp > 60)
            batch.setColor(Color.GREEN);
        else if (hp > 30)
            batch.setColor(Color.ORANGE);
        else
            batch.setColor(Color.RED);

        batch.draw(healthBar, position.x-900, position.y+450,  hp*4, 15);

        batch.setColor(Color.WHITE);


        stateTime += Gdx.graphics.getDeltaTime(); //delta;

        if (this.velocityY != 0) {
            if (lastDir.equals("Right")) {
                batch.draw((TextureRegion) animations[4].getKeyFrame(stateTime, true), position.x, position.y, getWidth(), getHeight());
            } else {
                batch.draw((TextureRegion) animations[5].getKeyFrame(stateTime, true), position.x, position.y, getWidth(), getHeight());
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            batch.draw((TextureRegion)animations[1].getKeyFrame(stateTime, true), position.x, position.y, getWidth(),getHeight());
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            batch.draw((TextureRegion)animations[0].getKeyFrame(stateTime, true), position.x, position.y, getWidth(),getHeight());
        }
        else {
            if (lastDir.equals("Right")) {
                batch.draw((TextureRegion)animations[2].getKeyFrame(stateTime, true), position.x, position.y, getWidth(),getHeight());
            } else {
                batch.draw((TextureRegion)animations[3].getKeyFrame(stateTime, true), position.x, position.y, getWidth(),getHeight());
            }
        }


    }

    /**
     * Draws the Player health bar, relative to Player position on the map.
     *
     * @param batch - the Player SpriteBatch to draw the health bar on.
     */
    public void drawHealthBar(SpriteBatch batch) {
        batch.setColor(Color.BLACK);
        BitmapFont font;
        CharSequence str = ("HP:");
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.draw(batch,str,position.x - 900-40,position.y+465);
        batch.draw(healthBar, position.x - 900-5, position.y+450-5, hp * 4+10, 15+10);
        batch.setColor(Color.WHITE);

    }

    /**
     * Draws the Player's points, relative to Player position on the map.
     * @param batch
     */
    public void drawPoints(SpriteBatch batch) {
        BitmapFont font;
        CharSequence str = ("Total coins: " + coins);
        font = new BitmapFont();
        font.setColor(Color.GOLD);
        font.draw(batch,str,position.x - 900-5,position.y+450-20);


    }



    /**
     * Loops through Enemy objects on the map, checking if CollisionArea of Player
     * collides with CollisionArea of any Enemy. Player takes damage if they collide.
     *
     * @return true if collision with Enemy is detected, false otherwise.
     */
    public boolean collidesWithEnemy() {
        ArrayList<Entity> entities = map.getEntities();
        for (Entity entity : entities) {
            if (entity.getName().equals("Enemy") && collisionArea.collidesWith(entity.getCollisionArea())) {
                takesDamage(entity.damage);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a given amount to the total coin amount
     * @param amount -Amount of coins given
     */
    public void addCoins(int amount) {
        coins += amount;
        System.out.println("Total coins: " + coins);
    }

    /**
     * Gets current coin amount
     * @return the Player's coin amount
     */
    public int getCoinTotal() {
        return coins;
    }

    public boolean hasChest() {
        for (Item item : inventory) {
            if (item.name.equals("CHEST")) {
                return true;
            }
        }
        return false;
    }
}
