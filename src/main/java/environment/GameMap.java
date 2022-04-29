package environment;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import objects.*;

import java.util.ArrayList;

import static environment.TileType.TILE_SIZE;

/**
 * This class is responsible for creating and maintaining the TiledGameMap
 * object based on the map file.
 * It makes it possible to identify the TileTypes based on location,
 * and get certain attributes of these tiles.
 */
public abstract class GameMap {

    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;

    protected ArrayList<Entity> entities;
    protected ArrayList<Enemy> enemies;
    protected ArrayList<Item> items;

    public Player player;


    /**
     * Initiates entities which will be used in the game. Creates a list for both
     * player and enemy which will make it easier to update them throughout the game.
     */
    public GameMap () {
        enemies = new ArrayList<>();
        entities = new ArrayList<>();
        items = new ArrayList<>();
    }

    /**
     * Sets the view for the camera, and renders the TiledGameMap.
     *
     * @param camera - the camera to show the chosen area of the map.
     */
    public void render(OrthographicCamera camera, SpriteBatch batch) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Item item : items) {
            item.render(batch);
        }
        for (Entity entity : entities) {
            entity.render(batch);
        }
        player.render(batch);
        batch.end();

    }

    /**
     * updates all entities in the entity array.
     *
     * @param delta - change in time since last update
     */
    public void update (float delta, Vector2 mouseLoc) {
        ArrayList<Enemy> enemiesCopy = (ArrayList<Enemy>) enemies.clone();
        player.update(delta, -9.8f, mouseLoc);
        for (Enemy enemy : enemiesCopy) {
            enemy.updatePlayer(player.getX(), player.getY());
            enemy.update(delta, -9.8f, mouseLoc);
            if (!enemy.isAlive()) {
                enemies.remove(enemy);
                entities.remove(enemy);
            }
        }

        for (Item item : items) {
            item.update(player, delta);
        }
    }
    public void dispose () {
        tiledMap.dispose();
    }

    /**
     *
     * @param layer - In which layer of the map is the tile located
     * @param x - x-position of the map
     * @param y - y-position of the map
     * @return The type of the tile found at location: (layer) (x, y)
     */
    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return this.getTileTypeByCoordinate(layer, (int) (x / TILE_SIZE),
                (int) (y / TILE_SIZE));
    }


    /**
     * Checks if a given rectangle is collides with a tile in map.
     *
     * @param x - x-position of the rectangle
     * @param y - y-position of the rectangle
     * @param width - width of the rectangle
     * @param height - height of the rectangle
     * @return - True if the rectangle collides with a tile. False otherwise.
     */
    public boolean rectCollidesWithMap(float x, float y, int width, int height){
        if (x < 0 || y < 0 || x + width > getPixelWidth() || y + height > getPixelHeight() ){
            return true;
        }
        for (int row = (int) (y / TILE_SIZE); row < Math.ceil((y + height) / TILE_SIZE); row++){
            for (int col = (int) (x / TILE_SIZE); col < Math.ceil((x + width) / TILE_SIZE); col++){
                for (int layer = 0; layer < getLayers(); layer++){
                    TileType type = getTileTypeByCoordinate(layer, col, row);
                    if (type != null) {System.out.println(type.name());}
                    if (type != null && type.isCollidable()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Finds TileType of an object when given a coordinate
     * @param layer - specifies the layer to get the TileType from.
     * @param col - the column where the TileType is located.
     * @param row - the row where the TileType is located.
     * @return id of tiletype at the given coordinate
     */
    public TileType getTileTypeByCoordinate(int layer, int col, int row) {
        TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col, row);

        if (cell != null) {
            TiledMapTile tile = cell.getTile();
            System.out.println(tile);
            if (tile != null) {
                int id = tile.getId();
                return TileType.getTileTypeById(id);
            }
        }
        return null;
    }


    public int getWidth () {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
    }


    public int getHeight() {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
    }


    public int getLayers() {
        return tiledMap.getLayers().getCount();
    }


    /**
     *
     * @return width of the map in pixels
     */
    public int getPixelWidth(){
        return this.getWidth() * TILE_SIZE;
    }

    /**
     *
     * @return the height of the map in pixels
     */
    public int getPixelHeight(){
        return this.getHeight() * TILE_SIZE;
    }

    /**
     *
     * @return returns the array of entities
     */
    public ArrayList getEntities() {
        return entities;
    }

    /**
     *
     * @return returns the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return returns the array of enemies
     */
    public ArrayList<Enemy> getEnemies() {
        return (ArrayList<Enemy>) enemies.clone();
    }

    /**
     *
     * @return returns the the first weapon found in the scene
     */
    public Weapon getWeapon() {
        for (Item item : items) {
            if(item instanceof Weapon) {
                return (Weapon)item;
            }
        }

        return null;

    }

    /**
     *
     * @return returns the first treasure found in the scene
     */
    public Treasure getTreasure() {
        for (Item item : items) {
            if(item instanceof Treasure) {
                return (Treasure)item;
            }
        }

        return null;

    }

    public String gameStatus() {
        int PlayerY = (int) player.getPosition().y;
        if (!player.isAlive()|| (PlayerY == 0)) {
            return "Game over";
        } else if (enemies.isEmpty() && player.hasChest()) {
            return "Stage complete";
        }
        return "Game not over";
    }


    public abstract void initPlayer();

    public abstract void initEnemies();

    public abstract void initItems();

    public abstract void initMap();

    public void load() {
        initMap();
        initPlayer();
        initEnemies();
        initItems();
    }
}
