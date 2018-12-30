package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseCommon {
    public static Properties properties;
    public int RESPONSE_STATUS_CODE_200=200;

    public BaseCommon(){
        try{
            properties = new Properties();
            FileInputStream ip = new FileInputStream("/Users/sony.priyadarshini/Documents/Sony_Personal_Code/" +
                    "HTTPClientApiAutomation/src/main/java/config/config.properties");
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
