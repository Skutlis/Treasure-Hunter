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

public abstract class Testing {

    
    // The gameScreen that is going to be used
    static GameScreen gameScreen;

    // Change this variable to load different testing map
    static String mapName = "testmap.tmx";
    // Note: Have to specify path down to src-folder for testing to work
    static String path = "src\\test\\resources\\";

    // Can initialize nonstatic GameMap here or static GameMap inside setUp()
    static GameMap gameMap;
    static Player player;

    static ArrayList<Enemy> enemies;
    

    /**
     * This method sets up LibGDX so that tests depending on it can run as intended
     */
    @BeforeAll
    public static void setUp() {
        gameScreen = new GameScreen(path + mapName);
        
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1920, 1080);
        new Lwjgl3Application(gameScreen, config);

        gameMap = gameScreen.getMap();
        player = gameMap.getPlayer();

        enemies = gameMap.getEnemies();

    } 

    

}
