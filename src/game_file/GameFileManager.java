package game_file;

import main.GamePanel;

import java.io.*;

import static main.GamePanel.getTileSize;

public class GameFileManager {
    private final String SAVE_FOLDER = "res/GameFile/";
    private final String SAVE_FILE_PREFIX = "save_slot_";
    private final int maxGameFiles = 3;

    public GameFileManager(){
        File saveDir = new File(SAVE_FOLDER);
        if (!saveDir.exists()){
            saveDir.mkdir();
        }
    }

    public void newGame(int gameSlot){
        if (gameSlot >= 0 && gameSlot < maxGameFiles) {
            GameFile newGameFile = new GameFile(gameSlot, "Level1", 10 * getTileSize(), 20 * getTileSize());
            saveGame(newGameFile, gameSlot, newGameFile.getMap(), newGameFile.getPlayerX(), newGameFile.getPlayerY());
        } else {
            System.out.println("Invalid slot number.");
        }
    }

    public void saveGame(GameFile gameFile, int gameSlot, String map, int playerX, int playerY) {
        if (gameSlot >= 0 && gameSlot < maxGameFiles) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FOLDER + SAVE_FILE_PREFIX + gameSlot + ".ser"))) {
                gameFile.setMap(map);
                gameFile.setPlayerX(playerX);
                gameFile.setPlayerY(playerY);
                oos.writeObject(gameFile);
                System.out.println("Game saved successfully.");
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