package weapon;

public class Sword extends Weapon {

    public Sword(String name, int damage, int range, int attackRate) {
        super(name, damage, range, attackRate);
    }

    @Override
    public void use() {
        System.out.printf("%s used\n", getName());
    }
    
}
