package enemy;

import java.awt.Graphics2D;
import enemy.type.Entity;
import main.GamePanel;
import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EntityManager {

    GamePanel gp;
    private final List<Entity> entities;

    public EntityManager(GamePanel gp) {
        this.gp = gp;
        entities = new ArrayList<>();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
    
    public List<Entity> getEntities() {
        return entities;
    }

    public void update() {
//        for (Object entityObject : entities) {
//            switch (entityObject) {
//                case Player player:
//                    player.update();
//                    break;
//                default: System.out.println("No entity found!");
//            }
//        }
        synchronized (entities) {
            for (Entity entity : entities) {
                entity.update();
            }
        }
    }

    public void draw(Graphics2D g2) {
        List<Entity> sortedEntities;
        synchronized (entities){
            sortedEntities = new ArrayList<>(entities);
        }
        sortedEntities.sort(Comparator.comparingInt(Entity::getY));
        
//        for (Object entityObject : entities) {
//            Entity entity = (Entity) entityObject;
//            entity.draw(g2);
//        }

        for (Entity entity : entities) { entity.draw(g2); }
    }
}
