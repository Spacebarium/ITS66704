package entity.enemy;

import entity.type.Enemy;
import main.GamePanel;

public class WhiteNinja extends Enemy {

    public WhiteNinja(GamePanel gp, int defaultX, int defaultY, int damage, int health) {
        super(gp, "whiteNinja", health, damage, defaultX, defaultY, 16 * gp.getScale(), 16 * gp.getScale(), 9, 12, 30, 36);
        getImage();
    }

    public void getImage(){
        String folderName = "entity/enemy/whiteNinja";
        setUp1(imageSetup(folderName, "WhiteUp1"));
        setUp2(imageSetup(folderName, "WhiteUp2"));
        setDown1(imageSetup(folderName, "WhiteDown1"));
        setDown2(imageSetup(folderName, "WhiteDown2"));
        setLeft1(imageSetup(folderName, "WhiteLeft1"));
        setLeft2(imageSetup(folderName, "WhiteLeft2"));
        setRight1(imageSetup(folderName, "WhiteRight1"));
        setRight2(imageSetup(folderName, "WhiteRight2"));
        setIdle(imageSetup(folderName, "WhiteDown1"));
    }
}

