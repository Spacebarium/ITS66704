package entity.enemy;

import entity.type.Enemy;
import main.GamePanel;

public class FinalBoss extends Enemy {

    public FinalBoss(GamePanel gp, int defaultX, int defaultY) {
        super(gp, "FinalBoss", defaultX, defaultY, 48 * gp.getScale(), 48 * gp.getScale(), 15 * gp.getScale(), 12, 60, 44 * gp.getScale());

        getImage();
    }

    public void getImage() {
        setUp1(imageSetup("FinalBoss", "FinalBossLeft1"));
        setUp2(imageSetup("FinalBoss", "FinalBossLeft3"));
        setDown1(imageSetup("FinalBoss", "FinalBossRight1"));
        setDown2(imageSetup("FinalBoss", "FinalBossRight2"));
        setLeft1(imageSetup("FinalBoss", "FinalBossLeft1"));
        setLeft2(imageSetup("FinalBoss", "FinalBossLeft2"));
        setRight1(imageSetup("FinalBoss", "FinalBossRight1"));
        setRight2(imageSetup("FinalBoss", "FinalBossRight2"));
        setIdle(imageSetup("FinalBoss", "FinalBossLeft1"));
    }
}

