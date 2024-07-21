package main;

import java.awt.*;
import javax.swing.*;

import entity.*;
import entity.enemy.*;
import entity.type.*;
import game_file.GameFileManager;
import item.ItemManager;
import movement.type.*;
import tile.TileManager;
import game_file.GameFile;
import weapon.*;

import static main.Sound.playMusic;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private final static int originalTileSize = 16;
    private final static int scale = 4;
    private final static int tileSize = originalTileSize * scale; // 48
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //GAME SETTINGS
    private GameFile gameFile;
    private final String map = "Level";
    private boolean mapLoaded = false;
    public boolean levelCleared = false;
    private int curLevel, level, defaultX, defaultY, nextMapX;

    //ENTITIES
    private Player player;


    //GAME INFO
    private boolean running = false;
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
    private final GameFileManager gameFileManager;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private final HUDRenderer hudRenderer;
    public final DebugRenderer debugRenderer;
    public final EntityManager entityManager;
    public final TileManager tileManager;
    public final ItemManager itemManager;
    private final Sound sound;

    public GamePanel() {
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        entityManager = new EntityManager(this);
        tileManager = new TileManager(this);
        itemManager = new ItemManager(this);
        gameFileManager = new GameFileManager();
        sound = new Sound();

        player = new Player(this, keyHandler, mouseHandler, new PlayerMovement(keyHandler));
        entityManager.addEntity(player);
        player.setWeaponToSlot(new Sword("Wooden Sword", 2, tileSize, 750, this, "netherite_sword"), 0);
        player.setWeaponToSlot(new Gun("Pew Pew", 1, 5 * tileSize, 200, this, "jb007"), 1);
        
        debugRenderer = new DebugRenderer(this);
        hudRenderer = new HUDRenderer(this);

        this.setPreferredSize(new Dimension(854, 480));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
    }

    public boolean isRunning() {
        return this.running;
    }

    public static int getTileSize() { return tileSize; }
    public int getScreenWidth() { return screenSize.width; }
    public int getScreenHeight() { return screenSize.height; }
    public int getScale() { return scale; }

    public final void initialiseEntities() {
        entityManager.showEntities();
        entityManager.clearEntities();
        
        switch (level) {
            case 1 -> {
                defaultX = 240;
                defaultY = 1276;
                nextMapX = 3148;
//                private BlackNinja blackNinja;
//                private GreenNinja greenNinja;
//                private Boss boss;
//                private FinalBoss finalBoss;
//                private Gun gun;
//                private Sword sword;
//                //private Key key;
//                //private Girl girl;
                WhiteNinja whiteNinja = new WhiteNinja(this, 16 * getTileSize(), 20 * getTileSize());
                entityManager.addEntity(whiteNinja);
            }
            case 2 -> {
                defaultX = 1012;
                defaultY = 2304;
                WhiteNinja whiteNinja = new WhiteNinja(this, 16 * getTileSize(), 20 * getTileSize());
                entityManager.addEntity(whiteNinja);
            }
            case 3 -> {
                WhiteNinja whiteNinja = new WhiteNinja(this, 16 * getTileSize(), 20 * getTileSize());
                entityManager.addEntity(whiteNinja);
            }
            case 4 -> {
                WhiteNinja whiteNinja = new WhiteNinja(this, 16 * getTileSize(), 20 * getTileSize());
                entityManager.addEntity(whiteNinja);
            }
            case 0 -> {

            }
            default -> {
            }
        }
        entityManager.showEntities();
    }

    public void startGameThread(GameFile gameFile) {
        if (gameThread == null || !gameThread.isAlive()) {
            loadGameFile(gameFile);
            gameThread = new Thread(this);
            running = true;
            gameThread.start();
            //playMusic(0);
        } else {
            System.out.println("Existing game thread found!!!");
        }
    }

    public void loadGameFile(GameFile gameFile){
        this.gameFile = gameFile;
        this.curLevel = gameFile.getMap();
        this.level = gameFile.getMap();
        this.player.setX(gameFile.getPlayerX());
        this.player.setY(gameFile.getPlayerY());
    }

    public void saveGameFile() {
        gameFileManager.saveGame(gameFile, gameFile.getGameFile(), level, player.getX(), player.getY());
    }

    public void restartGameThread(){
        run();
    }

    public void stopGameThread() {
        if (gameThread != null && gameThread.isAlive()) {
            running = false;
            try {
                gameThread.join(3000);
                gameThread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

        tileManager.loadMap(map + level);
        initialiseEntities();
        if (player.getX() == 0 && player.getY() == 0) {
            player.setX(defaultX);
            player.setY(defaultY);
        }
        mapLoaded = true;
        playMusic(5);

        // game loop
        while (running) {
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
            if (player.getHasKey() && player.getX() >= nextMapX) {
                player.setX(0);
                player.setY(0);
//                if (level != 0){
//                    level = 0;
//                } else {
                curLevel += 1;
                level = curLevel;
                
                player.setHasKey(false);
                levelCleared = false;
//                }
                initialiseEntities();
                gameFileManager.saveGame(gameFile, gameFile.getGameFile(), level, player.getX(), player.getY());
                mapLoaded = false;
                restartGameThread();
            }
            entityManager.update();
            itemManager.update();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (mapLoaded) {
            tileManager.draw(g2);
            itemManager.draw(g2);
            entityManager.draw(g2);

            if (gameState == GameState.PAUSED) {
                drawPauseScreen(g2);
            }

            if (keyHandler.isDebugMode()) {
                debugRenderer.renderDebugInfo(g2);
            }

            hudRenderer.draw(g2);

            g2.dispose();
        }
    }

    //CHAT GPTED WILL FIX
    private void drawPauseScreen(Graphics2D g2) {
        // Draw a semi-transparent overlay
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw pause text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        String pauseText = "Paused";
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(pauseText)) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(pauseText, x, y);

        // Optional: Draw additional pause menu options (e.g., resume, quit)
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        String resumeText = "Press 'P' to Resume";
        int resumeX = (getWidth() - fm.stringWidth(resumeText)) / 2;
        int resumeY = y + fm.getHeight() + 20;
        g2.drawString(resumeText, resumeX, resumeY);
    }

    private void drawDeathScreen(Graphics2D g2){

    }

}