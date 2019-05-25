package com.basketbandit.ddbl.io;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RequestHandler {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final JsonObject empty = new JsonParser().parse("{}").getAsJsonObject();
    private static long lastPost = 0;

    private static final String API_BASE = "https://divinediscordbots.com/bot/";

    /**
     * A OkHttp method used to connect to and return a JsonObject from the server.
     *
     * @param botId the ID of the bot to do the request against
     * @param endpoint which API endpoint to hit up
     * @return JsonObject of the returned content
     */
    public static JsonObject doGetRequest(String botId, String endpoint) {
        try {
            Request request = new Request.Builder()
                    .url(API_BASE + botId + "/" + endpoint)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();
            Response response = client.newCall(request).execute();

            if(response.code() != 200) {
                response.close();
                throw new IOException("Unable to retrieve information from the server.");
            }

            final JsonObject data = (response.body() != null) ? new JsonParser().parse(response.body().string()).getAsJsonObject() : empty;
            response.close();

            return data;
        } catch(IOException e) {
            log.error("There was a problem processing that request. -> doRequest() [GET]");
            return empty;
        }
    }

    /**
     * A OkHttp method used to connect to and post JSON data to the server.
     *
     * @param botId the ID of the bot to do the request against
     * @param endpoint which API endpoint to hit up
     * @param token API token to use for authentication
     * @param data json data to send to the server
     */
    public static void doPostRequest(String botId, String endpoint, String token, String data) {
        try {
            if(!canPost()) {
                log.warn("You can only post server count once every 1 minute.");
                return;
            }
            Request request = new Request.Builder()
                    .url(API_BASE + botId + "/" + endpoint)
                    .addHeader("Authorization", token)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(JSON, data))
                    .build();
            Response response = client.newCall(request).execute();
            response.close();

            lastPost = System.currentTimeMillis();
        } catch(IOException e) {
            log.error("There was a problem processing that request. -> doRequest() [POST]");
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
