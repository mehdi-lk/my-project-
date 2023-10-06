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