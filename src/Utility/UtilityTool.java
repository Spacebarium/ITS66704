package Utility;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {

    //Scale images before
    public BufferedImage scaleImage(BufferedImage oriImage, int width, int height) {

        BufferedImage scaledImage = new BufferedImage(width, height, oriImage.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(oriImage, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
}
