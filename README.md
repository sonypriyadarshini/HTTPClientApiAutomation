<h1>API automation framework using Apache-HTTpClient</h1>
<h3>Package-test</h3>

Contains TestCases for Get and Post method with and without headers.

**Header:**
header is created using HashMap

**Payload:**
payload is set using Jackson Object mapper `com.fasterxml.jackson.databind.ObjectMapper` and is converted to json string

**Response:**
Response is stored using Apache-HttpClient class CloseableHttpResponse `org.apache.http.client.methods.CloseableHttpResponse`
Assertion is done on status code
The Json response received is changed to String using Apache-EntityUtils `org.apache.http.util.EntityUtils`
The converted Json-to-String response is parsed using Gson Json parser `com.google.gson.JsonParser`
Assertions are done based on the request made
<h3>Package-base</h3>
Contains BaseCommon class which has method to read config.properties file
<h3>Package-client</h3>
Execution of get/post call using Apache-HttpClient `org.apache.http.impl.client.HttpClients` is done here 
Methods are overloaded to execute with and without headers
Response is stored in Apache-HttpClient class CloseableHttpResponse `org.apache.http.client.methods.CloseableHttpResponse` and is returned
<h3>Package-pojoResponse</h3>
Pojo of the response json is created and is used during parsing
<h3>Package-requestData</h3>
UserRequestData Class is written to set the request for post request
User.json is created which contains the json payload to be sent as request to the post request

