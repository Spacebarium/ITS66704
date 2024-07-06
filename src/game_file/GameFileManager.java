package game_file;

//package gameFile;
//
//import java.io.*;
//public class GameFileManager {
//    private GameFile [] gameFiles;
//    private final int maxGameFiles = 3;
//    private int gameIndex;
//
//    public GameFileManager(){
//        this.gameFiles = new GameFile[maxGameFiles];
//    }
//    public void newGame(){
//        if (gameFiles.length < maxGameFiles){
//            gameFiles[gameIndex] = new GameFile(400, 200);
//        }
//    }
//
//    public void saveGame(GameFile gameFile, int playerX, int playerY) {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
//            oos.writeObject(gameFile);
//            System.out.println("Game saved successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public GameFileManager loadGame() throws IOException {
//        GameFileManager savedGame = null;
//        File file = new File(SAVE_FILE);
//
//        if (file.exists()) {
//            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
//                savedGame = (GameFileManager) ois.readObject();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace(); // Handle or log the exception as needed
//            }
//        } else {
//            throw new FileNotFoundException("Save file not found: " + SAVE_FILE);
//        }
//
//        return savedGame;
//    }
//
//    public static void deleteSaveGame() {
//        File saveFile = new File(SAVE_FILE);
//        if (saveFile.exists()) {
//            if (saveFile.delete()) {
//                System.out.println("Saved game deleted successfully.");
//            } else {
//                System.out.println("Failed to delete saved game.");
//            }
//        } else {
//            System.out.println("No saved game found.");
//        }
//    }
//}