package entity;

import main.GamePanel;
import utility.UtilityTool;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Entity {

    public enum Type {
        PLAYER,
        ENEMY,
        NPC,
        OBJECT
    }
    
    public GamePanel gp;

    private Type      type;
    private String    name;
    private int       x;
    private int       y;
    private int       width;
    private int       height;
    private Rectangle hitbox;
    
    private int       health;
    private int       maxHealth;
    private int       speed;
    private int       damage;
    private boolean   inCombat;
    
    private int       entityCounterFrames = 12;
    private int       entityCounter = entityCounterFrames;
    private int       entityImage;
    private String    direction = "idle";
    
    private boolean topCol, botCol, leftCol, rightCol;
    private BufferedImage left1, left2, right1, right2, up1, up2, down1, down2, image, idle;


    public Entity(GamePanel gp, int x, int y, int width, int height) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(x, y, width, height);
        this.speed = 2;
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    
    public int getWidth() { return width; }
    
    public int getHeight() { return height; }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getMaxHealth() { return maxHealth; }
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }

    public Type getEntityType() { return type; }
    public final void setEntityType(Type entityType) { this.type = entityType; }

    public Rectangle getHitbox() { return hitbox; }

    public boolean topCol() {
        return !topCol;
    }

    public void setTopCol(boolean topCol) {
        this.topCol = topCol;
    }

    public boolean botCol() {
        return !botCol;
    }

    public void setBotCol(boolean botCol) {
        this.botCol = botCol;
    }

    public boolean leftCol() {
        return !leftCol;
    }

    public void setLeftCol(boolean leftCol) {
        this.leftCol = leftCol;
    }

    public boolean rightCol() {
        return !rightCol;
    }

    public void setRightCol(boolean rightCol) {
        this.rightCol = rightCol;
    }
    
    public void setLeft1(BufferedImage left1) { this.left1 = left1; }
    public void setLeft2(BufferedImage left2) { this.left2 = left2; }
    public void setRight1(BufferedImage right1) { this.right1 = right1; }
    public void setRight2(BufferedImage right2) { this.right2 = right2; }
    public void setUp1(BufferedImage up1) { this.up1 = up1; }
    public void setUp2(BufferedImage up2) { this.up2 = up2; }
    public void setDown1(BufferedImage down1) { this.down1 = down1; }
    public void setDown2(BufferedImage down2) { this.down2 = down2; }
    public void setIdle(BufferedImage idle){ this.idle = idle; }

    // Methods
    // Import images
    public BufferedImage imageSetup(String folderName, String imageName) {
        UtilityTool util = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(folderName + "/" + imageName + ".png"));
            image = util.scaleImage(image, gp.getTileSize(), gp.getTileSize());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void setAction() {

    }

    public void setEntityImage(){
        if (entityCounter > 12) { // change sprite every 12 frames
            entityImage = (entityImage == 1) ? 2 : 1;
            entityCounter = 0;
        }
        entityCounter++;
    }

    public void update() {
        hitbox.setLocation(x, y);
    }

    public void draw(Graphics2D g2) {
        if (entityImage == 1) {
            image = switch (direction) {
                case "up" -> up1;
                case "down" -> down1;
                case "left" -> left1;
                case "right" -> right1;
                default -> idle;
            };
        } else if (entityImage == 2) {
            image = switch (direction) {
                case "up" -> up2;
                case "down" -> down2;
                case "left" -> left2;
                case "right" -> right2;
                default -> idle;
	        };
        } else {
            image = idle;
        }
        setEntityImage();

        g2.drawImage(image, x, y, null);
        g2.setColor(Color.RED);
        g2.drawRect(x, y, width, height);
    }
}
