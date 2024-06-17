package Main;

import Entity.BlackNinja;
import Entity.WhiteNinja;
import Tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    final int FPS = 60;
    boolean isRunning = true;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    // default player pos
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 8;

    //instantiate BlackNinja class
    public Collision colChecker = new Collision(this);
    BlackNinja blackNinja = new BlackNinja(this, keyHandler);
    WhiteNinja whiteNinja = new WhiteNinja(this);
    TileManager tileM = new TileManager(this);

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenCol() {
        return maxScreenCol;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        // game loop
        while (isRunning && gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1_000_000_000) {
                System.out.printf("FPS: %d\n", drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update() {

        blackNinja.update();
        //whiteNinja.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        //Tiles
        tileM.draw(g2D);

        //Entities
        blackNinja.draw(g2D);
        //  whiteNinja.draw(g2D);

        g2D.dispose();
    }
}
