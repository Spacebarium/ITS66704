package main;

import java.awt.*;
import javax.swing.JPanel;

import entity.*;
import entity.enemy.WhiteNinja;
import entity.type.*;
import movement.type.*;
import tile.TileManager;
import weapon.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale; // 48
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private final int updatesPerSecond = 60;
    public static int FPS = 0;
    private long frameUpdateTime;
    private long frameRenderTime;
    private long totalUpdateDuration;
    private long totalRenderDuration;
    public static double updateDurationPerSecond;
    public static double renderDurationPerSecond;

    public static GameState gameState = GameState.PLAYING;
    public enum GameState {
        TITLE,
        PLAYING,
        PAUSED,
        SETTINGS,
        GAME_OVER
    }

    private Thread gameThread;
    private final UI ui;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    public final DebugRenderer debugRenderer;
    public final EntityManager entityManager;
    public final TileManager tileManager;

    public GamePanel() {
        ui = new UI(this);
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        entityManager = new EntityManager();
        tileManager = new TileManager(this);
        debugRenderer = new DebugRenderer(this);

        initialiseEntities();

        this.setPreferredSize(screenSize);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);

        tileManager.loadMap("map");
    }

    public int getTileSize() { return tileSize; }
    public int getScreenWidth() { return screenSize.width; }
    public int getScreenHeight() { return screenSize.height; }
    public int getScale() { return scale; }

    public final void initialiseEntities() {
        Player player = new Player(this, keyHandler, mouseHandler, new PlayerMovement(keyHandler));
        entityManager.addEntity(player);
        player.setWeaponToSlot(new Sword("Dull Blade", 2, 1 * tileSize, 500, this), 0);
        player.setWeaponToSlot(new Gun("Pew Pew", 1, 5 * tileSize, 200, this), 1);

        WhiteNinja whiteNinja = new WhiteNinja(this);
        entityManager.addEntity(whiteNinja);
    }


    protected void startGameThread() {
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
                frameUpdateTime = System.nanoTime();
                repaint();
                frameRenderTime = System.nanoTime();

                totalUpdateDuration += frameUpdateTime - cycleStart;
                totalRenderDuration += frameRenderTime - frameUpdateTime;

                delta--;
                drawCount++;
            }

            if (timer >= 1_000_000_000) {
                FPS = drawCount;
                updateDurationPerSecond = (double) totalUpdateDuration / 1_000_000;
                renderDurationPerSecond = (double) totalRenderDuration / 1_000_000;

                drawCount = 0;
                totalUpdateDuration = 0;
                totalRenderDuration = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == GameState.PLAYING) {
            entityManager.update();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        entityManager.draw(g2);
        
        if (gameState == GameState.PAUSED) {
            ui.drawPauseScreen(g2);
        }
        
        if (keyHandler.isDebugMode()) {
            debugRenderer.renderDebugInfo(g2);
        }
        
        g2.dispose();
    }
}