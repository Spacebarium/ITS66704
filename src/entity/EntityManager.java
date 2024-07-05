package entity;

import java.awt.Graphics2D;
import entity.type.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EntityManager {

    private final List<Entity> entities;
    private Player player;

    public EntityManager() {
        entities = new ArrayList<>();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        if (entity.getEntityType() == EntityType.PLAYER) {
            player = (Player) entity;
        }
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }
    
    public List<Entity> getEntitiesInRange(int x, int y, int range, EntityType entityType) {
        return entities.stream()
                .filter(e -> entityType == null || e.getEntityType() == entityType)
                .filter(e -> e.isInRange(x, y, range))
                .toList();
    }
    
    public List<Entity> getEntitiesInRange(int x, int y, int range) {
        return getEntitiesInRange(x, y, range, null);
    }
    
    public Player getPlayer() { return player; }

    public void update() {
        synchronized (entities) {
            entities.forEach(e -> e.update());
        }
    }

    public void draw(Graphics2D g2) {
        synchronized (entities) {
            entities.stream()
                    .sorted(Comparator.comparingInt(Entity::getY))
                    .forEach(e -> e.draw(g2));
        }
    }

}
