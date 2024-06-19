package movement.type;

import entity.type.Entity;
import main.KeyHandler;

public class PlayerMovement implements Movement {

    private KeyHandler keyHandler;

    public PlayerMovement(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    @Override
    public int[] getMovement(Entity entity) {
        int dx = 0;
        int dy = 0;

        if (keyHandler.isUp()) {
            dy -= entity.getSpeed();
        }
        if (keyHandler.isDown()) {
            dy += entity.getSpeed();
        }
        if (keyHandler.isLeft()) {
            dx -= entity.getSpeed();
        }
        if (keyHandler.isRight()) {
            dx += entity.getSpeed();
        }

        return new int[]{dx, dy};
    }

}
