package game_file;

import main.GamePanel;

import java.io.*;
public class GameFileManager {
    private final String SAVE_FOLDER = "res/GameFile/";
    private final String SAVE_FILE_PREFIX = "save_slot_";
    private final int maxGameFiles = 3;
    private GamePanel gp;

    public GameFileManager(GamePanel gp){
        this.gp = gp;
        File saveDir = new File(SAVE_FOLDER);
        if (!saveDir.exists()){
            saveDir.mkdir();
        }
    }

    public void newGame(int slot){
        if (slot >= 0 && slot < maxGameFiles) {
            GameFile newGameFile = new GameFile();
            saveGame(newGameFile, slot, 400, 200);
        } else {
            System.out.println("Invalid slot number.");
        }
    }

    public void saveGame(GameFile gameFile, int gameSlot, int playerX, int playerY) {
        if (gameSlot >= 0 && gameSlot < maxGameFiles) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FOLDER + SAVE_FILE_PREFIX + gameSlot + ".ser"))) {
                gameFile.playerX = playerX;
                gameFile.playerY = playerY;
                oos.writeObject(gameFile);
                System.out.println("Game saved successfully in slot " + gameSlot);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid slot number.");
        }
    }

    public GameFile loadGame(int gameSlot) throws IOException {
        if (gameSlot >= 0 && gameSlot < maxGameFiles) {
            File file = new File(SAVE_FOLDER + SAVE_FILE_PREFIX +gameSlot + ".ser");
            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    return (GameFile) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace(); // Handle or log the exception as needed
                }
            } else {
                throw new FileNotFoundException("Save file not found in slot " + gameSlot);
            }
        } else {
            System.out.println("Invalid slot number.");
        }
        return null;
    }

    public void deleteGame(int gameSlot) {
        if (gameSlot >= 0 && gameSlot < maxGameFiles) {
            if (checkFile(gameSlot)) {
                if (getFile(gameSlot).delete()) {
                    System.out.println("Saved game deleted successfully from slot " + gameSlot);
                } else {
                    System.out.println("Failed to delete saved game from slot " + gameSlot);
                }
            } else {
                System.out.println("No saved game found in slot " + gameSlot);
            }
        } else {
            System.out.println("Invalid slot number.");
        }
    }

    public boolean checkFile(int gameSlot){
        File saveFile = new File(SAVE_FOLDER + SAVE_FILE_PREFIX +gameSlot + ".ser");
        return saveFile.exists();
    }

    public File getFile(int gameSlot){
        return new File(SAVE_FOLDER + SAVE_FILE_PREFIX +gameSlot + ".ser");
    }
}