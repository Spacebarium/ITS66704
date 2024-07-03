//package entity.type;
//
//import entity.EntityManager;
//import java.util.Random;
//import entity.type.Entity;
//import main.GamePanel;
//
//public class Boss extends Entity{
//    GamePanel gp;
//
//    public static final String bossName="Lee Jon";
//
//    public Boss(GamePanel gp) {
//        super(gp);
//        this.gp = gp;
//
//        type=type_monster; //characteristics of LJ that should be shown in GamePanel?
//        boss=true;
//        name=bossName;
//        defaultSpeed=1;
//        speed=defaultSpeed;
//        maxLife=50;
//        life=maxLife;
//        attack=10;
//        defense=2;
//        exp=50;
//        knockBackPower=5;
//
//        int size=gp.tileSize*5;
//        solidArea.x=48;
//        solidArea.y=48;
//
//        getImage();
//        getAttackImage();
//    }
//
//    public void getImage(){ //need images of LJ boss for this
//        int i=5; //for multiplication of size of image
//        //up1=setup("/monster)
//    }
//
//    public void getAttackImage(){ //need images of LJ boss attack for this
//        int i=5; //for multiplication of size of image
//        //attackUp1
//    }
//
//    public void setAction(){
//        if(getTileDistance(gp.player)<10){
//            moveTowardPlayer(60);
//        }
//        else{
//            getRandomDirection(120);
//        }
//        if(attacking==false){
//            checkAttackOrNot(60, gp.tileSize*10, gp.tileSize*5);
//        }
//    }
//}
