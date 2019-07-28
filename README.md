# ddblAPI.java
Java wrapper for the [DivineDiscordBotList](https://divinediscordbots.com/) API.

## Class Data

### Methods

```java
 .postStats(int serverCount);
 .postStats(int serverCount, int shardCount);
 .canPost();
 .getStats();
 .getVotes();
 .hasVoted(String userId);
 .hasVoted(String userId, int hours);
```

## Usage

### Setup
```java
DivineAPI ddbl = new DivineAPI.Builder()
        .botId("botId")
        .token("token")
        .build();
```

### Post

**#1** - Post the total server count for your bot.
```java
int serverCount = ...; // The server count for the bot.

if(canPost()) {
    ddbl.postStats(serverCount);
}
```

**#2** - Post both the total server count and the shard count.
```java
int serverCount = ...; // The server count for the bot.
int shards = ...; // The number of shards the bot has.

if(canPost()) {
    ddbl.postStats(serverCount, shards);
}
```

### Get

**Stats** - Retrieve the stats stored about your bot, such as description and server count.
```java
DivineBot bot = ddbl.getStats();

bot.getDescription(); // Long description of your bot.
bot.getStats().getServerCount() // Server count of your bot.
```

**Votes** - Retrieve a list of all of the votes for your bot.
```java
List<Vote> votes = ddbl.getVotes();

votes.getSize(); // Number of votes.
votes.get(0).getUsername(); // Username of the user for vote index 0.
```

**Voted #1** - Retrieves a boolean value of if a user has voted in the past 24 hours.
```java
String userId = ...; // The ID of the user to check against. E.g. "215161101460045834"

boolean voted = ddbl.hasVoted(userId);
```

**Voted #2** - Retrieves a boolean value of if a user has voted in the past `x` hours.
```java
String userId = ...; // The ID of the user to check against. E.g. "215161101460045834"
int hours = ...; The number of hours in the past to check against. E.g. 6.

boolean voted = ddbl.hasVoted(userId, hours);
```

## Download

[![](https://jitpack.io/v/BasketBandit/ddblAPI.java.svg)](https://jitpack.io/#BasketBandit/ddblAPI.java)
 
Replace `VERSION` with the JitPack version number you can see above.
 
#### Gradle
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
```

```gradle
dependencies {
    implementation 'com.github.BasketBandit:ddblAPI.java:VERSION'
}
```
 
#### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```  

```xml
<dependency>
    <groupId>com.github.BasketBandit</groupId>
    <artifactId>ddblAPI.java</artifactId>
    <version>VERSION</version>
</dependency>
```


## Dependencies

| name | version |
|:---|:---|
| [SLF4J](https://github.com/qos-ch/slf4j) | 2.0.0-alpha0 |
| [OkHttp](https://github.com/square/okhttp/) | 4.0.1 |
| [Gson](https://github.com/google/gson) | 2.8.5'|