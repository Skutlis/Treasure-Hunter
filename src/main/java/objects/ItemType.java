package objects;

import com.badlogic.gdx.graphics.Texture;
import environment.TileType;

public enum ItemType {

    COIN(TileType.TILE_SIZE, TileType.TILE_SIZE, 1, "COIN", new Texture("gameCoin.png"), "TREASURE"),
    EMERALD(TileType.TILE_SIZE, TileType.TILE_SIZE, 10, "EMERALD", new Texture("emerald.png"), "TREASURE"),
    SPATULA(TileType.TILE_SIZE, TileType.TILE_SIZE, 100, "SPATULA", new Texture("spatula.png"), "TREASURE"),
    FIREBALL(TileType.TILE_SIZE, TileType.TILE_SIZE, 0, "FIREBALL", new Texture("fireball.png"), "WEAPON"),
    CHEST(2*TileType.TILE_SIZE, 2*TileType.TILE_SIZE, 1000, "CHEST", new Texture("CHEST.png"), "TREASURE");


    private int height;
    private int width;
    private int value;
    private String name;
    private Texture texture;
    private String instance;

    /**
     * Initiates a new instance of ItemType
     * @param height - Height of the item
     * @param width - Width if the item
     * @param value - Coin-value of the item
     * @param name - Name of the item
     * @param texture - The texture of the item
     * @param instance - Which class the item is a instance of
     */
    ItemType(int height, int width, int value, String name, Texture texture, String instance){
        this.height = height;
        this.width = width;
        this.value = value;
        this.name = name;
        this.texture = texture;
        this.instance = instance;
    }

    public int getWidth() {return width;}

    public int getHeight() {return height;}

    public int getValue() {return value;}

    public Texture getTexture() {return texture;}

    public String getInstance() {return instance;}

}
