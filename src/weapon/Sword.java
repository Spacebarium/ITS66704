package weapon;

import java.util.List;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import main.GamePanel;
import entity.type.*;

public class Sword extends Weapon {

    public Sword(String name, int damage, int range, int attackRate, GamePanel gp) {
        super(name, damage, range, attackRate, gp);
    }

    @Override
    public void use() {
        if (!canAttack()) { return; }
        
        List<Enemy> enemiesInRange = gp.entityManager.getEntitiesInRange(position.x, position.y, range, Enemy.class);
        
        enemiesInRange.forEach(enemy -> {
            Rectangle enemyBox = new Rectangle(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
            Ellipse2D weaponRange = new Ellipse2D.Double(position.x - range, position.y - range, 2 * range, 2 * range);

            if (weaponRange.intersects(enemyBox)) {
                enemy.setHealth(enemy.getHealth() - damage);
            }
        });
        
        lastAttackTime = System.currentTimeMillis();
    }
}
