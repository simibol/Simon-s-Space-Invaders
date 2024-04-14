package invaders.engine;

import java.util.List;
import java.util.ArrayList;

import invaders.ConfigReader;
import invaders.entities.EntityViewImpl;
import invaders.entities.SpaceBackground;
//import invaders.memento.GameCaretaker;
import invaders.observer.ScoreDisplay;
import invaders.observer.TimeDisplay;
import javafx.scene.control.Label;
import javafx.util.Duration;

import invaders.entities.EntityView;
import invaders.rendering.Renderable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import org.json.simple.JSONObject;

public class GameWindow {
	private final int width;
    private final int height;
	private Scene scene;
    private Pane pane;
    private GameEngine model;
    private List<EntityView> entityViews =  new ArrayList<EntityView>();
    private Renderable background;

    private Label introLabel = new Label("");
    private String demoLabel = "THIS IS A DEMO";


    private double xViewportOffset = 0.0;
    private double yViewportOffset = 0.0;
    


    //private final GameCaretaker caretaker = new GameCaretaker();

	public GameWindow(GameEngine model){
        this.model = model;
		this.width =  model.getGameWidth();
        this.height = model.getGameHeight();
        ScoreDisplay scoreDisplay = new ScoreDisplay();
        TimeDisplay timeDisplay = new TimeDisplay();
    
   

        pane = new Pane();
        scene = new Scene(pane, width, height);
        this.background = new SpaceBackground(model, pane);

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(this.model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        pane.getChildren().add(introLabel);
        pane.getChildren().add(scoreDisplay.getScoreText());
        pane.getChildren().add(timeDisplay.getTimeText());

 
       

        scoreDisplay.getScoreText().setX(300);
        scoreDisplay.getScoreText().setY(600);
        scoreDisplay.getScoreText().setFill(Color.TURQUOISE);
        

        timeDisplay.getTimeText().setX(300);
        timeDisplay.getTimeText().setY(300);
        timeDisplay.getTimeText().setFill(Color.TURQUOISE);
       


         model.getGameState().addObserver(scoreDisplay);
         model.getGameState().addObserver(timeDisplay);

    }

	public void run() {
         Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw()));

         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.play();
    }


    private void draw(){
        model.update();

        //updateTime();

        String intro = "Welcome to Simon's Space Invaders! " +"Press -E- for Easy, -M- for Medium, -H- for Hard Difficulty";

        introLabel.setText("" + intro);
        introLabel.setLayoutX(40);
        introLabel.setLayoutY(20);
        introLabel.setTextFill(Paint.valueOf("red"));
        

        List<Renderable> renderables = model.getRenderables();
        for (Renderable entity : renderables) {
            boolean notFound = true;
            for (EntityView view : entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
        }

        for (Renderable entity : renderables){
            if (!entity.isAlive()){
                for (EntityView entityView : entityViews){
                    if (entityView.matchesEntity(entity)){
                        entityView.markForDelete();
                    }
                }
            }
        }

        for (EntityView entityView : entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
            }
        }


        model.getGameObjects().removeAll(model.getPendingToRemoveGameObject());
        model.getGameObjects().addAll(model.getPendingToAddGameObject());
        model.getRenderables().removeAll(model.getPendingToRemoveRenderable());
        model.getRenderables().addAll(model.getPendingToAddRenderable());

        model.getPendingToAddGameObject().clear();
        model.getPendingToRemoveGameObject().clear();
        model.getPendingToAddRenderable().clear();
        model.getPendingToRemoveRenderable().clear();

        entityViews.removeIf(EntityView::isMarkedForDelete);

    }

	public Scene getScene() {
        return scene;
    }

 
    
}
