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
    Tile[] tile;
    int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[2];
        mapTileNum = new int[gp.getMaxScreenCol()][gp.getMaxScreenRow()];
        getTileImage();
        loadMap("Test");
    }

    public int getMapTileNum(int x, int y) {
        return mapTileNum[x][y];
    }

    public Tile getTile(int num) {
        return tile[num];
    }

    public void getTileImage() {
        tileSetup(0, "grass", false);
        tileSetup(1, "wall", true);
    }

    public void tileSetup(int index, String imageName, boolean isCollidable) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tile/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.getTileSize(), gp.getTileSize());
            tile[index].isCollidable = isCollidable;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapTxt) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("map/" + mapTxt + ".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while ((col < gp.getMaxScreenCol()) && row < (gp.getMaxScreenRow())) {
                String line = br.readLine();

                while (col < gp.getMaxScreenCol()) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.getMaxScreenCol()) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while ((col < gp.getMaxScreenCol()) && (row < gp.getMaxScreenRow())) {
            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, null);
            col++;

            x += gp.getTileSize();

            if (col == gp.getMaxScreenCol()) {
                col = 0;
                x = 0;
                row++;
                y += gp.getTileSize();
            }
        }
    }
}
