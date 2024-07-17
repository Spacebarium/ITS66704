package ui;

import utility.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class InitialUI extends JPanel {
    private final UtilityTool uTool;
    private Font arial_40, arial_80B;
    private int commandNum;
    private final int maxCommandNum = 2;
    private final Timer timer;
    private boolean blinking;

    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private JPanel centrePanel;
    private JPanel vPaddingTop, vPaddingBottom, hPaddingLeft, hPaddingRight;
    private JLabel titleLabel;

    private JPanel optionPanel;
    private JPanel buttonPanel;
    private JButton[] buttonArray;
    private String[] buttonText;
    private String oriText;
    private JButton startButton, settingButton, quitButton;

    private BufferedImage backgroundImage;

    public InitialUI(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.uTool = new UtilityTool();

        backgroundImage = uTool.imageSetup("UI", "ForestBackground");

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        setLayout(new BorderLayout());

        //CENTRE PANEL
        centrePanel = new JPanel(new BorderLayout(10, 10));
        centrePanel.setOpaque(false);

        // Vertical padding at the top
        vPaddingTop = new JPanel();
        vPaddingTop.setPreferredSize(new Dimension(getPreferredSize().width, 100));
        vPaddingTop.setOpaque(false);

        // Vertical padding at the bottom
        vPaddingBottom = new JPanel();
        vPaddingBottom.setPreferredSize(new Dimension(getPreferredSize().width, 100));
        vPaddingBottom.setOpaque(false);

        // Horizontal padding on the left
        hPaddingLeft = new JPanel();
        hPaddingLeft.setPreferredSize(new Dimension(100, getPreferredSize().height));
        hPaddingLeft.setOpaque(false);

        // Horizontal padding on the right
        hPaddingRight = new JPanel();
        hPaddingRight.setPreferredSize(new Dimension(100, getPreferredSize().height));
        hPaddingRight.setOpaque(false);
        //Title label
        titleLabel = new JLabel("Echoes of the Forest");
        titleLabel.setIcon(new ImageIcon(uTool.imageSetup("UI", "titleBackground", 1010, 240)));
        titleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        titleLabel.setVerticalTextPosition(SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(getPreferredSize().width, 250));
        titleLabel.setForeground(Color.gray);
        titleLabel.setFont(getFont().deriveFont(Font.BOLD, 55F));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        //Option panel
        optionPanel = new JPanel();
        optionPanel.setOpaque(false);

        //Button panel
        buttonPanel = new JPanel(new GridLayout(4, 1, 0, 30));
        buttonPanel.setOpaque(false);

        //Start button
        startButton = new JButton();

        //Tutorial button
        settingButton = new JButton();

        //Quit button
        quitButton = new JButton();

        buttonPanel.add(startButton);
        buttonPanel.add(settingButton);
        buttonPanel.add(quitButton);

        optionPanel.add(buttonPanel);

        centrePanel.add(titleLabel, BorderLayout.NORTH);
        centrePanel.add(optionPanel, BorderLayout.CENTER);

        add(centrePanel, BorderLayout.CENTER);
        add(vPaddingTop, BorderLayout.NORTH);
        add(hPaddingRight, BorderLayout.EAST);
        add(hPaddingLeft, BorderLayout.WEST);
        add(vPaddingBottom, BorderLayout.SOUTH);

        //Element configuration
        //Start button
        startButton.setText(" START GAME ");
        startButton.setIcon(new ImageIcon(uTool.imageSetup("UI", "buttonBackground", 225, 70)));
        startButton.setHorizontalTextPosition(SwingConstants.CENTER);
        startButton.setVerticalTextPosition(SwingConstants.CENTER);
        startButton.setContentAreaFilled(false);
        startButton.setPreferredSize(new Dimension(226, 72));
        startButton.setBorderPainted(false);
        startButton.addActionListener(e -> switchPanel("StartGameUI", 1));

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (commandNum != 0)
                    updateSelection(0);
            }
        });

        //Setting button
        settingButton.setText("  SETTINGS  ");
        settingButton.setIcon(new ImageIcon(uTool.imageSetup("UI", "buttonBackground", 225, 70)));
        settingButton.setHorizontalTextPosition(SwingConstants.CENTER);
        settingButton.setVerticalTextPosition(SwingConstants.CENTER);
        settingButton.setContentAreaFilled(false);
        settingButton.setPreferredSize(new Dimension(226, 72));
        settingButton.setBorderPainted(false);
        settingButton.addActionListener(e -> switchPanel("SettingUI", 2));
        settingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (commandNum != 1)
                    updateSelection(1);
            }
        });

        //Quit button
        quitButton.setText("  QUIT  ");
        quitButton.setIcon(new ImageIcon(uTool.imageSetup("UI", "buttonBackground", 225, 70)));
        quitButton.setHorizontalTextPosition(SwingConstants.CENTER);
        quitButton.setVerticalTextPosition(SwingConstants.CENTER);
        quitButton.setContentAreaFilled(false);
        quitButton.setPreferredSize(new Dimension(226, 72));
        quitButton.setBorderPainted(false);
        quitButton.addActionListener(e -> System.exit(0));
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (commandNum != 2)
                    updateSelection(2);
            }
        });
        buttonText = new String[]{startButton.getText(), settingButton.getText(), quitButton.getText()};
        buttonArray = new JButton[] {startButton, settingButton, quitButton};

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e){
                timer.start();
            }

            @Override
            public void componentHidden(ComponentEvent e){
                timer.stop();
            }
        });

        //Timer for blinking text !!! MUST MAKE SURE THIS IS STOPPED TO PREVENT LAG !!!
        timer = new Timer(300, e -> {
            if(blinking){
                buttonArray[commandNum].setText("");
            }
            else {
                buttonArray[commandNum].setText(oriText);
            }
            blinking = !blinking;
        });
        timer.start();

        //Accept keyboard inputs
        updateSelection(commandNum, 0);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                        if (commandNum > 0) {
                            updateSelection(commandNum, -1);
                        }
                    }
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                        if (commandNum < maxCommandNum) {
                            updateSelection(commandNum, +1);
                        }
                    }
                    case KeyEvent.VK_ENTER, KeyEvent.VK_F -> {
                        switch (commandNum) {
                            case 0 -> switchPanel("StartGameUI", 1);
                            case 1 -> switchPanel("SettingUI", 2);
                            case 2 -> System.exit(0);
                        }
                    }
                }
            }
        });

        setFocusable(true);
        requestFocusInWindow();
    }

    public void updateSelection(int commandNum, int i) {
        buttonArray[commandNum].setText(buttonText[commandNum]);

        buttonArray[commandNum + i].setText("> " + buttonText[commandNum + i].trim() + " <");
        this.oriText = buttonArray[commandNum + i].getText();
        this.commandNum += i;
    }

    public void updateSelection(int commandNum){
        for (int i = 0; i < buttonArray.length; i++){
            if (i == commandNum){
                buttonArray[i].setText("> " + buttonText[i].trim() + " <");
                this.oriText = buttonArray[i].getText();
            }
            else {
                buttonArray[i].setText(buttonText[i]);
            }
        }

        this.commandNum = commandNum;
    }


    public void switchPanel(String ui, int index){
        cardLayout.show(mainPanel, ui);
        mainPanel.getComponent(index).requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
