package game_file;

import java.io.Serializable;

public class GameFile implements Serializable {
    private int gameFile;
    private int curLevel;
    private int level;
    private int playerX;
    private int playerY;
    private int playerHP;

    public GameFile(int gameFile, int curLevel, int level, int playerX, int playerY, int playerHP) {
        this.gameFile = gameFile;
        this.curLevel = curLevel;
        this.level = level;
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerHP = playerHP;
    }

    public int getGameFile() {
        return gameFile;
    }

    public void setGameFile(int gameFile) {
        this.gameFile = gameFile;
    }

    public int getCurLevel() {
        return curLevel;
    }

    public void setCurLevel(int curLevel) {
        this.curLevel = curLevel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getPlayerHP() {
        return playerHP;
    }

    public void setPlayerHP(int playerHP) {
        this.playerHP = playerHP;
    }
}
