package invaders.singleton;

public class EasyMode {
    private static EasyMode instance;
    public static final String CONFIG_PATH = "src/main/resources/config_easy.json";

    private EasyMode(){

    }
    public static EasyMode getInstance(){
        if(instance == null){
            instance = new EasyMode();
        }
        return instance;
    }
    public String getConfigPath(){
        return CONFIG_PATH;
    }
}
