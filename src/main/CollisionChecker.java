package main;

import entity.type.Entity;
import java.awt.Rectangle;

public class CollisionChecker {

    private final GamePanel gp;
    private final int tileSize;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
        this.tileSize = gp.getTileSize();
    }
    
    public boolean canMove(Entity entity, int dx, int dy) {
        Rectangle hitbox = entity.getHitbox();
        
        int newX = hitbox.x + dx;
        int newY = hitbox.y + dy;

        int startTileX = newX / tileSize;
        int startTileY = newY / tileSize;
        int endTileX = (newX + hitbox.width - 1) / tileSize;
        int endTileY = (newY + hitbox.height - 1) / tileSize;
        
        // tile check
        for (int tileX = startTileX; tileX <= endTileX; tileX++) {
            for (int tileY = startTileY; tileY <= endTileY; tileY++) {

            }
        }
        
        // enemy check: maybe won't need, we'll see
        

        return true; // no collision detected
    }
}
