package entity.enemy;

import entity.type.Enemy;
import entity.type.Player;
import main.GamePanel;
import movement.type.EnemyMovement;

public class WhiteNinja extends Enemy {

    public WhiteNinja(GamePanel gp, EnemyMovement enemyMovement, Player player) {
        super(gp, enemyMovement, player);
    }

}
