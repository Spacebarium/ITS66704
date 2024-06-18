package main;

import entity.Entity;

import entity.Player;
import entity.EntityManager;
import entity.enemy.WhiteNinja;
import tile.TileManager;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    public Collision colChecker = new Collision(this);
    public EntityManager entityManager = new EntityManager(this);

    //Entity and Objects
    //Player
    Player player = new Player(this, keyHandler);
    //Entities
    WhiteNinja whiteNinja = new WhiteNinja(this);
    //Entity array list for drawing order
    //ArrayList <Entity> entityList = new ArrayList<>();

    //Tiles
    TileManager tileM = new TileManager(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
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

    public void setupEnemy(){
        entityManager.setupEntity(player);
        entityManager.setupEntity(whiteNinja);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
        double drawInterval = 1_000_000_000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
        
        // game loop
        while (isRunning && gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            
            if (delta >= 1) {
                repaint();
                update();

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
        List<Entity> sortedEntities = entityManager.sortedEntities();
        for (Entity entity : sortedEntities) {
            entity.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;

        //Tiles
        tileM.draw(g2D);

        //Entities
        for (Entity entity: entityManager.sortedEntities()){
            entity.draw(g2D);
        }


        g2D.dispose();
    }
}
