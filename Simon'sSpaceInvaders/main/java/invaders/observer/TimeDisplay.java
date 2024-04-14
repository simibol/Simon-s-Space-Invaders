package invaders.observer;

import javafx.scene.text.Text;

public class TimeDisplay implements Observer{
    private Text timeText;

    @Override
    public void update(int score, int time) {
        int minutes = time / 7200;
        int seconds = time / 120;

        timeText.setText(String.format("Time: %d:%02d", minutes, seconds % 60));
    }
    public TimeDisplay(){
        timeText = new Text();
    }
    public Text getTimeText(){
        return timeText;
    }
    
}
