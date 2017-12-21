package info.tehnut.csgo.gamestate.config;

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

        try {
            configFile.delete();
            configFile.createNewFile();
            FileWriter writer = new FileWriter(configFile);
            writer.write(toVson());
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to write game state configuration");
            e.printStackTrace();
        }
    }

    // Dear god this is cancer. Why couldn't it just be normal json :(
    // TODO - See if we can use gson and just like... nuke the colons or something :thonk:
    public String toVson() {
        StringBuilder config = new StringBuilder();
        config.append("\"").append(name).append("\"\n{"); // Open up our config object
        config.append("\n  \"uri\" \"").append(uri).append("\""); // Set the URI which is required

        config.append("\n  \"timeout\" \"").append(String.valueOf(timeout)).append("\""); // Set the timeout which *we* require
        if (buffer != -1)
            config.append("\n  \"buffer\" \"").append(String.valueOf(buffer)).append("\""); // Optionally set the buffer
        if (throttle != -1)
            config.append("\n  \"throttle\" \"").append(String.valueOf(throttle)).append("\""); // Optionally set the throttle
        if (heartbeat != -1)
            config.append("\n  \"heartbeat\" \"").append(String.valueOf(heartbeat)).append("\""); // Optionally set the heartbeat

        if (!auth.isEmpty()) { // Optionally set our auth keys
            config.append("\n  \"auth\"").append("\n  {"); // Open 'er up
            for (Map.Entry<String, String> entry : auth.entrySet())
                config.append("\n   \"").append(entry.getKey()).append("\" \"").append(entry.getValue()).append("\""); // Stick each entry in there
            config.append("\n  }"); // Close 'er down
        }

        if (!data.isEmpty()) { // "Optionally" set our data requests
            config.append("\n  \"data\"").append("\n  {"); // Open 'er up
            for (DataType type : data)
                config.append("\n   \"").append(type.toString()).append("\" \"").append("1").append("\""); // Just stick each type we want with a value of 1 to enable it
            config.append("\n  }"); // Close 'er down
        }

        config.append("\n}"); // Close the config object

        return config.toString();
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
