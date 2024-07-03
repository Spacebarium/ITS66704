package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Font arial_40, arial_80B;
    //BufferedImage keyimage;
    public boolean messageOn = false;
    public String message = "";
    private Graphics2D g2;
    public int commandNum = 0;

    public UI(GamePanel gp){

        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

    }

    /**public void showMessage(String text){ //MIGHT DELETE THIS

     message = text;
     messageOn = true;
     }**/

    public void draw(Graphics2D g2){

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);
        //g2.drawString("MAK KAU HIJAU", 50, 50);

        //TITLE STATE
        if (gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        //PAUSE STATE
        /**if (gp.gameState == gp.pauseState) {
         drawPauseScreen();
         }**/

    }

    //TITLE SCREEN
    public void drawTitleScreen(){

        //SET BACKGROUND COLOR
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 66F));
        String text = "Echoes Of The Forest";
        int x = getXforCenteredText(text); //CENTERING THE TEXT
        int y = gp.tileSize*3; //LOWERING THE TEXT

        //SHADOW
        g2.setColor(Color.GRAY);
        g2.drawString(text, x+5, y+5);

        //TEXTCOLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        //MAIN CHARACTER IMAGE
        /**x = gp.screenWidth/2 - (gp.tileSize*2)/2;
         y += gp.tileSize*3;
         g2.drawImage(addimagehere, x, y, gp.tileSize*2, gp.tileSize*2, null);**/ //NEED HELP DISPLAYING THE IMAGE

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y +=  gp.tileSize*5;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y +=  gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "SETTINGS";
        x = getXforCenteredText(text);
        y +=  gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y +=  gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 3){
            g2.drawString(">", x-gp.tileSize, y);
        }


    }

    //PAUSE SCREEN
    /**public void drawPauseScreen() {
     String text = "PAUSED";

     g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));
     g2.setFont(arial_80B);
     int x = getXforCenteredText(text);
     int y = gp.screenHeight / 2;

     g2.setColor(Color.WHITE);
     g2.drawString(text, x, y);
     }**/

    public int getXforCenteredText(String text){

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;

    }
}
