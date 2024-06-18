package weapon;

public abstract class Weapon {
    String name;
    int    damage;
    double range;
    double cooldown;
    
    public Weapon(String name, int damage, double range, double cooldown) {
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.cooldown = cooldown;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public double getRange() {
        return range;
    }

    public double getCooldown() {
        return cooldown;
    }
    
    public abstract void use();
}
