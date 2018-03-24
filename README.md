# CSGO Presence

This app provides Discord rich presence integration for Counter Strike: Global Offensive.

Hovering over the map displays the map name.

![Preview](https://cdn.discordapp.com/attachments/345670598472499201/427246141503963157/unknown.png)

Hovering over the gun displays the current Kills, Assists, and Deaths.

![Preview](https://cdn.discordapp.com/attachments/345670598472499201/427246290296897547/unknown.png)

There is also an embedded CSGO Game State Integration handler for anybody looking for one of those.

## Building

To build, open a terminal in the project directory and run `gradlew build`. The built jar will be stored in `/build/libs/`.

## Running

To run the application, open a terminal wherever you have the the jar at and run `java -jar CSGOPresence-$VERSION-all.jar`. Note that if you do not run the jar with `-all` in the name, you will have to provide the required libraries yourself.

Run with `--help` for a list of optional arguments to provide.

## Libraries

CSGOPresence currently depends on the following libraries

* [Java-DiscordRPC](https://github.com/MinnDevelopment/java-discord-rpc)
* [Java Native Access](https://github.com/java-native-access/jna)
* [Gson](https://github.com/google/gson)
* [JOpt Simple](https://pholser.github.io/jopt-simple/)

## Recommended Config

Copy and paste the following into `/csgo/cfg/gamestate_integration_discordpresence.cfg`. The file probably doesn't exist so just create it.

```
"Discord Rich Presence Integration"
{
 "uri" "http://127.0.0.1:1234"
 "timeout" "15.0"
 "data"
 {
   "provider"            "1"
   "map"                 "1"
   "round"               "1"
   "player_id"           "1"
   "player_state"        "1"
   "player_weapons"      "1"
   "player_match_stats"  "1"
 }
}
```

The port `1234` can be changed if desired. If you change it, make sure to pass `--port #` as a launch argument, or else the game and app will be out of sync.