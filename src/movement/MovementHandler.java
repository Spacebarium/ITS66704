package movement;

import entity.Entity;
import java.awt.Rectangle;
import main.CollisionHandler;
import movement.type.Movement;

public class MovementHandler {
    private Entity entity;
    private CollisionHandler collisionHandler;
    private Movement baseMovement;

    public MovementHandler(Entity entity, CollisionHandler collisionHandler, Movement movement) {
        this.entity = entity;
        this.collisionHandler = collisionHandler;
        this.baseMovement = movement;
    }
    
    public void update() {
        int[] movement = baseMovement.getMovement(entity);
        int dx = movement[0];
        int dy = movement[1];

        Rectangle newHitbox = new Rectangle(entity.getX() + dx, entity.getY() + dy, entity.getWidth(), entity.getHeight());

        if (true) { // check collision
            entity.setX(entity.getX() + dx);
            entity.setY(entity.getY() + dy);
        }
    }
}
