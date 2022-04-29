import ScreenHandler.ScreenHandler;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import screens.GameScreen;

public class Launcher {

    /**
     * Launcher for the game
     */
    public static void main(String[] args)
    {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1920, 1080);
        new Lwjgl3Application(new ScreenHandler(), config);
    }
}
