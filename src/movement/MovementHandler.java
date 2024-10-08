package movement;

import main.CollisionChecker;
import entity.type.Entity;
import movement.type.Movement;

public class MovementHandler {

    private final Entity entity;
    private final CollisionChecker collisionChecker;
    private final Movement movement;
    private int dx;
    private int dy;

    public MovementHandler(Entity entity, CollisionChecker collisionChecker, Movement movement) {
        this.entity = entity;
        this.collisionChecker = collisionChecker;
        this.movement = movement;
    }

    public Entity getEntity() {
        return entity;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public double getSpeed() {
        return Math.hypot(dx, dy);
    }

    public void update() {
        int[] vector = movement.getMovement(entity);
        dx = vector[0];
        dy = vector[1];
        
        int stepX = Integer.signum(dx);
        int stepY = Integer.signum(dy);
        
        if (dx != 0 && dy != 0 && collisionChecker.canMove(entity, stepX, stepY)) {
            dx = (int) Math.round(dx / Math.sqrt(2));
            dy = (int) Math.round(dy / Math.sqrt(2));
        }

        int stepsX = dx;
        int stepsY = dy;

        if (stepsX > 0) {
            entity.setDirection("right");
        } else if (stepsX < 0) {
            entity.setDirection("left");
        }
        if (stepsY > 0) {
            entity.setDirection("down");
        } else if (stepsY < 0) {
            entity.setDirection("up");
        }
        if (stepsX == 0 && stepsY == 0) {
            entity.setDirection("idle");
        }

        while (stepsX != 0) {
            if (collisionChecker.canMove(entity, stepX, 0)) {
                entity.setX(entity.getX() + stepX);
                stepsX -= stepX;
            } else {
                dx = 0;
                break;
            }
        }

        while (stepsY != 0) {
            if (collisionChecker.canMove(entity, 0, stepY)) {
                entity.setY(entity.getY() + stepY);
                stepsY -= stepY;
            } else {
                dy = 0;
                break;
            }
        }
    }
}
