# ddblAPI.java

Java wrapper for the [DivineDiscordBotList](https://divinediscordbots.com/) API.

[![](https://jitpack.io/v/BasketBandit/ddblAPI.java.svg)](https://jitpack.io/#BasketBandit/ddblAPI.java)

Methods:
```
 .postStats(int serverCount)
 .getStats()
 .getVotes()
 .hasVoted(String userId)
 .hasVoted(String userId, int hours)
 ```
 
 Gradle:
 ```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 
	dependencies {
	        implementation 'com.github.BasketBandit:ddblAPI.java:VERSION'
	}
 ```
 
Maven:
```
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
	
    <dependency>
        <groupId>com.github.BasketBandit</groupId>
        <artifactId>ddblAPI.java</artifactId>
        <version>VERSION</version>
    </dependency>
```

Setup:
 ```
DivineDiscordBotList ddbl = new DivineDiscordBotList.Builder()
        .botId()
        .token()
        .build();
 ```