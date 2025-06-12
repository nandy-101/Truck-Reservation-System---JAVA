import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReadMyFile{
    public static void main(String[] args) {
        // Specify the path to your text file
        String filePath = "user_data.txt";

        try {
            // Read all lines from the file into a List of Strings
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // Display each line
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // Handle exceptions, such as file not found or unable to read
            e.printStackTrace();
        }
    }
}
