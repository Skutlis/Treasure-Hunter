package objects;

import static environment.TileType.TILE_SIZE;

public enum EntityType {
    PLAYER("player", TILE_SIZE * 3, TILE_SIZE * 3, 150),
    ENEMY("enemy", TILE_SIZE * 3, TILE_SIZE * 3, 150);

    private String id;
    private int width;
    private int height;
    private float weight;


    /**
     * Constructs an entity with the parameters;
     * @param id
     * @param width
     * @param height
     * @param weight
     */
    EntityType(String id, int width, int height, float weight) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.weight = weight;

    }

    /**
     * Returns the identifier for the entity
     *
     * @return entity id
     */
    public String getId(){
       return id;
    }


    public int getWidth(){
        return width;
    }



    public int getHeight(){
        return height;
    }



    public float getWeight(){
        return weight;
    }
}
