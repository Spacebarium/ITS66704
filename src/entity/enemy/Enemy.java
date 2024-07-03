package entity.enemy;

import entity.type.Entity;
import entity.type.EntityType;
import entity.type.Player;
import main.GamePanel;
import movement.type.EnemyMovement;

public class Enemy extends Entity {

    Player player;
    final int aggroDistance = 160;
    int coolDown = 2 * 60;
    public int attackRange = 16;
    int attackCoolDown = coolDown;
    boolean canAttack = true;

    public Enemy(GamePanel gp, EnemyMovement enemyMovement, Player player) {
        super(gp, EntityType.ENEMY, "White ninja", 200, 200, 48, 48, 9, 12, 30, 36, enemyMovement);

        setSpeed(4);
        getImage();

        this.player = player;
    }

    public void getImage() {
        setUp1(imageSetup("whiteNinja", "whiteUp1"));
        setUp2(imageSetup("whiteNinja", "whiteUp2"));
        setDown1(imageSetup("whiteNinja", "whiteDown1"));
        setDown2(imageSetup("whiteNinja", "whiteDown2"));
        setLeft1(imageSetup("whiteNinja", "whiteLeft1"));
        setLeft2(imageSetup("whiteNinja", "whiteLeft2"));
        setRight1(imageSetup("whiteNinja", "whiteRight1"));
        setRight2(imageSetup("whiteNinja", "whiteRight2"));
        setIdle(imageSetup("blackNinja", "blackDown1"));
    }

    public int getPlayerHitBox(){
        return player.getHitbox().height;
    }

    public int getPlayerXCentre() {
        return player.getX() + (int)(player.getWidth() / 2.0);
    }

    public int getPlayerYCentre(){
        return player.getY() + (int)(player.getHeight() / 2.0);
    }

    public int getEntityCentreX(){
        return getX() + (int) Math.round(getWidth() / 2.0);
    }

    public int getEntityCentreY(){
        return getY() + (int) Math.round(getHeight() / 2.0);
    }

    public int getXDistance(){
        return getEntityCentreX() - getPlayerXCentre();
    }

    public int getYDistance(){
        return getEntityCentreY() - getPlayerYCentre();
    }

    public int getDistance(){
        //Euclidean distance
        return (int)(Math.sqrt(Math.pow(getXDistance(), 2) + Math.pow(getYDistance(), 2)));
    }

    @Override
    public void update(){
        super.update();
        if (getDistance() <= aggroDistance){
            setCombatStatus(true);
        }
        else {
            setCombatStatus(false);
        }
    }

    public void coolDownCounter(){
        if (attackCoolDown == coolDown){
           canAttack = true;
        }
        else{
            attackCoolDown++;
            canAttack = false;
        }
    }

    public void attack(){
        if (canAttack){
            player.setHealth(player.getHealth() - 1);
            attackCoolDown = 0;
        }
    }
}
