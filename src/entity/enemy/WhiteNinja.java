package entity.enemy;

import entity.NPC;
import main.GamePanel;

//enemy
public class WhiteNinja extends NPC {

    public WhiteNinja(GamePanel gp) {
        super(gp);
        setName("White Ninja");
        setEntityType(Type.ENEMY);

        setDefault(390, 250, 8 * 2);
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
