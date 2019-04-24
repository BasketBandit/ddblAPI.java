package com.basketbandit.ddbl.io;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public class RequestHandler {

    /**
     * A HttpsURLConnection method used to connect to and return a JsonObject from the server.
     *
     * @param url the endpoint to hit up
     * @return JsonObject of the returned content
     */
    public static JsonObject doRequest(String url) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            if(connection.getResponseCode() != 200) {
                throw new IOException("Unable to retrieve information from the server.");
            }

            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;

            while((length = connection.getInputStream().read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }

            return new JsonParser().parse(result.toString()).getAsJsonObject();
        } catch(IOException e) {
            e.printStackTrace();
            return new JsonParser().parse("{}").getAsJsonObject();
        }
    }
}
