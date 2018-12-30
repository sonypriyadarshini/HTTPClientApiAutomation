package client;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class RestApiHelperMethods {

    //get method
    public void get(String url) throws IOException {

        //set a default connection
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //get the api endpoint url
        HttpGet httpGet = new HttpGet(url);

        //execute or hit the api
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        //get the status code and print it
        int statuscode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("Status code: "+statuscode);

        //convert the response to String and print it
        String stringResponse = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
        System.out.println("String Response: "+stringResponse);

        //convert the String response to json object and print it
        JSONObject jsonResponse = new JSONObject(stringResponse);
        System.out.println("Json Response: "+jsonResponse);

        //get the header and iterate through it using hashmap
        Header[] allHeaders = httpResponse.getAllHeaders();
        HashMap<String,String > headerMap = new HashMap<String, String>();
        for(Header header: allHeaders){
            headerMap.put(header.getName(),header.getValue());
        }
        System.out.println("All headers: "+headerMap);

    }

    public CloseableHttpResponse getWithReturn(String url) throws IOException {

        //set a default connection
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //get the api endpoint url
        HttpGet httpGet = new HttpGet(url);

        //execute or hit the api and return the response
        return httpClient.execute(httpGet);

    }
}
