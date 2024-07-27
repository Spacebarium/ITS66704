package entity.enemy;

import entity.type.Enemy;
import main.GamePanel;

public class FinalBoss extends Enemy {

    public FinalBoss(GamePanel gp, int defaultX, int defaultY, int damage, int health) {
        super(gp, "FinalBoss", health, damage, defaultX, defaultY, 48 * gp.getScale(), 48 * gp.getScale(), 15 * gp.getScale(), 12, 60, 44 * gp.getScale());
        getImage();
    }

    public void getImage(){
        String folderName = "entity/enemy/finalboss";
        setUp1(imageSetup(folderName, "FinalBossUp1"));
        setUp2(imageSetup(folderName, "FinalBossUp2"));
        setDown1(imageSetup(folderName, "FinalBossDown1"));
        setDown2(imageSetup(folderName, "FinalBossDown2"));
        setLeft1(imageSetup(folderName, "FinalBossLeft1"));
        setLeft2(imageSetup(folderName, "FinalBossLeft2"));
        setRight1(imageSetup(folderName, "FinalBossRight1"));
        setRight2(imageSetup(folderName, "FinalBossRight2"));
        setIdle(imageSetup(folderName, "FinalBossDown1"));
    }
}

