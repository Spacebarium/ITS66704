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
    final int pressNHoldCd = 10;
    boolean canPressNHold;
    int pressNHold = pressNHoldCd;

    public InitialUI(CardLayout cardLayout, JPanel mainPanel, GamePanel gp) {
        this.ui = new UI(gp);
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.gp = gp;

        JButton startButton = new JButton("Start Game");

//        startButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                switchGamePanel();
//            }
//        });

        setLayout(new BorderLayout());
        add(startButton, BorderLayout.NORTH);

        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(keyHandler);

        setupKeyBindings();
    }

    private void setupKeyBindings(){
        Action switchPanelAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchGamePanel();
            }
        };

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "switchPanel");
        this.getActionMap().put("switchPanel", switchPanelAction);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        ui.drawTitleScreen(g2);
    }

    public void setGameState(){
        pressNHold();
        if (canPressNHold) {
            if (keyHandler.isUp() && ui.getCommandNum() > 0) {
                ui.setCommandNum(ui.getCommandNum() - 1);
                pressNHold = 0;
            } else if (keyHandler.isDown() && ui.getCommandNum() < ui.getMaxCommandNum()) {
                ui.setCommandNum((ui.getCommandNum() + 1));
                pressNHold = 0;
            }
        }
    }

    public void pressNHold(){
        if (pressNHold == pressNHoldCd) {
            canPressNHold = true;
        }
        else{
            pressNHold ++;
            canPressNHold = false;
        }
    }

    public void switchGamePanel(){
        cardLayout.show(mainPanel, "GamePanel");
        gp.startGameThread();
    }
}

