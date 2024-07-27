package entity.enemy;

import entity.type.Enemy;
import main.GamePanel;

public class GreenNinja extends Enemy {

    public GreenNinja(GamePanel gp, int defaultX, int defaultY, int damage, int health) {
        super(gp, "GreenNinja", health, damage, defaultX, defaultY, 16 * gp.getScale(), 16 * gp.getScale(), 9, 12, 30, 36);

        getImage();
    }

    public void getImage(){
        String folderName = "entity/enemy/greenninja";
        setUp1(imageSetup(folderName, "GreenUp1"));
        setUp2(imageSetup(folderName, "GreenUp2"));
        setDown1(imageSetup(folderName, "GreenDown1"));
        setDown2(imageSetup(folderName, "GreenDown2"));
        setLeft1(imageSetup(folderName, "GreenLeft1"));
        setLeft2(imageSetup(folderName, "GreenLeft2"));
        setRight1(imageSetup(folderName, "GreenRight1"));
        setRight2(imageSetup(folderName, "GreenRight2"));
        setIdle(imageSetup(folderName, "GreenDown1"));
    }
}

