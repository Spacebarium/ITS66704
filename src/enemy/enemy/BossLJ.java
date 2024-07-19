package entity.enemy;

import entity.EntityManager;
import java.util.Random;
import entity.type.Entity;
import entity.type.EntityType;
import main.GamePanel;

public class BossLJ extends Entity{
    GamePanel gp;

    public static final String bossName="Lee Jon";

    public BossLJ(GamePanel gp) {
        super(gp, EntityType.ENEMY, "Boss", 48, 48, 48, 48, 9, 12, 30, 36);
        this.gp = gp;

//        type=type_monster; //characteristics of LJ that should be shown in GamePanel?
//        boss=true; //not shown in GamePanel?
//        name=bossName;
//        defaultSpeed=1;
//        speed=defaultSpeed;
//        maxLife=50;
//        life=maxLife;
//        attack=10;
//        defense=2;
//        exp=50;
//        knockBackPower=5;

        getImage();
        getAttackImage();
    }

    public void getImage(){ //need images of LJ boss for this
        int i=5; //for multiplication of size of image
        setUp1(imageSetup("Boss", "BossUp1"));
        setUp2(imageSetup("Boss", "BossUp2"));
        setDown1(imageSetup("Boss", "BossDown1"));
        setDown2(imageSetup("Boss", "BossDown2"));
        setLeft1(imageSetup("Boss", "BossLeft1"));
        setLeft2(imageSetup("Boss", "BossLeft2"));
        setRight1(imageSetup("Boss", "BossRight1"));
        setRight2(imageSetup("Boss", "BossRight2"));
    }

    public void getAttackImage(){ //need images of LJ boss attack for this
        int i=5; //for multiplication of size of image
        setUp1(imageSetup("Boss", "BossAttackUp1"));
        setUp2(imageSetup("Boss", "BossAttackUp2"));
        setDown1(imageSetup("Boss", "BossAttackDown1"));
        setDown2(imageSetup("Boss", "BossAttackDown2"));
        setLeft1(imageSetup("Boss", "BossAttackLeft1"));
        setLeft2(imageSetup("Boss", "BossAttackLeft2"));
        setRight1(imageSetup("Boss", "BossAttackRight1"));
        setRight2(imageSetup("Boss", "BossAttackRight2"));
    }

    public void setAction(){
        GamePanel gp=new GamePanel();
        if(getTileDistance(gp.getPlayer())<10){
            moveTowardPlayer(60);
        }
        else{
            getRandomDirection(120);
        }
        if(attacking==false){ //Check if it attacks
            checkAttackOrNot(60, gp.getTileSize() *7, gp.getTileSize() *5);
        }
    }
}
