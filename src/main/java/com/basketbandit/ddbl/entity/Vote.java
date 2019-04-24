package com.basketbandit.ddbl.entity;

public class Vote {
    private final String id;
    private final String discriminator;
    private final String username;
    private final String timestamp;

    public Vote(String id, String discriminator, String username, String timestamp) {
        this.id = id;
        this.discriminator = discriminator;
        this.username = username;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public String getUsername() {
        return username;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
