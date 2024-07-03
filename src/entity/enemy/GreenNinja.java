package entity.enemy;

import entity.type.Enemy;
import entity.type.Player;
import main.GamePanel;
import movement.type.EnemyMovement;

public class GreenNinja extends Enemy {

    public GreenNinja(GamePanel gp, EnemyMovement enemyMovement, Player player) {
        super(gp, "greenNinja", 200, 200, 16 * gp.getScale(), 16 * gp.getScale(), 9, 12, 30, 36, enemyMovement, player);
        setSpeed(4);
        getImage();
    }

    public void getImage() {
        setUp1(imageSetup("greenNinja", "greenUp1"));
        setUp2(imageSetup("greenNinja", "greenUp2"));
        setDown1(imageSetup("greenNinja", "greenDown1"));
        setDown2(imageSetup("greenNinja", "greenDown2"));
        setLeft1(imageSetup("greenNinja", "greenLeft1"));
        setLeft2(imageSetup("greenNinja", "greenLeft2"));
        setRight1(imageSetup("greenNinja", "greenRight1"));
        setRight2(imageSetup("greenNinja", "greenRight2"));
        setIdle(imageSetup("greenNinja", "greenDown1"));
    }
}

