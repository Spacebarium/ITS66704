package entity.enemy;

import entity.type.Enemy;
import entity.type.Player;
import main.GamePanel;
import movement.type.EnemyMovement;

public class Boss extends Enemy {

    public Boss(GamePanel gp, int defaultX, int defaultY) {
        super(gp, "Boss", defaultX, defaultY, 16 * gp.getScale(), 16 * gp.getScale(), 9, 12, 30, 36);

        getImage();
    }


    public void getImage(){
        setUp1(imageSetup("Boss", "BossUp1"));
        setUp2(imageSetup("Boss", "BossUp2"));
        setDown1(imageSetup("Boss", "BossDown1"));
        setDown2(imageSetup("Boss", "BossDown2"));
        setLeft1(imageSetup("Boss", "BossLeft1"));
        setLeft2(imageSetup("Boss", "BossLeft2"));
        setRight1(imageSetup("Boss", "BossRight1"));
        setRight2(imageSetup("Boss", "BossRight2"));
        setIdle(imageSetup("Boss", "BossDown1"));
    }

//    public void getAttackImage(){
//        setUp1(imageSetup("Boss", "BossAttackUp1"));
//        setUp2(imageSetup("Boss", "BossAttackUp2"));
//        setDown1(imageSetup("Boss", "BossAttackDown1"));
//        setDown2(imageSetup("Boss", "BossAttackDown2"));
//        setLeft1(imageSetup("Boss", "BossAttackLeft1"));
//        setLeft2(imageSetup("Boss", "BossAttackLeft2"));
//        setRight1(imageSetup("Boss", "BossAttackRight1"));
//        setRight2(imageSetup("Boss", "BossAttackRight2"));
//    }
}