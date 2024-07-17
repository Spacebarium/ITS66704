package weapon;

import java.util.List;
import java.awt.*;
import java.awt.geom.Line2D;

import main.GamePanel;
import entity.type.*;
import java.awt.geom.AffineTransform;

public class Gun extends Weapon {
    
    private final int tileSize;
    private final Player player;
    private final int weaponOffset;

    public Gun(String name, int damage, int range, int attackRate, GamePanel gp, String textureName) {
        super(name, damage, range, attackRate, gp, textureName);
        
        this.tileSize = gp.getTileSize();
        this.player = gp.entityManager.getPlayer();
        this.weaponOffset = player.getWeaponOffset();
    }
    
    public Line2D getRaycast(Point playerPoint, Point mousePoint) {
        // Modified Bresenham's line drawing algorithm
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
        int dx2 = 2 * dx;
        int dy2 = 2 * dy;
        int err = dx - dy;

        int sx = (int) Math.signum(endX - px);
        int sy = (int) Math.signum(endY - py);
        
        Point lastWalkablePoint = new Point(x, y);

        while (true) {
            if (Math.hypot(x - px, y - py) > range + weaponOffset) { break; }

            lastWalkablePoint = new Point(x, y);

            if (err > 0) {
                err -= dy2;
                x += sx;
            } else {
                err += dx2;
                y += sy;
            }
        }
        return new Line2D.Double(playerPoint, lastWalkablePoint);
    }   

    @Override
    public void use() {
        List<Enemy> enemiesInRange = gp.entityManager.getEntitiesInRange(position.x, position.y, range, Enemy.class);
        Line2D raycast = getRaycast(player.getCentre(), player.getWorldMouse());
        
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
        
        Line2D raycast = getRaycast(player.getCentre(), player.getWorldMouse());
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(
                (int) (raycast.getX1() - player.getX() + player.getScreenX()),
                (int) (raycast.getY1() - player.getY() + player.getScreenY()),
                (int) (raycast.getX2() - player.getX() + player.getScreenX()),
                (int) (raycast.getY2() - player.getY() + player.getScreenY()));
    }
    
    @Override
    public void draw(Graphics2D g2) {
        // this sucks sobbing emoji
        Point screenMouse = gp.entityManager.getPlayer().getScreenMouse();
        double angleToMouse = Math.atan2(screenMouse.y - gp.getSize().height / 2, screenMouse.x - gp.getSize().width / 2);
        
        AffineTransform transform = new AffineTransform();
        
        if (screenMouse.x > gp.getSize().width / 2) {
            transform.rotate(angleToMouse, gp.getSize().getWidth() / 2 + 18, gp.getSize().getHeight() / 2 + 3);
            transform.translate(gp.getSize().getWidth() / 2 + 12, gp.getSize().getHeight() / 2 - 15);
        } else {
            transform.rotate(angleToMouse + Math.toRadians(180), gp.getSize().getWidth() / 2 - 18, gp.getSize().getHeight() / 2 + 3);
            transform.translate(gp.getSize().getWidth() / 2 - 12, gp.getSize().getHeight() / 2 - 15);
            transform.scale(-1, 1);
        }
        
        g2.drawImage(image, transform, null);
    }
}
