package com.basketbandit.ddbl.io;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;

public class RequestHandler {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static long lastPost = 0;

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

    /**
     * A HttpsURLConnection method used to connect to and post JSON data to the server.
     *
     * @param url the endpoint to hit up
     */
    public static void doRequest(String url, String token, String data) {
        try {
            if(!canPost()) {
                log.warn("You can only post server count every 1 minute.");
                return;
            }

            byte[] input = data.getBytes();

            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.addRequestProperty("Authorization", token);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.write(input, 0, input.length);
            outputStream.flush();
            outputStream.close();

            lastPost = System.currentTimeMillis();
        } catch(IOException e) {
            log.error("There was a problem processing that request. -> postStats()");
        }
    }

    /**
     * Returns if the rate limiting has expired.
     *
     * @return boolean if the api can be posted to again
     */
    public static boolean canPost() {
        return ((System.currentTimeMillis() - lastPost) > 60000);
    }
}
