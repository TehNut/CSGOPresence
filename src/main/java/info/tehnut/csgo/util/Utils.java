package info.tehnut.csgo.util;

import info.tehnut.csgo.presence.CSGOPresence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

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
            Process process = Runtime.getRuntime().exec(CSGOPresence.PROCESS_LIST_COMMAND);
            String response = toString(process.getInputStream());
            return response.contains("csgo"); // 10/10 check
        } catch (Exception e) {
            return false;
        }
    }
}
