package entity.type;

import java.util.ArrayList;
import java.util.List;
import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;
import movement.type.PlayerMovement;
import weapon.*;

public class Player extends Entity {

    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private final List<Weapon> storedWeapons;
    private int equippedWeaponIndex;
    private static final int MAX_WEAPON_COUNT = 2;

    public Player(GamePanel gp, KeyHandler keyHandler, MouseHandler mouseHandler, PlayerMovement playerMovement) {
        super(gp, EntityType.PLAYER, "Player", 200, 200, 48, 48, 9, 12, 30, 36, playerMovement);
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        
        this.storedWeapons = new ArrayList<>();
        this.storedWeapons.add(new Sword("Dull Blade", 2, 38, 500));
        this.storedWeapons.add(new Gun("Pew Pew", 1, 240, 200));
        this.equippedWeaponIndex = 0;
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
    
    public int getEquippedWeaponIndex() {
        return equippedWeaponIndex;
    }

    public Weapon getEquippedWeapon() {
        return this.storedWeapons.get(this.equippedWeaponIndex);
    }
    
    public void removeWeaponFromSlot(int slot) {
        if (slot >= 0 && slot < MAX_WEAPON_COUNT) {
            this.storedWeapons.set(slot, null);
        }
    }
    
    public void addWeaponToSlot(Weapon weapon, int slot) {
        if (slot >= 0 && slot < MAX_WEAPON_COUNT) {
            this.storedWeapons.set(slot, weapon);
        }
    }
    
    public void switchEquippedWeapon(int slot) {
        if (slot >= 0 && slot < MAX_WEAPON_COUNT) {
            this.equippedWeaponIndex = slot;
        }
    }
    
    public Weapon getWeaponFromSlot(int slot) {
        if (slot >= 0 && slot < MAX_WEAPON_COUNT) {
            return this.storedWeapons.get(slot);
        } else {
            return null;
        }
    }
    
    public void useEquippedWeapon() {
        Weapon weapon = getEquippedWeapon();
        if (weapon != null) {
            weapon.use();
        }
    }
    
    @Override
    public void update() {
        super.update();
        
        if (keyHandler.isOne()) {
            switchEquippedWeapon(0);
        }
        if (keyHandler.isTwo()) {
            switchEquippedWeapon(1);
        }
        if (mouseHandler.isLmb()) {
            useEquippedWeapon();
        }
    }
}
