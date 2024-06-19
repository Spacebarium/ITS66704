package movement.type;

import entity.Entity;

public class EnemyMovement implements Movement {

    @Override
    public int[] getMovement(Entity entity) {
        return new int[]{0, 0};
    }
}
