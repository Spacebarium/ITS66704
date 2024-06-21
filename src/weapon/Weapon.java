package weapon;

public abstract class Weapon {
    private String name;
    private int    damage;
    private int    range;
    private int    attackRate;
    
    public Weapon(String name, int damage, int range, int attackRate) {
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.attackRate = attackRate; // in milliseconds
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
    
    public abstract void use();
}
