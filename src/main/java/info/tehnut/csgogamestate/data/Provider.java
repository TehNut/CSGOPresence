package info.tehnut.csgogamestate.data;

import com.google.gson.annotations.SerializedName;

public class Provider {

    private final String name;
    @SerializedName("appid")
    private final int appId;
    private final int version;
    @SerializedName("steamid")
    private final String steamId;
    private final long timestamp;

    public Provider(String name, int appId, int version, String steamId, long timestamp) {
        this.name = name;
        this.appId = appId;
        this.version = version;
        this.steamId = steamId;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public int getAppId() {
        return appId;
    }

    public int getVersion() {
        return version;
    }

    public String getSteamId() {
        return steamId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
