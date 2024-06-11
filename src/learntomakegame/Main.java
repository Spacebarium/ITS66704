package learntomakegame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{ 

    @Override
    public void start(Stage primaryStage) throws Exception {
        GamePanel gamePanel = new GamePanel();

        Scene scene = new Scene(gamePanel, gamePanel.getPrefWidth(), gamePanel.getPrefHeight(), Color.BLACK);
        primaryStage.setTitle("Game Window");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            gamePanel.isRunning = false; //end thread
            Platform.exit();
                });
        
        gamePanel.startGameThread();
        gamePanel.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
