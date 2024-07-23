package tile;

import utility.UtilityTool;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import static main.GamePanel.getTileSize;

public class Tile {

    private final boolean walkable;
    private final BufferedImage image;

    public Tile(String type) {
        this.walkable = determineWalkability(type);
        this.image = loadImage(type);
    }

    public boolean isWalkable() {
        return walkable;
    }

    public BufferedImage getImage() {
        return image;
    }

    private boolean determineWalkability(String tileID) {
        switch (tileID) {
            case "001", "002", "003", "004", "005", "006", "007", "008", "009", "010", "011", "012", "013", "014", "015", "017", "034", "035", "036", "037", "038", "040", "041", "042", "043", "044", "045", "046", "047", "048", "049", "050", "051", "052", "053", "054", "056", "057", "060", "063", "065", "069", "070" -> {
                return true;
            }
            case "000", "016", "018", "019", "020", "021", "022", "023", "024", "025", "026", "027", "028", "029", "030", "031", "032", "033", "039", "055", "058", "059", "061", "062", "064", "067", "068", "066" -> {
                return false;
            }
            default -> {
                return true; // default to walkable for unknown
            }
        }
    }

    private BufferedImage loadImage(String name) {
        UtilityTool utilityTool = new UtilityTool();
        return utilityTool.imageSetup("tiles" , name);
    }
}
