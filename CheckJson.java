import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class CheckJson {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("app/src/main/assets/idioms/idioms.json"), StandardCharsets.UTF_8);
        int errors = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.trim().startsWith("\"actionableTip\":") && i + 1 < lines.size()) {
                String nextLine = lines.get(i + 1);
                if (nextLine.trim().startsWith("\"cognitiveBiasTags\":")) {
                    // 去掉尾部空白后检查是否以逗号结尾
                    String stripped = line.replaceAll("\\s+$", "");
                    if (!stripped.endsWith(",")) {
                        System.out.println("Missing comma at line " + (i + 1) + ": " + stripped.substring(0, Math.min(80, stripped.length())) + "...");
                        errors++;
                    }
                }
            }
        }
        if (errors == 0) {
            System.out.println("All actionableTip lines followed by cognitiveBiasTags end with comma. OK!");
        } else {
            System.out.println("Total errors: " + errors);
        }
    }
}
