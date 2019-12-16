package io.github.smyles96.eatstreet.util.http;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.smyles96.eatstreet.exception.EatStreetApiException;

/**
 * Internal class handles the underlying HTTP calls to the EatStreet API
 * 
 * @author smyles96
 */
public class EatStreetRequestor {
    
    public static final String EAT_STREET_API_URL = "eatstreet.com/publicapi/v1";
    //public static final String EAT_STREET_API_URL = "localhost:8000";
    
    private static String ACCESS_TOKEN;
    private static String userApiKey;
    
    /**
     * Creates a new EatStreetRequestor that handles the underlying REST calls
     * to the EatStreet API
     * 
     * @param accessToken A developer access token
     
    public EatStreetRequestor(String accessToken) {
        this.ACCESS_TOKEN = accessToken;
    }
    */
    
    /**
     * Creates a new EatStreetRequestor that handles the underlying REST calls
     * to the EatStreet API
     * 
     * @param accessToken A developer access token
     * @param userApiKey A user's api key
     
    public EatStreetRequestor(String accessToken, String userApiKey) {
        this.ACCESS_TOKEN = accessToken;
        this.userApiKey = userApiKey;
    }
    */
    
    public static void setAccessToken(String token) {
        ACCESS_TOKEN = token;
    }
    
    /**
     * Gets the current user's api key
     * 
     * @return The user's api key
     */
    public static String getUserApiKey() {
        return userApiKey;
    }
    
    /**
     * Sets the user's api key
     * 
     * @param apiKey The api key
     */
    public static void setUserApiKey(String apiKey) {
        userApiKey = apiKey;
    }
    
