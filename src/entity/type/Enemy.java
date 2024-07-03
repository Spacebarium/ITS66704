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
    public int           attackRange = 16;
    private int          attackCooldown = cooldown;
    private boolean      canAttack = true;

    public Enemy(GamePanel gp, EnemyMovement enemyMovement, Player player) {
        super(gp, EntityType.ENEMY, "White ninja", 200, 200, 48, 48, 9, 12, 30, 36, enemyMovement);
        this.player = player;

        setSpeed(2);
        setHealth(20);
        getImage();
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
        setIdle(imageSetup("blackNinja", "blackDown1"));
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
        
        return (int) Math.sqrt(deltaX * deltaX + deltaY + deltaY);
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
        
        // draw hp
        g2.drawString(String.valueOf(getHealth()), getX(), getY() - 10);
    }
}