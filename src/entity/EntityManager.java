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
        showEntities();
        entities.remove(entity);
        showEntities();
    }

    public void clearEntities() {
        List<Entity> entitiesToRemove = new ArrayList<>();

        for (Entity entity : entities) {
            if (entity != player) {
                entitiesToRemove.add(entity);
            }
        }

        for (Entity entity : entitiesToRemove) {
            removeEntity(entity);
        }
    }

    public void showEntities() {
        for (Entity entity : entities) {
            System.out.println(entity.getName());
        }
    }

    public int entityCount(){
        return entities.size();
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
                player.setHasKey(true);
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
