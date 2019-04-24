package com.basketbandit.ddbl.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DivineDiscordBotInfo {

    private BotStats botStats;
    private String name;
    private String id;
    private String owner;
    private String shortdesc;
    private String description;
    private String invite;
    private String prefix;
    private String[] owners;
    private boolean nsfw;

    public DivineDiscordBotInfo(JsonObject object) {
        if(object == null) {
            return;
        }

        JsonArray stats = object.get("stats").getAsJsonArray();
        this.botStats = new BotStats(stats.get(0).getAsJsonObject().get("server_count").getAsInt(), stats.get(0).getAsJsonObject().get("shards").getAsInt());
        this.name = object.get("name").getAsString();
        this.id = object.get("id").getAsString();
        this.owner = object.get("owner").getAsString();
        this.shortdesc = object.get("shortdesc").getAsString();
        this.description = object.get("description").getAsString();
        this.invite = object.get("invite").getAsString();
        this.prefix = object.get("prefix").getAsString();
        this.owners = new String[]{(object.get("owners").getAsJsonArray().size() > 0) ? object.get("owners").getAsString() : ""};
        this.nsfw = object.get("nsfw").getAsBoolean();
    }


    public BotStats getBotStats() {
        return botStats;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public String getDescription() {
        return description;
    }

    public String getInvite() {
        return invite;
    }

    public String getPrefix() {
        return prefix;
    }

    public String[] getOwners() {
        return owners;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public class BotStats {
        private int serverCount;
        private int shards;

        BotStats(int serverCount, int shards) {
            this.serverCount = serverCount;
            this.shards = shards;
        }

        public int getServerCount() {
            return serverCount;
        }

        public int getShards() {
            return shards;
        }
    }
}
