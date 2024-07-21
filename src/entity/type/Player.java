package entity.type;

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
import item.Item;
import item.KeyItem;
import item.WeaponItem;

public class Player extends Entity {

    private final int MAX_WEAPON_COUNT = 2;
    private final int PICKUP_RADIUS = 1;
    
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private final List<Weapon> storedWeapons;
    private int equippedWeaponIndex;
    private final int weaponOffset;
    private boolean hasKey;

    public Player(GamePanel gp, KeyHandler keyHandler, MouseHandler mouseHandler, PlayerMovement playerMovement) {
        super(gp, EntityType.PLAYER, "Player", 16 * gp.getScale(), 16 * gp.getScale(), 3 * gp.getScale(), 4 * gp.getScale(), 10 * gp.getScale(), 12 * gp.getScale(), playerMovement);

        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        
        this.storedWeapons = new ArrayList<>();
        this.storedWeapons.add(null);
        this.storedWeapons.add(null);
        this.equippedWeaponIndex = 0;
        this.weaponOffset = getWidth() / 2;
        this.hasKey = false;
        
        this.screenX = gp.getSize().width / 2 - getWidth() / 2;
        this.screenY = gp.getSize().height / 2 - getHeight() / 2;
        
        setSpeed(gp.getScale() + 2);
        getImage();

        setMaxHealth(10);
        setHealth(getMaxHealth());
    }
    
    public int getPickupRadius() {
        return PICKUP_RADIUS;
    }

    public void getImage() {
        String folderName = "entity/player";
        setUp1(imageSetup(folderName, "up1"));
        setUp2(imageSetup(folderName, "up2"));
        setDown1(imageSetup(folderName, "down1"));
        setDown2(imageSetup(folderName, "down2"));
        setLeft1(imageSetup(folderName, "left1"));
        setLeft2(imageSetup(folderName, "left2"));
        setRight1(imageSetup(folderName, "right1"));
        setRight2(imageSetup(folderName, "right2"));
        setIdle(imageSetup(folderName, "idle"));
    }
    
    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }
    
    public boolean getHasKey() {
        return hasKey;
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
        if (weapon != null && weapon.canAttack()) {
            weapon.use();
        }
    }
    
    public Point getScreenMouse() {
        Point componentP = gp.getLocationOnScreen();
        Point mouseScreenP = MouseInfo.getPointerInfo().getLocation();
        
        int mX = mouseScreenP.x - componentP.x;
        int mY = mouseScreenP.y - componentP.y;
        
        return new Point(mX, mY);
    }
    
    public Point getWorldMouse() {
        Point mouse = getScreenMouse();
        return new Point(mouse.x + getX() - getScreenX(), mouse.y + getY() - getScreenY());
    }
    
    public Point calculateWeaponPoint() {
        // https://www.desmos.com/calculator/oyir1xuqnd
        Point mousePoint = getScreenMouse();

        int pX = getX() + getWidth() / 2;
        int pY = getY() + getHeight() / 2;

        int deltaX = mousePoint.x - screenX - getWidth() / 2;
        int deltaY = mousePoint.y - screenY - getHeight() / 2;
        double dist = Math.hypot(deltaX, deltaY);
        
        return new Point((int) ((weaponOffset * deltaX / dist) + pX), (int) ((weaponOffset * deltaY / dist) + pY));
    }
    
    public int getWeaponOffset() {
        return weaponOffset;
    }
    
    public void pickUpWeaponItem(WeaponItem weaponItem) {
        Weapon weapon = weaponItem.getWeapon();
        
        if (getEquippedWeapon() != null) {
            weaponItem.setWeapon(getEquippedWeapon());
            setWeaponToSlot(weapon, getEquippedWeaponIndex());
        } else {
            setWeaponToSlot(weapon, getEquippedWeaponIndex());
            gp.itemManager.removeItem(weaponItem);
        }
    }
    
    public void pickUpKeyItem(KeyItem keyItem) {
        this.hasKey = true;
        gp.itemManager.removeItem(keyItem);
    }
    
    @Override
    public void update() {
        super.update();
        
        switch (getEquippedWeapon()) {
            case Sword sword -> sword.update();
            default          -> {}
        };
        
        this.screenX = gp.getSize().width / 2 - getWidth() / 2;
        this.screenY = gp.getSize().height / 2 - getHeight() / 2;
        
        if (keyHandler.isOne()) {
            switchEquippedWeapon(0);
        }
        if (keyHandler.isTwo()) {
            switchEquippedWeapon(1);
        }
        if (mouseHandler.isLmb()) {
            getEquippedWeapon().setPosition(calculateWeaponPoint());
            useEquippedWeapon();
        } else {
            getEquippedWeapon().setPosition(null);
        }
        if (keyHandler.isInteract()) {
            List<Item> itemsInRange = gp.itemManager.getItemsInRange(getX(), getY(), PICKUP_RADIUS);

            for (Item item : itemsInRange) {
                if (item instanceof WeaponItem) {
                    pickUpWeaponItem((WeaponItem) item);
                } else if (item instanceof KeyItem) {
                    pickUpKeyItem((KeyItem) item);
                }
            }
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        getEquippedWeapon().draw(g2);
    }
}
