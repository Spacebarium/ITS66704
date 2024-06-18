package entity;

import main.KeyHandler;
import main.GamePanel;

public class Player extends Entity{
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;

        setName("Player");
        setEntityType(Type.PLAYER);
        setDefault(48, 48, 4);
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
    }

        public void update() {
            if (keyH.upPressed) {
                setDirection("up");
                gp.colChecker.checkTop(this);
                if (TopCol()) {
                    updateColumn(-getSpeed());
                }
            }
            if (keyH.downPressed) {
                setDirection("down");
                gp.colChecker.checkBot(this);
                if (BotCol()) {
                    updateColumn(getSpeed());
                }
            }
            if (keyH.rightPressed) {
                setDirection("right");
                gp.colChecker.checkRight(this);
                if (RightCol()) {
                    updateRow(getSpeed());
                }
            }
            if (keyH.leftPressed) {
                gp.colChecker.checkLeft(this);
                setDirection("left");
                if (LeftCol()) {
                    updateRow(-getSpeed());
                }
            }
            setEntityImage();
        }
}
