package entity.enemy;

import entity.type.Enemy;
import main.GamePanel;

public class WhiteNinja extends Enemy {

    public WhiteNinja(GamePanel gp, int defaultX, int defaultY) {
        super(gp, "whiteNinja", defaultX, defaultY, 16 * gp.getScale(), 16 * gp.getScale(), 9, 12, 30, 36);

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
        setIdle(imageSetup("whiteNinja", "whiteDown1"));
    }
}

