package Main;

import Entity.Entity;

public class Collision {

    GamePanel gp;

    public Collision(GamePanel gp) {
        this.gp = gp;
    }

    private int top(Entity entity) {
        return entity.y + entity.solidArea.y;
    }

    private int bot(Entity entity) {
        return entity.y + entity.solidArea.y + entity.solidArea.height;
    }

    private int left(Entity entity) {
        return entity.x + entity.solidArea.x;
    }

    private int right(Entity entity) {
        return entity.x + entity.solidArea.x + entity.solidArea.width;
    }

    private int topRow(Entity entity) {
        return top(entity) / gp.getTileSize();
    }

    private int botRow(Entity entity) {
        return bot(entity) / gp.getTileSize();
    }

    private int leftCol(Entity entity) {
        return left(entity) / gp.getTileSize();
    }

    private int rightCol(Entity entity) {
        return right(entity) / gp.getTileSize();
    }

    private boolean isYCollision(int col1, int col2, int entityRow) {
        int tileNum1 = gp.tileM.getMapTileNum(col1, entityRow);
        int tileNum2 = gp.tileM.getMapTileNum(col2, entityRow);
        return gp.tileM.getTile(tileNum1).collision || gp.tileM.getTile(tileNum2).collision;
    }

    private boolean isXCollision(int row1, int row2, int entityCol) {
        int tileNum1 = gp.tileM.getMapTileNum(entityCol, row1);
        int tileNum2 = gp.tileM.getMapTileNum(entityCol, row2);
        return gp.tileM.getTile(tileNum1).collision || gp.tileM.getTile(tileNum2).collision;
    }

    public void checkTop(Entity entity) {
        int topRow = (top(entity) - entity.speed) / gp.getTileSize();
        entity.topCol = isYCollision(leftCol(entity), rightCol(entity), topRow);
    }

    public void checkBot(Entity entity) {
        int botRow = (bot(entity) + entity.speed) / gp.getTileSize();
        entity.botCol = isYCollision(leftCol(entity), rightCol(entity), botRow);
    }

    public void checkLeft(Entity entity) {
        int leftCol = (left(entity) - entity.speed) / gp.getTileSize();
        entity.leftCol = isXCollision(topRow(entity), botRow(entity), leftCol);
    }

    public void checkRight(Entity entity) {
        int rightCol = (right(entity) + entity.speed) / gp.getTileSize();
        entity.rightCol = isXCollision(topRow(entity), botRow(entity), rightCol);
    }
}
