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

public class ItemTests extends Testing {


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


}
