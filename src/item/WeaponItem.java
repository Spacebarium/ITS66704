package item;

import main.GamePanel;
import weapon.Weapon;

public class WeaponItem extends Item {
    
    private Weapon weapon;

    public WeaponItem(GamePanel gp, int x, int y, String name, String textureFilePath, Weapon weapon) {
        super(gp, x, y, name, textureFilePath);
        this.weapon = weapon;
    }
    
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
    
    public Weapon getWeapon() {
        return weapon;
    }
}
