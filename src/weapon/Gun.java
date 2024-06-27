package weapon;

public class Gun extends Weapon {

    public Gun(String name, int damage, int range, int attackRate) {
        super(name, damage, range, attackRate);
    }

    @Override
    public void use() {
        System.out.printf("%s used\n", getName());
    }

}
