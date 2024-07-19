package enemy.type;

import entity.enemy.BossLJ;
import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
<<<<<<< HEAD:src/entity/type/Entity.java
import java.util.Random;
=======
import main.CollisionHandler;
import movement.MovementHandler;
import movement.type.Movement;
import static utility.UtilityTool.scaleImage;
>>>>>>> 946209d3d1b6503543d1d8efe9ed7fe5c3aae672:src/enemy/type/Entity.java

public abstract class Entity {
    
    public GamePanel gp;
    private final MovementHandler movementHandler;

    private final EntityType type;
    private final String name;
    private int x;
    private int y;
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
    private int entityImage;
    private String direction = "idle";
    public int worldX, worldY;
    public int spriteNum=1;
    public boolean attacking=false;
    public int actionLockCounter=0;
    public boolean boss;
    
    private BufferedImage left1, left2, right1, right2, up1, up2, down1, down2, image, idle;


    public Entity(GamePanel gp, EntityType type, String name, int x, int y, int width, int height, int hitboxOffsetX, int hitboxOffsetY, int hitboxWidth, int hitboxHeight, Movement movement) {
        this.gp = gp;
        this.movementHandler = new MovementHandler(this, new CollisionHandler(gp), movement);
        this.type = type;
        this.name = name;
        this.x = x;
        this.y = y;
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
    
    public MovementHandler getMovementHandler() { return movementHandler; }

    public boolean getCombatStatus(){ return inCombat; }
    public void setCombatStatus(boolean inCombat) {
        this.inCombat = inCombat;
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
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(folderName + "/" + imageName + ".png"));
            image = scaleImage(image, gp.getTileSize(), gp.getTileSize());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void setEntityImage() {
        if (entityCounter > 12) { // change sprite every 12 frames
            entityImage = (entityImage == 1) ? 2 : 1;
            entityCounter = 0;
        }
        entityCounter++;
    }

    public void update() {
        getMovementHandler().update();
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

        g2.drawImage(image, x, y, null);

        //Lee Jon's boss code
        int screenX=worldX- gp.getPlayer().worldX + gp.getPlayer().screenX;
        int screenY=worldY- gp.getPlayer().worldY + gp.getPlayer().screenY;

        if(worldX + gp.getTileSize() *5 > gp.getPlayer().worldX - gp.getPlayer().screenX &&
                worldX - gp.getTileSize() < gp.getPlayer().worldX + gp.getPlayer().screenX &&
                worldY + gp.getTileSize() *5 > gp.getPlayer().worldY - gp.getPlayer().screenY &&
                worldY - gp.getTileSize() < gp.getPlayer().worldY + gp.getPlayer().screenY){

            int tempScreenX=screenX;
            int tempScreenY=screenY;

            switch(direction){ //method to execute attack images
                case "up":
                    if(attacking==false){
                        if(spriteNum==1){image=up1;}
                        if(spriteNum==2){image=up2;}
                    }
                    if(attacking==true){
                        tempScreenY=screenY-up1.getHeight();
                        if(spriteNum==1){image=attackUp1;} //how should I implement the images?
                        if(spriteNum==2){image=attackUp2;}
                    }
                    break;
                case "down":
                    if(attacking==false){
                        if(spriteNum==1){image=down1;}
                        if(spriteNum==2){image=down2;}
                    }
                    if(attacking==true){
                        if(spriteNum==1){image=attackDown1;}
                        if(spriteNum==2){image=attackDOwn2;}
                    }
                    break;
                case "left":
                    if(attacking==false){
                        if(spriteNum==1){image=left1;}
                        if(spriteNum==2){image=left2;}
                    }
                    if(attacking==true){
                        tempScreenX=screenX-left1.getWidth();
                        if(spriteNum==1){image=attackLeft1;}
                        if(spriteNum==2){image=attackLeft2;}
                    }
                    break;
                case "right":
                    if(attacking==false){
                        if(spriteNum==1){image=right1;}
                        if(spriteNum==2){image=right2;}
                    }
                    if(attacking==true){
                        //tempScreenY=screenY-left1.getWidth();
                        if(spriteNum==1){image=attackRight1;}
                        if(spriteNum==2){image=attackRight2;}
                    }
                    break;
            }
        }
    }

    public int getCenterX(){
        int centerX=worldX + left1.getWidth()/2; //width of LJ image
        return centerX;
    }
    public int getCenterY(){
        int centerY=worldY + up1.getHeight()/2; //height of LJ image
        return centerY;
    }
    public int getXdistance(Entity target){
        int xDistance=Math.abs(getCenterX()-target.getCenterX());
        return xDistance;
    }
    public int getYdistance(Entity target){
        int yDistance=Math.abs(getCenterY()-target.getCenterY());
        return yDistance;
    }

    public void checkAttackOrNot(int rate, int straight, int horizontal){

        GamePanel gp=new GamePanel();
        boolean targetInRange=false;
        int xDia=getXdistance(gp.getPlayer()); 
        int yDia=getYdistance(gp.getPlayer());

        switch(direction){
            case "up":
                if(gp.getPlayer().getCenterY() < getCenterY() && yDia < straight && xDia < horizontal){
                    targetInRange=true;
                }
                break;
            case "down":
                if(gp.getPlayer().getCenterY() > getCenterY() && yDia < straight && xDia < horizontal){
                    targetInRange=true;
                }
                break;
            case "left":
                if(gp.getPlayer().getCenterX() < getCenterX() && yDia < straight && xDia < horizontal){
                    targetInRange=true;
                }
                break;
            case "right":
                if(gp.getPlayer().getCenterX() > getCenterX() && yDia < straight && xDia < horizontal){
                    targetInRange=true;
                }
                break;
        }
    }

    public void getRandomDirection(int interval){ //dk if it's useful or not
        actionLockCounter++; //where's the actionLockCounter located in the Entity?
        if(actionLockCounter>interval){
            Random random=new Random();
            int i=random.nextInt(100)+1;
            if(i<=25){direction="up";}
            if(i>25 && i<50){direction="down";}
            if(i>50 && i<75){direction="left";}
            if(i>75 && i<=100){direction="right";}
            actionLockCounter=0;
        }
    }

    public void moveTowardPlayer(int interval){
        actionLockCounter++;
        if(actionLockCounter>interval){
            if(getXdistance(gp.getPlayer()) > getYdistance(gp.getPlayer())){
                if(gp.getPlayer().getCenterX() < getCenterX()){
                    direction="left";
                }
                else{
                    direction="right";
                }
            }
            else if(getXdistance(gp.getPlayer()) < getYdistance(gp.getPlayer())){
                if(gp.getPlayer().getCenterY() < getCenterY()){
                    direction="up";
                }
                else{
                    direction="down";
                }
            }
            actionLockCounter=0;
        }
    }

    public void setKnockBack(Entity entity, int knockBackPower){
        entity.speed+=knockBackPower;
        entity.knockBack=true;
    }
}
