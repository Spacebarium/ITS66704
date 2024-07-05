package weapon;

import entity.EntityManager;
import entity.type.*;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;

public class Gun extends Weapon {

    public Gun(String name, int damage, int range, int attackRate, EntityManager entityManager) {
        super(name, damage, range, attackRate, entityManager);
    }

    @Override
    public void use() {
        if (!canAttack()) { return; }
        
        Player player = entityManager.getPlayer();
        Point mouse = player.getMousePoint();
        List<Entity> enemiesInRange = entityManager.getEntitiesInRange(player.getCentreX(), player.getCentreY(), range, EntityType.ENEMY);

        enemiesInRange.stream()
                .forEach(e -> System.out.println(e.getX() + ", " + e.getY()));
        
        
        
//        for (Entity entity : entityManager.getEntities()) {
//            if (entity instanceof Enemy) {
//                Enemy enemy = (Enemy) entity;
//
//                Rectangle enemyBox = new Rectangle(enemy.getScreenX(), enemy.getScreenY(), enemy.getWidth(), enemy.getHeight());
//                Ellipse2D weaponRange = new Ellipse2D.Double(position.x - range, position.y - range, 2 * range, 2 * range);
//
//                if (enemyBox.contains(playerMousePoint) && weaponRange.intersects(enemyBox)) {
//                    enemy.setHealth(enemy.getHealth() - damage);
//                }
//            }
//        }
        // problems:
        // 1: check for walls in line
        // 2: check for other entities in line
        
        lastAttackTime = System.currentTimeMillis();
    }
}
