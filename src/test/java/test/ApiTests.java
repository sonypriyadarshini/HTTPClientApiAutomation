package test;

import base.BaseCommon;
import client.RestApiHelperMethods;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.Users;

import java.io.IOException;

/* Contains test class for Get, Put, Post Apis
* HTTP client is used to make the calls
* Gson is used to parse thriugh the reponse using pojos
*/

public class ApiTests extends BaseCommon {
    BaseCommon baseCommon;
    String url;
    RestApiHelperMethods restApiHelperMethods;
    CloseableHttpResponse httpResponse;

    @BeforeClass
    public void beforeClass() throws IOException {
        baseCommon = new BaseCommon();
        url = properties.getProperty("URL") + properties.getProperty("user");
    }

    //uses the basic get method which prints statuscode, headers and body. no validation through the json response is done
    @Test
    public void getTest() throws IOException {
        restApiHelperMethods = new RestApiHelperMethods();
        restApiHelperMethods.get(url);
    }

    //uses the get mthod which returns the response. rest of the validation though the response is done in the test using Gson
    @Test
    public void getVlidateJsonTest() throws IOException {
        restApiHelperMethods = new RestApiHelperMethods();
        httpResponse = restApiHelperMethods.getWithReturn(url);

        //get the status code, print and validate
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("Status code: " + statusCode);
        Assert.assertEquals(statusCode, baseCommon.RESPONSE_STATUS_CODE_200, "Status code isn't 200");

        //convert the response to String and print it
        String stringResponse = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        System.out.println("String Response: " + stringResponse);

//        JsonParser jsonParser = new JsonParser();
//        JsonElement jsonTree = jsonParser.parse(stringResponse);
//        if(jsonTree.isJsonObject()) {
//            JsonObject jsonObject = jsonTree.getAsJsonObject();
//        }
//        JsonObject jsonObject = jsonTree.getAsJsonObject();
//        JsonElement last_name = jsonObject.get("data");
//        System.out.println("Last name Response: "+last_name);

        //use Gson to parse through the response string. Pojos of the response are created in pojo class. pojo classes are invoked here and are parsed through.
        Gson gson = new GsonBuilder().create();

        //reference of Users pojo is created
        Users users;
        users = gson.fromJson(stringResponse, Users.class);

        //the created users object is parsed and first_name of 1st 3 users are printed
        for (int i = 0; i < 3; i++) {
            System.out.println("First_name of all users : " + users.getData().get(i).getFirstName());
        }
    }
}
