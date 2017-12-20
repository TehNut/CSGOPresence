package info.tehnut.csgo.gamestate.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class GameStateConfiguration {

    private final String name;
    private final String uri;
    private final double timeout;
    private final double buffer;
    private final double throttle;
    private final double heartbeat;
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

    // Dear god this is cancer. Why couldn't it just be normal json :(
    // TODO - See if we can use gson and just like... nuke the colons or something :thonk:
    public void print(File gameDir, boolean overwrite) {
        if (!gameDir.isDirectory())
            throw new IllegalArgumentException("gameDir must be a directory.");

        File cfgDir = new File(gameDir, "csgo" + File.separator + "cfg");
        cfgDir.mkdirs();

        String safeName = name.toLowerCase(Locale.ROOT).replace(" ", "");
        File configFile = new File(cfgDir, "gamestate_integration_" + safeName + ".cfg");

        if (configFile.exists() && !overwrite)
            return;

        configFile.delete();

        try {
            configFile.createNewFile();
            FileWriter writer = new FileWriter(configFile);

            writer.append("\"").append(name).append("\"\n{"); // Open up our config object
            writer.append("\n  \"uri\" \"").append(uri).append("\""); // Set the URI which is required

            writer.append("\n  \"timeout\" \"").append(String.valueOf(timeout)).append("\""); // Set the timeout which *we* require
            if (buffer != -1)
                writer.append("\n  \"buffer\" \"").append(String.valueOf(buffer)).append("\""); // Optionally set the buffer
            if (throttle != -1)
                writer.append("\n  \"throttle\" \"").append(String.valueOf(throttle)).append("\""); // Optionally set the throttle
            if (heartbeat != -1)
                writer.append("\n  \"heartbeat\" \"").append(String.valueOf(heartbeat)).append("\""); // Optionally set the heartbeat

            if (!auth.isEmpty()) { // Optionally set our auth keys
                writer.append("\n  \"auth\"").append("\n  {"); // Open 'er up
                for (Map.Entry<String, String> entry : auth.entrySet())
                    writer.append("\n   \"").append(entry.getKey()).append("\" \"").append(entry.getValue()).append("\""); // Stick each entry in there
                writer.append("\n  }"); // Close 'er down
            }

            if (!data.isEmpty()) { // "Optionally" set our data requests
                writer.append("\n  \"data\"").append("\n  {"); // Open 'er up
                for (DataType type : data)
                    writer.append("\n   \"").append(type.toString()).append("\" \"").append("1").append("\""); // Just stick each type we want with a value of 1 to enable it
                writer.append("\n  }"); // Close 'er down
            }

            writer.append("\n}"); // Close the config object
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to write game state configuration");
            e.printStackTrace();
        }
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
