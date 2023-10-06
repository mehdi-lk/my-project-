import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class CodeMetricsCalculator {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java CodeMetricsCalculator <source_file>");
            System.exit(1);
        }
            String sourceFileName = args[0];
        int totalLinesOfCode = calculateCodeMetrics(sourceFileName);
        System.out.println("Total Lines of Code: " + totalLinesOfCode);
    }
 private static int calculateCodeMetrics(String sourceFileName) {
        int locCount = 0;
 try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFileName))) {
            String line;
            boolean insideCommentBlock = false;
  while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                 if (line.isEmpty() || line.startsWith("//")) {
                    continue;
                }
                
                 if (line.isEmpty() || line.startsWith("//")) {
                    continue;
                }
               if (line.startsWith("/*")) {
                    insideCommentBlock = true;
                    if (line.endsWith("*/")) {
                        insideCommentBlock = false;
                    }
                    continue;
                }  
                 if (line.startsWith("/*")) {
                    insideCommentBlock = true;
                    if (line.endsWith("*/")) {
                        insideCommentBlock = false;
                    }
                    continue;
                }
                  } catch (IOException exception) {
            System.err.println("Error reading the file: " + exception.getMessage());
            System.exit(1);
        }

        return locCount;
    }
}