package movement.type;

import entity.type.Entity;
import main.KeyHandler;

public class PlayerMovement implements Movement {

    private final KeyHandler keyHandler;

    public PlayerMovement(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    @Override
    public int[] getMovement(Entity entity) {
        int dx = 0;
        int dy = 0;

        if (keyHandler.isUp()) {
            entity.setDirection("up");
            dy -= entity.getSpeed();
        }
        if (keyHandler.isDown()) {
            entity.setDirection("down");
            dy += entity.getSpeed();
        }
        if (keyHandler.isLeft()) {
            entity.setDirection("left");
            dx -= entity.getSpeed();
        }
        if (keyHandler.isRight()) {
            entity.setDirection("right");
            dx += entity.getSpeed();
        }
        if (!(keyHandler.isUp() || keyHandler.isDown() || keyHandler.isLeft() || keyHandler.isRight())){
            entity.setDirection("idle");
        }

        return new int[]{dx, dy};
    }

}
