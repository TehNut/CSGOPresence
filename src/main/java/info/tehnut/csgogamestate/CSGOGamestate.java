package info.tehnut.csgogamestate;

import com.google.common.eventbus.EventBus;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class CSGOGamestate {

    public static final EventBus EVENT_BUS = new EventBus("gamestate_updater");

    public static void initGamestate(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), port);
        server.createContext("/", new StateReciever());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
    }
}
