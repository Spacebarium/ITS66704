package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import main.GamePanel.GameState;

public class KeyHandler extends KeyAdapter {
    
    private boolean up, down, left, right, debugMode, one, two, interact;
    public boolean isUp() { return up; }
    public boolean isDown() { return down; }
    public boolean isLeft() { return left; }
    public boolean isRight() { return right; }
    public boolean isDebugMode() { return debugMode; }
    public boolean isInteract() { return interact; }
    public boolean isOne() { return one; }
    public boolean isTwo() { return two; }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> up = true;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> down = true;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_1 -> one = true;
            case KeyEvent.VK_2 -> two = true;
            
            case KeyEvent.VK_F3 -> debugMode = !debugMode;
            case KeyEvent.VK_ESCAPE -> {
                if (GamePanel.gameState == GameState.PLAYING) {
                    GamePanel.gameState = GameState.PAUSED;
                } else if (GamePanel.gameState == GameState.PAUSED) {
                    GamePanel.gameState = GameState.PLAYING;
                }
            }
            case KeyEvent.VK_ENTER, KeyEvent.VK_F -> interact = true;
            default -> {}
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> up = false;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> down = false;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> right = false;
            case KeyEvent.VK_ENTER, KeyEvent.VK_F -> interact = false;
            case KeyEvent.VK_1 -> one = false;
            case KeyEvent.VK_2 -> two = false;
            default -> {}
        }

    }
}
