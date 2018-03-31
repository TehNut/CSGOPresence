package info.tehnut.csgo.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static final String PROCESS_LIST_COMMAND;
    static {
        if (System.getProperty("os.name").startsWith("Windows"))
            PROCESS_LIST_COMMAND = System.getenv("windir") + "\\system32\\" + "tasklist.exe /fo csv /nh";
        else
            PROCESS_LIST_COMMAND = "ps -few";
    }

    public static List<String> readLines(InputStream inputStream) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            //
        }
        return lines;
    }

    public static String toString(InputStream inputStream) {
        return String.join("\n", readLines(inputStream));
    }

    public static boolean isCSGORunning() {
        try {
            Process process = Runtime.getRuntime().exec(PROCESS_LIST_COMMAND);
            String response = toString(process.getInputStream());
            return response.contains("csgo"); // 10/10 check
        } catch (Exception e) {
            return false;
        }
    }
}
