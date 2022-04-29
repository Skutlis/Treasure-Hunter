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

public class MovementTests extends Testing {


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
     * Test for checking that player movement works
     */
    @Test
	public void playerMoveTest_withColliding() {
        
        // Choosing x and y values that puts the player at the ground
        float xPos = 927f;
        float yPos = 320f;

        float expectedXPos = 927f;
        
        player.setPosition(xPos,yPos);
        
        player.moveX(1);
        
        assertEquals(expectedXPos, player.getX());
        
	} 


}
