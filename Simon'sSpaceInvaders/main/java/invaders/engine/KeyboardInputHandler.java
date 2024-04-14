package invaders.engine;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import invaders.ConfigReader;
//import invaders.memento.GameCaretaker;
import invaders.singleton.EasyMode;
import invaders.singleton.HardMode;
import invaders.singleton.MediumMode;

class KeyboardInputHandler {
    private final GameEngine model;
    private boolean left = false;
    private boolean right = false;
    private Set<KeyCode> pressedKeys = new HashSet<>();
   

    

    private Map<String, MediaPlayer> sounds = new HashMap<>();

    KeyboardInputHandler(GameEngine model) {
        this.model = model;
       


        // TODO (longGoneUser): Is there a better place for this code?
        URL mediaUrl = getClass().getResource("/shoot.wav");
        String jumpURL = mediaUrl.toExternalForm();

        Media sound = new Media(jumpURL);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        sounds.put("shoot", mediaPlayer);
    }

    void handlePressed(KeyEvent keyEvent) {
        ConfigReader configReader;
        if (pressedKeys.contains(keyEvent.getCode())) {
            return;
        }
        pressedKeys.add(keyEvent.getCode());

        if (keyEvent.getCode().equals(KeyCode.SPACE)) {
            if (model.shootPressed()) {
                MediaPlayer shoot = sounds.get("shoot");
                shoot.stop();
                shoot.play();
            }
        }

        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            left = true;
        }
        if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            right = true;
        }

        if (left) {
            model.leftPressed();
        }

        if(right){
            model.rightPressed();
        }

        switch(keyEvent.getCode()){
            case E:
                configReader = new ConfigReader(EasyMode.getInstance().getConfigPath());
                //System.out.println("easy mode");
            case M:
                configReader = new ConfigReader(MediumMode.getInstance().getConfigPath());
            case H:
                configReader = new ConfigReader(HardMode.getInstance().getConfigPath());
            default:
                break;
        }
        
    }

    

    void handleReleased(KeyEvent keyEvent) {
        pressedKeys.remove(keyEvent.getCode());

        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            left = false;
            model.leftReleased();
        }
        if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            model.rightReleased();
            right = false;
        }
    }
}
