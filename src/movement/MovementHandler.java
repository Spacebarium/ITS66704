package movement;

import entity.type.Entity;
import main.CollisionHandler;
import movement.type.Movement;

public class MovementHandler {

    private final Entity entity;
    private final CollisionHandler collisionHandler;
    private final Movement baseMovement;
    private int dx;
    private int dy;

    public MovementHandler(Entity entity, CollisionHandler collisionHandler, Movement movement) {
        this.entity = entity;
        this.collisionHandler = collisionHandler;
        this.baseMovement = movement;
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
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void update() {
        int[] movement = baseMovement.getMovement(entity);
        dx = movement[0];
        dy = movement[1];
        
        if (collisionHandler.canMove(entity, dx, 0)) {
            if (dy != 0 && collisionHandler.canMove(entity, 0, dy)) {
                dx = (int) Math.round(dx / Math.sqrt(2));
            }
        } else {
            dx = 0;
        }
        
        if (collisionHandler.canMove(entity, 0, dy)) {
            if (dx != 0 && collisionHandler.canMove(entity, dx, 0)) {
                dy = (int) Math.round(dy / Math.sqrt(2));
            }
        } else {
            dy = 0;
        }
        
        entity.setX(entity.getX() + dx);
        entity.setY(entity.getY() + dy);
    }
}
