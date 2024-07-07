package weapon;

import entity.EntityManager;
import entity.type.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.List;
import main.GamePanel;

public class Gun extends Weapon {
    
    private final EntityManager entityManager;
    private final int tileSize;
    private final Player player;
    private final int weaponOffset;

    public Gun(String name, int damage, int range, int attackRate, GamePanel gp) {
        super(name, damage, range, attackRate, gp);
        
        this.entityManager = gp.entityManager;
        this.tileSize = gp.getTileSize();
        this.player = entityManager.getPlayer();
        this.weaponOffset = player.getWeaponOffset();
    }
    
    public Point getWorldMouse() {
        Point mouse = player.getMousePoint();
        return new Point(mouse.x + player.getX() - player.getScreenX(), mouse.y + player.getY() - player.getScreenY());
    }
    
    public Line2D getRaycast(Point playerPoint, Point mousePoint) {
        int px = playerPoint.x;
        int py = playerPoint.y;
        int mx = mousePoint.x;
        int my = mousePoint.y;
        int x = px;
        int y = py;
        
        double dist = Math.hypot((mx - px), (my - py));
        int endX = (int) (px + (mx - px) / dist * range);
        int endY = (int) (py + (my - py) / dist * range);

        int dx = Math.abs(endX - px);
        int dy = Math.abs(endY - py);
        int err = dx - dy;

        int sx = (int) Math.signum(endX - px);
        int sy = (int) Math.signum(endY - py);
        
        Point lastWalkablePoint = new Point(x, y);

        while (true) {
            if (Math.hypot(x - px, y - py) > range + weaponOffset) { break; }

            lastWalkablePoint = new Point(x, y);

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }
            if (e2 < dx) {
                err += dx;
                y += sy;
            }
        }
        return new Line2D.Double(playerPoint, lastWalkablePoint);
    }   

    @Override
    public void use() {
        if (!canAttack()) { return; }
        
        List<Entity> enemiesInRange = entityManager.getEntitiesInRange(position.x, position.y, range, EntityType.ENEMY);
        Line2D raycast = getRaycast(player.getCentre(), getWorldMouse());
        
        // check for enemies in line
        enemiesInRange.stream()
                .filter(e -> e.getBoundingBox().intersectsLine(raycast))
                .forEach(e -> e.setHealth(e.getHealth() - damage));
        
        lastAttackTime = System.currentTimeMillis();
    }
    
    public void renderDebugInfo(Graphics2D g2) {
        g2.setColor(new Color(255, 192, 203, 128));
        
//        g2.drawOval(mouse.x - 4, mouse.y - 4, 8, 8);
//        g2.drawOval(position.x - 4 - player.getX() + player.getScreenX(), position.y - 4 - player.getY() + player.getScreenY(), 8, 8);
        
        Line2D raycast = getRaycast(player.getCentre(), getWorldMouse());
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(
                (int) (raycast.getX1() - player.getX() + player.getScreenX()),
                (int) (raycast.getY1() - player.getY() + player.getScreenY()),
                (int) (raycast.getX2() - player.getX() + player.getScreenX()),
                (int) (raycast.getY2() - player.getY() + player.getScreenY()));
    }
}
