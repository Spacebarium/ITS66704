package ui;

import game_file.GameFileManager;
import main.GamePanel;
import utility.UtilityTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static main.Sound.stopMusic;

public class StartGameUI extends JPanel{
    private final GamePanel gp;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final GameFileManager gameFileManager;
    private final UtilityTool uTool;

    private Font arial_40, arial_80B;
    private int commandNum;
    private final int maxCommandNum = 2;

    private JPanel centrePanel;
    private JPanel vPaddingTop, vPaddingBottom, hPaddingLeft, hPaddingRight;
    private JPanel exitLeftPad, exitMiddlePad;
    private JPanel leftPad, topPad;
    private JButton exit;
    private JLabel titleLabel;

    private JPanel optionPanel;
    private JPanel buttonPanel;
    private JButton gameButton1, gameButton2, gameButton3;
    private JPanel closePanel;
    private JButton closeButton1, closeButton2, closeButton3;

    private BufferedImage backgroundImage;

    public StartGameUI(CardLayout cardLayout, JPanel mainPanel, GamePanel gp) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.gp = gp;
        gameFileManager = new GameFileManager();
        uTool = new UtilityTool();

        backgroundImage = uTool.imageSetup("UI", "ForestBackground");

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        setLayout(new BorderLayout());

        //Instantiate elements
        //CENTRE PANEL
        centrePanel = new JPanel(new BorderLayout(10, 10));
        centrePanel.setOpaque(false);

        // Vertical padding at the top
        vPaddingTop = new JPanel(new BorderLayout());
        vPaddingTop.setPreferredSize(new Dimension(getPreferredSize().width, 100));
        vPaddingTop.setOpaque(false);

        //Exit button left padding
        exitLeftPad = new JPanel(new BorderLayout());
        exitLeftPad.setPreferredSize(new Dimension(200, 100));
        exitLeftPad.setOpaque(false);
        //Exit button
        exit = new JButton("BACK");

        //Exit button padding's left padding
        leftPad = new JPanel();
        leftPad.setPreferredSize(new Dimension(25, getPreferredSize().height));
        leftPad.setOpaque(false);
        //Exit button padding's top padding
        topPad = new JPanel();
        topPad.setPreferredSize(new Dimension(getPreferredSize().width, 25));
        topPad.setOpaque(false);

        //Exit button middle padding
        exitMiddlePad = new JPanel();
        exitMiddlePad.setPreferredSize(new Dimension(vPaddingTop.getPreferredSize().width, 100));
        exitMiddlePad.setOpaque(false);

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
        titleLabel = new JLabel("START GAME");
        titleLabel.setIcon(new ImageIcon(uTool.imageSetup("UI", "titleBackground", 1010, 240)));
        titleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        titleLabel.setVerticalTextPosition(SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(getPreferredSize().width, 250));
        titleLabel.setForeground(Color.gray);
        titleLabel.setFont(getFont().deriveFont(Font.BOLD, 55F));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        //Option panel
        optionPanel = new JPanel();
        //optionPanel.setOpaque(false);


        //Button panel
        buttonPanel = new JPanel(new GridLayout(1, 3, 100, 0));
        buttonPanel.setOpaque(false);

        //Start button
        gameButton1 = new JButton();

        //Tutorial button
        gameButton2 = new JButton();

        //Quit button
        gameButton3 = new JButton();

        //Close button panel
        closePanel = new JPanel(new GridLayout(1, 3, 100, 0));
        closePanel.setOpaque(false);

        //Close left button
        closeButton1 = new JButton("X");

        //Close middle button
        closeButton2 = new JButton("X");

        //Close right button
        closeButton3 = new JButton("X");




        //Add elements to panel
        buttonPanel.add(gameButton1);
        buttonPanel.add(gameButton2);
        buttonPanel.add(gameButton3);
        optionPanel.add(buttonPanel, BorderLayout.CENTER);

        closePanel.add(closeButton1);
        closePanel.add(closeButton2);
        closePanel.add(closeButton3);

        centrePanel.add(titleLabel, BorderLayout.NORTH);
        centrePanel.add(buttonPanel, BorderLayout.CENTER);
        centrePanel.add(closePanel, BorderLayout.SOUTH);

        exitLeftPad.add(leftPad, BorderLayout.WEST);
        exitLeftPad.add(topPad, BorderLayout.NORTH);
        exitLeftPad.add(exit, BorderLayout.CENTER);

        vPaddingTop.add(exitLeftPad, BorderLayout.WEST);
        vPaddingTop.add(exitMiddlePad, BorderLayout.CENTER);

        add(centrePanel, BorderLayout.CENTER);
        add(vPaddingTop, BorderLayout.NORTH);
        add(hPaddingRight, BorderLayout.EAST);
        add(hPaddingLeft, BorderLayout.WEST);
        add(vPaddingBottom, BorderLayout.SOUTH);




