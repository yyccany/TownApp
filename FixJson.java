import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class FixJson {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("app/src/main/assets/idioms/idioms.json"), StandardCharsets.UTF_8);
        List<String> result = new ArrayList<>();
        int fixed = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            // 如果当前行包含 actionableTip，且下一行是 cognitiveBiasTags
            if (line.trim().startsWith("\"actionableTip\":") && i + 1 < lines.size()) {
                String nextLine = lines.get(i + 1);
                if (nextLine.trim().startsWith("\"cognitiveBiasTags\":")) {
                    // 检查当前行末尾是否有逗号
                    String trimmed = line.trim();
                    if (!trimmed.endsWith(",")) {
                        // 在行尾添加逗号（注意保留原缩进和格式）
                        line = line + ",";
                        fixed++;
                    }
                }
            }
            result.add(line);
        }

        Files.write(Paths.get("app/src/main/assets/idioms/idioms.json"), result, StandardCharsets.UTF_8);
        System.out.println("Fixed: " + fixed);
    }
}
