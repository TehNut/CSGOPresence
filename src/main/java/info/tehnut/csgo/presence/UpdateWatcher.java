package info.tehnut.csgo.presence;

import club.minnced.discord.rpc.DiscordRPC;
import info.tehnut.csgo.gamestate.IStateUpdateWatcher;
import info.tehnut.csgo.gamestate.data.GameMap;
import info.tehnut.csgo.gamestate.data.GameState;
import info.tehnut.csgo.gamestate.data.MatchStats;
import info.tehnut.csgo.gamestate.data.Weapon;

import java.util.Locale;

public class UpdateWatcher implements IStateUpdateWatcher {

    public void handleUpdatedState(GameState newState, GameState oldState) {
        if (newState.isOurUser()) {
            if (newState.getMap() != null) { // We're in a match of some kind
                GameMap map = newState.getMap();

                MapImages mapImage = MapImages.getbyName(map.getName());
                CSGOPresence.DISCORD_PRESENCE.largeImageKey = mapImage.getImage();
                CSGOPresence.DISCORD_PRESENCE.largeImageText = "Map: " + map.getName();

                GameModes gameMode = GameModes.getbyName(map.getMode());
                CSGOPresence.DISCORD_PRESENCE.details = capitalize(gameMode.getName()); // deathmatch -> Deathmatch

                String scoreText = "Score: ";
                if (gameMode.isTeamOriented()) { // If we're in a team-based mode (ie: Defusal)
                    boolean ct = newState.getPlayer().getTeam().equalsIgnoreCase("ct");
                    if (ct)
                        scoreText += map.getCounterTerrorist().getScore() + " - " + map.getTerrorist().getScore();
                    else
                        scoreText += map.getTerrorist().getScore() + " - " + map.getCounterTerrorist().getScore();
                } else { // Non-team-based (ie: Death match)
                    scoreText += newState.getPlayer().getMatchStats().getScore();
                }

                CSGOPresence.DISCORD_PRESENCE.smallImageKey = "sdefault";
                MatchStats matchStats = newState.getPlayer().getMatchStats();
                CSGOPresence.DISCORD_PRESENCE.smallImageText = String.format("%dK / %dA / %dD", matchStats.getKills(), matchStats.getAssists(), matchStats.getDeaths());

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
    }

    private static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase(Locale.ROOT) + input.substring(1, input.length());
    }
}
