package test;

import base.BaseCommon;
import client.RestApiHelperMethods;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.glassfish.gmbal.Description;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojoResponse.Users;
import requestData.UsersRequestData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/* Contains test class for Get and Post Apis
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

    // test to call get method with header. statuscode, headers and body are printed as part of the get() called.
    // no additional validations in the test is done.

    @Test
    public void getWithHeaderTest() throws IOException {
        restApiHelperMethods = new RestApiHelperMethods();

        //set the header
        HashMap<String,String> headers = new HashMap<String, String>();
        headers.put("Content-Type","application/json");

        //call get method
        restApiHelperMethods.get(url, headers);
    }

    //test to call get method without header. validates the response returned using Gson

    @Test
    public void getValidateJsonTest() throws IOException {
        restApiHelperMethods = new RestApiHelperMethods();
        httpResponse = restApiHelperMethods.get(url);

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

        //use Gson to parse through the response string. Pojos of the response are created in pojoResponse class.
        // pojoResponse classes are invoked here and are parsed through.
        Gson gson = new GsonBuilder().create();

        //reference of Users pojoResponse is created
        Users users;
        users = gson.fromJson(stringResponse, Users.class);

        //the created users object is parsed and first_name of 1st 3 users are printed
        for (int i = 0; i < 3; i++) {
            System.out.println("First_name of all users : " + users.getData().get(i).getFirstName());
        }
    }

    //POST method with header and payload.
    //StatusCode validation is done using HttpResponse and other validations are done using HttpResponse and Gson

    @Test
    @Description("Check POST api with Header, Payload and validate the response received using GSON")
    public void postTest() throws IOException {
        restApiHelperMethods = new RestApiHelperMethods();

        //set the header
        HashMap<String,String> headers = new HashMap<String, String>();
        headers.put("Content-Type","application/json");

        //set payload using jackson
        ObjectMapper mapper = new ObjectMapper();
        UsersRequestData usersRequestData = new UsersRequestData("mydummyname","mydummyjob");

        //object to json file. this creates User.json file in the given path
        mapper.writeValue(new File(System.getProperty("user.dir")+"/src/main/java/requestData/Users.json"), usersRequestData);

        //object to json string
        String payload = mapper.writeValueAsString(usersRequestData);

        httpResponse = restApiHelperMethods.post(url,payload,headers);

        //assertions on statuscode
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(),baseCommon.RESPONSE_STATUS_CODE_201);

        //convert the response to String and print it
        String stringResponse = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        System.out.println("String Response: " + stringResponse);

        //parse through the json object using gson. had the response been complex, we had to use pojo as done in getValidateJsonTest earlier
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(stringResponse);
        JsonObject jsonObject = jsonTree.getAsJsonObject();
        Assert.assertTrue(jsonObject.get("name").toString().equals("\"mydummyname\""),"Name isn't updated");
        Assert.assertTrue(jsonObject.get("job").toString().equals("\"mydummyjob\""),"Job isn't updated");
    }
}
