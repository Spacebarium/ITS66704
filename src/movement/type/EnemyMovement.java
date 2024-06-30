package movement.type;

import entity.type.Entity;

import java.awt.*;
import java.util.Random;

public class EnemyMovement implements Movement {

    int dx;
    int dy;
    int actionLockCounter = 0;
    Random random;
    int locationX, locationY, multiplier = 1;
    int idleCount;

    Rectangle movementBound;
    int boundX, boundY;
    int entityCentreX, entityCentreY;

    boolean boundInitialized;

    private void setMovementBound(Entity entity){
        boundX = entity.getWidth() + 200;
        boundY = entity.getHeight() + 200;
        entityCentreX = entity.getX() + (int) Math.round(entity.getWidth() / 2.0);
        entityCentreY = entity.getY() + (int) Math.round(entity.getHeight() / 2.0);
        movementBound = new Rectangle(entityCentreX - (int) Math.round(boundX / 2.0), entityCentreY - (int) Math.round(boundY / 2.0), boundX, boundY);

    }
    @Override
    public int[] getMovement(Entity entity) {
        if (!boundInitialized){
            setMovementBound(entity);
            boundInitialized = true;
        }
        dx = 0;
        dy = 0;

        if (movementBound.contains(entity.getX(), entity.getY())){
            if (!entity.getCombatStatus()) {
                if (actionLockCounter == 60) {
                    setLocation();
                    actionLockCounter = 0;
                } else {
                    actionLockCounter++;
                }
                passive(entity);
            } else {
                aggro();
            }
        }

        return new int[]{dx, dy};
    }

    private void setLocation() {
        random = new Random();
        multiplier = random.nextInt(7);

        if (idleCount < 5) {
            locationX = (random.nextInt(51) - 25) * multiplier;
            locationY = (random.nextInt(51) - 25) * multiplier;
            idleCount++;
        }
        else {
            idleCount = 0;
        }
    }

    private void passive(Entity entity) {
        int speed = entity.getSpeed();
        if (Math.abs(locationX) > 0) {
            if (locationX > 0) {
                dx += speed;
                locationX = Math.max(locationX - speed, 0);
            }
            else {
                dx += -speed;
                locationX = Math.min(locationX + speed, 0);
            }
        }

        if (Math.abs(locationY) > 0) {
            if (locationY > 0) {
                dy += speed;
                locationY = Math.max(locationY - speed, 0);
            } else {
                dy += -speed;
                locationY = Math.min(locationY + speed, 0);
            }
        }
    }

    public void aggro() {
        // Implement aggro behavior
    }

    public void attack() {
        // Implement attack behavior
    }
}
