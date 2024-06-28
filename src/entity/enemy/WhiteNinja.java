package entity.enemy;

import entity.type.Entity;
import entity.type.EntityType;
import main.GamePanel;

public class WhiteNinja extends Entity {

    public WhiteNinja(GamePanel gp) {
        super(gp, EntityType.ENEMY, "White ninja", 232, 200, 48, 48, 9, 12, 30, 36);

        setSpeed(4);
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
