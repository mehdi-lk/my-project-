4)

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TROPCOMP {
    public static void main(String[] args) throws IOException {

        String projetPath = args[0];
        int seuil = Integer.parseInt(args[1]);

        List<File> classesdetest = listerClassesDeTest(projetPath);

        List<File> classesSuspectes = new ArrayList<>();
        for (File fichier : classesdetest) {
            int tloc = calculerTLOC(fichier);
            int tassert = calculerTASSERT(fichier);
            double tcmp = (tassert == 0) ? 0 : (double) tloc / tassert;
            if (tloc >= seuil && tcmp >= seuil) {
                classesSuspectes.add(fichier);
            }
        }

    }

    private static List<File> listerClassesDeTest(String projetPath) {
        File dossier = new File(projetPath + "/src/test/java");
        File[] fichiers = dossier.listFiles((dir, name) -> name.endsWith(".java"));
        return (fichiers != null) ? List.of(fichiers) : new ArrayList<>();
    }

    private static int calculateTLOC(String fileName) {
        int locCount = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFileName));
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

    private static int calculateAssertionCount(String fileName) {
        int assertCount = 0;
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();

            if (line.contains("Assert.") && line.contains("(") && line.contains(")")) {
                assertCount += countassertionsinline(line);
            }
        }

        return assertCount;
    }

private static int countassertionsinline(String line) {
        int count = 0;

       
        String[] parts = line.split("\\(");
        for (String part : parts) {
            if (part.contains("Assert.")  {
                count++;
            }
        }

        return count;
    }
}
