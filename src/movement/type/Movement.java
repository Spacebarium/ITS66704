package movement.type;

import enemy.type.Entity;

public interface Movement {
    int[] getMovement(Entity entity);
}
