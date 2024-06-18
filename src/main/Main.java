package main;

import javax.swing.*;

public class Main{

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Steve simulator");

        GamePanel gp = new GamePanel();
        window.add(gp);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.setupEnemy();
        gp.startGameThread();
    }
}

//TO DO
/* Add world camera need to change rendering properties
 * Current render renders everything so implement method to render camera jer
 */