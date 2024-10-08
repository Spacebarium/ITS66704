package entity.type;

import main.GamePanel;
import utility.UtilityTool;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.CollisionChecker;
import movement.MovementHandler;
import movement.type.Movement;

public class Entity {
    
    protected final GamePanel gp;
    private final MovementHandler movementHandler;

    private final EntityType type;
    private final String name;
    private int x;
    private int y;
    protected int screenX;
    protected int screenY;
    private int width;
    private int height;
    private int hitboxOffsetX;
    private int hitboxOffsetY;
    private Rectangle hitbox;

    private int health;
    private int maxHealth;
    private int speed;
    private int damage;
    private boolean inCombat;

    private int entityCounterFrames = 12;
    private int entityCounter = entityCounterFrames;
    protected int entityImage;
    protected String direction = "idle";
    
    protected BufferedImage left1, left2, right1, right2, up1, up2, down1, down2, image, idle;


    public Entity(GamePanel gp, EntityType type, String name, int width, int height, int hitboxOffsetX, int hitboxOffsetY, int hitboxWidth, int hitboxHeight, Movement movement) {
        this.gp = gp;
        this.movementHandler = new MovementHandler(this, new CollisionChecker(gp), movement);
        this.type = type;
        this.name = name;
        this.width = width;
        this.height = height;
        
        this.hitboxOffsetX = hitboxOffsetX;
        this.hitboxOffsetY = hitboxOffsetY;
        this.hitbox = new Rectangle(x + hitboxOffsetX, y + hitboxOffsetY, hitboxWidth, hitboxHeight);
        
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
        this.hitbox.setLocation(x + hitboxOffsetX, y + hitboxOffsetY);
    }
    
    public Rectangle getBoundingBox() { return new Rectangle(x, y, width, height); }
    
    public int getScreenX() { return screenX; } 
    public int getScreenY() { return screenY; }
    
    public int getCentreX() { return x + (width / 2); }
    public int getCentreY() { return y + (height / 2); }
    public Point getCentre() { return new Point(getCentreX(), getCentreY()); }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = Math.max(0, health); }

    public int getMaxHealth() { return maxHealth; }
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }
    
    public MovementHandler getMovementHandler() { return movementHandler; }

    public boolean getCombatStatus(){ return inCombat; }
    public void setCombatStatus(boolean inCombat) {
        this.inCombat = inCombat;
    }
    
    public boolean isInRange(int x, int y, int range) {
        int closestX = UtilityTool.clamp(x, this.x, this.x + this.width);
        int closestY = UtilityTool.clamp(y, this.y, this.y + this.height);
        
        int distX = x - closestX;
        int distY = y - closestY;
        
        return distX * distX + distY * distY <= range * range;
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
        return util.imageSetup(folderName, imageName);
    }

    public void setEntityImage() {
        // change sprite every 12 frames
        if (entityCounter > 12) {
            entityImage = (entityImage == 1) ? 2 : 1;
            entityCounter = 0;
        }
        entityCounter++;
    }

    public void update() {
        movementHandler.update();
    };

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
        
        if (type != EntityType.PLAYER) { // draw all other entities relative to player
            Player player = gp.entityManager.getPlayer();
            this.screenX = getX() - player.getX() + player.getScreenX();
            this.screenY = getY() - player.getY() + player.getScreenY();
        }
        
        g2.drawImage(image, screenX, screenY, image.getWidth() * gp.getScale(), image.getHeight() * gp.getScale(), null);
    }
}
