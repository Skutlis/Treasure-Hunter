package environment;

import java.util.HashMap;

/**
 * Set of constant objects on the map
 * to keep track of what is, and isn't collidable
 */
public enum TileType {

    TESTGRASS(371, true, "TestGrass"),
    TESTDIRT(385, true, "TestDirt"),
    STONE(65, true, "Stone"),
    BGSTONE(80, false, "BGStone"),
    BGSKY(103, false, "BGSky"),
    WATER(149, false, "BGWater"),
    BGWOOD(55, false, "BGWood"),
    BGPLANK(179, false, "BGPlank"),
    BGCANNONHOLE(86, false, "BGCannonHole"),
    BGCANVAS(158, false, "BGCANVAS"),
    SAND(133, true, "Sand"),
    GRASS(84, true, "Grass"),
    DIRT(67, true, "Dirt"),
    ROOFGRASS(83, true, "RoofGrass"),

    DARK_STONE(377, true, "Dark Stone"),
    TALL_GRASS1(240, false, "Tall Grass"),
    TALL_GRASS2(254, false, "Tall Grass2"),
    PLATFORM(321, true, "Platform"),
    LEAVES(265, true, "Leaves"),
    WOOD(42, true, "Wood"),
    //SAND(132, true, "Sand"),
    GRASS2(263, true, "Grass 2"),
    DIRT2(261, true, "Dirt 2"),
    ROOF_GRASS(279, false, "Roof grass"),
    GRAVEL(180, true, "Gravel"),
    //WATER(149, false, "bg water"),
    BG_SKY(103, false, "bg sky"),
    BG_WOOD(41, false, "bg wood"),
    BG_PLANK(179, false, "bg plank"),
    BG_CANNON_HOLE(86, false, "bg Cannon Hole"),
    BG_CANVAS(158, false, "bg Canvas"),
    FG_STONE(78, false, "bg stone");

    public static final int TILE_SIZE = 32; //The size of a tile in pixels

    private int id; //id of the tile in the TMX file (game map)
    private final boolean collidable; //true if the tile is collidable. False otherwise
    private String name; // Name of the tile
    private float damage; //Damage given for entities in contact of the tile

    /**
     * Constructor for the tile
     * @param id
     * @param collidable
     * @param name
     */
    TileType(int id, boolean collidable, String name){
        this(id, collidable, name, 0);
    }

    /**
     * Constructor for the tiles that do damage
     * @param id
     * @param collidable
     * @param name
     */
    TileType(int id, boolean collidable, String name, float damage){
        this.id = id;
        this.collidable = collidable;
    }


    /**
     * Returns the identifier for the TileType
     *
     * @return TileType id
     */
    private Integer getId() {
        return id;
    }


    /**
     * HashMap made to easily to identify the TilType on a map
     */
    private static HashMap<Integer, TileType> tileMap;


    /**
     *Loops through every tile, and adds it to the HashMap tileMap
     */
    static {
        tileMap = new HashMap<>();
        for (TileType tileType : TileType.values()) {
            tileMap.put(tileType.getId(), tileType);
        }
    }


    /**
     *Returns the TileType by the id
     * @param id
     * @return TileType id
     */
    public static TileType getTileTypeById(int id) {
        return tileMap.get(id);
    }

    /**
     *
     * @return True if the given tile is collidable. False otherwise
     */
    public boolean isCollidable(){
        return this.collidable;
    }
}
