package entity.enemy;

import entity.type.Enemy;
import main.GamePanel;

public class Boss extends Enemy {

    public Boss(GamePanel gp, int defaultX, int defaultY, int damage, int health) {
        super(gp, "Boss", health, damage, defaultX, defaultY, 32 * gp.getScale(), 32 * gp.getScale(), 5 * gp.getScale(), 3 * gp.getScale(), 21 * gp.getScale(), 27 * gp.getScale());
        getImage();
    }
//hello testing 1, 2, 3
    //hero wars peak
    //hello darkness my old friends

    public void getImage(){
        String folderName = "entity/enemy/boss";
        setUp1(imageSetup(folderName, "BossUp1"));
        setUp2(imageSetup(folderName, "BossUp2"));
        setDown1(imageSetup(folderName, "BossDown1"));
        setDown2(imageSetup(folderName, "BossDown2"));
        setLeft1(imageSetup(folderName, "BossLeft1"));
        setLeft2(imageSetup(folderName, "BossLeft2"));
        setRight1(imageSetup(folderName, "BossRight1"));
        setRight2(imageSetup(folderName, "BossRight2"));
        setIdle(imageSetup(folderName, "BossDown1"));
    }

//    public void getAttackImage(){
//        setUp1(imageSetup(folderName, "BossAttackUp1"));
//        setUp2(imageSetup(folderName, "BossAttackUp2"));
//        setDown1(imageSetup(folderName, "BossAttackDown1"));
//        setDown2(imageSetup(folderName, "BossAttackDown2"));
//        setLeft1(imageSetup(folderName, "BossAttackLeft1"));
//        setLeft2(imageSetup(folderName, "BossAttackLeft2"));
//        setRight1(imageSetup(folderName, "BossAttackRight1"));
//        setRight2(imageSetup(folderName, "BossAttackRight2"));
//    }
}