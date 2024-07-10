package main;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InitialUI extends JPanel {

    private final GamePanel gp;
    private Font arial_40, arial_80B;
    private int commandNum;
    private final int maxCommandNum = 2;
    private Timer timer;
    private boolean blinking;

    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private JPanel centrePanel;
    private JPanel vPaddingTop, vPaddingBottom, hPaddingLeft, hPaddingRight;
    private JLabel titleLabel;

    private JPanel optionPanel;
    private JLayeredPane optionLayer;
    private JPanel stringPanel, buttonPanel;
    private JLabel[] buttonOptions;
    private JButton startButton, tutorialButton, quitButton;

    public InitialUI(CardLayout cardLayout, JPanel mainPanel, GamePanel gp) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        //CENTRE PANEL
        centrePanel = new JPanel(new BorderLayout(10, 10));
        centrePanel.setBackground(Color.cyan);

        // Vertical padding at the top
        vPaddingTop = new JPanel();
        vPaddingTop.setPreferredSize(new Dimension(getPreferredSize().width, 100));
        vPaddingTop.setBackground(Color.BLUE);

        // Vertical padding at the bottom
        vPaddingBottom = new JPanel();
        vPaddingBottom.setPreferredSize(new Dimension(getPreferredSize().width, 100));
        vPaddingBottom.setBackground(Color.BLUE);

        // Horizontal padding on the left
        hPaddingLeft = new JPanel();
        hPaddingLeft.setPreferredSize(new Dimension(100, getPreferredSize().height));
        hPaddingLeft.setBackground(Color.GREEN);

        // Horizontal padding on the right
        hPaddingRight = new JPanel();
        hPaddingRight.setPreferredSize(new Dimension(100, getPreferredSize().height));
        hPaddingRight.setBackground(Color.GREEN);

        //Title label
        titleLabel = new JLabel("Echoes of the Forest");
        titleLabel.setPreferredSize(new Dimension(getPreferredSize().width, 100));
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(getFont().deriveFont(Font.BOLD, 66F));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        //Option panel
        optionPanel = new JPanel();
        optionPanel.setLayout(null);

        //Option layer
        optionLayer = new JLayeredPane();

        //String panel
        stringPanel = new JPanel(new GridLayout(3, 1, 10, 0));
        String[] optionTexts = {"  START GAME  ", "  TUTORIAL  ", "  QUIT  "};
        buttonOptions = new JLabel[optionTexts.length];

        for (int i = 0; i < optionTexts.length; i++) {
            buttonOptions[i] = new JLabel(optionTexts[i]);
            buttonOptions[i].setHorizontalAlignment(JLabel.CENTER);
            buttonOptions[i].setVerticalAlignment(JLabel.CENTER);
            stringPanel.add(buttonOptions[i]);
        }

        //Button panel
        buttonPanel = new JPanel();

        //Start button
        startButton = new JButton();

        //Tutorial button
        tutorialButton = new JButton();

        //Quit button
        quitButton = new JButton();

        buttonPanel.add(startButton);
        buttonPanel.add(tutorialButton);
        buttonPanel.add(quitButton);

        optionLayer.add(stringPanel, JLayeredPane.DRAG_LAYER);
        optionLayer.add(buttonPanel, JLayeredPane.DEFAULT_LAYER);

        optionPanel.add(optionLayer);

        centrePanel.add(titleLabel, BorderLayout.NORTH);
        centrePanel.add(optionPanel, BorderLayout.CENTER);

        add(centrePanel, BorderLayout.CENTER);
        add(vPaddingTop, BorderLayout.NORTH);
        add(hPaddingRight, BorderLayout.EAST);
        add(hPaddingLeft, BorderLayout.WEST);
        add(vPaddingBottom, BorderLayout.SOUTH);

        //Element configuration
        //Start button
        startButton.setText("Start game");
        startButton.addActionListener(e -> switchGamePanel());
        //startButton.setContentAreaFilled(false);
        // startButton.setBorderPainted(false);

        //Tutorial button
        tutorialButton.setText("        ");
        tutorialButton.addActionListener(e -> switchGamePanel());
        //tutorialButton.setContentAreaFilled(false);
        // startButton.setBorderPainted(false);

        //Quit button
        quitButton.setText("    ");
        quitButton.addActionListener(e -> System.exit(0));
        //quitButton.setContentAreaFilled(false);
        // startButton.setBorderPainted(false);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int optionPanelWidth = optionPanel.getWidth();
                int optionPanelHeight = optionPanel.getHeight();
                int panelWidth = 100;
                int panelHeight = 100;

                optionLayer.setBounds(0,0, optionPanelWidth, optionPanelHeight);
                stringPanel.setBounds((optionPanelWidth - panelWidth) / 2, (optionPanelHeight - panelHeight) / 2, panelWidth, panelHeight);
                buttonPanel.setBounds((optionPanelWidth - panelWidth) / 2, (optionPanelHeight - panelHeight) / 2, panelWidth, panelHeight);
            }
        });

        //Accept keyboard inputs
        updateSelection(commandNum);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                        if (commandNum > 0) {
                            updateSelection(commandNum, -1);
                            gp.playSE(3);
                        }
                    }
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                        if (commandNum < maxCommandNum) {
                            updateSelection(commandNum, +1);
                            gp.playSE(3);
                        }
                    }
                    case KeyEvent.VK_ENTER, KeyEvent.VK_F -> {
                        switch (commandNum) {
                            case 0 -> switchGamePanel();
                            //    case 1 -> settingPanel();
                            case 2 ->
                                System.exit(0);
                        }
                        gp.playSE(4);
                        timer.stop();
                    }
                }
            }
        });
        //Timer for blinking text !!! MUST MAKE SURE THIS IS STOPPED TO PREVENT LAG !!!
        timer = new Timer(300, e -> {
            blinking = !blinking;
            buttonOptions[commandNum].setVisible(blinking);
        });
        timer.start();

        setFocusable(true);
        requestFocusInWindow();
    }

    private void updateSelection(int commandNum) {
        buttonOptions[commandNum].setText("> " + buttonOptions[commandNum].getText().trim() + " <");
    }

    private void updateSelection(int commandNum, int i) {
        buttonOptions[commandNum].setText(buttonOptions[commandNum].getText().replace(">", " ").trim());
        buttonOptions[commandNum].setText(buttonOptions[commandNum].getText().replace("<", " ").trim());
        buttonOptions[commandNum].setVisible(true);

        buttonOptions[commandNum + i].setText("> " + buttonOptions[commandNum + i].getText().trim() + " <");

        this.commandNum += i;
    }

    private void switchGamePanel() {
        cardLayout.show(mainPanel, "GamePanel");
        gp.requestFocusInWindow();
        gp.startGameThread();
    }
}
