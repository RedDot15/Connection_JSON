package com.example.jsonapplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class JsonapplicationApplication {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String GET_URL = "https://jsonplaceholder.typicode.com/albums";

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(JsonapplicationApplication.class, args);
        sendGETSize();
        sendGETIdList();
    }

    private static void sendGETSize() throws IOException, MalformedURLException {
        URL url = new URL(GET_URL);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpsURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            Object obj = JSONValue.parse(response.toString());
            JSONArray jsonArray = (JSONArray) obj;
            System.out.println(jsonArray.size());
        } else {
            System.out.println("GET request did not work.");
        }
    }

    private static void sendGETIdList() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GET_URL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //print result
        Object obj = JSONValue.parse(response.body());
        JSONArray jsonArray = (JSONArray) obj;
        for (Object object : jsonArray){
            JSONObject jsonObject = (JSONObject) object;
            System.out.println(jsonObject.get("id"));
        }
    }
}
