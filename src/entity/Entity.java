package entity;

import main.GamePanel;
import utility.UtilityTool;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GamePanel gp;
    public int x, y;
    public int speed;

    public BufferedImage left1, left2, right1, right2, up1, up2;
    public String direction = "";

//    public int spriteCounter = 0;
//    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(8, 16, 32, 32);
    public boolean topCol, botCol, leftCol, rightCol = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    // Import images
    public BufferedImage imageSetup(String folderName, String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(folderName + "/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.getTileSize(), gp.getTileSize());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
