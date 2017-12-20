package info.tehnut.csgo.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IOUtils {

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
}
