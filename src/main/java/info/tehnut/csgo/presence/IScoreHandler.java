package info.tehnut.csgo.presence;

import info.tehnut.csgo.gamestate.data.GameMap;
import info.tehnut.csgo.gamestate.data.Player;

public interface IScoreHandler {

    String getScore(Player player, GameMap map);

    IScoreHandler TEAM_ORIENTED = (player, map) -> {
        String score = "Score: ";
        boolean ct = player.getTeam().equalsIgnoreCase("ct");
        if (ct)
            score += map.getCounterTerrorist().getScore() + " - " + map.getTerrorist().getScore();
        else
            score += map.getTerrorist().getScore() + " - " + map.getCounterTerrorist().getScore();

        return score;
    };

    IScoreHandler NOT_TEAM_ORIENTED = (player, map) -> "Score: " + player.getMatchStats().getScore();
}
