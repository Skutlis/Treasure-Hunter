package screens;

import ScreenHandler.ScreenHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import environment.*;

import java.util.ArrayList;
import java.util.logging.Handler;

public class MainGameScreen implements Screen {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private GameMap gameMap;
    float deltaX, deltaY;
    ScreenHandler Handler;
    private String extraMap = "testmap3.tmx";
    Vector3 mouseInWorld3D = new Vector3();
    Vector2 mouseInWorld2D = new Vector2();

    private ArrayList<GameMap> maps;
    private int mapNr;

    /**
     * Constructor for MainGameScreen
     * Creates a new camera and gamemap
     * @param Handler
     * @param map
     */
    public MainGameScreen(ScreenHandler Handler, String map){
        this.Handler = Handler;

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        //gameMap = new TiledGameMap();

        maps = new ArrayList<>();
        maps.add(new DeathMap());
        maps.add(new TiledGameMap());



        gameMap = maps.get(0);
        gameMap.load();

        mapNr = 0;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        if (gameMap.gameStatus().equals("Stage complete")) {
            System.out.println("Stage complete! You found the secret Spatula and killed all the enemies");
            //Handler.setScreen(new MainGameScreen(Handler, extraMap));
            nextMap();
        }


        camera.position.set(gameMap.player.getX(), gameMap.player.getY(), 0);
        camera.update();

        //Gets position of mouse on screen and projects it such that it
        //starts in the center of the camera position(which is centered on the player)
        mouseInWorld3D.x = Gdx.input.getX();
        mouseInWorld3D.y = Gdx.input.getY();
        mouseInWorld3D.z = 0;
        camera.unproject(mouseInWorld3D);
        mouseInWorld2D.x = mouseInWorld3D.x;
        mouseInWorld2D.y = mouseInWorld3D.y;

        gameMap.update(Gdx.graphics.getDeltaTime(), mouseInWorld2D);
        gameMap.render(camera, batch);


        if (gameMap.gameStatus().equals("Game over")){
            Handler.setScreen(new DeathScreen(Handler));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        gameMap.dispose();

    }

    public void nextMap() {
        gameMap.dispose();
        mapNr++;
        if (maps.size() - 1 >= mapNr) {
            gameMap = maps.get(mapNr);
            gameMap.load();
        }
    }
}
