package entity.type;

import main.GamePanel;
import utility.UtilityTool;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Entity {
    
    public GamePanel gp;

    private final EntityType type;
    private final String name;
    private int x;
    private int y;
    private int width;
    private int height;
    private int hitboxOffsetX;
    private int hitboxOffsetY;
    private int hitboxWidth;
    private int hitboxHeight;
    private Rectangle hitbox;

    private int health;
    private int maxHealth;
    private int speed;
    private int damage;
    private boolean inCombat;

    private int entityCounterFrames = 12;
    private int entityCounter = entityCounterFrames;
    private int entityImage;
    private String direction = "idle";
    
    private BufferedImage left1, left2, right1, right2, up1, up2, down1, down2, image, idle;


    public Entity(GamePanel gp, EntityType type, String name, int x, int y, int width, int height, int hitboxOffsetX, int hitboxOffsetY, int hitboxWidth, int hitboxHeight) {
        this.gp = gp;
        this.type = type;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        this.hitboxOffsetX = hitboxOffsetX;
        this.hitboxOffsetY = hitboxOffsetY;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        setHitbox();
        
        this.speed = 2;
    }

    public EntityType getEntityType() { return type; }
    
    public String getName() { return name; }

    public int getX() { return x; }
    public void setX(int x) {
        this.x = x;
        setHitbox();
    }

    public int getY() { return y; }
    public void setY(int y) {
        this.y = y;
        setHitbox();
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public Rectangle getHitbox() { return hitbox; }
    private void setHitbox() {
        this.hitbox = new Rectangle(x + hitboxOffsetX, y + hitboxOffsetY, hitboxWidth, hitboxHeight);
    }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public int getMaxHealth() { return maxHealth; }
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }

    
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

    public void setEntityImage() {
        if (entityCounter > 12) { // change sprite every 12 frames
            entityImage = (entityImage == 1) ? 2 : 1;
            entityCounter = 0;
        }
        entityCounter++;
    }

    public void update() {

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
    }
}
