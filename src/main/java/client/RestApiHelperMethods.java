package client;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RestApiHelperMethods {

    //get method with header. Statuscode, body and headers are printed
    public void get(String url, HashMap<String, String> headers) throws IOException {

        //set a default connection
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //get the api endpoint url
        HttpGet httpGet = new HttpGet(url);

        for(Map.Entry<String,String> entryKey:headers.entrySet()){
            httpGet.addHeader(entryKey.getKey(),entryKey.getValue());
        }

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

    //get method without header. returns response
    public CloseableHttpResponse get(String url) throws IOException {

        //set a default connection
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //get the api endpoint url
        HttpGet httpGet = new HttpGet(url);

        //execute or hit the api and return the response
        return httpClient.execute(httpGet);
    }

    //post method with header and payloac
    public CloseableHttpResponse post(String url, String payload, HashMap<String, String> headers) throws IOException {

        //set a default connection
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //get the api endpoint url
        HttpPost httpPost = new HttpPost(url);

        //set payload
        httpPost.setEntity(new StringEntity(payload));

        //headers
        for(Map.Entry<String,String> entryKey:headers.entrySet()){
            httpPost.setHeader(entryKey.getKey(),entryKey.getValue());
        }

        //execute the post request and return the response
        return httpClient.execute(httpPost);
    }

}
