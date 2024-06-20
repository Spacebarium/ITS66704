package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    private Tile[][] tiles;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[gp.getMaxScreenRow()][gp.getMaxScreenCol()];
    }

    public void setTile(int x, int y, TileType type) {
        tiles[y][x] = new Tile(type);
    }

    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }

    public int getWidth() {
        return tiles[0].length;
    }

    public int getHeight() {
        return tiles.length;
    }

    private TileType tileTypeToEnum(int type) {
        switch (type) {
            case 0:
                return TileType.EMPTY;
            case 1:
                return TileType.GRASS;
            case 2:
                return TileType.WALL;
            default:
                throw new IllegalArgumentException("Unknown tile type: " + type);
        }
    }

    public void loadMap(String mapName){
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("map/" + mapName + ".txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            int y = 0;
            
            while ((line = bufferedReader.readLine()) != null) {
                String[] ids = line.split(" ");
                
                for (int x = 0; x < ids.length; x++) {
                    int tileType = Integer.parseInt(ids[x]);
                    setTile(x, y, tileTypeToEnum(tileType));
                }
                y++;
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int tileSize = gp.getTileSize();
        int mapWidth = this.getWidth();
        int mapHeight = this.getHeight();
        int x = 0;
        int y = 0;
        Tile tile;
        BufferedImage image;
        
        while (x < mapWidth && y < mapHeight) {
            tile = this.getTile(x, y);
            if (tile != null) {
                image = tile.getImage();
                    if (image != null) {
                        g2.drawImage(image, x * tileSize, y * tileSize, tileSize, tileSize, null);
                    }
            }
            x++;

            if (x == mapWidth) {
                x = 0;
                y++;
            }
        }
    }
}
