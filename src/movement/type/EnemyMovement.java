package movement.type;

import entity.type.Entity;

import java.util.Random;

public class EnemyMovement implements Movement {

    int dx;
    int dy;
    int actionLockCounter = 0;
    int randomVertical;
    int randomHorizontal;
    int directionMultiplier;

    @Override
    public int[] getMovement(Entity entity) {
//        if see player:
//            get player last seen coordinate
//            do math
//            move to coordinate based on speed
//        
//        if idle:
//            pick random walkable spot within like 10 units range
//            do math
//            move to the spot
//                    
//        if agro on player:
//            get player coordinate
//            do math
//            keep moving towards player or something

        dx = 0;
        dy = 0;

        if (!entity.getCombatStatus()) {

            passive(entity);
        } else {
            aggro();
        }
        return new int[]{dx, dy};
    }

    public void passive(Entity entity) {
        Random random = new Random();

        if (actionLockCounter == 10) {
            randomVertical = random.nextInt(101);
            randomHorizontal = random.nextInt(101);
            directionMultiplier = random.nextInt(10) + 1;
            actionLockCounter = 0;

            dy += entity.getSpeed() * directionMultiplier;
            dx += entity.getSpeed() * directionMultiplier;
//            if (randomVertical > 5 && randomVertical <= 15) {
//                dy -= entity.getSpeed()*directionMultiplier;
//            }
//            if (randomVertical > 30 && randomVertical <= 45){
//                dy += entity.getSpeed()*directionMultiplier;
//            }
//            if (randomHorizontal > 5 && randomHorizontal <= 15){
//                dx -= entity.getSpeed()*directionMultiplier;
//            }
//            if (randomHorizontal > 30 && randomHorizontal <= 45){
//                dx += entity.getSpeed()*directionMultiplier;
//            }

        } else {
            actionLockCounter++;
            randomVertical = 0;
            randomHorizontal = 0;
            directionMultiplier = 0;
        }
    }

    public void aggro() {

    }

    public void attack() {

    }
}
