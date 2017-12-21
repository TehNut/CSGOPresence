package info.tehnut.csgo.gamestate.data;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class GameState {

    private final Provider provider;
    private final GameMap map;
    private final Round round;
    private final Player player;
    @SerializedName("allplayers")
    private final Map<String, Player> allPlayers;
    @SerializedName("phase_countdowns")
    private final PhaseCountdowns phaseCountdowns;
    // FIXME CS returns true as the value for all added fields. Need to just collect a list
//    private final GameState added;
    private final GameState previously;

    public GameState(Provider provider, GameMap map, Round round, Player player, Map<String, Player> allPlayers, PhaseCountdowns phaseCountdowns, GameState previously) {
        this.provider = provider;
        this.map = map;
        this.round = round;
        this.player = player;
        this.allPlayers = allPlayers;
        this.phaseCountdowns = phaseCountdowns;
        this.previously = previously;
    }

    public Provider getProvider() {
        return provider;
    }

    public GameMap getMap() {
        return map;
    }

    public Round getRound() {
        return round;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isOurUser() {
        return getPlayer() == null || getPlayer().getSteamId().equals(getProvider().getSteamId());
    }

    public Map<String, Player> getAllPlayers() {
        return allPlayers;
    }

    public PhaseCountdowns getPhaseCountdowns() {
        return phaseCountdowns;
    }

    public GameState getPreviously() {
        return previously;
    }
}
