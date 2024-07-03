package weapon;

import entity.EntityManager;
import entity.type.Enemy;
import entity.type.Entity;
import entity.type.Player;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class Gun extends Weapon {

    public Gun(String name, int damage, int range, int attackRate, EntityManager entityManager) {
        super(name, damage, range, attackRate, entityManager);
    }

    @Override
    public void use() {
        if (!canAttack()) { return; }
        
        Player player = entityManager.getPlayer();
        Point playerMousePoint = player.getMousePoint();
        
        for (Entity entity : entityManager.getEntities()) {
            if (entity instanceof Enemy) {
                Enemy enemy = (Enemy) entity;;

                Rectangle enemyBox = new Rectangle(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
                Ellipse2D weaponRange = new Ellipse2D.Double(position.x - range, position.y - range, 2 * range, 2 * range);

                if (enemyBox.contains(playerMousePoint) && weaponRange.intersects(enemyBox)) {
                    enemy.setHealth(enemy.getHealth() - damage);
                }
            }
        }
        
        lastAttackTime = System.currentTimeMillis();

    }

}
