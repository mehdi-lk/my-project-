import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TAssert {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.exit(1);
        }

        String fileName = args[0];
        int assertCount = calculateAssertionCount(fileName);
        System.out.println("Number of Assertions: " + assertCount);
    }
        private static int calculateAssertionCount(String fileName) {
        int assertCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
               
                line = line.trim();

                if (line.contains("Assert.") && line.contains("(") && line.contains(")")) {
                    assertCount += countAssertionsInLine(line);
                }
            }
        } catch (IOException e) {
            System.err.println( e.getMessage());
            System.exit(1);
        }
      return assertCount;
    }

    private static int countAssertionsInLine(String line) {
        int count = 0;

       
        String[] parts = line.split("\\(");
        for (String part : parts) {
            if (part.contains("Assert.")  {
                count++;
            }
        }

        return count;
    }