package main;

import java.awt.*;
import java.util.List;

import entity.type.*;
import weapon.*;

public class DebugRenderer {

    private final GamePanel gp;
    
    public DebugRenderer(GamePanel gp) {
        this.gp = gp;
    }
    
    public Rectangle translateRectangle(Rectangle rectangle, int dx, int dy) {
        return new Rectangle(rectangle.x + dx, rectangle.y + dy, rectangle.width, rectangle.height);
    }
    
    public void renderDebugInfo(Graphics2D g2) {
        // FONT SETTINGS
        g2.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.setColor(Color.WHITE);
        
        // IMPORTANT STUFF
        Player player = gp.entityManager.getPlayer();
        List<Entity> entities = gp.entityManager.getEntities();
        int screenX = player.getScreenX() - player.getX();
        int screenY = player.getScreenY() - player.getY();
        
        // STUFF TO RENDER
        g2.drawString("FPS: " + GamePanel.FPS, 10, 20);
        g2.drawString("Update duration: " + String.format("%.2f", GamePanel.updateDurationPerSecond) + "ms", 10, 40);
        g2.drawString("Render duration: " + String.format("%.2f", GamePanel.renderDurationPerSecond) + "ms", 10, 60);
        g2.drawString("X: " + player.getX(), 10, 80);
        g2.drawString("Y: " + player.getY(), 10, 100);

        g2.drawString("DX: " + player.getMovementHandler().getDx(), 10, 120);
        g2.drawString("DY: " + player.getMovementHandler().getDy(), 10, 140);
        g2.drawString("Speed: " + String.format("%.2f", player.getMovementHandler().getSpeed()), 10, 160);
        g2.drawString("Health: " + player.getHealth(), 10, 180);

        g2.drawString("slot0: " + player.getWeaponFromSlot(0).getName(), 10, 200);
        g2.drawString("slot1: " + player.getWeaponFromSlot(1).getName(), 10, 220);
        g2.drawString("equipped slot: " + player.getEquippedWeaponIndex(), 10, 240);

        
        for (Entity entity : entities) {
            // image box
            g2.setColor(Color.RED);
            g2.drawRect(entity.getScreenX(), entity.getScreenY(), entity.getWidth(), entity.getHeight());

            // hitbox
            Rectangle hitbox = translateRectangle(entity.getHitbox(),screenX, screenY);
            g2.setColor(new Color(0, 0, 255, 128));
            g2.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

            if (entity.getEntityType() == EntityType.ENEMY) {
                Enemy enemy = (Enemy) entity;

                // enemy movement bounds
                int boundX = enemy.getWidth() + 200;
                int boundY = enemy.getHeight() + 200;

                Rectangle movementBound = new Rectangle(enemy.getCentreX() - boundX / 2, enemy.getCentreY() - boundY / 2, boundX, boundY);
                movementBound = translateRectangle(movementBound, screenX, screenY);

                g2.setColor(new Color(128, 0, 255, 128));
                g2.drawRect(movementBound.x, movementBound.y, movementBound.width, movementBound.height);
            }

        }

        // draw weapon range
        if (player.getEquippedWeapon().getPosition() != null) {
            Weapon equippedWeapon = player.getEquippedWeapon();
            int r = player.getEquippedWeapon().getRange();
            
            g2.setColor(new Color(128, 0, 255, 128));
            g2.fillOval(equippedWeapon.getPosition().x - r + screenX, equippedWeapon.getPosition().y - r + screenY, 2 * r, 2 * r);
            
            if (equippedWeapon instanceof Gun gun) {
                gun.renderDebugInfo(g2);
            }
        }
    }
}
