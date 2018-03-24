package info.tehnut.csgo.gamestate.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * See the Valve <a href="https://developer.valvesoftware.com/wiki/Counter-Strike:_Global_Offensive_Game_State_Integration">Wiki page</a>
 * for the full details.
 */
public class GameStateConfiguration {

    private final String name;
    /**
     * Game will be making POST requests to this uri. If the endpoint needs traffic to be encrypted in flight then it is
     * recommended to specify a secure uri and use SSL on the service end. Steam client will automatically use SSL and
     * validate endpoint certificate for https destinations.
     */
    private final String uri;
    /**
     * Game expects an HTTP 2XX response code from its HTTP POST request, and game will not attempt submitting the next
     * HTTP POST request while a previous request is still in flight. The game will consider the request as timed out if
     * a response is not received within so many seconds, and will re-heartbeat next time with full state omitting any
     * delta-computation. If the setting is not specified then default short timeout of 1.1 sec will be used.
     */
    private final double timeout;
    /**
     * Because multiple game events tend to occur one after another very quickly, it is recommended to specify a non-zero
     * buffer. When buffering is enabled, the game will collect events for so many seconds to report a bigger delta. For
     * localhost service integration this is less of an issue and can be tuned to match the needs of the service or set
     * to 0.0 to disable buffering completely. If the setting is not specified then default buffer of 0.1 sec will be used.
     */
    private final double buffer;
    /**
     * For high-traffic endpoints this setting will make the game client not send another request for at least this many
     * seconds after receiving previous HTTP 2XX response to avoid notifying the service when game state changes too frequently.
     * If the setting is not specified then default throttle of 1.0 sec will be used.
     */
    private final double throttle;
    /**
     * Even if no game state change occurs, this setting instructs the game to send a request so many seconds after receiving
     * previous HTTP 2XX response. The service can be configured to consider game as offline or disconnected if it didn't
     * get a notification for a significant period of time exceeding the heartbeat interval.
     */
    private final double heartbeat;
    /**
     *  In most localhost or even LAN integration scenarios this section can be completely omitted, but when it is present,
     *  fields in this section will be transmitted as JSON string fields to the endpoint to authenticate the payload. It
     *  is recommended for the endpoint to also use SSL to protect the in flight payload containing an authentication block.
     */
    private final Map<String, String> auth;
    private final Set<DataType> data;

    public GameStateConfiguration(String name, String uri, double timeout, double buffer, double throttle, double heartbeat, Map<String, String> auth, Set<DataType> data) {
        this.name = name;
        this.uri = uri;
        this.timeout = timeout;
        this.buffer = buffer;
        this.throttle = throttle;
        this.heartbeat = heartbeat;
        this.auth = auth;
        this.data = data;
    }

    public void print(File gameDir, boolean overwrite) {
        if (!gameDir.isDirectory())
            throw new IllegalArgumentException("gameDir must be a directory.");

        File cfgDir = new File(gameDir, "csgo" + File.separator + "cfg");
        cfgDir.mkdirs();

        String safeName = name.toLowerCase(Locale.ROOT).replace(" ", "");
        File configFile = new File(cfgDir, "gamestate_integration_" + safeName + ".cfg");

        if (configFile.exists() && !overwrite)
            return;

        try (FileWriter writer = new FileWriter(configFile)) {
            configFile.delete();
            writer.write(toVson());
            System.out.println("Created game state configuration at " + configFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Failed to write game state configuration");
            e.printStackTrace();
        }
    }

    public String toJson() {
        JsonObject config = new JsonObject();
        config.addProperty("uri", uri);
        config.addProperty("heartbeat", String.valueOf(heartbeat));
        if (buffer != -1)
            config.addProperty("buffer", String.valueOf(buffer));
        if (throttle != -1)
            config.addProperty("throttle", String.valueOf(throttle));
        if (timeout != -1)
            config.addProperty("timeout", String.valueOf(timeout));

        if (!auth.isEmpty()) {
            JsonObject auth = new JsonObject();
            this.auth.forEach(auth::addProperty);
            config.add("auth", auth);
        }

        if (!data.isEmpty()) {
            JsonObject data = new JsonObject();
            this.data.forEach(dataType -> data.addProperty(dataType.toString(), "1"));
            config.add("data", data);
        }

        JsonObject object = new JsonObject();
        object.add(name, config);

        return new GsonBuilder().setPrettyPrinting().create().toJson(object);
    }

    public String toVson() {
        String json = toJson();
        return json.substring(1, json.length() - 1).replaceAll("\":", "\"").replaceAll(",\n", "\n");
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public double getTimeout() {
        return timeout;
    }

    public double getBuffer() {
        return buffer;
    }

    public double getThrottle() {
        return throttle;
    }

    public double getHeartbeat() {
        return heartbeat;
    }

    public Map<String, String> getAuth() {
        return auth;
    }

    public Set<DataType> getData() {
        return data;
    }
}
