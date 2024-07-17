package game_file;

import java.io.Serializable;

public class GameFile implements Serializable {
    private String map;
    private int playerX;
    private int playerY;

    public GameFile(String map, int playerX, int playerY) {
        this.map = map;
        this.playerX = playerX;
        this.playerY = playerY;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
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
