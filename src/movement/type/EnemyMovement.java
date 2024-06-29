package movement.type;

import entity.type.Entity;

import java.util.Random;

public class EnemyMovement implements Movement {

    int dx;
    int dy;
    int actionLockCounter = 0;
    Random random = new Random();
    int targetX, targetY;

    public EnemyMovement() {
        setNewTarget();
    }

    @Override
    public int[] getMovement(Entity entity) {
        dx = 0;
        dy = 0;

        if (!entity.getCombatStatus()) {
            if (actionLockCounter == 60) {
                setNewTarget();
                actionLockCounter = 0;
            } else {
                actionLockCounter++;
            }
            smoothMove(entity);
        } else {
            aggro();
        }

        return new int[]{dx, dy};
    }

    private void setNewTarget() {
        targetX = random.nextInt(100) - 50; // Example range, adjust as needed
        targetY = random.nextInt(100) - 50; // Example range, adjust as needed
    }

    private void smoothMove(Entity entity) {
        float speed = entity.getSpeed();

        // Move towards the target position
        if (Math.abs(targetX) > speed) {
            dx += (targetX > 0) ? speed : -speed;
            targetX -= (targetX > 0) ? speed : -speed;
        } else {
            dx += targetX;
            targetX = 0;
        }

        if (Math.abs(targetY) > speed) {
            dy += (targetY > 0) ? speed : -speed;
            targetY -= (targetY > 0) ? speed : -speed;
        } else {
            dy += targetY;
            targetY = 0;
        }
    }

    public void aggro() {
        // Implement aggro behavior
    }

    public void attack() {
        // Implement attack behavior
    }
}
