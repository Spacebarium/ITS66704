package item;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ItemManager {
    
    private final List<Item> items;
    
    public ItemManager() {
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
        itemsInRange.forEach(i -> i.unhighlight());
        
        itemsInRange.getFirst().highlight();
    }
    
    public void draw(Graphics2D g2) {
        synchronized (items) {
            items.stream()
                    .sorted(Comparator.comparingInt(Item::getY))
                    .forEach(e -> e.draw(g2));
        }
    }
}
