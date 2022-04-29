package screens;

import ScreenHandler.ScreenHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import screens.camera.GameCamera;

public class DeathScreen implements Screen {

    Texture PlayActive;
    Texture PlayInactive;
    Texture ExitActive;
    Texture ExitInactive;
    Texture YouDied;
    public GameCamera camera;

    final ScreenHandler handler;

    private static final int ExitWidth = 250;
    private static final int ExitHeight = 120;
    private static final int PlayWidth = 300;
    private static final int PlayHeight = 120;
    private static final int Exit_Y = 100;
    private static final int Play_Y = 230;
    private static final int YouDied_Y = 400;
    private static final int YouDied_Width = 750;
    private static final int YouDied_Height = 450;

    private String defaultMap = "testmap.tmx";

    /**
     * Constructor for MainMenuScreen
     * Uses method touchdown to check if either Play or Exit is pressed
     * @param handler - The ScreenHandler
     */
    public DeathScreen(final ScreenHandler handler) {
        this.handler = handler;
        PlayActive = new Texture("PlayActive.png");
        PlayInactive = new Texture("PlayInactive.png");
        ExitActive = new Texture("QuitActive.png");
        ExitInactive = new Texture("QuitInactive.png");
        YouDied = new Texture ("YouDied.jpg");

        final DeathScreen deathScreen = this;

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                //Exit button
                int x = ScreenHandler.WIDTH / 2 - ExitWidth / 2;
                if (handler.camera.getScreenInput().x < x + ExitWidth && handler.camera.getScreenInput().x > x && ScreenHandler.HEIGHT - handler.camera.getScreenInput().y < Exit_Y + ExitHeight && handler.HEIGHT - handler.camera.getScreenInput().y > Exit_Y) {
                    deathScreen.dispose();
                    Gdx.app.exit();
                }

                //Play game button
                x = handler.WIDTH / 2 - PlayWidth / 2;
                if (handler.camera.getScreenInput().x < x + PlayWidth && handler.camera.getScreenInput().x > x && handler.HEIGHT - handler.camera.getScreenInput().y < Play_Y + PlayHeight && handler.HEIGHT - handler.camera.getScreenInput().y > Play_Y) {
                    deathScreen.dispose();
                    handler.setScreen(new MainGameScreen(handler, defaultMap));
                }

                return super.touchUp(screenX, screenY, pointer, button);
            }

        });
    }





    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handler.batch.begin();

        int x = handler.WIDTH / 2 - ExitWidth / 2;
        if (handler.camera.getScreenInput().x < x + ExitWidth && handler.camera.getScreenInput().x > x && handler.HEIGHT - handler.camera.getScreenInput().y < Exit_Y + ExitHeight && handler.HEIGHT - handler.camera.getScreenInput().y > Exit_Y) {
            handler.batch.draw(ExitActive, x, Exit_Y, ExitWidth, ExitHeight);
        } else {
            handler.batch.draw(ExitInactive, x, Exit_Y, ExitWidth, ExitHeight);
        }

        x = handler.WIDTH / 2 - PlayWidth / 2;
        if (handler.camera.getScreenInput().x < x + PlayWidth && handler.camera.getScreenInput().x > x && handler.HEIGHT - handler.camera.getScreenInput().y < Play_Y + PlayHeight && handler.HEIGHT - handler.camera.getScreenInput().y > Play_Y) {
            handler.batch.draw(PlayActive, x, Play_Y, PlayWidth, PlayHeight);
        } else {
            handler.batch.draw(PlayInactive, x, Play_Y, PlayWidth, PlayHeight);
        }


        handler.batch.draw(YouDied, handler.WIDTH / 2 - YouDied_Width / 2, YouDied_Y, YouDied_Width, YouDied_Height);



        handler.batch.end();
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
        Gdx.input.setInputProcessor(null);

    }
}
