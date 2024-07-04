package entity.type;

import entity.EntityManager;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
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
    private final EntityManager entityManager;

    public Player(GamePanel gp, KeyHandler keyHandler, MouseHandler mouseHandler, PlayerMovement playerMovement) {
        super(gp, EntityType.PLAYER, "Player", 400, 200, 48, 48, 9, 12, 30, 36, playerMovement);
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        this.entityManager = gp.entityManager;
        
        this.storedWeapons = new ArrayList<>();
        this.storedWeapons.add(new Sword("Dull Blade", 2, 48, 500, entityManager));
        this.storedWeapons.add(new Gun("Pew Pew", 1, 240, 200, entityManager));
        this.equippedWeaponIndex = 0;
        
        this.screenX = gp.getScreenWidth() / 2 - getWidth() / 2;
        this.screenY = gp.getScreenHeight() / 2 - getHeight() / 2;
        
        setSpeed(4);
        getImage();

        setMaxHealth(10);
        setHealth(getMaxHealth());
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
    
    public int getEquippedWeaponIndex() { return equippedWeaponIndex; }

    public Weapon getEquippedWeapon() {
        return storedWeapons.get(equippedWeaponIndex);
    }
    
    public void removeWeaponFromSlot(int slot) {
        if (slot >= 0 && slot < MAX_WEAPON_COUNT) {
            storedWeapons.set(slot, null);
        }
    }
    
    public void setWeaponToSlot(Weapon weapon, int slot) {
        if (slot >= 0 && slot < MAX_WEAPON_COUNT) {
            storedWeapons.set(slot, weapon);
        }
    }
    
    public void switchEquippedWeapon(int slot) {
        if (slot >= 0 && slot < MAX_WEAPON_COUNT) {
            equippedWeaponIndex = slot;
        }
    }
    
    public Weapon getWeaponFromSlot(int slot) {
        if (slot >= 0 && slot < MAX_WEAPON_COUNT) {
            return storedWeapons.get(slot);
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
    
    public Point getMousePoint() {
        Point componentP = gp.getLocationOnScreen();
        Point mouseScreenP = MouseInfo.getPointerInfo().getLocation();
        
        int mX = mouseScreenP.x - componentP.x;
        int mY = mouseScreenP.y - componentP.y;
        
        return new Point(mX, mY);
    }
    
    public void calculateWeaponPoint() {
        // https://www.desmos.com/calculator/oyir1xuqnd
        Point mousePoint = getMousePoint();

        int pX = getX() + getWidth() / 2;
        int pY = getY() + getHeight() / 2;

        int radius = getWidth();

        int deltaX = mousePoint.x - screenX;
        int deltaY = mousePoint.y - screenY;
        double dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        
        getEquippedWeapon().setPosition(new Point((int) ((radius * deltaX / dist) + pX), (int) ((radius * deltaY / dist) + pY)));
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
            calculateWeaponPoint();
            useEquippedWeapon();
        } else {
            getEquippedWeapon().setPosition(null);
        }
    }
    
    @Override
    public void draw(Graphics2D g2) {
        if (entityImage == 1) {
            image = switch (direction) {
                case "up" -> up1;
                case "down" -> down1;
                case "left" -> left1;
                case "right" -> right1;
                default -> idle;
            };
        } else if (entityImage == 2) {
            image = switch (direction) {
                case "up" -> up2;
                case "down" -> down2;
                case "left" -> left2;
                case "right" -> right2;
                default -> idle;
	    };
        } else {
            image = idle;
        }
        setEntityImage();

        g2.drawImage(image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
    }
}
