package item;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;
import entity.type.Player;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import utility.UtilityTool;

public class Item {
    
    private GamePanel gp;
    private Player player;
    private int x, y;
    private int windowX, windowY;
    private int width, height;
    private String name;
    private BufferedImage texture;
    private boolean highlighted;

    public Item(GamePanel gp, int x, int y, int width, int height, String name, String textureFilePath) {
        this.gp = gp;
        this.player = gp.entityManager.getPlayer();
        this.x = x;
        this.y = y;
        this.windowX = x - player.getX() + player.getScreenX();
        this.windowY = y - player.getY() + player.getScreenY();
        this.width = width;
        this.height = height;
        this.name = name;
        
        try {
            this.texture = ImageIO.read(getClass().getClassLoader().getResourceAsStream(textureFilePath + ".png"));
            this.texture = UtilityTool.scaleImage(texture, gp.getScale() * texture.getWidth(), gp.getScale() * texture.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.highlighted = false;
    }
    
    // no width and height specified, default to 16 * scale
    public Item(GamePanel gp, int x, int y, String name, String textureFilePath) {
        this(gp, x, y, 16 * gp.getScale(), 16 * gp.getScale(), name, textureFilePath);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        this.windowX = x - player.getX() + player.getScreenX();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        this.windowY = y - player.getY() + player.getScreenY();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getTexture() {
        return texture;
    }
    
    public double getDistanceFrom(int x, int y, int range) {
        int closestX = UtilityTool.clamp(x, this.x, this.x + this.width);
        int closestY = UtilityTool.clamp(y, this.y, this.y + this.height);
        
        int distX = x - closestX;
        int distY = y - closestY;
        
        return Math.hypot(distX, distY);
    }
    
    public double getDistanceFrom(Point point, int range) {
        return getDistanceFrom(point.x, point.y, range);
    }
    
    public boolean isInRange(int x, int y, int range) {
        return getDistanceFrom(x, y, range) <= range;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(texture, windowX, windowY, null);
        
        if (highlighted) {
            g2.setColor(Color.PINK);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(windowX, windowY, width, height);
        }
    }
    

}
