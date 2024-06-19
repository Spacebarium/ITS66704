package main;

import entity.Entity;
import entity.Player;
import entity.EntityManager;
import entity.enemy.*;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private final int originalTileSize = 16;
    private final int scale            = 3;
    private final int maxScreenCol     = 16;
    private final int maxScreenRow     = 12;
    private final int tileSize         = originalTileSize * scale;
    private final int screenWidth      = tileSize * maxScreenCol;
    private final int screenHeight     = tileSize * maxScreenRow;

    int FPS = 60;
    int calcedFPS;

    private Thread gameThread;
    private final KeyHandler keyHandler = new KeyHandler();
    private final MouseHandler mouseHandler = new MouseHandler();
    public Collision colChecker = new Collision(this);
    public EntityManager entityManager = new EntityManager(this);

    // Entity and Objects
    // Player
    Player player = new Player(this, keyHandler, mouseHandler);
    // Entities
    WhiteNinja whiteNinja = new WhiteNinja(this);
    // Entity array list for drawing order
    // ArrayList <Entity> entityList = new ArrayList<>();

    // Tiles
    TileManager tileManager = new TileManager(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.setFocusable(true);
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenCol() {
        return maxScreenCol;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public void setupEntity() {
        entityManager.setupEntity(player);
        entityManager.setupEntity(whiteNinja);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS;
        double delta        = 0;
        long   lastTime     = System.nanoTime();
        long   currentTime;
        long   timer        = 0;
        int    drawCount    = 0; // FPS

        // game loop
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                repaint();
                update();

                delta--;
                drawCount++;
            }

            if (timer >= 1_000_000_000) {
                calcedFPS = drawCount;
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update() {
        List<Entity> sortedEntities = entityManager.sortedEntities();
        for (Entity entity : sortedEntities) {
            entity.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        // Tiles
        tileManager.draw(g2D);

        // Entities
        for (Entity entity : entityManager.sortedEntities()) {
            entity.draw(g2D);
        }
        
        if (keyHandler.getIsDebugMode()) {
            renderDebugInfo(g2D);
        }

        g2D.dispose();
    }
    
    public void renderDebugInfo(Graphics2D g2D) {
        g2D.setFont(new Font("Arial", Font.BOLD, 14));
        g2D.setColor(Color.WHITE);
        g2D.drawString("FPS: " + calcedFPS, 10, 20);
        g2D.drawString("X: " + player.getX(), 10, 40);
        g2D.drawString("Y: " + player.getY(), 10, 60);
        //g2D.drawString("equip1: " + player.getWeaponName(), 10, 80);
    }
}
