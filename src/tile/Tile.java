package tile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile {

    private TileType type;
    private boolean walkable;
    private BufferedImage image;

    public Tile(TileType type) {
        this.type = type;
        this.walkable = determineWalkability(type);
        this.image = loadImage(type.name().toLowerCase());
    }

    public TileType getType() {
        return type;
    }

    public boolean isWalkable() {
        return walkable;
    }
    
    public BufferedImage getImage() {
        return image;
    }

    private boolean determineWalkability(TileType type) {
        switch (type) {
            case EMPTY:
            case GRASS:
                return true;
            case WALL:
                return false;
            default:
                return true; // default to walkable for unknown
        }
    }

    private BufferedImage loadImage(String name) {
        try {
            System.out.println(name);
            return ImageIO.read(getClass().getClassLoader().getResourceAsStream("tile/" + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
