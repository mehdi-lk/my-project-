import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class TLS {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.exit(1);
        }

        String directoryPath = args[0];
        File directory = new File(directoryPath);

        System.out.println("path,classPackage,className,tloc,tassert,tcmp");

        processDirectory(directory);
    }

    private static void processDirectory(File directory) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    processDirectory(file);
                } else if (file.getName().endsWith(".java") && file.getName().endsWith("Test.java")) {
                    processFile(file);
                }
            }
        }
    }

    private static void processFile(File file) {
        try {
            Path filePath = file.toPath();
            String className = file.getName().replace(".java", "");
            String classPackage = getPackage(file.toPath());
            int tloc = calculateTLOC(file);
            int tassert = calculateAssertionCount(file);
            double tcmp = tloc / tassert;

            System.out.printf("%s,%s,%s,%d,%d,%.2f%n", filePath, classPackage, className, tloc, tassert, tcmp);
        } catch (IOException e) {
            System.err.println("Error file: " + e.getMessage());
        }
    }

    private static String getPackage(Path filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("package ")) {
                    return line.replace("package ", "").replace(";", "");
                }
            }
        } catch (IOException e) {
            System.err.println("Error file: " + e.getMessage());
        }

        return "";
    }

    private static int calculateTLOC(String fileName) {
        int locCount = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String line;
        boolean insideCommentBlock = false;

        while ((line = bufferedReader.readLine()) != null) {
            line = line.trim();

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

            locCount++;
        }

        return locCount;
    }

    private static int calculateAssertionCount(String fileName) throws IOException {
        int assertCount = 0;
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();

            if (line.contains("Assert.") && line.contains("(") && line.contains(")")) {
                assertCount += countAssertionsInLine(line);
            }
        }

        return assertCount;
    }

    private static int countAssertionsInLine(String line) {
        int count = 0;

        String[] parts = line.split("\\(");
        for (String part : parts) {
            if (part.contains("Assert.")) {
                count++;
            }
        }

        return count;
    }

}