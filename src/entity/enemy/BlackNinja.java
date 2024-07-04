package entity.enemy;

import entity.type.Enemy;
import entity.type.Player;
import main.GamePanel;
import movement.type.EnemyMovement;

public class BlackNinja extends Enemy {

    public BlackNinja(GamePanel gp, EnemyMovement enemyMovement, Player player) {
        super(gp, "blackNinja", 200, 200, 16 * gp.getScale(), 16 * gp.getScale(), 9, 12, 30, 36);
        setSpeed(4);
        getImage();
    }

    public void getImage() {
        setUp1(imageSetup("blackNinja", "blackUp1"));
        setUp2(imageSetup("blackNinja", "blackUp2"));
        setDown1(imageSetup("blackNinja", "blackDown1"));
        setDown2(imageSetup("blackNinja", "blackDown2"));
        setLeft1(imageSetup("blackNinja", "blackLeft1"));
        setLeft2(imageSetup("blackNinja", "blackLeft2"));
        setRight1(imageSetup("blackNinja", "blackRight1"));
        setRight2(imageSetup("blackNinja", "blackRight2"));
        setIdle(imageSetup("blackNinja", "blackDown1"));
    }
}