    /**
     * Makes a GET request to the EatStreet API
     * 
     * @param apiEndpoint The url of the RESTful end point to get from EatStreet
     * @param queryParams A map of GET (name, value) parameters to set for the request
     * @param urlParams Variable number of String arguments to format the endpoint path with
     * 
     * @return The HTTP response stream from the EatStreet server
     * 
     * @throws EatStreetApiException If the request is unable to be made or was corrupted
     */
    public static Reader makeGetRequest(ApiEndpoint apiEndpoint, List<NameValuePair> queryParams, String... urlParams) throws EatStreetApiException {
        // Attempt to make the GET request. Any errors that occur are wrapped in a
        // EatStreetApiException object to provide more specific details to the caller
        try {
            CloseableHttpClient client = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();
            
            // Before the URL is constructed, the access-token param must be added
            // to ensure the request is authenticated with the EatStreet server
            if( queryParams == null ) {
                queryParams = new ArrayList<>();
            }
            
            queryParams.add(new BasicNameValuePair("access-token", ACCESS_TOKEN));
            
            // Some RESTful URLs of the EatStreet API require values to be inserted inside the endpoint
            // path url (such as the user's api key or a card id). The constructEndpointPath will format
            // these particualr endpoint paths with the given urlParams
            String endpointPath = EatStreetRequestor.constructEndpointPath(apiEndpoint, urlParams);
            
            // Start constructing the required EatStreet REST URL
            URI getUri = new URIBuilder()
                         .setScheme("https")
                         .setHost(EAT_STREET_API_URL)
                         .setPath(endpointPath)
                         .setParameters(queryParams)
                         .build();
            
            CloseableHttpResponse response = client.execute(new HttpGet(getUri));
            
            // Check if the server returned a proper JSON payload
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new EatStreetApiException("The server response did not contain any JSON data to parse");
            }
            
            int statusCode = response.getStatusLine().getStatusCode();
            // Check the status code
            if(statusCode != 200) {
                
                // If the status code was in the 400 range, then the server also
                // returned a JSON object with more details
                if(statusCode >= 400 && statusCode <= 499) {
                    try(Reader reader = new InputStreamReader(entity.getContent());) {
                        JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                        
                        int code = json.get("errorCode").getAsInt(); // Error code
                        String message = json.get("details").getAsString(); // Error message
                        
                        throw new EatStreetApiException(String.format("Received error code [%d] from the EatStreet server.\n"
                            + "Server error message: [%s]", code, message));
                    }
                }
                else {
                    throw new EatStreetApiException(String.format("Receieved error code [%d]", statusCode));
                }
                
            }
            
            return new InputStreamReader(entity.getContent());
        }
        catch (URISyntaxException e) {
            String errorMessage = String.format("An error occurred when constructing the GET request URI:\n\t%s", e.getMessage());
            throw new EatStreetApiException(errorMessage);
        }
        catch (IOException e) {
            String errorMessage = String.format("An IO error occurred when making the GET request:\n\t%s", e.getMessage());
            throw new EatStreetApiException(errorMessage);
        }
    }
    
    /**
     * Makes a POST request to the EatStreet API
     * 
     * @param apiEndpoint The url of the RESTful end point to get from EatStreet
     * @param bodyParams A map of parameters to place in the POST request body
     * @param urlParams Variable number of String arguments to format the endpoint path with
     * 
     * @return The HTTP response stream from the EatStreet server
     * 
     * @throws EatStreetApiException If the request is unable to be made or was corrupted
     */
    public static Reader makePostRequest(ApiEndpoint apiEndpoint, List<NameValuePair> jsonParams, String... urlParams) throws EatStreetApiException {
        // Attempt to make the POST request. Any errors that occur are wrapped in a
        // EatStreetApiException object to provide more specific details to the caller
        try {
            CloseableHttpClient client = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();
            
            // Some RESTful URLs of the EatStreet API require values to be inserted inside the endpoint
            // path url (such as the user's api key or a card id). The constructEndpointPath will format
            // these particualr endpoint paths with the given urlParams
            String endpointPath = constructEndpointPath(apiEndpoint, urlParams);
            
            // Construct the URI for the specified end point
            URI postUri = new URIBuilder()
                              .setScheme("https")
                              .setHost(EAT_STREET_API_URL)
                              .setPath(endpointPath)
                              .setParameter("access-token", ACCESS_TOKEN)
                              .build();
            
            // Create the POST object
            HttpPost postRequest = new HttpPost(postUri);
            
            // Construct a JSON object string using the jsonParams, then attach to the POST request
            if( jsonParams != null && jsonParams.size() != 0 ) {
                HttpEntity jsonEntity = new StringEntity(constructJsonObjectStr(jsonParams), ContentType.APPLICATION_JSON);
                postRequest.setEntity(jsonEntity);
                postRequest.setHeader("Accept", "application/json");
                postRequest.setHeader("Content-type", "application/json");
            }
            
            //postRequest.setEntity(new UrlEncodedFormEntity(jsonParams));
            
            // Make the actual request
            CloseableHttpResponse response = client.execute(postRequest);
            
            // Check if the server returned a proper JSON payload
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new EatStreetApiException("The server response did not contain any JSON data to parse");
            }
            
            int statusCode = response.getStatusLine().getStatusCode();
            // Check the status code
            if(statusCode != 200) {
                // If the status code was in the 400 range, then the server also
                // returned a JSON object with more details
                if(statusCode >= 400 && statusCode <= 499) {
                    try(Reader reader = new InputStreamReader(entity.getContent());) {
                        JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                        
                        int code = json.get("errorCode").getAsInt(); // Error code
                        String message = json.get("details").getAsString(); // Error message
                        
                        throw new EatStreetApiException(String.format("Received error code [%d] from the EatStreet server.\n"
                            + "Server error message: [%s]", code, message));
                    }
                }
                else {
                    throw new EatStreetApiException(String.format("Receieved error code [%d]", statusCode));
                }
                
            }
            
            // Get the character set of the returned payload and convert to a Reader object
            ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();
            
            return new InputStreamReader(entity.getContent(), charset);
        }
        catch (URISyntaxException e) {
            String errorMessage = String.format("An error occurred when constructing the POST request URI:\n\t%s", e.getMessage());
            throw new EatStreetApiException(errorMessage);
        }
        catch(IOException e) {
            String errorMessage = String.format("An IO error occurred when making the POST request:\n\t%s", e.getMessage());
            throw new EatStreetApiException(errorMessage);
        }
    }
    
    /**
     * Makes a POST request to the EatStreet API using an existing JSON payload
     * 
     * @param apiEndpoint The url of the RESTful end point to get from EatStreet
     * @param jsonStrPayload The JSON object string to attach to the request
     * @param urlParams Variable number of String arguments to format the endpoint path with
     * 
     * @return The HTTP response stream from the EatStreet server
     * 
     * @throws EatStreetApiException If the request is unable to be made or was corrupted
     */
    public static Reader makePostRequest(ApiEndpoint apiEndpoint, String jsonStrPayload, String... urlParams) throws EatStreetApiException {
        // Attempt to make the POST request. Any errors that occur are wrapped in a
        // EatStreetApiException object to provide more specific details to the caller
        try {
            CloseableHttpClient client = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();
            
            // Some RESTful URLs of the EatStreet API require values to be inserted inside the endpoint
            // path url (such as the user's api key or a card id). The constructEndpointPath will format
            // these particualr endpoint paths with the given urlParams
            String endpointPath = constructEndpointPath(apiEndpoint, urlParams);
            
            // Construct the URI for the specified end point
            URI postUri = new URIBuilder()
                              .setScheme("https")
                              .setHost(EAT_STREET_API_URL)
                              .setPath(endpointPath)
                              .setParameter("access-token", ACCESS_TOKEN)
                              .build();
            
            // Create the POST object
            HttpPost postRequest = new HttpPost(postUri);
            
            // Construct a JSON object string using the jsonPayload, then attach to the POST request
            if( jsonStrPayload != null && (!jsonStrPayload.equals(""))) {
                HttpEntity jsonEntity = new StringEntity(jsonStrPayload, ContentType.APPLICATION_JSON);
                postRequest.setEntity(jsonEntity);
                postRequest.setHeader("Accept", "application/json");
                postRequest.setHeader("Content-type", "application/json");
            }
            
            //postRequest.setEntity(new UrlEncodedFormEntity(jsonParams));
            
            // Make the actual request
            CloseableHttpResponse response = client.execute(postRequest);
            
            // Check if the server returned a proper JSON payload
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new EatStreetApiException("The server response did not contain any JSON data to parse");
            }
            
            int statusCode = response.getStatusLine().getStatusCode();
            // Check the status code
            if(statusCode != 200) {
                
                // If the status code was in the 400 range, then the server also
                // returned a JSON object with more details
                if(statusCode >= 400 && statusCode <= 499) {
                    try(Reader reader = new InputStreamReader(entity.getContent());) {
                        JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                        
                        int code = json.get("errorCode").getAsInt(); // Error code
                        String message = json.get("details").getAsString(); // Error message
                        
                        throw new EatStreetApiException(String.format("Received error code [%d] from the EatStreet server.\n"
                            + "Server error message: [%s]", code, message));
                    }
                }
                else {
                    throw new EatStreetApiException(String.format("Receieved error code [%d]", statusCode));
                }
                
            }
            
            // Get the character set of the returned payload and convert to a Reader object
            ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();
            
            return new InputStreamReader(entity.getContent(), charset);
        }
        catch (URISyntaxException e) {
            String errorMessage = String.format("An error occurred when constructing the POST request URI:\n\t%s", e.getMessage());
            throw new EatStreetApiException(errorMessage);
        }
        catch(IOException e) {
            String errorMessage = String.format("An IO error occurred when making the POST request:\n\t%s", e.getMessage());
            throw new EatStreetApiException(errorMessage);
        }
    }
    
    /**
     * Private helper method that formats certain EatStreet endpoint paths with the
     * user's api key
     * 
     * @param apiEndpoint A apiEndpoint path to check
     * @param formatStrings Variable number of Strings to format the apiEndpoint argument with
     * 
     * @return The endpoint path
     */
    private static String constructEndpointPath(ApiEndpoint apiEndpoint, String... formatStrings) {
        if( apiEndpoint.requiresFormatting() ) {
            // String.format takes a Object[] for the values. We can take advantage of this to
            // pass the variable length formatStrings argument as the arguments to format the
            // endpoint path with the correct values.
            //   Example:
            //      ApiEndpoint.REMOVE_CARD == "/user/%s/remove-card/%s" (First %s is the user's API key; the second %s must be the card's id)
            //
            //      Calling constructEndpointPath(ApiEndpoint.REMOVE_CARD, "user1234", "card1234")
            //      returns "/user/user1234/remove-card/card1234" which is the desired endpoint path
            return String.format(apiEndpoint.toString(), (Object[]) formatStrings);
        }
        else {
            // If the endpoint path didn't require formatting, we just return its path
            return apiEndpoint.toString();
        }
    }
    
    /**
     * Constructs a JSON object string using a given list of NameValuePair objects
     * 
     * @param jsonParams A list of NameValuePair objects representing the key-value items to place in the JSON object
     * 
     * @return The constructed JSON object string
     */
    private static String constructJsonObjectStr(List<NameValuePair> jsonParams) {
        // Construct a new JSONObject
        JsonObject json = new JsonObject();
        
        // Loop through the pairs
        for(NameValuePair pair : jsonParams) {
            if(pair.getValue() != null) {
                json.addProperty(pair.getName(), pair.getValue());
            }
        }
        
        return json.toString();
    }
    
}
