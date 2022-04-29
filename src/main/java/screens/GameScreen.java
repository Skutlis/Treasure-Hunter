package screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import environment.GameMap;
import environment.TestMap;
import environment.TiledGameMap;

import static environment.TileType.TILE_SIZE;

public class GameScreen extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private GameMap gameMap;
    float deltaX, deltaY;

    private String mapName;


    /**
     * Constructor for GameScreen specifying which 
     * mapName to use when creating TiledGameMap
     */
    public GameScreen(String mapName) {
        if (mapName.length() > 0){
            this.mapName = mapName;
        }

    }

    /**
     * This method initiates the GameScreen. Here we create all resources needed to run the game.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        if(mapName == null || mapName.isBlank()) { 
            gameMap = new TestMap();
            gameMap.load();
        } else {
            gameMap = new TestMap();
            gameMap.load();
        }
    }

    @Override
    public void resize(int i, int i1) {

    }

    // This method is called to get gameMap when testing functions in the game
    public GameMap getMap() {
        return gameMap;
    }

    /**
     * This method is called continuously throughout the game. In render, all the visuals
     * are updated and then rewritten to the screen according to the user input.
     */
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        camera.position.set(gameMap.player.getX(), gameMap.player.getY(), 0);
        camera.update();
        gameMap.update(Gdx.graphics.getDeltaTime(), new Vector2());
        gameMap.render(camera, batch);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    /**
     * Destroys all resources created in the create method.
     */
    @Override
    public void dispose() {
        batch.dispose();
        gameMap.dispose();
    }
}