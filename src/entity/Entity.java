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
    // In grid format
    private int x, y;
    private int speed;
    private int entityCounter = 0;
    private int entityImage;
    private String direction = "";
    private String name;
    private int maxHealth;
    private int health;
    private int damage;
    private Type entityType;
    private boolean inCombat = false;
    private boolean topCol, botCol, leftCol, rightCol = false;
    private Rectangle hitbox = new Rectangle(12, 23, 26, 27);
    private BufferedImage left1, left2, right1, right2, up1, up2, down1, down2, image;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    // Getters and setters
    public int getX() {
        return x;
    }

    public void updateX(int x) {
        this.x += x;
    }

    public int getY() {
        return y;
    }

    public void updateY(int y) {
        this.y += y;
    }

    public int getSpeed() {
        return speed;
    }

    public void updateSpeed(int speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    // Setter for maxHealth
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    // Getter for health
    public int getHealth() {
        return health;
    }

    // Setter for health
    public void updateHealth(int health) {
        this.health = health;
    }

    // Getter for damage
    public int getDamage() {
        return damage;
    }

    // Setter for damage
    public void updateDamage(int damage) {
        this.damage = damage;
    }

    public Type getEntityType() {
        return entityType;
    }

    public final void setEntityType(Type entityType) {
        this.entityType = entityType;
    }

    public Rectangle getSolidArea() {
        return hitbox;
    }

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

    public void setLeft1(BufferedImage left1) {
        this.left1 = left1;
    }

    public void setLeft2(BufferedImage left2) {
        this.left2 = left2;
    }

    public void setRight1(BufferedImage right1) {
        this.right1 = right1;
    }

    public void setRight2(BufferedImage right2) {
        this.right2 = right2;
    }

    public void setUp1(BufferedImage up1) {
        this.up1 = up1;
    }

    public void setUp2(BufferedImage up2) {
        this.up2 = up2;
    }

    public void setDown1(BufferedImage down1) {
        this.down1 = down1;
    }

    public void setDown2(BufferedImage down2) {
        this.down2 = down2;
    }

    // Methods
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

    public void setDefault(int row, int column, int speed) {
        this.x = row;
        this.y = column;
        this.speed = speed;
    }

    public void setAction() {

    }

    public void setEntityImage() {
        entityCounter++;

        if (entityCounter > 12) {
            entityImage = (entityImage == 1) ? 2 : 1;
            entityCounter = 0;
        }
    }

    public void update() {
        setAction();
        setEntityImage();
    }

    //draw in GP
    public void draw(Graphics2D g2D) {
        if (entityImage == 1) {
            image = switch (direction) {
                case "up" ->
                    up1;
                case "down" ->
                    down1;
                case "left" ->
                    left1;
                case "right" ->
                    right1;
                default ->
                    down1;
            };
        } else if (entityImage == 2) {
            image = switch (direction) {
                case "up" ->
                    up2;
                case "down" ->
                    down2;
                case "left" ->
                    left2;
                case "right" ->
                    right2;
                default ->
                    down2;
            };
        }

        g2D.drawImage(image, x, y, null);
    }
}