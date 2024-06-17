package entity;

import main.GamePanel;

public class WhiteNinja extends Entity {

    public WhiteNinja(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 2;
    }
}
