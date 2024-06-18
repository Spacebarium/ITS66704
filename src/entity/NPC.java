package entity;

import main.GamePanel;

import java.util.Random;

public class NPC extends Entity {

    private int actionLockCounter = 0;

    public NPC(GamePanel gp) {
        super(gp);
    }

    public void setAction() {
        actionLockCounter++;
        int i = -1;
        int j = -1;
        Random random = new Random();

       if (actionLockCounter == 60) {
           for (int x = 0; x <= 5; x++){
               i = random.nextInt(100) + 1;
               j = random.nextInt(100) + 1;
           }
            actionLockCounter = 0;
       }

        if (i > 0 && i <= 40) {
            setDirection("up");
            gp.colChecker.checkTop(this);
            if (TopCol()) {
                updateColumn(-getSpeed());
            }
        }
        if (i > 65 && i <= 100) {
            setDirection("down");
            gp.colChecker.checkBot(this);
            if (BotCol()) {
                updateColumn(getSpeed());
            }
        }
        if (j > 0 && j <= 40) {
            setDirection("left");
            gp.colChecker.checkLeft(this);
            if (LeftCol()) {
                updateRow(-getSpeed());
            }
        }
        if (j > 65 && j <= 100){
            setDirection("right");
            gp.colChecker.checkRight(this);
            if (RightCol()) {
                updateRow(getSpeed());
            }
        }

    }
}
