package movement.type;

import entity.type.Entity;


public class NoMovement implements Movement {

    @Override
    public int[] getMovement(Entity entity) {
        return new int[]{0, 0};
    }

}