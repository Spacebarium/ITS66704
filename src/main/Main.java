package main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;


public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Echoes of the Forest");
//        window.setResizable(false);
        window.setMinimumSize(new Dimension(1280, 720));

        GamePanel gp = new GamePanel();
        InitialUI ui = new InitialUI(cardLayout,mainPanel, gp);

        mainPanel.add(ui, "InitialUI");
        mainPanel.add(gp, "GamePanel");

        window.add(mainPanel);

        window.pack();
        window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gp.setPreferredSize(window.getSize());
                gp.revalidate();
                gp.repaint();
            }
        });
        
        gp.startGameThread();
    }
}
