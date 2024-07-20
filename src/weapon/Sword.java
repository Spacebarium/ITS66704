package weapon;

import java.util.List;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import main.GamePanel;
import entity.type.*;
import main.Sound;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class Sword extends Weapon {
    private final Sound soundManager = new Sound();
    private double swingStartAngle;
    private double swingCurrentAngle;
    private double swingAngleStep;
    private int swingSteps;
    private int swingCurrentStep;
    private boolean isSwinging = false;

    public Sword(String name, int damage, int range, int attackRate, GamePanel gp, String textureName) {
        super(name, damage, range, attackRate, gp, textureName);
    }
    
    public void animateSwing() {
        // get mouse coords
        Point screenMouse = gp.entityManager.getPlayer().getScreenMouse();
        
        // determine angle to start swing arc animation depending on mouse coords (120deg sweep, +-60deg from mouse angle)
        double angleToMouse = Math.atan2(screenMouse.y - gp.getSize().height / 2, screenMouse.x - gp.getSize().width / 2);
        int totalAngleDeg = 120;
        double totalAngleRad = Math.toRadians(totalAngleDeg);
        
        swingStartAngle = angleToMouse + Math.toRadians(45) - totalAngleRad / 2;
        swingCurrentAngle = swingStartAngle;
        swingSteps = 20;
        swingAngleStep = totalAngleRad / swingSteps;
        swingCurrentStep = 0;
        isSwinging = true;
    }

    @Override
    public void use() {
        List<Enemy> enemiesInRange = gp.entityManager.getEntitiesInRange(position.x, position.y, range, Enemy.class);
        
        animateSwing();
        soundManager.playSE(2);

        enemiesInRange.forEach(enemy -> {
            Rectangle enemyBox = new Rectangle(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
            Ellipse2D weaponRange = new Ellipse2D.Double(position.x - range, position.y - range, 2 * range, 2 * range);


                if (weaponRange.intersects(enemyBox)) {
                    enemy.setHealth(enemy.getHealth() - damage);
                }
        });
        
        lastAttackTime = System.currentTimeMillis();
    }
    
    public void update() {
        if (isSwinging) {
            swingCurrentStep++;
            if (swingCurrentStep < swingSteps) {
                swingCurrentAngle += swingAngleStep;
            } else {
                isSwinging = false;
            }
        }
    }
    
    @Override
    public void draw(Graphics2D g2) {
        if (!isSwinging) return;

        AffineTransform transform = new AffineTransform();
        transform.rotate(swingCurrentAngle, gp.getSize().width / 2, gp.getSize().height / 2);
        transform.translate(gp.getSize().width / 2 - 6, gp.getSize().height / 2 - gp.entityManager.getPlayer().getHeight() + 6);

        g2.drawImage(image, transform, null);
    }
}
