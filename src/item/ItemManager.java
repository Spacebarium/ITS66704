package item;

import entity.type.Player;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import main.GamePanel;

public class ItemManager {
    
    private final Player player;
    private final List<Item> items;
    
    public ItemManager(GamePanel gp) {
        this.player = gp.entityManager.getPlayer();
        items = new ArrayList<>();
    }
    
    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }
    
    public List<Item> getItemsInRange(int x, int y, int range) {
        return items.stream()
                .filter(i -> i.isInRange(x, y, range))
                .sorted(Comparator.comparingDouble(i -> i.getDistanceFrom(x, y, range)))
                .toList();
    }
    
    public void highlightNearestItem(int x, int y, int range) {
        List<Item> itemsInRange = getItemsInRange(x, y, range);
        items.forEach(i -> i.setHighlighted(false));
        
        itemsInRange.getFirst().setHighlighted(true);
    }
    
    public void update() {
        synchronized (items) {
            items.forEach(i -> i.update());
        }
//        highlightNearestItem(player.getCentreX(), player.getCentreY(), player.getPickupRadius());
    }
    
    public void draw(Graphics2D g2) {
        synchronized (items) {
            items.forEach(i -> i.draw(g2));
        }
    }
}
