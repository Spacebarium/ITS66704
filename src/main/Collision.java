package main;

import entity.Entity;

public class Collision {

    GamePanel gp;

    public Collision(GamePanel gp) {
        this.gp = gp;
    }

    private double top(Entity entity) {
        return entity.getY() + entity.getHitBox().y;
    }

    private double bot(Entity entity) {
        return entity.getY() + entity.getHitBox().y + entity.getHitBox().height;
    }

    private double left(Entity entity) {
        return entity.getX() + entity.getHitBox().x;
    }

    private double right(Entity entity) {
        return entity.getX() + entity.getHitBox().x + entity.getHitBox().width;
    }

    private double topRow(Entity entity) {
        return top(entity) / gp.getTileSize();
    }

    private double botRow(Entity entity) {
        return bot(entity) / gp.getTileSize();
    }

    private double leftCol(Entity entity) {
        return left(entity) / gp.getTileSize();
    }

    private double rightCol(Entity entity) {
        return right(entity) / gp.getTileSize();
    }

    private boolean isYCollision(double col1, double col2, int entityRow) {
        try {
            int tileNum1 = gp.tileManager.getMapTileNum((int) col1, entityRow);
            int tileNum2 = gp.tileManager.getMapTileNum((int) col2, entityRow);
            return gp.tileManager.getTile(tileNum1).collision || gp.tileManager.getTile(tileNum2).collision;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isXCollision(double row1, double row2, int entityCol) {
        try {
            int tileNum1 = gp.tileManager.getMapTileNum(entityCol, (int) row1);
            int tileNum2 = gp.tileManager.getMapTileNum(entityCol, (int) row2);
            return gp.tileManager.getTile(tileNum1).collision || gp.tileManager.getTile(tileNum2).collision;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void checkTop(Entity entity) {
        int topRow = (int) ((top(entity) - entity.getSpeed()) / gp.getTileSize());
        entity.setTopCol(isYCollision(leftCol(entity), rightCol(entity), topRow));
    }

    public void checkBot(Entity entity) {
        int botRow = (int) ((bot(entity) + entity.getSpeed()) / gp.getTileSize());
        entity.setBotCol(isYCollision(leftCol(entity), rightCol(entity), botRow));
    }

    public void checkLeft(Entity entity) {
        int leftCol = (int) ((left(entity) - entity.getSpeed()) / gp.getTileSize());
        entity.setLeftCol(isXCollision(topRow(entity), botRow(entity), leftCol));
    }

    public void checkRight(Entity entity) {
        int rightCol = (int) ((right(entity) + entity.getSpeed()) / gp.getTileSize());
        entity.setRightCol(isXCollision(topRow(entity), botRow(entity), rightCol));
    }
}
