package game_file;

import java.io.Serializable;

public class GameFile implements Serializable {
    private int gameFile;
    private int map;
    private int playerX;
    private int playerY;
    private int playerHP;

    public GameFile(int gameFile, int map, int playerX, int playerY) {
        this.gameFile = gameFile;
        this.map = map;
        this.playerX = playerX;
        this.playerY = playerY;
    }

    public int getGameFile() {
        return gameFile;
    }

    public void setGameFile(int gameFile) {
        this.gameFile = gameFile;
    }

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
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
}
