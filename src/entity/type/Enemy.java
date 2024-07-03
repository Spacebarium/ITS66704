package entity.type;

import main.GamePanel;
import movement.type.EnemyMovement;

public class Enemy extends Entity {

    public Player player;
    final int aggroDistance = 160;
    int coolDown = 2 * 60;
    public int attackRange = 16;
    int attackCoolDown = coolDown;
    boolean canAttack = true;

    public Enemy(GamePanel gp, String name, int x, int y, int width, int height, int hitboxOffsetX, int hitboxOffsetY, int hitboxWidth, int hitboxHeight, EnemyMovement enemyMovement, Player player) {
            super(gp, EntityType.ENEMY, name, x, y, width, height, hitboxOffsetX, hitboxOffsetY, hitboxWidth, hitboxHeight, enemyMovement);
            this.player = player;
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
