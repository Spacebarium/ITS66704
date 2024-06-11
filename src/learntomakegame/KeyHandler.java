package learntomakegame;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent>{

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void handle(KeyEvent e) {
        if (e.getEventType() == KeyEvent.KEY_PRESSED) {
            keyPressed(e);
        } else if (e.getEventType() == KeyEvent.KEY_RELEASED) {
            keyReleased(e);
        }
    }

    private void keyPressed(KeyEvent e) {
        KeyCode code = e.getCode();
        
        switch (code) {
            case W, UP -> upPressed = true;
            case A, LEFT -> leftPressed = true;
            case S, DOWN -> downPressed = true;
            case D, RIGHT -> rightPressed = true;
            default -> {}
        }
    }

    private void keyReleased(KeyEvent e) {
        KeyCode code = e.getCode();
        
        switch (code) {
            case W, UP -> upPressed = false;
            case A, LEFT -> leftPressed = false;
            case S, DOWN -> downPressed = false;
            case D, RIGHT -> rightPressed = false;
            default -> {}
        }
    }
    
}
