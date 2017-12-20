package info.tehnut.csgo.presence;

import club.minnced.discord.rpc.DiscordRPC;
import info.tehnut.csgo.gamestate.IStateUpdateWatcher;
import info.tehnut.csgo.gamestate.data.GameMap;
import info.tehnut.csgo.gamestate.data.GameState;

import java.util.Locale;

public class UpdateWatcher implements IStateUpdateWatcher {

    private long lastCheck;

    public void handleUpdatedState(GameState newState, GameState oldState) {
        if (System.currentTimeMillis() - lastCheck < 1000 * 14) // Occasionally CS will spam updates, so we need to rate limit it.
            return;

        if (newState.isOurUser()) {
            if (newState.getMap() != null) { // We're in a match of some kind
                GameMap map = newState.getMap();

                MapImages mapImage = MapImages.getbyName(map.getName());
                CSGOPresence.DISCORD_PRESENCE.largeImageKey = mapImage.getImage();
                CSGOPresence.DISCORD_PRESENCE.largeImageText = "Map: " + map.getName();
                CSGOPresence.DISCORD_PRESENCE.details = capitalize(map.getMode()); // deathmatch -> Deathmatch

                String scoreText = "Score: ";
                if (map.getCounterTerrorist() != null && map.getTerrorist() != null) { // If we're in a team-based mode (ie: Defusal)
                    boolean ct = newState.getPlayer().getTeam().equalsIgnoreCase("ct");
                    // This will format the score as CT - T and place ><'s around the team the user is on
                    scoreText += String.format(ct ? ">%d<" : "%d", map.getCounterTerrorist().getScore());
                    scoreText += " - ";
                    scoreText += String.format(!ct ? ">%d<" : "%d", map.getCounterTerrorist().getScore());
                } else { // Non-team-based (ie: Death match)
                    scoreText += newState.getPlayer().getMatchStats().getScore();
                }

                CSGOPresence.DISCORD_PRESENCE.state = scoreText;
            } else { // We're not in a match. Must be in the main menu... or at least a menu of some sort... probably
                CSGOPresence.DISCORD_PRESENCE.details = "In menu";
                CSGOPresence.DISCORD_PRESENCE.state = null;
                CSGOPresence.DISCORD_PRESENCE.largeImageKey = MapImages.UNKNOWN.getImage();
                CSGOPresence.DISCORD_PRESENCE.largeImageText = null;
                CSGOPresence.DISCORD_PRESENCE.smallImageKey = null;
                CSGOPresence.DISCORD_PRESENCE.smallImageText = null;
            }
        }

        DiscordRPC.INSTANCE.Discord_UpdatePresence(CSGOPresence.DISCORD_PRESENCE);
        lastCheck = System.currentTimeMillis();
    }

    private static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase(Locale.ROOT) + input.substring(1, input.length());
    }
}
