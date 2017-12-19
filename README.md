# CSGO Presence

This app provides Discord rich presence integration for Counter Strike: Global Offensive.

![Preview](https://cdn.discordapp.com/attachments/168162260852670465/392506022272696320/unknown.png)

There is also an embedded CSGO Game State Integration handler for anybody looking for one of those. It depends on Guava, Gson, and Commons-IO.

Run with `--help` for a list of arguments to provide.

## Status

Currently there are no map images besides the badge for `de_dust2`. It also doesn't create the game state config for you.

If you want to try this, clone it or download the zip and run `gradlew build`. Run the jar classified with `-all`.

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