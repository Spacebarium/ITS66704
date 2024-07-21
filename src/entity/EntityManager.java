package entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.awt.Graphics2D;

import entity.type.*;
import item.KeyItem;
import main.GamePanel;

public class EntityManager {

    private final GamePanel gp;
    private final List<Entity> entities;
    private Player player;
    private int lastEnemyDeathX, lastEnemyDeathY;

    public EntityManager(GamePanel gp) {
        entities = new ArrayList<>();
        this.gp = gp;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        if (entity.getEntityType() == EntityType.PLAYER) {
            player = (Player) entity;
        }
    }
    
    public Player getPlayer() { return player; }

    public void removeEntity(Entity entity) {
        System.out.println("DEBUG3");
        entities.remove(entity);
    }

    public void clearEntities() {
        List<Entity> entitiesToRemove = new ArrayList<>();

        System.out.println("Entities before clearing: " + entities.size());
        for (Entity entity : entities) {
            if (entity != player) {
                System.out.println("Marking for removal: " + entity);
                entitiesToRemove.add(entity);
            }
        }

        System.out.println("Entities to remove: " + entitiesToRemove.size());
        for (Entity entity : entitiesToRemove) {
            removeEntity(entity);
            System.out.println("Removed: " + entity);
        }

        System.out.println("Entities after clearing: " + entities.size());
        System.out.println("DEBUG2");
    }

    public void showEntities() {
        for (Entity entity : entities) {
            System.out.println(entity.getName());
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }
    
    public <T extends Entity> List<T> getEntities(Class<T> entityType) {
        return (List<T>) entities.stream()
                .filter(e -> entityType.isInstance(e))
                .toList();
    }
    
    public <T extends Entity> List<T> getEntitiesInRange(int x, int y, int range, Class<T> entityType) {
        return entities.stream()
                .filter(e -> entityType == null || entityType.isInstance(e))
                .filter(e -> e.isInRange(x, y, range))
                .map(entityType::cast)
                .toList();
    }
    
    public List<Entity> getEntitiesInRange(int x, int y, int range) {
        return getEntitiesInRange(x, y, range, Entity.class);
    }

    public void update() {
        synchronized (entities) {
            entities.forEach(e -> e.update());
            
            List<Entity> entitiesToRemove = new ArrayList<>();
            entities.stream()
                    .filter(e -> e.getHealth() == 0)
                    .forEach(e -> {
                        if (e.getEntityType() == EntityType.ENEMY) {
                            lastEnemyDeathX = e.getX();
                            lastEnemyDeathY = e.getY();
                        }
                        entitiesToRemove.add(e);
                    });


            entitiesToRemove.forEach(this::removeEntity);
            
            if (getEntities(Enemy.class).isEmpty() && !gp.levelCleared) {
                // spawn key
                gp.itemManager.addItem(new KeyItem(gp, lastEnemyDeathX, lastEnemyDeathY));
                System.out.printf("spawned key at (%d, %d)\n", lastEnemyDeathX, lastEnemyDeathY);
                gp.levelCleared = true;
            }
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
