package main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

import entity.*;
import entity.enemy.*;
import entity.type.*;
import game_file.GameFileManager;
import item.ItemManager;
import movement.type.*;
import tile.TileManager;
import game_file.GameFile;
import utility.UtilityTool;
import weapon.*;

import static main.Sound.playMusic;
import static main.Sound.setVolume;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private final static int originalTileSize = 16;
    private final static int scale = 4;
    private final static int tileSize = originalTileSize * scale; // 48
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //GAME SETTINGS
    private GameFile gameFile;
    private final String map = "Level";
    private boolean promptMessage = false;
    public boolean levelCleared = false;
    private int curLevel, level, defaultX, defaultY, nextMapX;
    private final BufferedImage loadingImage;
    private final BufferedImage winImage;
    private final Object[] options = {"RESPAWN"};
    private MouseAdapter mouseAdapter;

    //ENTITIES
    private Player player;
    private Sword sword;
    private Gun gun;

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
        GAME_OVER,
        LOADING,
        WIN
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
    private final UtilityTool utilityTool;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public GamePanel(CardLayout cardLayout, JPanel mainPanel) {
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        entityManager = new EntityManager(this);
        tileManager = new TileManager(this);
        itemManager = new ItemManager(this);
        gameFileManager = new GameFileManager();
        utilityTool = new UtilityTool();
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        setVolume(0.7f);

        loadingImage = utilityTool.imageSetup("UI", "Loading");
        winImage = utilityTool.imageSetup("UI", "Win");
        player = new Player(this, keyHandler, mouseHandler, new PlayerMovement(keyHandler));
        entityManager.addEntity(player);
        sword = new Sword("Wooden Sword", 100, tileSize, 500, this, "netherite_sword");
        gun = new Gun("Pew Pew", 1, 5 * tileSize, 300, this, "jb007");
        player.setWeaponToSlot(sword, 0);
        player.setWeaponToSlot(gun, 1);
        
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
        entityManager.clearEntities();
        
        switch (level) {
            case 1 -> {
                defaultX = 240;
                defaultY = 1276;
                nextMapX = 3148;
                WhiteNinja whiteNinja = new WhiteNinja(this, 27 * getTileSize(), 16 * getTileSize(), 1, 10);
                WhiteNinja whiteNinja2 = new WhiteNinja(this, 30 * getTileSize(), 22 * getTileSize(), 1, 10);
                GreenNinja greenNinja = new GreenNinja(this, 21 * getTileSize(), 23 * getTileSize(), 2, 10);
                entityManager.addEntity(whiteNinja2);
                entityManager.addEntity(whiteNinja);
                entityManager.addEntity(greenNinja);
            }
            case 2 -> {
                defaultX = 1012;
                defaultY = 2304;
                WhiteNinja whiteNinja = new WhiteNinja(this, 16 * getTileSize(), 20 * getTileSize(), 3, 16);
                GreenNinja greenNinja = new GreenNinja(this, 17 * getTileSize(), 21 * getTileSize(), 3, 16);
                WhiteNinja whiteNinja2 = new WhiteNinja(this, 24 * getTileSize(), 35 * getTileSize(), 3, 16);
                GreenNinja greenNinja2 = new GreenNinja(this, 25 * getTileSize(), 36 * getTileSize(), 3, 16);
                WhiteNinja whiteNinja3 = new WhiteNinja(this, 40 * getTileSize(), 26 * getTileSize(), 3, 16);
                entityManager.addEntity(whiteNinja3);
                entityManager.addEntity(whiteNinja2);
                entityManager.addEntity(greenNinja2);
                entityManager.addEntity(whiteNinja);
                entityManager.addEntity(greenNinja);
            }
            case 3 -> {
                defaultX = 709;
                defaultY = 2608;
                nextMapX = 3144;
                WhiteNinja whiteNinja = new WhiteNinja(this, 16 * getTileSize(), 20 * getTileSize(), 3, 16);
                GreenNinja greenNinja = new GreenNinja(this, 17 * getTileSize(), 21 * getTileSize(), 3, 16);
                WhiteNinja whiteNinja2 = new WhiteNinja(this, 23 * getTileSize(), 36 * getTileSize(), 3, 16);
                GreenNinja greenNinja2 = new GreenNinja(this, 24 * getTileSize(), 37 * getTileSize(), 3, 16);
                WhiteNinja whiteNinja3 = new WhiteNinja(this, 35 * getTileSize(), 17 * getTileSize(), 3, 16);
                GreenNinja greenNinja3 = new GreenNinja(this, 36 * getTileSize(), 18 * getTileSize(), 3, 16);
                WhiteNinja whiteNinja4 = new WhiteNinja(this, 44 * getTileSize(), 29 * getTileSize(), 3, 16);
                GreenNinja greenNinja4 = new GreenNinja(this, 45 * getTileSize(), 30 * getTileSize(), 3, 16);
                entityManager.addEntity(whiteNinja4);
                entityManager.addEntity(greenNinja4);
                entityManager.addEntity(whiteNinja3);
                entityManager.addEntity(greenNinja3);
                entityManager.addEntity(whiteNinja2);
                entityManager.addEntity(greenNinja2);
                entityManager.addEntity(whiteNinja);
                entityManager.addEntity(greenNinja);
            }
            case 4 -> {
                defaultX = 1612;
                defaultY = 640;
                WhiteNinja whiteNinja = new WhiteNinja(this, 10 * getTileSize(), 22 * getTileSize(), 3, 16);
                GreenNinja greenNinja = new GreenNinja(this, 10 * getTileSize(), 21 * getTileSize(), 3, 16);
                WhiteNinja whiteNinja5 = new WhiteNinja(this, 11 * getTileSize(), 22 * getTileSize(), 3, 16);
                GreenNinja greenNinja5 = new GreenNinja(this, 11 * getTileSize(), 21 * getTileSize(), 3, 16);
                WhiteNinja whiteNinja2 = new WhiteNinja(this, 27 * getTileSize(), 22 * getTileSize(), 3, 16);
                GreenNinja greenNinja2 = new GreenNinja(this, 28 * getTileSize(), 23 * getTileSize(), 3, 16);
                WhiteNinja whiteNinja3 = new WhiteNinja(this, 27 * getTileSize(), 23 * getTileSize(), 3, 16);
                GreenNinja greenNinja3 = new GreenNinja(this, 28 * getTileSize(), 22 * getTileSize(), 3, 16);
                WhiteNinja whiteNinja4 = new WhiteNinja(this, 44 * getTileSize(), 25 * getTileSize(), 3, 16);
                GreenNinja greenNinja4 = new GreenNinja(this, 45 * getTileSize(), 26 * getTileSize(), 3, 16);
                WhiteNinja whiteNinja6 = new WhiteNinja(this, 46 * getTileSize(), 27 * getTileSize(), 3, 16);
                GreenNinja greenNinja6 = new GreenNinja(this, 44 * getTileSize(), 25 * getTileSize(), 3, 16);
                WhiteNinja whiteNinja7 = new WhiteNinja(this, 45 * getTileSize(), 26 * getTileSize(), 3, 16);
                GreenNinja greenNinja7 = new GreenNinja(this, 46 * getTileSize(), 27 * getTileSize(), 3, 16);
                entityManager.addEntity(whiteNinja7);
                entityManager.addEntity(greenNinja7);
                entityManager.addEntity(whiteNinja6);
                entityManager.addEntity(greenNinja6);
                entityManager.addEntity(whiteNinja4);
                entityManager.addEntity(greenNinja4);
                entityManager.addEntity(whiteNinja3);
                entityManager.addEntity(greenNinja3);
                entityManager.addEntity(whiteNinja2);
                entityManager.addEntity(greenNinja2);
                entityManager.addEntity(whiteNinja5);
                entityManager.addEntity(greenNinja5);
                entityManager.addEntity(whiteNinja);
                entityManager.addEntity(greenNinja);
            }
            case 0 -> {
                defaultX = 1646;
                defaultY = 742;
                switch (curLevel){
                    case 1 -> {
                        Boss boss =  new Boss(this,25 * getTileSize(), 23 * getTileSize(), 3, 30);
                        entityManager.addEntity(boss);

                    }
                    case 2 -> {
                        Boss boss =  new Boss(this,25 * getTileSize(), 23 * getTileSize(), 6, 40);
                        entityManager.addEntity(boss);
                    }
                    case 3 -> {
                        Boss boss =  new Boss(this,25 * getTileSize(), 23 * getTileSize(), 9, 50);
                        entityManager.addEntity(boss);
                    }
                    case 4 -> {
                        FinalBoss finalBoss = new FinalBoss(this, 25 * getTileSize(), 23 * getTileSize(), 15, 70);
                        entityManager.addEntity(finalBoss);
                    }
                }

            }
            default -> { }
        }
    }

    public void startGameThread(GameFile gameFile) {
        if (gameThread == null || !gameThread.isAlive()) {
            gameState = GameState.LOADING;
            loadGameFile(gameFile);
            gameThread = new Thread(this);
            running = true;
            gameThread.start();
        } else {
            System.out.println("Existing game thread found!!!");
        }
    }

    public void loadGameFile(GameFile gameFile){
        this.gameFile = gameFile;
        curLevel = gameFile.getCurLevel();
        level = gameFile.getLevel();
        player.setHealth(gameFile.getPlayerHP());
        player.setX(gameFile.getPlayerX());
        player.setY(gameFile.getPlayerY());
    }

    public void saveGameFile() {
        gameFileManager.saveGame(gameFile, gameFile.getGameFile(), curLevel, level, player.getX(), player.getY(), player.getHealth());
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
        
        System.out.println("loading " + map + level);
        tileManager.loadMap(map + level);
        initialiseEntities();
        if (player.getX() == 0 && player.getY() == 0) {
            player.setX(defaultX);
            player.setY(defaultY);
        }
        gameState = GameState.PLAYING;
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
            if (player.getHealth() <= 0) {
                int option = JOptionPane.showOptionDialog(null, "YOU DIED", "YOU DIED\n RESPAWN?", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                if (option == JOptionPane.OK_OPTION) {
                    player.setX(gameFile.getPlayerX());
                    player.setY(gameFile.getPlayerX());
                    player.setHealth(player.getMaxHealth());

                    initialiseEntities();
                    restartGameThread();
                }
                return;
            } 
            
            if (level == 0) {
                if (entityManager.entityCount() == 1) {
                    gameState = GameState.LOADING;
                    repaint();

                    player.setX(0);
                    player.setY(0);
                    curLevel += 1;

                    if (curLevel == 5){
                        gameState = GameState.WIN;
                        repaint();
                        return;
                    }
                    level = curLevel;
                    player.setHasKey(false);
                    levelCleared = false;

                    initialiseEntities();
                    saveGameFile();
                    restartGameThread();
                }
            } else if (player.getX() >= nextMapX && player.getHasKey()) {
                gameState = GameState.LOADING;
                repaint();
                player.setMaxHealth(player.getMaxHealth() + (curLevel) * 2);
                player.setHealth(player.getMaxHealth());
                sword.setDamage(sword.getDamage() + level * 2);
                gun.setDamage(gun.getDamage() + level * 2);
                player.setX(0);
                player.setY(0);

                level = 0;

                player.setHasKey(false);
                levelCleared = false;
                initialiseEntities();
                saveGameFile();
                restartGameThread();
            }
            promptMessage = !player.getHasKey() && player.getX() >= nextMapX;

            entityManager.update();
            itemManager.update();
        }
    }

    @Override
    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == GameState.WIN){
            g.drawImage(winImage, 0, 0, getWidth(), getHeight(), this);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 50));
            FontMetrics fm = g2.getFontMetrics();
            String gameWin = "CLICK ANYWHERE TO RETURN";
            int x = (getWidth() - fm.stringWidth(gameWin)) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 - fm.getAscent();
            g2.drawString(gameWin, x, y);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    switchPanel();
                    saveGameFile();
                    stopGameThread();
                }
            });
        }
        else if (gameState == GameState.LOADING) {
            g.drawImage(loadingImage, 0, 0, getWidth(), getHeight(), this);
        } else if (gameState == GameState.PLAYING || gameState == GameState.PAUSED) {

            tileManager.draw(g2);
            itemManager.draw(g2);
            entityManager.draw(g2);



            if (gameState == GameState.PAUSED) {
                    drawPauseScreen(g2);
                    if (mouseAdapter == null) {
                        mouseAdapter = new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                super.mouseClicked(e);
                                switchPanel();
                                saveGameFile();
                                stopGameThread();
                            }
                        };
                        addMouseListener(mouseAdapter);
                    }
                } else {
                    if (mouseAdapter != null) {
                        removeMouseListener(mouseAdapter);
                        mouseAdapter = null;
                    }
                }

            if (player.getX() <= defaultX && player.getY() <= defaultY && level == 1){
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 20));
                String promptText = "WASD To Move; 1 for melee; 2 for gun; ESC to pause; LMB to attack";
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(promptText)) / 2;
                int y = -20 + (getHeight() - fm.getHeight()) / 2 - fm.getAscent();
                g2.drawString(promptText, x, y);
                String promptText2 = "Defeat all enemies to go deeper into the forest and save your waifus";
                int resumeX = (int)((getWidth() / 2.0) - (fm.stringWidth(promptText2)/2));
                int resumeY = y - fm.getHeight() - 20;
                g2.drawString(promptText2, resumeX, resumeY);
            }

            if (promptMessage){
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 20));
                String promptText = "Defeat all enemies and get the key to go deeper into the forest";
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(promptText)) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 - fm.getAscent();
                g2.drawString(promptText, x, y);
            }

            if (keyHandler.isDebugMode()) {
                debugRenderer.renderDebugInfo(g2);
            }

            hudRenderer.draw(g2);
        }
        g2.dispose();
    }

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
        int y = (getHeight() - fm.getHeight()) / 2 - fm.getAscent();
        g2.drawString(pauseText, x, y);

        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        String resumeText = "Press 'ESCAPE' to Resume";
        int resumeX = (int)((getWidth() / 2.0) - (fm.stringWidth(resumeText)/3.5));
        int resumeY = y + fm.getHeight() + 20;
        g2.drawString(resumeText, resumeX, resumeY);

        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        String exitText = "CLICK ANYWHERE TO EXIT TO MAIN MENU";
        int exitX = (int)((getWidth() / 2.0) - (fm.stringWidth(exitText)/3.5));
        int exitY = y + fm.getHeight() + 80;
        System.out.println("X: "+x +" Y: " + y);
        g2.drawString(exitText, exitX, exitY);
    }

    public void switchPanel(){
        cardLayout.show(mainPanel, "InitialUI");
        mainPanel.getComponent(3).requestFocusInWindow();
    }
}
