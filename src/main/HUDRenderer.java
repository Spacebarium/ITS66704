package main;

import entity.type.Player;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HUDRenderer {
    private final GamePanel gp;
    private final int scale;
    private Player player;
    
    private BufferedImage slot;
    private BufferedImage slotSelected;
    
    public HUDRenderer(GamePanel gp) {
        this.gp = gp;
        this.scale = gp.getScale();
        this.player = gp.entityManager.getPlayer();
        
        loadTextures();
    }
    
    public final void loadTextures() {
        try {
            slot = ImageIO.read(getClass().getResource("/ui/slot.png"));
            slotSelected = ImageIO.read(getClass().getResource("/ui/slot_selected.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2) {
        // SETTINGS
        g2.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.setColor(Color.WHITE);
        
        Dimension window = gp.getSize();
        int ww = window.width;
        int wh = window.height;
        int wh_2 = wh / 2;
        
        // draw hp bar
        int maxHealth = player.getMaxHealth();
        int currentHealth = player.getHealth();
        int hpw = 160;
        int hph = 40;
        int currentHealthX = (int) ((double) currentHealth / maxHealth * hpw);

        int hpx = ww - hpw - 5 * scale;
        int hpy = wh_2 - (slot.getHeight() + 8) * scale - hph;
        g2.setColor(new Color(32, 32, 32));
        g2.fillRect(hpx, hpy, hpw, hph);
        g2.setColor(Color.RED);
        g2.fillRect(hpx, hpy, currentHealthX, hph);
        g2.setColor(new Color(163, 19, 25));
        g2.fillRect(hpx, hpy + 24, currentHealthX, hph - 24);
        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(6));
        g2.drawRect(hpx, hpy, hpw, hph);
        g2.setColor(Color.WHITE);
        g2.drawString(String.format("%d / %d", currentHealth, maxHealth), hpx + 24, hpy + 26);
        
        // draw slots
        int slotsX = ww - (slot.getWidth() + 4) * scale;
        int slot0Y = wh_2 - (slot.getHeight() + 2) * scale;
        g2.drawImage(slot, slotsX, wh_2 - (slot.getHeight() + 2) * scale, slot.getWidth() * scale, slot.getHeight() * scale, null);
        g2.drawImage(slot, slotsX, wh_2 + 2 * scale, slot.getWidth() * scale, slot.getHeight() * scale, null);
        
        // draw weapons in hotbar
        BufferedImage slot0Img = player.getWeaponFromSlot(0).getImage();
        BufferedImage slot1Img = player.getWeaponFromSlot(1).getImage();
        g2.drawImage(slot0Img, slotsX + 15, slot0Y + 15, 32, 32, null);
        g2.drawImage(slot1Img, slotsX + 12, slot0Y + slot.getHeight() * scale + 30, null);
        
        // draw equipped index
        int sx = ww - (slotSelected.getWidth() + 3) * scale;
        int sy = wh_2 - (player.getEquippedWeaponIndex() == 0 ? slotSelected.getHeight() + 1 : -1) * scale;
        int sw = slotSelected.getWidth() * scale;
        int sh = slotSelected.getHeight() * scale;
        g2.drawImage(slotSelected, sx, sy, sw, sh, null);
    }
}
