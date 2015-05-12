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
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
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
    String hostPort = "8080";
    String context = "retsys/rest";

    public HttpGet getHttpGetObj(String operation) throws IOException {
        HttpGet get = null;

        get = new HttpGet(getHttpUrl(hostName, hostPort, context) + "/" + operation);

        return get;
    }
    
    public HttpDelete getHttpDeleteObj(String operation, Integer id) throws IOException {
        HttpDelete delete = null;

        delete = new HttpDelete(getHttpUrl(hostName, hostPort, context) + "/" + operation+ "/" + id);

        return delete;
    }

    public HttpPost getHttpPostObj(String operation, String body) throws IOException {
        HttpPost post = null;

        post = new HttpPost(getHttpUrl(hostName, hostPort, context) + "/" + operation);

        StringEntity se = new StringEntity(body);
        se.setContentEncoding("UTF-8");
        se.setContentType("application/json");

        post.setEntity(se);

        return post;
    }

    public HttpPut getHttpPutObj(String operation, String body) throws IOException {
        HttpPut put = null;

        put = new HttpPut(getHttpUrl(hostName, hostPort, context) + "/" + operation);

        StringEntity se = new StringEntity(body);
        se.setContentEncoding("UTF-8");
        se.setContentType("application/json");

        put.setEntity(se);

        return put;
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
        if (httpStatus >= 200 && httpStatus < 300) {
             if (!HttpDelete.METHOD_NAME.equals(req.getMethod()) && !HttpPut.METHOD_NAME.equals(req.getMethod())) {
                response = readFromStream(httpResponse.getEntity().getContent());
            }
        } else if (httpStatus == 409) {
            response = readFromStream(httpResponse.getEntity().getContent());
            System.out.println("response:  " + response);
            response = "!ERROR!";
        } else {
            response = "!ERROR!";
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

    public HttpGet getHttpGetObj(String url, boolean fullUrl) {
        HttpGet get = null;
        if (fullUrl) {

            get = new HttpGet(url);

        }
        return get;
    }

}
