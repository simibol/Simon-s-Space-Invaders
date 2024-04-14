package invaders;

import javafx.application.Application;
import javafx.stage.Stage;
import invaders.engine.GameEngine;
import invaders.engine.GameWindow;
import invaders.singleton.EasyMode;
import invaders.singleton.HardMode;
import invaders.singleton.MediumMode;

import java.util.Map;

public class App extends Application {

    private String selectedConfig = "src/main/resources/config_easy.json";
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameEngine model = new GameEngine("src/main/resources/config_easy.json");
        GameWindow window = new GameWindow(model);
        window.run();

        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(window.getScene());
        primaryStage.show();

        window.getScene().setOnKeyPressed(event -> {

            switch (event.getCode()) {
                case E:
                    selectedConfig = EasyMode.getInstance().getConfigPath();
                    System.out.println("Easy Level Selected!");
                    break;
                case M:
                    selectedConfig = MediumMode.getInstance().getConfigPath();
                    System.out.println("Medium Level Selected!");
                    break;
                case H:
                    selectedConfig = HardMode.getInstance().getConfigPath();
                    System.out.println("Hard Level Selected!");
                    break;
                default:
                    break;
            }
            loadGame(primaryStage);
        });

        primaryStage.setScene(window.getScene());
        primaryStage.show();
    }
    private void loadGame(Stage primaryStage){
        GameEngine model = new GameEngine(selectedConfig);
        GameWindow window = new GameWindow(model);
        window.run();

        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(window.getScene());
        primaryStage.show();

        window.run();
    }
}
