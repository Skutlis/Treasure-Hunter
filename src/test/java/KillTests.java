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

public class KillTests extends Testing {


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
