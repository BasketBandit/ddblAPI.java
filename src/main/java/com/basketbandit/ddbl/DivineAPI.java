package com.basketbandit.ddbl;

import com.basketbandit.ddbl.entity.DivineBot;
import com.basketbandit.ddbl.entity.Vote;
import com.basketbandit.ddbl.io.RequestHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DivineAPI {
    private static final Logger log = LoggerFactory.getLogger(DivineAPI.class);
    private String botId;
    private String token;

    private DivineAPI() {
    }

    /**
     * Posts bot server & shard count to the server.
     *
     * @param serverCount number of servers
     * @param shardCount number of shards
     */
    public void postStats(int serverCount, int shardCount) {
        if(serverCount < 0) {
            throw new IllegalArgumentException("Server count must be 0 or greater.");
        }

        if(shardCount < 0) {
            throw new IllegalArgumentException("Shard count must be 0 or greater.");
        }

        String json = "{\"server_count\" : " + serverCount + ",\"shards\" : " + shardCount + "}";
        RequestHandler.doPostRequest(this.botId, "stats", this.token, json);
    }

    /**
     * Posts bot server count to the server.
     *
     * @param serverCount number of servers
     */
    public void postStats(int serverCount) {
        if(serverCount < 0) {
            throw new IllegalArgumentException("Server count must be 0 or greater.");
        }

        String json = "{\"server_count\" : " + serverCount + "}";
        RequestHandler.doPostRequest(this.botId, "stats", this.token, json);
    }

    /**
     * Finds out if the rate limiting has expired.
     *
     * @return boolean if the rate limiting has expired
     */
    public boolean canPost() {
        return RequestHandler.canPost();
    }

    /**
     * Retrieves DivineAPI bot stats.
     *
     * @return DivineBot
     */
    public DivineBot getStats() {
        JsonObject stats = RequestHandler.doGetRequest(this.botId, "stats");

        if(stats.has("stats")) {
            return new DivineBot(stats);
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
        JsonArray votes = RequestHandler.doGetRequest(this.botId, "votes").getAsJsonArray("votes");

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
        JsonArray votes = RequestHandler.doGetRequest(this.botId, "votes?filter=" + hours).getAsJsonArray("votes");

        for(JsonElement vote: votes) {
            if(vote.getAsJsonObject().get("id").getAsString().equals(userId)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Builder class for DivineAPI
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

        public DivineAPI build() {
            DivineAPI ddbl = new DivineAPI();

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
