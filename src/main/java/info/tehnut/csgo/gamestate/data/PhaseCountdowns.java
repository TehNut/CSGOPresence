package info.tehnut.csgo.gamestate.data;

import com.google.gson.annotations.SerializedName;

public class PhaseCountdowns {

    private final String phase;
    @SerializedName("phase_ends_in")
    private final String endsIn;

    public PhaseCountdowns(String phase, String endsIn) {
        this.phase = phase;
        this.endsIn = endsIn;
    }

    public String getPhase() {
        return phase;
    }

    public double getEndsIn() {
        return Double.parseDouble(endsIn);
    }
}
