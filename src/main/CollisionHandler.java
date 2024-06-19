package main;

import entity.type.Entity;

public class CollisionHandler {

    GamePanel gp;

    public CollisionHandler(GamePanel gp) {
        this.gp = gp;
    }
    
    public boolean canMove(Entity entity, int dx, int dy) {
        int tileX = (int) (entity.getX() + dx) / gp.getTileSize();
        int tileY = (int) (entity.getY() + dy) / gp.getTileSize();
        
//        if (gp.tileManager)
        return true;
        
    }
}
