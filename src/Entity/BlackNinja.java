package Entity;

import Main.KeyHandler;
import Main.GamePanel;
import Utility.UtilityTool;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BlackNinja extends Entity {

    UtilityTool uTool = new UtilityTool();
    GamePanel gp;
    KeyHandler keyH;

    public BlackNinja(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        setDefault();
        getImage();
    }

    public void setDefault() {
        x = 96;
        y = 96;
        speed = 4;
    }

    public void getImage() {
        left1 = imageSetup("BlackNinja", "blackLeft");
    }

    public void update() {
        gp = new GamePanel();
        if (keyH.upPressed) {
            direction = "up";
            gp.colChecker.checkTop(this);
            if (!topCol) {
                y -= speed;
            }
        }
        if (keyH.downPressed) {
            direction = "down";
            gp.colChecker.checkBot(this);
            if (!botCol) {
                y += speed;
            }
        }
        if (keyH.rightPressed) {
            direction = "right";
            gp.colChecker.checkRight(this);
            if (!rightCol) {
                x += speed;
            }
        }
        if (keyH.leftPressed) {
            gp.colChecker.checkLeft(this);
            direction = "left";
            if (!leftCol) {
                x -= speed;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case "up" ->
                left1;
            case "down" ->
                left1;
            case "left" ->
                left1;
            case "right" ->
                left1;
            default ->
                left1;
        };

        g2.drawImage(image, x, y, null);
    }
}
