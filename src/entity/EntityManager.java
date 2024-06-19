package entity;

import java.awt.Graphics2D;
import main.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    GamePanel gp;
    private List<Entity> entities;

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

    public void update() {
//        for (Object entityObject : entities) {
//            switch (entityObject) {
//                case Player player:
//                    player.update();
//                    break;
//                default: System.out.println("No entity found!");
//            }
//        }

        for (Entity entity : entities) {
            entity.update();
        }
    }

    public void draw(Graphics2D g2) {
//        for (Object entityObject : entities) {
//            Entity entity = (Entity) entityObject;
//            entity.draw(g2);
//        }

        for (Entity entity : entities) {
            entity.draw(g2);
        }
    }

}
