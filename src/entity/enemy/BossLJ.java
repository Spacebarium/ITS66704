package entity.enemy;

import java.awt.image.BufferedImage;
import java.util.Random;
import entity.Entity;
import main.GamePanel;

public class BossLJ extends Entity {
    GamePanel gp;

    public BossLJ(GamePanel gp){
        super(gp);
        this.gp=gp;
        //type=type_monster;
        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/bat_down_1", gp.getTileSize(), gp.getTileSize());
    }

    private BufferedImage setup(String path, int tileSize, int tileSize1) {
    }

    @Override
    public void getImage() {

    }

    public void setAction(){
        boolean onPath;
        if (onPath==true){
            //checkStopChasingOrNot(gp.player, 15, 100);
            //searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
        }
    }
}
