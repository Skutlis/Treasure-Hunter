package environment;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import objects.*;

import static environment.TileType.TILE_SIZE;

public class DeathMap extends GameMap{
    @Override
    public void initPlayer() {
        player = new Player(2*TILE_SIZE, TILE_SIZE*30, this);
        entities.add(player);
    }

    @Override
    public void initEnemies() {
        Enemy enemy1 = new Enemy(TILE_SIZE*15, TILE_SIZE * 6, this);
        Weapon weapon1 = new Weapon(enemy1.getX(), enemy1.getY() , ItemType.FIREBALL, 5f*TILE_SIZE, 5, 10*TILE_SIZE, 400, false);
        enemy1.equip(weapon1);

        Enemy enemy2 = new Enemy(TILE_SIZE*25, TILE_SIZE * 6, this);
        Weapon weapon2 = new Weapon(enemy2.getX(), enemy1.getY() , ItemType.FIREBALL, 5f*TILE_SIZE, 5, 10*TILE_SIZE, 400, false);
        enemy2.equip(weapon2);

        Enemy enemy3 = new Enemy(TILE_SIZE*35, TILE_SIZE * 6, this);
        Weapon weapon3 = new Weapon(enemy2.getX(), enemy1.getY() , ItemType.FIREBALL, 5f*TILE_SIZE, 5, 10*TILE_SIZE, 400, false);
        enemy2.equip(weapon3);

        Enemy enemy4 = new Enemy(TILE_SIZE*45, TILE_SIZE * 6, this);
        Weapon weapon4 = new Weapon(enemy2.getX(), enemy1.getY() , ItemType.FIREBALL, 5f*TILE_SIZE, 5, 10*TILE_SIZE, 400, false);
        enemy2.equip(weapon4);

        Enemy enemy5 = new Enemy(TILE_SIZE*15, TILE_SIZE * 12, this);
        Weapon weapon5 = new Weapon(enemy2.getX(), enemy1.getY() , ItemType.FIREBALL, 5f*TILE_SIZE, 5, 10*TILE_SIZE, 400, false);
        enemy2.equip(weapon5);

        Enemy enemy6 = new Enemy(TILE_SIZE*25, TILE_SIZE * 12, this);
        Weapon weapon6 = new Weapon(enemy2.getX(), enemy1.getY() , ItemType.FIREBALL, 5f*TILE_SIZE, 5, 10*TILE_SIZE, 400, false);
        enemy2.equip(weapon6);

        Enemy enemy7 = new Enemy(TILE_SIZE*81, TILE_SIZE * 30, this);
        Weapon weapon7 = new Weapon(enemy2.getX(), enemy1.getY() , ItemType.FIREBALL, 5f*TILE_SIZE, 5, 10*TILE_SIZE, 400, false);
        enemy2.equip(weapon2);

        enemies.add(enemy1);
        enemies.add(enemy2);
        enemies.add(enemy3);
        enemies.add(enemy4);
        enemies.add(enemy5);
        enemies.add(enemy6);
        enemies.add(enemy7);

        entities.add(enemy1);
        entities.add(enemy2);
        entities.add(enemy3);
        entities.add(enemy4);
        entities.add(enemy5);
        entities.add(enemy6);
        entities.add(enemy7);

        items.add(weapon1);
        items.add(weapon2);
        items.add(weapon3);
        items.add(weapon4);
        items.add(weapon5);
        items.add(weapon6);
        items.add(weapon7);
    }

    @Override
    public void initItems() {
        items.add(new Treasure(2f*TILE_SIZE, 21f*TILE_SIZE, ItemType.COIN, true));
        items.add(new Treasure(2f*TILE_SIZE, 12f*TILE_SIZE, ItemType.EMERALD, true));
        items.add(new Treasure(74f*TILE_SIZE, 13f*TILE_SIZE, ItemType.COIN, true));
        items.add(new Treasure(93f*TILE_SIZE, 33f*TILE_SIZE, ItemType.EMERALD, true));
        items.add(new Treasure(94f*TILE_SIZE, 10f*TILE_SIZE, ItemType.CHEST, true));
        items.add(new Weapon(4f*TILE_SIZE, 21f*TILE_SIZE , ItemType.FIREBALL, 5f*TILE_SIZE, 10, 50*TILE_SIZE, 200, true));
    }

    @Override
    public void initMap() {
        tiledMap = new TmxMapLoader().load("deathmap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }
}
