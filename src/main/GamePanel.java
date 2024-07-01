package main;

import enemy.*;
import enemy.type.*;
import enemy.enemy.*;
import movement.type.*;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;

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
    long updateTime;
    long renderTime;
    long updateDuration;
    long renderDuration;
    double updateDurationPerSecond;
    double renderDurationPerSecond;

    private Thread gameThread;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private final EntityManager entityManager;
    final TileManager tileManager;
    
    final Player player;
    final Enemy whiteNinja;

    public GamePanel() {
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        entityManager = new EntityManager(this);
        tileManager = new TileManager(this);
        
        // setup player
        player = new Player(this, keyHandler, mouseHandler, new PlayerMovement(keyHandler));
        entityManager.addEntity(player);

        // setup enemy
        // White ninja
        whiteNinja = new Enemy(this, new EnemyMovement(), player);
        entityManager.addEntity(whiteNinja);

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
        long   previous = System.nanoTime();
        long   current;
        long   timer = 0;
        int    drawCount = 0; // FPS
        double cycleStart;

        // game loop
        while (gameThread != null) {
            current = System.nanoTime();
            delta += (current - previous) / drawInterval;
            timer += current - previous;
            previous = current;

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
                updateDurationPerSecond = (double) updateDuration / 1_000_000;
                renderDurationPerSecond = (double) renderDuration / 1_000_000;
                
                drawCount = 0;
                updateDuration = 0;
                renderDuration = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        entityManager.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        entityManager.draw(g2);

        if (keyHandler.isDebugMode()) { renderDebugInfo(g2); }
//        renderDebugInfo(g2);

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
        
        if (player != null) {
            g2.drawString("DX: " + player.getMovementHandler().getDx(), 10, 120);
            g2.drawString("DY: " + player.getMovementHandler().getDy(), 10, 140);
            g2.drawString("Speed: " + String.format("%.2f", player.getMovementHandler().getSpeed()), 10, 160);
            g2.drawString("Health: " + player.getHealth(), 10, 180);
        }
        
        g2.drawString("slot0: " + player.getWeaponFromSlot(0).getName(), 10, 200);
        g2.drawString("slot1: " + player.getWeaponFromSlot(1).getName(), 10, 220);
        g2.drawString("equip slot: " + player.getEquippedWeaponIndex(), 10, 240);
        
        for (Entity entity : entityManager.getEntities()) {
            // image box
            g2.setColor(Color.RED);
            g2.drawRect(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
            
            // collision box
            Rectangle hitbox = entity.getHitbox();
            g2.setColor(new Color(0, 0, 255, 128));
            g2.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

            int boundX = entity.getWidth() + 200;
            int boundY = entity.getHeight() + 200;
            int entityCentreX = entity.getX() + (int)Math.round(entity.getWidth() / 2.0);
            int entityCentreY = entity.getY() + (int)Math.round(entity.getHeight() / 2.0);
            Rectangle movementBound = new Rectangle(entityCentreX - (int)Math.round(boundX / 2.0), entityCentreY - (int)Math.round(boundY / 2.0), boundX, boundY);
            g2.setColor(new Color(128, 0, 255, 128));


            g2.drawRect(movementBound.x, movementBound.y, movementBound.width, movementBound.height);
        }
    }
}