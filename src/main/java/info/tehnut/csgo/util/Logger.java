package info.tehnut.csgo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public static final Logger DEFAULT = new Logger("CSGOPresence");

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    private final String name;

    public Logger(String name) {
        this.name = name;
    }

    public void info(Object info, Object... args) {
        log(LoggingLevel.INFO, info, args);
    }

    public void debug(Object info, Object... args) {
        log(LoggingLevel.DEBUG, info, args);
    }

    public void error(Object info, Object... args) {
        log(LoggingLevel.ERROR, info, args);
    }

    public void fatal(Object info, Object... args) {
        log(LoggingLevel.FATAL, info, args);
    }

    public void log(LoggingLevel level, Object info, Object... args) {
        System.out.println(level.color + String.format("[%s] [%s] [%s] [%s] - %s" + "\u001B[0m", level, DATE_FORMAT.format(new Date()), Thread.currentThread().getName(), name, String.format(info == null ? "null" : info.toString(), args)));
    }

    public String getName() {
        return name;
    }

    public enum LoggingLevel {
        INFO("INF", ""),
        DEBUG("DBG", "\u001B[36m"),
        ERROR("ERR", "\u001B[33m"),
        FATAL("FTL", "\u001B[31m");

        private final String simpleName;
        private final String color;

        LoggingLevel(String simpleName, String color) {
            this.simpleName = simpleName;
            this.color = color;
        }

        @Override
        public String toString() {
            return simpleName;
        }
    }
}
