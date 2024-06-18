package entity;

import main.GamePanel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityManager {

    GamePanel gp;
    List<ArrayList<Entity>> entities = new CopyOnWriteArrayList<>();
    ArrayList<Entity> player = new ArrayList<>();
    ArrayList<Entity> newEnemy = new ArrayList<>();
    ArrayList<Entity> newNPC = new ArrayList<>();
    ArrayList<Entity> newObject = new ArrayList<>();
    ArrayList<Entity> entitySort = new ArrayList<>();

    public EntityManager(GamePanel gp){
        this.gp = gp;

        for (Entity.Type entityType : Entity.Type.values()){
            entities.add(new ArrayList<>());
        }
    }

    public synchronized void setupEntity(Entity entity) {
        switch (entity.getEntityType()) {
            case PLAYER -> entities.get(Entity.Type.PLAYER.ordinal()).add(entity);

            case ENEMY -> entities.get(Entity.Type.ENEMY.ordinal()).add(entity);

            case NPC -> entities.get(Entity.Type.NPC.ordinal()).add(entity);

            case OBJECT -> entities.get(Entity.Type.OBJECT.ordinal()).add(entity);

        }
    }

    public List<Entity> sortedEntities() {
        List<Entity> sortedList = new ArrayList<>();
        for (ArrayList<Entity> entityType : entities) {
            sortedList.addAll(entityType);
        }
        sortedList.sort(Comparator.comparingInt(Entity::getY));
        return sortedList;
    }
}
