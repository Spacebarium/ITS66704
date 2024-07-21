package main;

import game_file.GameFileManager;
import ui.InitialUI;
import ui.SettingUI;
import ui.StartGameUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Echoes of the Forest");

        window.setMinimumSize(new Dimension(1280, 720));

        GameFileManager gameFileManager = new GameFileManager();
        gameFileManager.loadSettings();

        Sound sound = new Sound();
        GamePanel gp = new GamePanel();
        InitialUI ui = new InitialUI(cardLayout, mainPanel);
        StartGameUI startGame = new StartGameUI(cardLayout, mainPanel, gp);
        SettingUI setting = new SettingUI(cardLayout, mainPanel);

        mainPanel.add(ui, "InitialUI");
        mainPanel.add(startGame, "StartGameUI");
        mainPanel.add(setting, "SettingUI");
        mainPanel.add(gp, "GamePanel");

        window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        window.add(mainPanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gameFileManager.saveSettings();
                if (gp.isRunning()) {
                    gp.saveGameFile();
                }
                System.exit(1);
            }
        });

        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gp.setPreferredSize(window.getSize());
                gp.revalidate();
                gp.repaint();
            }
        });
    }
}
