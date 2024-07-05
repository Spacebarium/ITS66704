package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialUI extends JPanel {
    private final UI ui;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final GamePanel gp;
    private final KeyHandler keyHandler = new KeyHandler();

    public InitialUI(CardLayout cardLayout, JPanel mainPanel, GamePanel gp) {
        this.ui = new UI(gp);
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.gp = gp;

        setLayout(null);

        JButton startButton = new JButton("Start Game");
        startButton.setBounds(200, 500, 1000, 1000);
        startButton.addActionListener(e -> switchGamePanel());
        add(startButton, BorderLayout.NORTH);

        addKeyListener(keyHandler);

        Timer timer = new Timer(70, e -> {
            repaint();
            if (keyHandler.isUp() && ui.getCommandNum() > 0) {
                ui.setCommandNum(ui.getCommandNum() - 1);
            }
            if (keyHandler.isDown() && ui.getCommandNum() < ui.getMaxCommandNum()) {
                ui.setCommandNum((ui.getCommandNum() + 1));
            }
            if (keyHandler.isInteract()) {
                switch (ui.getCommandNum()) {
                    case 0 -> switchGamePanel();
                    //    case 1 -> gameState = settingState;
                    case 2 -> System.exit(0);
                }
            }
        });


        timer.start();

        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        ui.drawTitleScreen(g2);
    }

    public void switchGamePanel(){
        cardLayout.show(mainPanel, "GamePanel");
        gp.requestFocusInWindow();
        gp.startGameThread();
    }
}

