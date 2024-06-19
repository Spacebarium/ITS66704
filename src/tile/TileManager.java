package tile;

import main.GamePanel;
import utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    Tile[] tiles;
    int[][] mapTileData;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[2];
        mapTileData = new int[gp.getMaxScreenCol()][gp.getMaxScreenRow()];
        getTileImage();
        loadMap("Test");
    }

    public int getMapTileNum(int x, int y) {
        return mapTileData[x][y];
    }

    public Tile getTile(int num) {
        return tiles[num];
    }

    public void getTileImage() {
        tileSetup(0, "grass", false);
        tileSetup(1, "wall", true);
    }

    public void tileSetup(int i, String imageName, boolean isCollidable) {
        UtilityTool util = new UtilityTool();

        try {
            tiles[i] = new Tile();
            tiles[i].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tile/" + imageName + ".png"));
            tiles[i].isCollidable = isCollidable;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapTxt) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("map/" + mapTxt + ".txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int x = 0;
            int y = 0;

            while ((x < gp.getMaxScreenCol()) && (y < gp.getMaxScreenRow())) {
                String line = bufferedReader.readLine();
                String[] numbers = line.split(" ");

                while (x < gp.getMaxScreenCol()) {
                    mapTileData[x][y] = Integer.parseInt(numbers[x]);
                    x++;
                }
                if (x == gp.getMaxScreenCol()) {
                    x = 0;
                    y++;
                }
            }
            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int tileX = 0;
        int tileY = 0;
        int x = 0;
        int y = 0;

        while ((tileX < gp.getMaxScreenCol()) && (tileY < gp.getMaxScreenRow())) {
            int tileID = mapTileData[tileX][tileY];

            g2.drawImage(tiles[tileID].image, x, y, gp.getTileSize(), gp.getTileSize(), null);
            tileX++;

            x += gp.getTileSize();

            if (tileX == gp.getMaxScreenCol()) {
                tileX = 0;
                x = 0;
                tileY++;
                y += gp.getTileSize();
            }
        }
    }
}
