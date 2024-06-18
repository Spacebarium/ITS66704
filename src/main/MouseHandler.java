package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        int code = e.getButton();

        switch (code) {
            case 1 -> System.out.println("left");
            case 2 -> System.out.println("middle");
            case 3 -> System.out.println("right");
            default -> {}
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
