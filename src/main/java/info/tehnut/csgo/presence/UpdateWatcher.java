package info.tehnut.csgo.presence;

import club.minnced.discord.rpc.DiscordRPC;
import info.tehnut.csgo.gamestate.IStateUpdateWatcher;
import info.tehnut.csgo.gamestate.data.GameMap;
import info.tehnut.csgo.gamestate.data.GameState;
import info.tehnut.csgo.gamestate.data.MatchStats;

public class UpdateWatcher implements IStateUpdateWatcher {

    public void handleUpdatedState(GameState newState) {
        if (newState.getMap() != null) { // We're in a match of some kind
            GameMap map = newState.getMap();
            GameModes gameMode = GameModes.getbyName(map.getMode());
            CSGOPresence.DISCORD_PRESENCE.details = gameMode.getDisplayName(); // deathmatch -> Deathmatch

            if (!newState.isOurUser())
                CSGOPresence.DISCORD_PRESENCE.details += " (Spectating)";

            MapImages mapImage = MapImages.getbyName(map.getName());
            CSGOPresence.DISCORD_PRESENCE.largeImageKey = mapImage.getImage();
            CSGOPresence.DISCORD_PRESENCE.largeImageText = "Map: " + map.getName();

            String scoreText = "Score: ";
            if (gameMode.isTeamOriented()) { // If we're in a team-based mode (ie: Defusal)
                boolean ct = newState.getPlayer().getTeam().equalsIgnoreCase("ct");
                if (ct)
                    scoreText += map.getCounterTerrorist().getScore() + " - " + map.getTerrorist().getScore();
                else
                    scoreText += map.getTerrorist().getScore() + " - " + map.getCounterTerrorist().getScore();
            } else if (newState.isOurUser()) { // Non-team-based (ie: Death match)
                scoreText += newState.getPlayer().getMatchStats().getScore();
            }
            CSGOPresence.DISCORD_PRESENCE.state = scoreText;

            if (!newState.isOurUser())
                return;

            CSGOPresence.DISCORD_PRESENCE.smallImageKey = "sdefault";
            MatchStats matchStats = newState.getPlayer().getMatchStats();
            CSGOPresence.DISCORD_PRESENCE.smallImageText = String.format("%dK / %dA / %dD", matchStats.getKills(), matchStats.getAssists(), matchStats.getDeaths());
        } else { // We're not in a match. Must be in the main menu... or at least a menu of some sort... probably
            CSGOPresence.DISCORD_PRESENCE.details = "In menu";
            CSGOPresence.DISCORD_PRESENCE.state = null;
            CSGOPresence.DISCORD_PRESENCE.largeImageKey = "unknown";
            CSGOPresence.DISCORD_PRESENCE.largeImageText = null;
            CSGOPresence.DISCORD_PRESENCE.smallImageKey = null;
            CSGOPresence.DISCORD_PRESENCE.smallImageText = null;
        }

        DiscordRPC.INSTANCE.Discord_UpdatePresence(CSGOPresence.DISCORD_PRESENCE);
    }
}
