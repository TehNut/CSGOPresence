package info.tehnut.csgogamestate.data;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Player {

    @SerializedName("steamid")
    private final String steamId;
    private final String name;
    @SerializedName("observer_slot")
    private final int observerSlot;
    private final String team;
    private final String activity;
    private final PlayerState state;
    private final Map<String, Weapon> weapons;
    @SerializedName("match_stats")
    private final MatchStats matchStats;

    public Player(String steamId, String name, int observerSlot, String team, String activity, PlayerState state, Map<String, Weapon> weapons, MatchStats matchStats) {
        this.steamId = steamId;
        this.name = name;
        this.observerSlot = observerSlot;
        this.team = team;
        this.activity = activity;
        this.state = state;
        this.weapons = weapons;
        this.matchStats = matchStats;
    }

    public String getSteamId() {
        return steamId;
    }

    public String getName() {
        return name;
    }

    public int getObserverSlot() {
        return observerSlot;
    }

    public String getTeam() {
        return team;
    }

    public String getActivity() {
        return activity;
    }

    public PlayerState getState() {
        return state;
    }

    public Map<String, Weapon> getWeapons() {
        return weapons;
    }

    public MatchStats getMatchStats() {
        return matchStats;
    }
}
