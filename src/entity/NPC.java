package entity;

import main.GamePanel;

import java.util.Random;

public class NPC extends Entity {

    private int actionLockCounter = 0;

    public NPC(GamePanel gp) {
        super(gp);
    }

    @Override
    public void setAction() {
        actionLockCounter++;
        int i = -1;
        int j = -1;
        Random random = new Random();

       if (actionLockCounter == 30) {
           for (int x = 0; x <= 5; x++){
               i = random.nextInt(100) + 1;
               j = random.nextInt(100) + 1;
           }
            actionLockCounter = 0;
       }

        if (i > 0 && i <= 20) {
            setDirection("up");
            gp.colChecker.checkTop(this);
            if (topCol()) {
                updateY(-getSpeed());
            }
        }
        if (i > 80 && i <= 100) {
            setDirection("down");
            gp.colChecker.checkBot(this);
            if (botCol()) {
                updateY(getSpeed());
            }
        }
        if (j > 0 && j <= 20) {
            setDirection("left");
            gp.colChecker.checkLeft(this);
            if (leftCol()) {
                updateX(-getSpeed());
            }
        }
        if (j > 80 && j <= 100){
            setDirection("right");
            gp.colChecker.checkRight(this);
            if (rightCol()) {
                updateX(getSpeed());
            }
        }

        if ((i > 20 && i <=80) && (j > 20 && j <=80)){
            setDirection("idle");
        }
    }
}
