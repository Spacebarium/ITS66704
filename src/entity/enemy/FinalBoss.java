//package entity.enemy;
//
//import entity.type.Enemy;
//import entity.type.Player;
//import main.GamePanel;
//import movement.type.EnemyMovement;
//
//public class FinalBoss extends Enemy {
//
//    public FinalBoss(GamePanel gp, EnemyMovement enemyMovement, Player player) {
//        super(gp, "finalBoss", 16 * gp.getScale(), 16 * gp.getScale(), 9, 12, 30, 36);
//        setSpeed(4);
//        getImage();
//    }
//
//    public void getImage() {
//        setUp1(imageSetup("FinalBoss", "FinalBossLeft1"));
//        setUp2(imageSetup("FinalBoss", "FinalBossLeft3"));
//        setDown1(imageSetup("FinalBoss", "FinalBossRight1"));
//        setDown2(imageSetup("FinalBoss", "FinalBossRight2"));
//        setLeft1(imageSetup("FinalBoss", "FinalBossLeft1"));
//        setLeft2(imageSetup("FinalBoss", "FinalBossLeft2"));
//        setRight1(imageSetup("FinalBoss", "FinalBossRight1"));
//        setRight2(imageSetup("FinalBoss", "FinalBossRight2"));
//        setIdle(imageSetup("FinalBoss", "FinalBossLeft1"));
//    }
//}
//
