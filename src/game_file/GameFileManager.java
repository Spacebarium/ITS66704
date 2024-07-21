package game_file;

import javax.crypto.SecretKey;
import java.io.*;

import static main.GamePanel.getTileSize;
import static main.Sound.*;

public class GameFileManager {
    private final String SAVE_FOLDER = "res/GameFile/";
    private final String SAVE_FILE_PREFIX = "save_slot_";
    private final String SETTINGS_FILE = "settings_";
    private final String KEY_FILE = "res/GameFile/secret.key";
    private final int maxGameFiles = 3;
    private SecretKey secretKey;

    public GameFileManager() {
        File saveDir = new File(SAVE_FOLDER);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        try {
            if (new File(KEY_FILE).exists()) {
                secretKey = FileEncryptor.loadSecretKey(KEY_FILE);
            } else {
                secretKey = FileEncryptor.generateSecretKey();
                FileEncryptor.saveSecretKey(secretKey, KEY_FILE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newGame(int gameSlot) {
        if (gameSlot >= 0 && gameSlot < maxGameFiles) {
            GameFile newGameFile = new GameFile(gameSlot, 1, 0, 0);
            saveGame(newGameFile, gameSlot, newGameFile.getMap(), newGameFile.getPlayerX(), newGameFile.getPlayerY());
        } else {
            System.out.println("Invalid slot number.");
        }
    }

    public void saveGame(GameFile gameFile, int gameSlot, int map, int playerX, int playerY) {
        if (gameSlot >= 0 && gameSlot < maxGameFiles) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FOLDER + SAVE_FILE_PREFIX + gameSlot + ".ser"))) {
                gameFile.setMap(map);
                gameFile.setPlayerX(playerX);
                gameFile.setPlayerY(playerY);
                oos.writeObject(gameFile);
                oos.close();
                // Encrypt the file
                File inputFile = new File(SAVE_FOLDER + SAVE_FILE_PREFIX + gameSlot + ".ser");
                File encryptedFile = new File(SAVE_FOLDER + SAVE_FILE_PREFIX + gameSlot + ".enc");
                FileEncryptor.encryptFile(secretKey, inputFile, encryptedFile);
                // Delete the unencrypted file
                inputFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid slot number.");
        }
    }

    public GameFile loadGame(int gameSlot) throws IOException {
        if (gameSlot >= 0 && gameSlot < maxGameFiles) {
            File encryptedFile = new File(SAVE_FOLDER + SAVE_FILE_PREFIX + gameSlot + ".enc");
            File decryptedFile = new File(SAVE_FOLDER + SAVE_FILE_PREFIX + gameSlot + ".ser");
            if (encryptedFile.exists()) {
                try {
                    // Decrypt the file
                    FileEncryptor.decryptFile(secretKey, encryptedFile, decryptedFile);
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(decryptedFile))) {
                        GameFile gameFile = (GameFile) ois.readObject();
                        // Delete the decrypted file
                        decryptedFile.delete();
                        return gameFile;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                throw new FileNotFoundException("Save file not found in slot " + gameSlot);
            }
        } else {
            System.out.println("Invalid slot number.");
        }
        return null;
    }

    public void saveSettings() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FOLDER + SETTINGS_FILE + ".ser"))) {
            // Create a new SettingFile object with the current settings
            SettingFile settings = new SettingFile(isMusicOn(), isSoundEffectOn());
            // Write the settings object to the file
            oos.writeObject(settings);
            oos.close();
            // Encrypt the file
            File inputFile = new File(SAVE_FOLDER + SETTINGS_FILE + ".ser");
            File encryptedFile = new File(SAVE_FOLDER + SETTINGS_FILE + ".enc");
            FileEncryptor.encryptFile(secretKey, inputFile, encryptedFile);
            // Delete the unencrypted file
            inputFile.delete();
            System.out.println("Settings saved and encrypted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadSettings() {
        File encryptedFile = new File(SAVE_FOLDER + SETTINGS_FILE + ".enc");
        File decryptedFile = new File(SAVE_FOLDER + SETTINGS_FILE + ".ser");
        if (encryptedFile.exists()) {
            try {
                // Decrypt the file
                FileEncryptor.decryptFile(secretKey, encryptedFile, decryptedFile);
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(decryptedFile))) {
                    SettingFile settings = (SettingFile) ois.readObject();
                    setMusicOn(settings.isMusicOn());
                    setSoundEffectOn(settings.isSoundEffectOn());
                    decryptedFile.delete(); // Delete the decrypted file
                    System.out.println("Settings loaded successfully.");
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            SettingFile settings = new SettingFile(true, true);
            setMusicOn(settings.isMusicOn());
            setSoundEffectOn(settings.isSoundEffectOn());
            System.out.println("No settings file found, using default settings.");
        }
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

    public boolean checkFile(int gameSlot) {
        File saveFile = new File(SAVE_FOLDER + SAVE_FILE_PREFIX + gameSlot + ".enc");
        return saveFile.exists();
    }

    public File getFile(int gameSlot) {
        return new File(SAVE_FOLDER + SAVE_FILE_PREFIX + gameSlot + ".enc");
    }
}
