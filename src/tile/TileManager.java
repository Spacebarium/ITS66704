package tile;

import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;
import entity.type.Player;

public class TileManager {

    private final GamePanel gp;
    private Tile[][] tiles;

    public TileManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setTile(int x, int y, String type) {
        Tile tile = new Tile(type);
        this.tiles[y][x] = tile;
    }

    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }

    public int getMapTileWidth() {
        return tiles[0].length;
    }

    public int getMapTileHeight() {
        return tiles.length;
    }

    public void loadMap(String mapName){
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Map/" + mapName + ".txt")) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> lines = new ArrayList<>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            bufferedReader.close();

            int mapTileHeight = lines.size();
            int mapTileWidth = lines.get(0).split(" ").length;

            tiles = new Tile[mapTileHeight][mapTileWidth];

            for (int y = 0; y < mapTileHeight; y++) {
                String[] tileIds = lines.get(y).split(" ");
                for (int x = 0; x < tileIds.length; x++) {
                    String tileId = String.format("%03d", Integer.parseInt(tileIds[x]));
                    setTile(x, y, tileId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int tileSize = gp.getTileSize();
        int mapTileWidth = this.getMapTileWidth();
        int mapTileHeight = this.getMapTileHeight();
        Player player = gp.entityManager.getPlayer();

        for (int y = 0; y < mapTileHeight; y++) {
            for (int x = 0; x < mapTileWidth; x++) {
                Tile tile = this.getTile(x, y);
                if (tile == null) { return; }
                BufferedImage image = tile.getImage();
                if (image == null) { return; }

                int screenX = x * tileSize - player.getX() + player.getScreenX();
                int screenY = y * tileSize - player.getY() + player.getScreenY();

                if ((x + 1) * tileSize > player.getX() - player.getScreenX()
                        && (x - 1) * tileSize < player.getX() + player.getScreenX()
                        && (y + 1) * tileSize > player.getY() - player.getScreenY()
                        && (y - 1) * tileSize < player.getY() + player.getScreenY()) {
                    g2.drawImage(image, screenX, screenY, tileSize, tileSize, null);
                }
            }
        }
    }
}
