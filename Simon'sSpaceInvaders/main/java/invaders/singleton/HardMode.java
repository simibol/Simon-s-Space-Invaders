package invaders.singleton;

public class HardMode {
    private static HardMode instance;
    public static final String CONFIG_PATH = "src/main/resources/config_hard.json";

    private HardMode(){

    }
    public static HardMode getInstance(){
        if(instance == null){
            instance = new HardMode();
        }
        return instance;
    }
    public String getConfigPath(){
        return CONFIG_PATH;
    }
}
