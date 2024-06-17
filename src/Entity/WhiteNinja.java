package Entity;

import Main.GamePanel;

public class WhiteNinja extends Entity {

    public WhiteNinja(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 2;
    }
}
