package info.tehnut.csgo.gamestate;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

public class CSGOGamestate {

    static final Queue<IStateUpdateWatcher> UPDATE_WATCHERS = new ConcurrentLinkedQueue<>();

    public static void initGamestate(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), port);
        server.createContext("/", new StateReciever());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
    }

    public static void subscribeWatcher(IStateUpdateWatcher watcher) {
        UPDATE_WATCHERS.add(watcher);
    }

    public static void unsubscribeWatcher(IStateUpdateWatcher watcher) {
        UPDATE_WATCHERS.remove(watcher);
    }
}
