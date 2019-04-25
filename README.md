# ddblAPI.java
Java wrapper for the [DivineDiscordBotList](https://divinediscordbots.com/) API.

## Usage

### Fields
```java
int serverCount = ...; // The server count for the bot.
int shards = ...; // The number of shards the bot has.
```

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

#### Setup
```java
DivineDiscordBotList ddbl = new DivineDiscordBotList.Builder()
        .botId("botId")
        .token("token")
        .build();
```

#### Post

**#1** - Post the total server count for your bot.
```java
if(canPost()) {
    ddbl.postStats(serverCount);
}
```

**#2** - Post both the total server count and the shard count.
```java
if(canPost()) {
    ddbl.postStats(serverCount, shards);
}
```

#### Get

**Stats** - Retrieve the stats stored about your bot, such as description and server count.
```java
DivineDiscordBotInfo botInfo = ddbl.getStats();

botInfo.getDescription(); // Long description of your bot.
botInfo.getStats().getServerCount() // Server count of your bot.
```

**Votes** - Retrieve a list of all of the votes for your bot.
```java
List<Vote> votes = ddbl.getVotes();

votes.getSize(); // Number of votes.
vote.get(0).getUsername(); // Username of the user for vote index 0.
```

**Voted #1** - Retrieves a boolean value of if a user has voted or not in the past 24 hours.
```java
String userId = ...; // The ID of the user to check against. E.g. "215161101460045834"

boolean voted = ddbl.hasVoted(userId);
```

**Voted #2** - Retrieves a boolean value of if a user has voted or not in the past `x` hours.
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