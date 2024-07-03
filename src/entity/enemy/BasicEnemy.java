package entity.enemy;

import entity.type.Enemy;
import entity.type.EntityType;
import entity.type.Player;
import main.GamePanel;
import movement.type.EnemyMovement;

public class BasicEnemy extends Enemy {

    Player player;
    final int aggroDistance = 160;
    int coolDown = 2 * 60;
    public int attackRange = 16;
    int attackCoolDown = coolDown;
    boolean canAttack = true;


    public BasicEnemy(GamePanel gp) {
        super(gp, "whiteNinja", 200, 200, 48, 48, 9, 12, 30, 36);
        setSpeed(2);
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
}

