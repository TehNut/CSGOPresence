package info.tehnut.csgogamestate.data;

import com.google.gson.annotations.SerializedName;

public class GameMap {

    private final String mode;
    private final String name;
    private final String phase;
    private final int round;
    private final Team counterTerrorist;
    private final Team terrorist;
    @SerializedName("num_matches_to_win_series")
    private final int matchesToWinSeries;
    @SerializedName("current_spectators")
    private final int spectatorCount;
    @SerializedName("souvenirs_total")
    private final int totalSouvenirs;

    public GameMap(String mode, String name, String phase, int round, Team counterTerrorist, Team terrorist, int matchesToWinSeries, int spectatorCount, int totalSouvenirs) {
        this.mode = mode;
        this.name = name;
        this.phase = phase;
        this.round = round;
        this.counterTerrorist = counterTerrorist;
        this.terrorist = terrorist;
        this.matchesToWinSeries = matchesToWinSeries;
        this.spectatorCount = spectatorCount;
        this.totalSouvenirs = totalSouvenirs;
    }

    public String getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public String getPhase() {
        return phase;
    }

    public int getRound() {
        return round;
    }

    public Team getCounterTerrorist() {
        return counterTerrorist;
    }

    public Team getTerrorist() {
        return terrorist;
    }

    public int getMatchesToWinSeries() {
        return matchesToWinSeries;
    }

    public int getSpectatorCount() {
        return spectatorCount;
    }

    public int getTotalSouvenirs() {
        return totalSouvenirs;
    }
}
