package invaders.singleton;

public class MediumMode {
    private static MediumMode instance;
    public static final String CONFIG_PATH = "src/main/resources/config_medium.json";

    private MediumMode(){

    }
    public static MediumMode getInstance(){
        if(instance == null){
            instance = new MediumMode();
        }
        return instance;
    }
    public String getConfigPath(){
        return CONFIG_PATH;
    }
}