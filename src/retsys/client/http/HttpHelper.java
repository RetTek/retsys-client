/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author ranju
 */
public class HttpHelper {

    String hostName = "localhost";
    String hostPort = "8082";
    String context = "retsys/rest";

    public HttpGet getHttpGetObj(String operation) throws IOException {
        HttpGet get = null;

        get = new HttpGet(getHttpUrl(hostName, hostPort, context) + "/" + operation);

        return get;
    }

    public HttpGet getHttpGetObj(String operation, String body) throws IOException {
        HttpGet get = null;

        get = new HttpGet(getHttpUrl(hostName, hostPort, context) + "/" + operation);

        StringBody payload = new StringBody("payload", ContentType.APPLICATION_JSON);

        //HttpEntity reqEntity = EntityBuilder.create().setText(body).build();
        StringEntity se = new StringEntity(body);
        se.setContentEncoding("UTF-8");
        se.setContentType("application/json");

        return get;
    }

    public HttpPost getHttpPostObj(String operation, String body) throws IOException {
        HttpPost post = null;

        post = new HttpPost(getHttpUrl(hostName, hostPort, context) + "/" + operation);

        StringBody payload = new StringBody("payload", ContentType.APPLICATION_JSON);

        //HttpEntity reqEntity = EntityBuilder.create().setText(body).build();
        StringEntity se = new StringEntity(body);
        se.setContentEncoding("UTF-8");
        se.setContentType("application/json");

        post.setEntity(se);

        return post;
    }

    public String getHttpUrl(String host, String port, String context) {
        return new StringBuilder("http://").append(host).append(":").append(port).append("/").append(context).toString();
    }

    public HttpClient getHttpClient(CredentialsProvider credsProvider) {
        HttpClient client = null;

        if (credsProvider == null) {
            client = HttpClients.createDefault();
        } else {
            client = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
        }

        return client;
    }

    public String executeHttpRequest(HttpClient client, HttpUriRequest req) throws IOException {
        String response = null;
        HttpResponse httpResponse = client.execute(req);
        int httpStatus = httpResponse.getStatusLine().getStatusCode();
        if(httpStatus>=200 && httpStatus<300){
            response = readFromStream(httpResponse.getEntity().getContent());
        }else{
            response="!ERROR!";
        }
        
        
        return response;
    }

    private String readFromStream(InputStream stream) throws IOException {
        StringBuilder response = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));

        String output;
        response = new StringBuilder();
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            response.append(output);
        }

        return response.toString();
    }

}
