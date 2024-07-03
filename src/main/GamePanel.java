package main;

import entity.*;
import entity.enemy.*;
import entity.type.*;
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

    final int pressNHoldCd = 10;
    boolean canPressNHold;
    int pressNHold = pressNHoldCd;

    private int gameState = 1;
    private final int titleState = 0, playState = 1, pauseState = -1, settingState = 2;

    private Thread gameThread;

    private final UI ui;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private final EntityManager entityManager;
    private final PlayerMovement playerMovement;
    final TileManager tileManager;
    final Player player;
    final WhiteNinja whiteNinja;
    final FinalBoss finalBoss;
    final Boss boss;

    public GamePanel() {
        ui = new UI(this);
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        entityManager = new EntityManager();
        tileManager = new TileManager(this);
        playerMovement = new PlayerMovement(keyHandler);
        
        // setup player
        player = new Player(this, keyHandler, mouseHandler, new PlayerMovement(keyHandler), entityManager);
        entityManager.addEntity(player);

        // setup enemy
        // White ninja
        whiteNinja = new WhiteNinja(this, new EnemyMovement(), player);
        entityManager.addEntity(whiteNinja);
        //Boss
        boss = new Boss(this, new EnemyMovement(), player);
        entityManager.addEntity(boss);
        //Final boss
        finalBoss = new FinalBoss(this, new EnemyMovement(), player);
        entityManager.addEntity(finalBoss);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        
        tileManager.loadMap("Test");
    }

    public int getScale(){
        return scale;
    }

    public int getTileSize() { return tileSize; }
    public int getMaxScreenCol() { return maxScreenCol; }
    public int getMaxScreenRow() { return maxScreenRow; }
    public int getScreenWidth(){ return screenWidth;}
    public int getScreenHeight(){ return screenHeight;}

    public void pressNHold(){
        if (pressNHold == pressNHoldCd) {
            canPressNHold = true;
        }
        else{
            pressNHold ++;
            canPressNHold = false;
        }
    }
    public void setGameState(){
        pressNHold();
        if (canPressNHold) {
            if (keyHandler.isUp() && ui.getCommandNum() > 0) {
                ui.setCommandNum(ui.getCommandNum() - 1);
                pressNHold = 0;
            } else if (keyHandler.isDown() && ui.getCommandNum() < ui.getMaxCommandNum()) {
                ui.setCommandNum((ui.getCommandNum() + 1));
                pressNHold = 0;
            }
        }
    }

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
        setGameState();
        if (gameState == playState) {
            entityManager.update();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Title Screen
        switch (gameState) {
            case titleState -> {
                ui.drawTitleScreen(g2);

                if (keyHandler.isInteract()) {
                    switch (ui.getCommandNum()) {
                        case 0 -> gameState = playState;
                        case 1 -> gameState = settingState;
                        case 2 -> System.exit(0);
                        default -> gameState = titleState;
                    }
                }
            }
            case settingState -> ui.drawSettingScreen(g2);
            case playState, pauseState -> {
                // drawing elements and entities
                tileManager.draw(g2);
                entityManager.draw(g2);

                if (keyHandler.isMenu()){
                    gameState = pauseState;
                    ui.drawPauseScreen(g2);
                }
                else{
                    gameState = playState;
                }
                // DEBUG
                if (keyHandler.isDebugMode()) {
                    renderDebugInfo(g2);
                }
            }
        }
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
        
        if (player.getEquippedWeapon().getPosition() != null) {
            int r = player.getEquippedWeapon().getRange();
            g2.setColor(new Color(128, 0, 255, 128));
            g2.fillOval(player.getEquippedWeapon().getPosition().x - r, player.getEquippedWeapon().getPosition().y - r, 2 * r, 2 * r);
        }
    }
}