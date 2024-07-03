package main;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Echoes of the Forest");

        GamePanel gp = new GamePanel();
        InitialUI ui = new InitialUI(cardLayout,mainPanel, gp);

        mainPanel.add(ui, "InitialUI");
        mainPanel.add(gp, "GamePanel");
        window.add(mainPanel);
        gp.startGameThread();
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}

//TO DO
/* Add world camera need to change rendering properties
 * Current render renders everything so implement method to render camera jer
 */
