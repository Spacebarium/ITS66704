package weapon;

public abstract class Weapon {
    private String name;
    private int    damage;
    private int    range;
    private int    attackRate;
    private int    currentCooldown;
    
    public Weapon(String name, int damage, int range, int attackRate) {
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.attackRate = attackRate; // in frames @60fps because honestly screw it lol idk a better way to implement this
        this.currentCooldown = 0;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public int getAttackRate() {
        return attackRate;
    }
    
    public void use() {
        if (currentCooldown == 0) {
            currentCooldown = attackRate;
            System.out.printf("%s used\n", getName());
        } else {
            currentCooldown--;
        }
    };
}
