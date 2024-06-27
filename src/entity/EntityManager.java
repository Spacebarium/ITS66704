package entity;

import java.awt.Graphics2D;
import entity.type.Entity;
import entity.type.Player;
import main.GamePanel;

import java.util.ArrayList;
import java.util.Collections;
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

<<<<<<< HEAD
    public void update() {
        for (Object entityObject : entities) {
            switch (entityObject) {
                case Player player:
                    player.update();
                    break;
                default: System.out.println("No entity found!");
            }
        }
=======
    public synchronized void update() {
//        for (Object entityObject : entities) {
//            switch (entityObject) {
//                case Player player:
//                    player.update();
//                    break;
//                default: System.out.println("No entity found!");
//            }
//        }
>>>>>>> 724e511fdc8f90f84b4f817e4be172a4f13851b8

        for (Entity entity : entities) { entity.update(); }
    }

    public void draw(Graphics2D g2) {
        Collections.sort(entities, Comparator.comparingInt(Entity::getY));
        
        for (Object entityObject : entities) {
            Entity entity = (Entity) entityObject;
            entity.draw(g2);
        }

        for (Entity entity : entities) { entity.draw(g2); }
    }

}
