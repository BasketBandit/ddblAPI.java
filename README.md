# DDBL-Java

Java wrapper for the [Divine Discord Bot List](https://divinediscordbots.com/) API.

[![](https://jitpack.io/v/BasketBandit/DDBL-Java.svg)](https://jitpack.io/#BasketBandit/DDBL-Java)

Methods:
```
 .postStats(int serverCount)
 .getStats()
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