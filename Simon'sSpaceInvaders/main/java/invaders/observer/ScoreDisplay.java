package invaders.observer;

import javafx.scene.text.Text;

public class ScoreDisplay implements Observer{
    private Text scoreText;

    public ScoreDisplay(){
        scoreText = new Text();
    }
    public void update(int score, int time){
        scoreText.setText("Score: "+ score);
    }
    public Text getScoreText(){
        return scoreText;
    }
}
