package screens.camera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameCamera {

    private OrthographicCamera BaseCamera;
    private StretchViewport viewport;

    /**
     * Constructor for GameCamera
     * Constructs a camera with extra methods, that helps get mouse-input
     * @param width
     * @param height
     */
    public GameCamera(int width, int height) {
        BaseCamera = new OrthographicCamera();
        viewport = new StretchViewport(width, height, BaseCamera);
        viewport.apply();
        BaseCamera.position.set(width / 2, height / 2, 0);
        BaseCamera.update();
        }

    public void update (int width, int height) {
        viewport.update(width, height);
    }

    /**
     * Method to get input from mouse when it is on the screen, returns mouse x and y.
     * @return
     *
     */
    public Vector2 getScreenInput() {
        Vector3 inputScreen = new Vector3(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0);
        Vector3 unprojected = BaseCamera.unproject(inputScreen);
        return new Vector2(unprojected.x, unprojected.y);
    }



}
