
package entity.type;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import main.GamePanel;
import movement.type.EnemyMovement;

public class Enemy extends Entity {

    private final Player player;
    private final int    aggroDistance = 140;
    private int          cooldown = 2 * 60;
    private int          attackRange = 16;
    private int          attackCooldown = cooldown;
    private boolean      canAttack = true;

    public Enemy(GamePanel gp, String name, int width, int height, int hitboxOffsetX, int hitboxOffsetY, int hitboxWidth, int hitboxHeight) {
        super(gp, EntityType.ENEMY, name, width, height, hitboxOffsetX, hitboxOffsetY, hitboxWidth, hitboxHeight, new EnemyMovement());
        this.player = gp.entityManager.getPlayer();

        setSpeed(3);
        setHealth(20);
    }
    
    public int getAttackRange() {
        return attackRange;
    }


    public int getPlayerHitbox() {
        return player.getHitbox().height;
    }
    
    // so scuff need to rework this
    public int getXDistance() {
        int pCX = player.getCentreX();
        int eCX = this.getCentreX();
        return eCX - pCX;
    }
    
    public int getYDistance() {
        int pCY = player.getCentreY();
        int eCY = this.getCentreY();
        return eCY - pCY;
    }

    public int getDistanceToPlayer() {        
        int deltaX = getXDistance();
        int deltaY = getYDistance();
        
        return (int) Math.hypot(deltaX, deltaY);
    }

    public void cooldownCounter() {
        if (attackCooldown == cooldown) {
            canAttack = true;
        } else {
            attackCooldown++;
            canAttack = false;
        }
    }

    public void attack() {
        if (canAttack) {
            player.setHealth(player.getHealth() - 1);
            attackCooldown = 0;
        }
    }

    @Override
    public void update() {
        super.update();
        setCombatStatus(getDistanceToPlayer() <= aggroDistance);
    }
    
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        
        // draw hp
        g2.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.setColor(Color.WHITE);

        g2.drawString(String.valueOf(getHealth()), getScreenX(), getScreenY() - 10);
    }
}
