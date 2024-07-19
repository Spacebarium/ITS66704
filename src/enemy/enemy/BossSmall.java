package entity.enemy;

import entity.EntityManager;
import java.util.Random;
import entity.type.Entity;
import entity.type.EntityType;
import main.GamePanel;

public class BossSmall extends Entity{
    GamePanel gp;

    public BossSmall(GamePanel gp) {
        super(gp, EntityType.ENEMY, "Boss", 48, 48, 48, 48, 9, 12, 30, 36);
        this.gp = gp;

//        type=type_monster; //characteristics of LJ that should be shown in GamePanel?
//        boss=true; //not shown in GamePanel?
//        name=bossName;
//        defaultSpeed=1;
//        speed=defaultSpeed;
//        getMaxHealth()=50;
//        health=maxLife;
//        attack=10;
//        defense=2;
//        exp=50;
//        knockBackPower=5;
//        attackArea.width=48; //for attacking area tile size purposes
//        attackArea.height=48;

        getImage();
        getAttackImage();
    }

    public void getImage(){
        setUp1(imageSetup("BossMini", "BossUp1"));
        setUp2(imageSetup("BossMini", "BossUp2"));
        setDown1(imageSetup("BossMini", "BossDown1"));
        setDown2(imageSetup("BossMini", "BossDown2"));
        setLeft1(imageSetup("BossMini", "BossLeft1"));
        setLeft2(imageSetup("BossMini", "BossLeft2"));
        setRight1(imageSetup("BossMini", "BossRight1"));
        setRight2(imageSetup("BossMini", "BossRight2"));
    }

    public void getAttackImage(){
        setUp1(imageSetup("BossMini", "BossAttackUp1"));
        setUp2(imageSetup("BossMini", "BossAttackUp2"));
        setDown1(imageSetup("BossMini", "BossAttackDown1"));
        setDown2(imageSetup("BossMini", "BossAttackDown2"));
        setLeft1(imageSetup("BossMini", "BossAttackLeft1"));
        setLeft2(imageSetup("BossMini", "BossAttackLeft2"));
        setRight1(imageSetup("BossMini", "BossAttackRight1"));
        setRight2(imageSetup("BossMini", "BossAttackRight2"));
    }

    public void setAction(){
        GamePanel gp=new GamePanel();
        if(onPath==true){
            checkStopChasingOrNot(gp.getPlayer(), 15, 100);
            searchPath(getGoalCol(gp.getPlayer()), getGoalRow(gp.getPlayer()));
        }
        else{
            checkStartChasingOrNot(gp.getPlayer(), 5, 100);
            getRandomDirection(120);
        }
        if(attacking==false){ //Check if it attacks
            checkAttackOrNot(60, gp.getTileSize() *7, gp.getTileSize() *5);
        }
    }
}
