//package entity.enemy;
//
//import entity.type.Enemy;
//import entity.type.EntityType;
//import main.GamePanel;
//
//public class Boss extends Enemy {
//    GamePanel gp;
//
//    public static final String bossName="Lee Jon";
//
//    public Boss(GamePanel gp) {
//        super(gp, EntityType.ENEMY, "Boss", 48, 48, 48, 48, 9, 12, 30, 36);
//        this.gp = gp;
//
//        getImage();
//        getAttackImage();
//    }
//
//    public void getImage(){ //need images of LJ boss for this
//        int i=5; //for multiplication of size of image
//        setUp1(imageSetup("Boss", "BossUp1"));
//        setUp2(imageSetup("Boss", "BossUp2"));
//        setDown1(imageSetup("Boss", "BossDown1"));
//        setDown2(imageSetup("Boss", "BossDown2"));
//        setLeft1(imageSetup("Boss", "BossLeft1"));
//        setLeft2(imageSetup("Boss", "BossLeft2"));
//        setRight1(imageSetup("Boss", "BossRight1"));
//        setRight2(imageSetup("Boss", "BossRight2"));
//    }
//
//    public void getAttackImage(){ //need images of LJ boss attack for this
//        int i=5; //for multiplication of size of image
//        setUp1(imageSetup("Boss", "BossAttackUp1"));
//        setUp2(imageSetup("Boss", "BossAttackUp2"));
//        setDown1(imageSetup("Boss", "BossAttackDown1"));
//        setDown2(imageSetup("Boss", "BossAttackDown2"));
//        setLeft1(imageSetup("Boss", "BossAttackLeft1"));
//        setLeft2(imageSetup("Boss", "BossAttackLeft2"));
//        setRight1(imageSetup("Boss", "BossAttackRight1"));
//        setRight2(imageSetup("Boss", "BossAttackRight2"));
//    }
//
//    public void setAction(){
//        GamePanel gp=new GamePanel();
//        if(getTileDistance(gp.getPlayer())<10){
//            moveTowardPlayer(60);
//        }
//        else{
//            getRandomDirection(120);
//        }
//        if(attacking==false){ //Check if it attacks
//            checkAttackOrNot(60, gp.getTileSize() *7, gp.getTileSize() *5);
//        }
//    }
//}