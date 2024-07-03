package weapon;

import entity.EntityManager;
import entity.type.Enemy;
import entity.type.Entity;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class Sword extends Weapon {

    public Sword(String name, int damage, int range, int attackRate, EntityManager entityManager) {
        super(name, damage, range, attackRate, entityManager);
    }

    @Override
    public void use() {
        if (!canAttack()) { return; }
        
        for (Entity entity : entityManager.getEntities()) {
            if (entity instanceof Enemy) {
                Enemy enemy = (Enemy) entity;

                Rectangle enemyBox = new Rectangle(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
                Ellipse2D weaponRange = new Ellipse2D.Double(position.x - range, position.y - range, 2 * range, 2 * range);

                if (weaponRange.intersects(enemyBox)) {
                    enemy.setHealth(enemy.getHealth() - damage);
                }
            }
        }
        lastAttackTime = System.currentTimeMillis();
    }
    
}
