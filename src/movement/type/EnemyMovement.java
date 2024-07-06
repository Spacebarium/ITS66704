package movement.type;

import entity.type.Enemy;
import entity.type.Entity;

import java.awt.*;
import java.util.Random;

public class EnemyMovement implements Movement {

    int dx;
    int dy;

    int initialX, initialY;

    Random random;
    int actionLockCounter = 0;
    int distanceX, distanceY, multiplier = 1;
    int idleCount;

    Rectangle movementBound;
    boolean boundInitialized;
    boolean originInitialized;

    private void setOrigin(Enemy enemy) {
        initialX = enemy.getX();
        initialY = enemy.getY();
    }

    private void setMovementBound(Enemy enemy) {
        final int boundX = enemy.getWidth() + 200;
        final int boundY = enemy.getHeight() + 200;
        movementBound = new Rectangle(enemy.getCentreX() - (int) Math.round(boundX / 2.0), enemy.getCentreY() - (int) Math.round(boundY / 2.0), boundX, boundY);
    }

    private boolean rightXBoundCheck(Enemy enemy) {
        return (enemy.getHitbox().x + enemy.getHitbox().width) <= (movementBound.x + movementBound.width);
    }

    private boolean leftXBoundCheck(Enemy enemy) {
        return enemy.getHitbox().x >= movementBound.x;
    }

    private boolean downYBoundCheck(Enemy enemy) {
        return (enemy.getHitbox().y + enemy.getHitbox().height) <= (movementBound.y + movementBound.height);
    }

    private boolean upYBoundCheck(Enemy enemy) {
        return enemy.getHitbox().y >= movementBound.y;
    }

    private boolean setActionLock() {
        if (actionLockCounter == 60) {
            actionLockCounter = 0;
            return true;
        } else {
            actionLockCounter++;
            return false;
        }
    }

    private void setLocation() {
        random = new Random();
        multiplier = random.nextInt(7);

        if (idleCount < 5) {
            distanceX = (random.nextInt(51) - 25) * multiplier;
            distanceY = (random.nextInt(51) - 25) * multiplier;
            idleCount++;
        } else {
            idleCount = 0;
        }
    }

    private void passive(Enemy enemy) {
        move(distanceX, distanceY, enemy.getSpeed(), enemy);
    }

    public void aggro(Enemy enemy) {
        distanceX = enemy.getXDistance();
        distanceY = enemy.getYDistance();

        enemy.cooldownCounter();

        if (enemy.getDistanceToPlayer() <= enemy.attackRange + enemy.getPlayerHitbox()) {
            enemy.attack();
        } else {
            move(distanceX, distanceY, enemy.getSpeed());
        }
    }

    public void move(int distanceX, int distanceY, int speed) {
        distanceX = -distanceX;
        distanceY = -distanceY;
        if (Math.abs(distanceX) > 0) {
            if (distanceX > 0) {
                dx += Math.min(speed, distanceX);
                this.distanceX = Math.max(distanceX - speed, 0);
            } else if (distanceX < 0) {
                dx += Math.max(-speed, distanceX);
                this.distanceX = Math.min(distanceX + speed, 0);
            } else {
                this.distanceX = 0;
            }
        }

        if (Math.abs(distanceY) > 0) {
            if (distanceY > 0) {
                dy += Math.min(speed, distanceY);
                this.distanceY = Math.max(distanceY - speed, 0);
            } else if (distanceY < 0) {
                dy += Math.max(-speed, distanceY);
                this.distanceY = Math.min(distanceY + speed, 0);
            } else {
                this.distanceY = 0;
            }
        }
    }

    public void move(int distanceX, int distanceY, int speed, Enemy enemy) {
        if (Math.abs(distanceX) > 0) {
            if (distanceX > 0 && rightXBoundCheck(enemy)) {
                dx += Math.min(speed, distanceX);
                this.distanceX = Math.max(distanceX - speed, 0);
            } else if (distanceX < 0 && leftXBoundCheck(enemy)) {
                dx += Math.max(-speed, distanceX);
                this.distanceX = Math.min(distanceX + speed, 0);
            } else {
                this.distanceX = 0;
            }
        }

        if (Math.abs(distanceY) > 0) {
            if (distanceY > 0 && downYBoundCheck(enemy)) {
                dy += Math.min(speed, distanceY);
                this.distanceY = Math.max(distanceY - speed, 0);
            } else if (distanceY < 0 && upYBoundCheck(enemy)) {
                dy += Math.max(-speed, distanceY);
                this.distanceY = Math.min(distanceY + speed, 0);
            } else {
                this.distanceY = 0;
            }
        }
    }

    @Override
    public int[] getMovement(Entity entity) {
        Enemy enemy = (Enemy) entity;
        dx = 0;
        dy = 0;

        if (!originInitialized) {
            setOrigin(enemy);
            originInitialized = true;
        }

        if (!boundInitialized) {
            setMovementBound(enemy);
            boundInitialized = true;
        }

        if (!enemy.getCombatStatus()) {
            if (!movementBound.contains(enemy.getCentreX(), enemy.getCentreY())) {
                move(enemy.getCentreX() - initialX, enemy.getCentreY() - initialY, enemy.getSpeed());
            } else {
                if (setActionLock()) {
                    setLocation();
                } else {
                    passive(enemy);
                }
            }
        } else {
            aggro(enemy);
        }

        return new int[]{dx, dy};
    }
}
