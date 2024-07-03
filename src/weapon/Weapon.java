package weapon;

import entity.EntityManager;
import java.awt.Point;

public abstract class Weapon {
    protected String name;
    protected int    damage;
    protected int    range;
    protected int    attackRate;
    protected long   lastAttackTime;
    protected Point  position;
    protected EntityManager entityManager;
    
    public Weapon(String name, int damage, int range, int attackRate, EntityManager entityManager) {
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.attackRate = attackRate; // in milliseconds
        this.lastAttackTime = 0;
        this.entityManager = entityManager;
    }

    public String getName() { return name;}
    public int getDamage() { return damage; }
    public int getRange() { return range; }
    public int getAttackRate() { return attackRate; }
    
    public void setPosition(Point position) {
        this.position = position;
    }
    
    public Point getPosition() { return position; }
    public EntityManager getEntityManager() { return entityManager;}
    
    public boolean canAttack() {
        long currentTime = System.currentTimeMillis();
        return currentTime - lastAttackTime >= attackRate;
    }
    
    public void use() {
        
    };
}
