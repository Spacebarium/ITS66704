package main;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseInputAdapter {
    
    private boolean lmb, rmb, mmb;
    public boolean isLmb() { return lmb; }
    public boolean isRmb() { return rmb; }
    public boolean isMmb() { return mmb; }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> lmb = true;
            case MouseEvent.BUTTON2 -> mmb = true;
            case MouseEvent.BUTTON3 -> rmb = true;
            default -> {}
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> lmb = false;
            case MouseEvent.BUTTON2 -> mmb = false;
            case MouseEvent.BUTTON3 -> rmb = false;
            default -> {}
        }
    }
}
