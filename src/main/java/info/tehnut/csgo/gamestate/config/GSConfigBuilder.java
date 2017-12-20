package info.tehnut.csgo.gamestate.config;

import java.util.*;

public class GSConfigBuilder {

    private final String name;
    private String uri;
    private double timeout;
    private double buffer = -1;
    private double throttle = -1;
    private double heartbeat = -1;
    private final Map<String, String> auth = new HashMap<>();
    private final Set<DataType> data = EnumSet.noneOf(DataType.class);

    public GSConfigBuilder(String name) {
        this.name = name;
    }

    public GSConfigBuilder withURI(String uri) {
        this.uri = uri;
        return this;
    }

    public GSConfigBuilder withLocalURI(int port, String context) {
        this.uri = "http://127.0.0.1:" + port + (context != null && !context.isEmpty() ? "/" + context : "");
        return this;
    }

    public GSConfigBuilder withTimeout(double timeout) {
        this.timeout = timeout;
        return this;
    }

    public GSConfigBuilder withBuffer(double buffer) {
        this.buffer = buffer;
        return this;
    }

    public GSConfigBuilder withThrottle(double throttle) {
        this.throttle = throttle;
        return this;
    }

    public GSConfigBuilder withHeartbeat(double heartbeat) {
        this.heartbeat = heartbeat;
        return this;
    }

    public GSConfigBuilder withAuth(String key, String value) {
        this.auth.put(key, value);
        return this;
    }

    public GSConfigBuilder withData(DataType... data) {
        this.data.addAll(Arrays.asList(data));
        return this;
    }

    public GameStateConfiguration build() {
        if (uri == null)
            throw new RuntimeException("uri must be set.");

        if (timeout == -1)
            throw new RuntimeException("timeout must be set.");

        return new GameStateConfiguration(name, uri, timeout, buffer, throttle, heartbeat, auth, data);
    }
}
