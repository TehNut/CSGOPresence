package info.tehnut.csgo.gamestate.data;

import com.google.gson.annotations.SerializedName;

public class Team {

    private final int score;
    @SerializedName("timeouts_remaining")
    private final int remainingTimeouts;
    @SerializedName("matches_won_this_series")
    private final int matchesWonThisSeries;

    public Team(int score, int remainingTimeouts, int matchesWonThisSeries) {
        this.score = score;
        this.remainingTimeouts = remainingTimeouts;
        this.matchesWonThisSeries = matchesWonThisSeries;
    }

    public int getScore() {
        return score;
    }

    public int getRemainingTimeouts() {
        return remainingTimeouts;
    }

    public int getMatchesWonThisSeries() {
        return matchesWonThisSeries;
    }
}
