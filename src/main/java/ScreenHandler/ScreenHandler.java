package ScreenHandler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.lwjgl.opengl.GL20;
import screens.MainMenuScreen;
import screens.camera.GameCamera;


public class ScreenHandler extends Game {


    public SpriteBatch batch;
    public GameCamera camera;
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;


    @Override
    public void create() {
        camera = new GameCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        this.setScreen(new MainMenuScreen(this));


    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update(WIDTH,HEIGHT);
        super.render();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.setScreen(new MainMenuScreen(this));
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        camera.update(WIDTH,HEIGHT);
        super.resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}



