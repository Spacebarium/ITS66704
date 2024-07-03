package main;

import entity.type.Entity;
import java.awt.Rectangle;

public class CollisionHandler {

    GamePanel gp;
    private final int tileSize;

    public CollisionHandler(GamePanel gp) {
        this.gp = gp;
        this.tileSize = gp.getTileSize();
    }
    
    public boolean canMove(Entity entity, int dx, int dy) {
        Rectangle hitBox = entity.getHitbox();
        int x = hitBox.x;
        int y = hitBox.y;
        int width = hitBox.width;
        int height = hitBox.height;
        
        int newX = x + dx;
        int newY = y + dy;

        int startTileX = newX / tileSize;
        int startTileY = newY / tileSize;
        int endTileX = (newX + width - 1) / tileSize;
        int endTileY = (newY + height - 1) / tileSize;
        
        // tile check
        for (int tileX = startTileX; tileX <= endTileX; tileX++) {
            for (int tileY = startTileY; tileY <= endTileY; tileY++) {
                if (!gp.tileManager.getTile(tileX, tileY).isWalkable()) {
                    return false; // collision detected
                }
            }
        }
        
        // enemy check
        

        return true; // no collision detected
    }
}
