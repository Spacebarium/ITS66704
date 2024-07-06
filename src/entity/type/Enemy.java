
package entity.type;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import main.GamePanel;
import movement.type.EnemyMovement;
import movement.type.NoMovement;

public class Enemy extends Entity {

    private final Player player;
    private final int    aggroDistance = 140;
    private int          cooldown = 2 * 60;
    private int          attackRange = 16;
    private int          attackCooldown = cooldown;
    private boolean      canAttack = true;

    public Enemy(GamePanel gp, String name, int x, int y, int width, int height, int hitboxOffsetX, int hitboxOffsetY, int hitboxWidth, int hitboxHeight) {
        super(gp, EntityType.ENEMY, name, x, y, width, height, hitboxOffsetX, hitboxOffsetY, hitboxWidth, hitboxHeight, new NoMovement());
        this.player = gp.entityManager.getPlayer();

        setSpeed(2);
        setHealth(20);
        getImage();
    }
    
    public int getAttackRange() {
        return attackRange;
    }

    public void getImage() {
        setUp1(imageSetup("whiteNinja", "whiteUp1"));
        setUp2(imageSetup("whiteNinja", "whiteUp2"));
        setDown1(imageSetup("whiteNinja", "whiteDown1"));
        setDown2(imageSetup("whiteNinja", "whiteDown2"));
        setLeft1(imageSetup("whiteNinja", "whiteLeft1"));
        setLeft2(imageSetup("whiteNinja", "whiteLeft2"));
        setRight1(imageSetup("whiteNinja", "whiteRight1"));
        setRight2(imageSetup("whiteNinja", "whiteRight2"));
        setIdle(imageSetup("whiteNinja", "whiteDown1"));
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
        if (getDistanceToPlayer() <= aggroDistance) {
            setCombatStatus(true);
        } else {
            setCombatStatus(false);
        }
    }
    
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        
        g2.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.setColor(Color.WHITE);

        Player player = gp.entityManager.getPlayer();
        this.screenX = getX() - player.getX() + player.getScreenX();
        this.screenY = getY() - player.getY() + player.getScreenY();

        // draw hp
        g2.drawString(String.valueOf(getHealth()), getScreenX(), getScreenY() - 10);
    }
}
