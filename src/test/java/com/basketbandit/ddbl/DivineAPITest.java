package com.basketbandit.ddbl;

import com.basketbandit.ddbl.entity.DivineBot;
import com.basketbandit.ddbl.entity.Vote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class DivineAPITest {

    private DivineAPI ddbl;

    @BeforeEach
    void setUp() {
        ddbl = new DivineAPI.Builder().botId(System.getenv("botId")).token(System.getenv("botToken")).build();
    }

    @Test
    void postStats() {
        System.out.println(ddbl.canPost());
        ddbl.postStats(421, 1);
        System.out.println(ddbl.canPost());
        ddbl.postStats(421);
    }

    @Test
    void getStats() {
        DivineBot info = ddbl.getStats();
        System.out.println("Server Count: " + info.getBotStats().getServerCount());
        System.out.println("Shards: " + info.getBotStats().getShards());
        System.out.println("Name: " + info.getName());
        System.out.println("Prefix: " + info.getPrefix());
        System.out.println("Id: " + info.getId());
        System.out.println("Owner: " + info.getOwner());
        System.out.println("ShortDesc: " + info.getShortDesc());
        System.out.println("Invite: " + info.getInvite());
        System.out.println("Is NSFW: " + info.isNsfw());
        System.out.println("Owners: " + info.getOwners()[0]);
        System.out.println("Description: " + info.getDescription());
    }

    @Test
    void getVotes() {
        List<Vote> votes = ddbl.getVotes();
        System.out.println("Votes: " + votes.size());
        System.out.println("List: " + votes.toString());
    }

    @Test
    void hasVoted() {
        System.out.println("Has voted? (24h): " + ddbl.hasVoted(System.getenv("userId")));
    }

    @Test
    void hasVoted1() {
        System.out.println("Has voted? (96h): " + ddbl.hasVoted(System.getenv("userId"), 96));
    }
}