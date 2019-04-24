package com.basketbandit.ddbl;

import com.basketbandit.ddbl.entity.DivineDiscordBotInfo;
import com.basketbandit.ddbl.entity.Vote;
import com.basketbandit.ddbl.io.RequestHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DivineDiscordBotList {

    private static final Logger log = LoggerFactory.getLogger(DivineDiscordBotList.class);

    private String botId;
    private String token;
    private static long lastPost = 0;

    private DivineDiscordBotList() {
    }

    /**
     * Posts bot server count to the server.
     *
     * @param serverCount number of servers
     */
    public void postStats(int serverCount) {
        try {
            if(serverCount < 0) {
                throw new IllegalArgumentException("Server count must be 0 or greater.");
            }

            if((System.currentTimeMillis() - lastPost) < 60000) {
                log.warn("You can only post server count every 1 minute.");
                return;
            }

            String inputString = "{" + serverCount + "}";
            byte[] input = inputString.getBytes();

            HttpsURLConnection connection = (HttpsURLConnection) new URL("https://divinediscordbots.com/bot/" + this.botId + "/stats").openConnection();
            connection.addRequestProperty("Authorization", this.token);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
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
     * Retrieves DivineDiscordBotList bot stats.
     *
     * @return DivineDiscordBotInfo
     */
    public DivineDiscordBotInfo getStats() {
        JsonObject stats = RequestHandler.doRequest("https://divinediscordbots.com/bot/" + this.botId + "/stats");

        if(stats.has("stats")) {
            return new DivineDiscordBotInfo(stats);
        }

        log.error("There was a problem processing that request. -> getStats()");
        return null;
    }

    /**
     * Retrieves a list of all votes given to the bot.
     *
     * @return List<Vote>
     */
    public List<Vote> getVotes() {
        JsonArray votes = RequestHandler.doRequest("https://divinediscordbots.com/bot/" + this.botId + "/votes").getAsJsonArray("votes");

        ArrayList<Vote> voteList = new ArrayList<>();
        for(JsonElement vote: votes) {
            JsonObject voteObject = vote.getAsJsonObject();
            voteList.add(new Vote(voteObject.get("id").getAsString(), voteObject.get("discriminator").getAsString(), voteObject.get("username").getAsString(), voteObject.get("timestamp").getAsString()));
        }

        return voteList;
    }

    /**
     * Checks if the given user has voted within the last 24 hours.
     *
     * @param userId the user ID to check against
     * @return boolean if the user has voted or not
     */
    public boolean hasVoted(String userId) {
        return hasVoted(userId, 24);
    }

    /**
     * Checks if the given user has voted within the last x amount of hours.
     *
     * @param userId the user ID to check against
     * @param hours the number of hours to look back
     * @return boolean if the user has voted or not
     */
    public boolean hasVoted(String userId, int hours) {
        JsonArray votes = RequestHandler.doRequest("https://divinediscordbots.com/bot/" + this.botId + "/votes?filter=" + hours).getAsJsonArray("votes");

        for(JsonElement vote: votes) {
            if(vote.getAsJsonObject().get("id").getAsString().equals(userId)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Builder class for DivineDiscordBotList
     */
    public static class Builder {
        private String botId;
        private String token;

        public Builder() {
        }

        public Builder botId(String botId) {
            this.botId = botId;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public DivineDiscordBotList build() {
            DivineDiscordBotList ddbl = new DivineDiscordBotList();

            if(botId == null) {
                throw new IllegalArgumentException("The provided bot ID cannot be null.");
            }

            if(token == null) {
                throw new IllegalArgumentException("The provided token cannot be null.");
            }

            ddbl.botId = this.botId;
            ddbl.token = this.token;

            return ddbl;
        }
    }

}
