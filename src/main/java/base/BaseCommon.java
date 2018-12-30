package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseCommon {
    public static Properties properties;
    public int RESPONSE_STATUS_CODE_200=200;
    public int RESPONSE_STATUS_CODE_201=201;

    public BaseCommon(){
        try{
            properties = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/config/config.properties");
            properties.load(ip);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
