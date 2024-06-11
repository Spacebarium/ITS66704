package learntomakegame;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GamePanel extends Pane implements Runnable {
    
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
    
    private Canvas canvas;
    private GraphicsContext gc;
    
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    
    // default player pos
    double playerX = 100;
    double playerY = 100;
    double playerSpeed = 4;
    
    
    
    public GamePanel() {
        this.setPrefSize(screenWidth, screenHeight);
        this.setStyle("-fx-background-color: #000");
        
        this.setOnKeyPressed(keyHandler);
        this.setOnKeyReleased(keyHandler);
        this.setFocusTraversable(true);
        
        canvas = new Canvas(screenWidth, screenHeight);
        gc = canvas.getGraphicsContext2D();
        
        this.getChildren().add(canvas);
        draw();
    }
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    
    @Override
    public void run() {
        double drawInterval = 1_000_000_000/FPS;
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
                update();
                draw();
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
        if (keyHandler.upPressed) {
            playerY -= (keyHandler.leftPressed || keyHandler.rightPressed) ? playerSpeed / Math.sqrt(2) : playerSpeed;
        }
        if (keyHandler.downPressed) {
            playerY += (keyHandler.leftPressed || keyHandler.rightPressed) ? playerSpeed / Math.sqrt(2) : playerSpeed;
        }
        if (keyHandler.leftPressed) {
            playerX -= (keyHandler.upPressed || keyHandler.downPressed) ? playerSpeed / Math.sqrt(2) : playerSpeed;
        }
        if (keyHandler.rightPressed) {
            playerX += (keyHandler.upPressed || keyHandler.downPressed) ? playerSpeed / Math.sqrt(2) : playerSpeed;
        }
   }

    public void draw() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        gc.fillRect(playerX, playerY, tileSize, tileSize);
    }
}
