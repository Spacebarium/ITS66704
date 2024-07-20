package item;

import main.GamePanel;

public class KeyItem extends Item {
    
    private int id;

    public KeyItem(GamePanel gp, int x, int y, String name, String textureFilePath, int id) {
        super(gp, x, y, name, textureFilePath);
        this.id = id;
    }

}
