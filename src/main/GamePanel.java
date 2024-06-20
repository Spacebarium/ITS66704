package main;

import entity.*;
import entity.type.*;
import movement.*;
import movement.type.*;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int tileSize = originalTileSize * scale; // 48
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    int updatesPerSecond = 60;
    int FPS = 0;
    double updateTime;
    double renderTime;
    double updateDuration;
    double renderDuration;
    double updateDurationPerSecond;
    double renderDurationPerSecond;

    private Thread gameThread;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private final CollisionHandler collisionHandler;
    private final EntityManager entityManager;
    private final List<MovementHandler> movementHandlers;
    private final MovementHandler playerMovementHandler;
    final TileManager tileManager;
    
    final Player player;

    public GamePanel() {
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        collisionHandler = new CollisionHandler(this);
        entityManager = new EntityManager(this);
        tileManager = new TileManager(this);
        movementHandlers = new ArrayList<>();

        // setup player
        player = new Player(this, keyHandler, mouseHandler);
        entityManager.addEntity(player);
        playerMovementHandler = new MovementHandler(player, collisionHandler, new PlayerMovement(keyHandler));
        movementHandlers.add(playerMovementHandler);
        
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        
        tileManager.loadMap("Test");
    }

    public int getTileSize() { return tileSize; }
    public int getMaxScreenCol() { return maxScreenCol; }
    public int getMaxScreenRow() { return maxScreenRow; }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / updatesPerSecond;
        double delta = 0;
        long   lastTime = System.nanoTime();
        long   currentTime;
        long   timer = 0;
        int    drawCount = 0; // FPS
        double cycleStart;

        // game loop
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            
            if (delta >= 1) {
                cycleStart = System.nanoTime();
                update();
                updateTime = System.nanoTime();
                repaint();
                renderTime = System.nanoTime();
                
                updateDuration += updateTime - cycleStart;
                renderDuration += renderTime - updateTime;

                delta--;
                drawCount++;
            }

            if (timer >= 1_000_000_000) {
                FPS = drawCount;
                updateDurationPerSecond = updateDuration / 1_000_000;
                renderDurationPerSecond = renderDuration / 1_000_000;
                
                drawCount = 0;
                updateDuration = 0;
                renderDuration = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        for (MovementHandler handler : movementHandlers) {
                handler.update();
            }
        
        entityManager.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        entityManager.draw(g2);

//        if (keyHandler.isDebugMode()) {
//            renderDebugInfo(g2);
//        }
        renderDebugInfo(g2);

        g2.dispose();
    }

    public void renderDebugInfo(Graphics2D g2) {
        g2.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.setColor(Color.WHITE);
        g2.drawString("FPS: " + FPS, 10, 20);
        g2.drawString("Update duration: " + String.format("%.2f", updateDurationPerSecond) + "ms", 10, 40);
        g2.drawString("Render duration: " + String.format("%.2f", renderDurationPerSecond) + "ms", 10, 60);
        g2.drawString("X: " + player.getX(), 10, 80);
        g2.drawString("Y: " + player.getY(), 10, 100);
        
        if (playerMovementHandler != null) {
            g2.drawString("DX: " + playerMovementHandler.getDx(), 10, 120);
            g2.drawString("DY: " + playerMovementHandler.getDy(), 10, 140);
            g2.drawString("Speed: " + playerMovementHandler.getSpeed(), 10, 160);
        }
    }
}