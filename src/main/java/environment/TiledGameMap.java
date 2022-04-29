package environment;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import objects.*;

import java.util.ArrayList;

import static environment.TileType.TILE_SIZE;

/**
 * This class is responsible for updating the map and finding what
 * TileTypes are where.
 */
public class TiledGameMap extends GameMap {


    @Override
    public void initPlayer() {
        player = new Player(TILE_SIZE + 5, TILE_SIZE * 20, this);
        entities.add(player);
    }

    @Override
    public void initEnemies() {
        Enemy enemy1 = new Enemy(TILE_SIZE + 400, TILE_SIZE * 20, this);
        Weapon weapon1 = new Weapon(enemy1.getX(), 11f*TILE_SIZE , ItemType.FIREBALL, 5f*TILE_SIZE, 10, 10*TILE_SIZE, 400, false);
        enemy1.equip(weapon1);

        Enemy enemy2 = new Enemy(TILE_SIZE + 600, TILE_SIZE * 20, this);
        Weapon weapon2 = new Weapon(enemy2.getX(), 11f*TILE_SIZE , ItemType.FIREBALL, 5f*TILE_SIZE, 10, 10*TILE_SIZE, 400, false);
        enemy2.equip(weapon2);

        enemies.add(enemy1);
        enemies.add(enemy2);

        entities.add(enemy1);
        entities.add(enemy2);

        items.add(weapon1);
        items.add(weapon2);
    }

    @Override
    public void initItems() {
        items.add(new Treasure(75f*TILE_SIZE, 17f*TILE_SIZE, ItemType.EMERALD, true));
        items.add(new Treasure(80f*TILE_SIZE, 17f*TILE_SIZE, ItemType.CHEST, true));
        items.add(new Weapon(6f*TILE_SIZE, 15f*TILE_SIZE , ItemType.FIREBALL, 5f*TILE_SIZE, 10, 50*TILE_SIZE, 200, true));
    }

    @Override
    public void initMap() {
        tiledMap = new TmxMapLoader().load("outside.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

}
