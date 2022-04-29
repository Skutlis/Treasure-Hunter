package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import environment.GameMap;
import environment.TileType;

/**
 * Representation of the Enemy in the game. This class decides all information and Textures for Enemy,
 * and controls Enemy movement.
 * It is responsible for correct updating and rendering of the Enemy object.
 */

public class Enemy extends Entity {

    private static final int SPEED = 140;
    private static final int HEALTH = 50;
    private float startPosX;
    private float walkRange = 100f;
    private boolean shouldMoveRight = true;
    private Vector2 playerPos;

    /**
     * Constructs an entity with the several parameters
     *
     * @param x
     * @param y
     * @param map
     */
    public Enemy(float x, float y, GameMap map) {
        super(x, y, EntityType.ENEMY, map);
        name = "Enemy";
        hp = HEALTH;
        damage = 20;
        startPosX = x;
        playerPos = new Vector2();
        animations = new Animation[2];

        TextureRegion[][] spriteSheet = TextureRegion.split(new Texture("GhostEnemyAnimations.png"), 300, 300);

        for (int i = 0; i < animations.length; i++) {
            animations[i] = new Animation<>(ANIMATION_SPEED, spriteSheet[i]);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        if (hp > 0){
            if (lastDir.equals("Right")) {
                batch.draw((TextureRegion) animations[0].getKeyFrame(stateTime, true), position.x, position.y, getWidth(), getHeight());
            } else {
                batch.draw((TextureRegion) animations[1].getKeyFrame(stateTime, true), position.x, position.y, getWidth(), getHeight());
            }
        }

    }

    /**
     * Extention of update method in Entity.
     * Enemy moves back and forth between startPosX and startPosX + walkRange 
     *
     * @param deltaTime - used by gdx to synchronize events.
     * @param gravity - gravity constant to represent the gravitational force.
     */
    public void update(float deltaTime, float gravity, Vector2 mouseLoc) {
        super.update(deltaTime,gravity, mouseLoc);
        move(deltaTime);

    }

    public void updatePlayer(float x, float y) {
        playerPos.x = x;
        playerPos.y = y;
    }

    public void move(float delta) {
        Vector2 toPlayer = new Vector2(position.x - playerPos.x, position.y - playerPos.y);
        float distance = toPlayer.len();
        if (distance > 8*TileType.TILE_SIZE) {
            if(grounded) {
                if(this.getX() > startPosX + walkRange) {
                    shouldMoveRight = false;
                } else if(this.getX() < startPosX) {
                    shouldMoveRight = true;
                }

                if(shouldMoveRight) {
                    lastDir = "Right";
                    moveX(SPEED * delta);
                } else {
                    lastDir = "Left";
                    moveX(-SPEED * delta);
                }
            }
        } else {
            if (weapon != null){
                if (distance < weapon.getRange() && grounded) {
                    weapon.fire(this, map, playerPos.cpy());
                    System.out.println("FIRE!");
                }
            }


            moveX(-(toPlayer.x / Math.abs(toPlayer.x))*SPEED*delta);
        }
    }


}
