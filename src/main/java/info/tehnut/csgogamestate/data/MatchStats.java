package info.tehnut.csgogamestate.data;

public class MatchStats {

    private final int kills;
    private final int deaths;
    private final int assists;
    private final int mvps;
    private final int score;

    public MatchStats(int kills, int deaths, int assists, int mvps, int score) {
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.mvps = mvps;
        this.score = score;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getAssists() {
        return assists;
    }

    public int getMvps() {
        return mvps;
    }

    public int getScore() {
        return score;
    }
}
