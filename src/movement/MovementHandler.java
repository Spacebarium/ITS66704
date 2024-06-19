package movement;

import entity.type.Entity;
import main.CollisionHandler;
import movement.type.Movement;

public class MovementHandler {
    private final Entity entity;
    private final CollisionHandler collisionHandler;
    private final Movement baseMovement;

    public MovementHandler(Entity entity, CollisionHandler collisionHandler, Movement movement) {
        this.entity = entity;
        this.collisionHandler = collisionHandler;
        this.baseMovement = movement;
    }
    
    public void update() {
        int[] movement = baseMovement.getMovement(entity);
        int dx = movement[0];
        int dy = movement[1];

        if (collisionHandler.canMove(entity, dx, dy)) { // check collision
            entity.setX(entity.getX() + dx);
            entity.setY(entity.getY() + dy);
        } else {
            System.out.println("cannot move");
        }
    }
}
