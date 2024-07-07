package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {

    GamePanel gp;
    Font arial_40, arial_80B;

    // BufferedImage keyimage;
    private boolean messageOn = false;
    private String message = "";

    private int commandNum;
    private final int maxCommandNum = 2;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

    }

    /**
     * public void showMessage(String text){ //MIGHT DELETE THIS
     * <p>
     * message = text;
     * messageOn = true;
     * }
     **/

    public int getCommandNum() {
        return commandNum;
    }

    public void setCommandNum(int commandNum) {
        this.commandNum = commandNum;
    }

    public int getMaxCommandNum() {
        return maxCommandNum;
    }

    // TITLE SCREEN
    public void drawTitleScreen(Graphics2D g2) {

        //SET BACKGROUND COLOR
        g2.setColor(new Color(0, 0, 128));
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 66F));
        String text = "Echoes Of The Forest";
        int x = getXforCenteredText(text, g2); //CENTERING THE TEXT
        int y = gp.getTileSize() * 3; //LOWERING THE TEXT

        //SHADOW
        g2.setColor(Color.GRAY);
        g2.drawString(text, x + 5, y + 5);

        //TEXTCOLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        //MAIN CHARACTER IMAGE
        /**x = gp.screenWidth/2 - (gp.getTileSize()*2)/2;
         y += gp.getTileSize()*3;
         g2.drawImage(addimagehere, x, y, gp.getTileSize()*2, gp.getTileSize()*2, null);**/ //NEED HELP DISPLAYING THE IMAGE

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        text = "START GAME";
        x = getXforCenteredText(text, g2);
        y += gp.getTileSize() * 5;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.getTileSize(), y);
        }

        text = "SETTINGS";
        x = getXforCenteredText(text, g2);
        y += gp.getTileSize();
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.getTileSize(), y);
        }

        text = "QUIT";
        x = getXforCenteredText(text, g2);
        y += gp.getTileSize();
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.getTileSize(), y);
        }
    }

    // SETTINGS SCREEN
    public void drawSettingScreen(Graphics2D g2) {
        String text = "SETTINGS";

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));
        g2.setFont(arial_80B);
        int x = getXforCenteredText(text, g2);
        int y = gp.getScreenHeight() / 2;

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }

    // PAUSE SCREEN
    public void drawPauseScreen(Graphics2D g2) {
        String text = "PAUSED";

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));
        g2.setFont(arial_80B);
        int x = getXforCenteredText(text, g2);
        int y = gp.getScreenHeight() / 2;

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.getScreenWidth() / 2 - length / 2;
        return x;
    }
}