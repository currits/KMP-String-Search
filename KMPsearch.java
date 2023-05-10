import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KMPsearch {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println("Usage: java KMPsearch <kmp filepath> <searchable file>");
            System.exit(0);
        }

        //Files to open
        File kmpFile = new File(args[0]), searchFile = new File(args[1]);

        boolean exists = true;

        //Checking to see if the files actually exist
        if (!kmpFile.exists()) {
            System.err.println("KMP table file does not exist!");
            exists = false;
        }

        if (!searchFile.exists()) {
            System.err.println("File to search does not exist!");
            exists = false;
        }

        //Exit if not
        if (!exists)
            System.exit(0);

        //Reader for kmpTable
        BufferedReader kmpReader = new BufferedReader(new FileReader(kmpFile));

        //The string we are matching against
        //We don't actually need this but we have to skip the first line of the table so we may as well store it :3
        String matchString = kmpReader.readLine().trim().replaceAll("\\s+", "");

        String line;

        //List for unique character lookup
        List<Character> uniqueChar = new ArrayList<Character>();
        //List of lists of skip values
        //Each top level list corresponds to a unique character (the index for the unique char in uniqueChar will match)
        List<List<Integer>> skipValues = new ArrayList<List<Integer>>();
        int count = 0;

        //For each line in the table (thats left)
        while ((line = kmpReader.readLine()) != null) {
            // remove whitespace
            line = line.trim().replaceAll("\\s+", "");

            //Add the character to the unique character list and add a list for that character in skipvalues
            uniqueChar.add(line.charAt(0));
            skipValues.add(new ArrayList<>());

            //Temp string for skip values
            String skips = line.substring(1);

            // Store each skip int for each unique character
            for (int i = 0; i < skips.length(); i++) {
                skipValues.get(count).add(Character.getNumericValue(skips.charAt(i)));
            }

            count++;
        }

        kmpReader.close();


        BufferedReader fileReader = new BufferedReader(new FileReader(searchFile));

        
        while ((line = fileReader.readLine()) != null) {

            //Analyze file
        }
    }
}
