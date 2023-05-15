///Name: Ethyn Gillies
///ID: 1503149
///Name: Kurtis-Rae Mokaraka
///ID: 1256115

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
        //We only actually need the length of the string
        String matchString = kmpReader.readLine().trim().replaceAll("\\s+", "");
        final int MATCH_LENGTH = matchString.length();

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

        //Remove the wildcard line as it is always the same (although we dont really have to if there is an astrix it may interfere)
        skipValues.remove(skipValues.size() - 1);

        kmpReader.close();

        BufferedReader fileReader = new BufferedReader(new FileReader(searchFile));

        //Counting lines
        int lineCount = 0;
        
        //For each line in the file
        while ((line = fileReader.readLine()) != null) {
            //Increase the line num and reset the current match index
            lineCount++;
            int currMatch = 0;

            //For each character in the line
            //Pointer points to start of a matching sequence, currMatch is the number of matches we have made for a sequence
            for(int pointer = 0; pointer + currMatch < line.length();){
                //Get the char to check
                char currChar = line.charAt(pointer + currMatch);

                //See if the character is a character in our string
                int uniqueMatch = uniqueChar.indexOf(currChar);

                //-1 means no match, reset and continue on line
                if(uniqueMatch == -1){
                    pointer += currMatch + 1;
                    currMatch = 0;
                    continue;
                }

                //Get the skip value for the match
                int skip = skipValues.get(uniqueMatch).get(currMatch);

                //Zero means a match!
                if(skip == 0){
                    //If the currMatch index equals the length of the string (- 1) we have found a full match
                    if(currMatch == MATCH_LENGTH - 1){
                        System.out.println(lineCount + " " + (pointer+1));
                        //Break as we only match the first occurance on a line
                        break;
                    }

                    //Found part of string so increase currMatch and continue
                    currMatch++;
                }
                else{
                    //Mismatch, increase pointer by skip value and reset currMatch
                    pointer += skip;
                    currMatch = 0;
                }
            }
        }

        //Tidy kiwi :3
        fileReader.close();
    }
}
