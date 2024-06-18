package entity;

import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;
import weapon.*;

public class Player extends Entity {
    KeyHandler keyHandler;
    MouseHandler mouseHandler;
    private Weapon equip1, equip2;

    public Player(GamePanel gp, KeyHandler keyHandler, MouseHandler mouseHandler) {
        super(gp);
        this.mouseHandler = mouseHandler;
        this.keyHandler = keyHandler;
        this.equip1 = new Sword();
        this.equip2 = null;

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
        setIdle(imageSetup("blackNinja", "blackDown1"));
    }

    @Override
    public void update() {
        if (keyHandler.upPressed) {
            setDirection("up");
            gp.colChecker.checkTop(this);
            if (topCol()) {
                updateY(-getSpeed());
            }
        }
        if (keyHandler.downPressed) {
            setDirection("down");
            gp.colChecker.checkBot(this);
            if (botCol()) {
                updateY(getSpeed());
            }
        }
        if (keyHandler.rightPressed) {
            setDirection("right");
            gp.colChecker.checkRight(this);
            if (rightCol()) {
                updateX(getSpeed());
            }
        }
        if (keyHandler.leftPressed) {
            gp.colChecker.checkLeft(this);
            setDirection("left");
            if (leftCol()) {
                updateX(-getSpeed());
            }
<<<<<<< HEAD
            if (!keyH.leftPressed && !keyH.rightPressed && !keyH.upPressed && !keyH.downPressed){
                setDirection("idle");
                return;
            }
            setEntityImage();
=======
>>>>>>> 34f01f45b103c62c6c17a07b3441b053082277a5
        }
        setEntityImage();
    }
    
    public void equipWeapon(Weapon weapon) {
        this.equip1 = weapon;
    }
    
    public String getWeaponName() {
        return this.equip1.getName();
    }
}
