package entity;

import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;
import weapon.*;

public class Player extends Entity {

    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private Weapon weaponSlot1;
    private Weapon weaponSlot2;
    private Weapon equippedWeapon;

    public Player(GamePanel gp, KeyHandler keyHandler, MouseHandler mouseHandler) {
        super(gp, 48, 48, 48, 48);
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        this.weaponSlot1 = null;
        this.weaponSlot2 = null;
        this.equippedWeapon = null;

        setName("Player");
        setEntityType(Type.PLAYER);
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

    public void equipWeapon(Weapon weapon) {
        this.weaponSlot1 = weapon;
    }

    public String getWeaponName() {
        return (this.equippedWeapon != null) ? this.equippedWeapon.getName() : null;
    }
}