        //Element configuration
        //Exit button
        exit.setIcon(new ImageIcon(uTool.imageSetup("UI", "buttonBackground", 182, 82)));
        exit.setHorizontalTextPosition(SwingConstants.CENTER);
        exit.setVerticalTextPosition(SwingConstants.CENTER);
        exit.addActionListener(e -> switchPanel("InitialUI", 0));
        exit.setPreferredSize(new Dimension(exitLeftPad.getWidth(), 75));
        exit.setContentAreaFilled(false);

        //Close left button
        closeButton1.setContentAreaFilled(false);
        if (!gameFileManager.checkFile(0)) {
            closeButton1.setVisible(false);
        }
        closeButton1.setIcon(new ImageIcon(uTool.imageSetup("UI", "buttonBackground", 255, 69)));
        closeButton1.addActionListener(e -> {
            gameFileManager.deleteGame(0);
            updateGameButtons(gameButton1, closeButton1, 0);
        });

        //Close middle button
        closeButton2.setContentAreaFilled(false);
        if (!gameFileManager.checkFile(1)) {
            closeButton2.setVisible(false);
        }
        closeButton2.setIcon(new ImageIcon(uTool.imageSetup("UI", "buttonBackground", 255, 69)));
        closeButton2.addActionListener(e -> {
            gameFileManager.deleteGame(1);
            updateGameButtons(gameButton2, closeButton2, 1);
        });

        //Close right button
        if (!gameFileManager.checkFile(2)) {
            closeButton3.setVisible(false);
        }
        closeButton3.setIcon(new ImageIcon(uTool.imageSetup("UI", "buttonBackground", 255, 69)));
        closeButton3.addActionListener(e -> {
            gameFileManager.deleteGame(2);
            updateGameButtons(gameButton3, closeButton3, 2);
        });
        closeButton3.setContentAreaFilled(false);

        //Game button 1
        setupGameButton(0, gameButton1);
        gameButton1.setIcon(new ImageIcon(uTool.imageSetup("UI", "buttonBackground", 500, 600)));
        gameButton1.setHorizontalTextPosition(SwingConstants.CENTER);
        gameButton1.setVerticalTextPosition(SwingConstants.CENTER);
        gameButton1.setContentAreaFilled(false);

        //Game button 2
        setupGameButton(1, gameButton2);
        gameButton2.setIcon(new ImageIcon(uTool.imageSetup("UI", "buttonBackground", 500, 600)));
        gameButton2.setHorizontalTextPosition(SwingConstants.CENTER);
        gameButton2.setVerticalTextPosition(SwingConstants.CENTER);
        gameButton2.setContentAreaFilled(false);

        //Game button 3
        setupGameButton(2, gameButton3);
        gameButton3.setIcon(new ImageIcon(uTool.imageSetup("UI", "buttonBackground", 500, 600)));
        gameButton3.setHorizontalTextPosition(SwingConstants.CENTER);
        gameButton3.setVerticalTextPosition(SwingConstants.CENTER);
        gameButton3.setContentAreaFilled(false);




        //Accept keyboard inputs
        //updateSelection(commandNum);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    switchPanel("InitialUI", 0);
                }
            }
        });

        setFocusable(true);
        requestFocusInWindow();
    }

    public void updateGameButtons(JButton gameButton, JButton closeButton, int gameSlot) {
        if (!gameFileManager.checkFile(gameSlot)) {
            gameButton.setText(" + ");
            ActionListener[] listeners = gameButton.getActionListeners();
            for (ActionListener listener : listeners) {
                gameButton.removeActionListener(listener);
            }
            gameButton.addActionListener(e -> {
                stopMusic(0);
                switchPanel("GamePanel", 3);
                gameFileManager.newGame(gameSlot);
                try {
                    gp.startGameThread(gameFileManager.loadGame(gameSlot));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            closeButton.setVisible(false); // Hide close button if no game file
        }
    }

    public void setupGameButton(int gameSlot, JButton gameButton) {
        if (gameFileManager.checkFile(gameSlot)) {
            gameButton.setText(" Load Game ");
            gameButton.addActionListener(e -> {
                stopMusic(0);
                switchPanel("GamePanel", 3);
                try {
                    gp.startGameThread(gameFileManager.loadGame(gameSlot));
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        } else {
            gameButton.setText(" + ");
            gameButton.addActionListener(e -> {
                stopMusic(0);
                switchPanel("GamePanel", 3);
                gameFileManager.newGame(gameSlot);
                try {
                    gp.startGameThread(gameFileManager.loadGame(gameSlot));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        gameButton.setContentAreaFilled(false);
    }

    public void switchPanel(String ui, int index){
        cardLayout.show(mainPanel, ui);
        mainPanel.getComponent(index).requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.setColor(new Color(0, 0, 0, 150));
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}