package main;


import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;


public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Echoes of the Forest");
//        window.setResizable(false);

        GamePanel gp = new GamePanel();
      //  InitialUI ui = new InitialUI(cardLayout,mainPanel, gp);

        //mainPanel.add(ui, "InitialUI");
        mainPanel.add(gp, "GamePanel");
        gp.startGameThread();

        window.add(mainPanel);
        window.pack();

        window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}
