import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import environment.GameMap;
import objects.Enemy;
import objects.Player;
import objects.Treasure;
import objects.Weapon;
import screens.GameScreen;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class CollideTests extends Testing {


    /**
     * Test for the method rectCollidesWithMap
     */
    @Test
	public void rectCollidesWithMapTest_withoutPlayerObject() {

        float playerPosX = 100;
        float playerPosY = 100;
        int playerWidthX = 1;
        int playerWidthY = 1;

        assertTrue(gameMap.rectCollidesWithMap(playerPosX, playerPosY, playerWidthX, playerWidthY));
          
	}   

    /**
     * Test for the method rectCollidesWithMap using Player
     */
    @Test
	public void rectCollidesWithMapTest_withPlayerObject() {

        player.setPosition(37f, 320f);

        assertFalse(gameMap.rectCollidesWithMap(player.getX() + 1, player.getY(), player.getHeight(), player.getWidth()));
        assertTrue(gameMap.rectCollidesWithMap(player.getX(), player.getY() - 1, player.getHeight(), player.getWidth()));
        assertFalse(gameMap.rectCollidesWithMap(player.getX() + -1, player.getY(), player.getHeight(), player.getWidth()));
        assertFalse(gameMap.rectCollidesWithMap(player.getX(), player.getY() + 1, player.getHeight(), player.getWidth()));
          
        // x position 1f left of obstacle
        player.setPosition(927f, 320f);
        assertTrue(gameMap.rectCollidesWithMap(player.getX() + 1, player.getY(), player.getHeight(), player.getWidth()));

	}   

    /**
     * Test for weapon pick up
     */
    @Test
	public void pickUpWeaponTest() {

        Weapon weapon = gameMap.getWeapon();
        if(weapon!= null)
            player.equip(weapon);

        assertNotNull(player.getCurrentWeapon());

        player.unEquip(weapon);
        
        assertNull(player.getCurrentWeapon());
          
	}

    /**
     * Test for enemy weapon pick up
     */
    @Test
	public void enemyPickUpWeaponTest() {

        Enemy enemy = enemies.get(0);

        Weapon weapon = gameMap.getWeapon();
        if(weapon!= null)
            enemy.equip(weapon);

        assertNotNull(enemy.getCurrentWeapon());

        enemy.unEquip(weapon);
        
        assertNull(enemy.getCurrentWeapon());
          
	}
    
    /**
     * Test for treasure pick up
     */
    @Test
	public void pickUpTreasureTest() {

        //assertEquals(0, player.getCoinTotal());

        Treasure treasure = gameMap.getTreasure();
        if(treasure!= null)
            treasure.pickUp(player);

        assertEquals(10, player.getCoinTotal());
          
	} 

    /**
     * Test for checking that player movement works
     */
    @Test
	public void playerMoveTest() {
        
        // Choosing x and y values that puts the player at the ground
        float xPos = 37f;
        float yPos = 320f;

        float expectedXPos = 38f;
        
        player.setPosition(xPos,yPos);
        player.moveX(1);
        
        assertEquals(expectedXPos, player.getX());
        
	} 

    /**
     * Test for checking that enemy is alive if not killed and dead if killed
     */
    @Test
	public void enemyKilledTest() { 

        ArrayList<Enemy> enemies = gameMap.getEnemies();
        
        assertTrue(enemies.get(0).isAlive());

        enemies.get(0).kill();
        
        assertFalse(enemies.get(0).isAlive());
        assertTrue(enemies.get(1).isAlive());

	} 

    /**
     * Test for checking that player is dead after losing all health
     */
    @Test
	public void playerKilledTest() { 

        assertTrue(player.isAlive());

        // Player has currently 100 health, 500 damage is overkill
        player.takesDamage(500);

        assertFalse(player.isAlive());
	} 

}
