package weapon;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import utility.UtilityTool;

public abstract class Weapon {
    protected String name;
    protected int    damage;
    protected int    range;
    protected int    attackRate;
    protected long   lastAttackTime;
    protected Point  position;
    protected GamePanel gp;
    protected BufferedImage image;
    
    public Weapon(String name, int damage, int range, int attackRate, GamePanel gp, String textureName) {
        this.gp = gp;
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.attackRate = attackRate; // in milliseconds
        this.lastAttackTime = 0;
        
        try {
            this.image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("weapon/" + textureName + ".png"));
            this.image = UtilityTool.scaleImage(image, 3 * image.getWidth(), 3 * image.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() { return name;}
    public void setDamage(int damage){
        this.damage = damage;
    }
    public int getDamage() { return damage; }
    public int getRange() { return range; }
    public int getAttackRate() { return attackRate; }
    
    public void setPosition(Point position) {
        this.position = position;
    }
    
    public Point getPosition() { return position; }
    public BufferedImage getImage() { return image; }
    
    public boolean canAttack() {
        long currentTime = System.currentTimeMillis();
        return currentTime - lastAttackTime >= attackRate;
    }
    
    // possibly need to fix melee weapons being able to hit entities through walls

    // == animation ==
    // get mouse coords

        // a.1: if SWORD (or melee), determine angle to start swing arc animation depending on mouse coords (60deg? +-30deg from mouse angle)
    // a.2: swing(time, angle) to fit swing animation within each attack
    // **use AffineTransform to rotate weapon sprite

    // b.1: if GUN, draw hitmark sprite at (1) centre of hit enemy OR (2) last walkable point
    // b.2: maybe draw some intermediary lines to signify flying bullets
    
    abstract public void use();
    abstract public void draw(Graphics2D g2);
}
