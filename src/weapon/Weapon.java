package weapon;

import java.awt.Point;
import main.GamePanel;

public abstract class Weapon {
    protected String name;
    protected int    damage;
    protected int    range;
    protected int    attackRate;
    protected long   lastAttackTime;
    protected Point  position;
    protected GamePanel gp;
    
    public Weapon(String name, int damage, int range, int attackRate, GamePanel gp) {
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.attackRate = attackRate; // in milliseconds
        this.lastAttackTime = 0;
        this.gp = gp;
    }

    public String getName() { return name;}
    public int getDamage() { return damage; }
    public int getRange() { return range; }
    public int getAttackRate() { return attackRate; }
    
    public void setPosition(Point position) {
        this.position = position;
    }
    
    public Point getPosition() { return position; }
    
    public boolean canAttack() {
        long currentTime = System.currentTimeMillis();
        return currentTime - lastAttackTime >= attackRate;
    }
    
    public abstract void use();
}
