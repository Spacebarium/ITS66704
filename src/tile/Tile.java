package tile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile {

   // private final TileType type;
   // private final boolean walkable;
    private final BufferedImage image;

    public Tile(int type) {
        //this.type = type;
     //   this.walkable = determineWalkability(type);
        this.image = loadImage(type);
    }

//    public TileType getType() {
//        return type;
//    }

//    public boolean isWalkable() {
//        return walkable;
//    }
    
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

    private BufferedImage loadImage(int name) {
        try {
            String test = String.format("%03d", name);
            return ImageIO.read(getClass().getClassLoader().getResourceAsStream("tile/" + test + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
