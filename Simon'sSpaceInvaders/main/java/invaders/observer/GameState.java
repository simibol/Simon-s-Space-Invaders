package invaders.observer;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private int score;
    private int time;
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        observers.add(observer);
    }
    public void removeObserver(Observer observer){
        observers.remove(observer);
    }
    public void setTime(int time){
        this.time = time;
        alertObservers();
    }
    public void updateScore(int pointScore){
        this.score += pointScore;
        alertObservers();
    }
    public void alertObservers(){
        for(Observer observer : observers){
            observer.update(score, time);
        }
    }
    public int getScore(){
        return score;
    }
    public int getTime(){
        return time;
    }
    
}
