///Name: Ethyn Gillies
///ID: 1503149
///Name: Kurtis-Rae Mokaraka
///ID: 1256115

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class KMPtable{
        public static void main(String[] args){
            if (args.length == 0) {
                System.out.println("Usage: KMPtable String");
                System.out.println("String:  The string to build a KMP skip array for");
                return;
            }
            // Get the search string
            String search = args[0];
            System.err.println("Search Term: " + search);
            // Split string into an array using negative lookahead
            String[] searchStrArray = search.split("(?!^)");
            System.err.println("Search array length: " + searchStrArray.length);
            // Store the unique characters in a new string
            String uniqueCharStr = "";
            for(int i = 0; i < searchStrArray.length; i++){
                if (!uniqueCharStr.contains(searchStrArray[i])){
                    uniqueCharStr += searchStrArray[i];
                }
            }
            System.err.println("Unique chars string: " + uniqueCharStr);
            // Split the unique characters into an array
            String[] uniqueCharacters = uniqueCharStr.split("(?!^)");
            System.err.println("Unique char array length: " + uniqueCharacters.length);
            // A 2D array, x axis search string, y axis unique characters

            int[][] skipArray = new int[searchStrArray.length][uniqueCharacters.length];
            
            // Three! Nested loops to populate the skip array
            // Filling Column(y) first, then Row(x)
            for(int x = 0; x < searchStrArray.length; x++){
                // Create strings to use for this rows calculation
                // One for the context of what we have matched so far
                String rowContext = search.substring(0, x);
                // And another for the expected pattern up to this point
                String pattern = search.substring(0, x+1);

                // Loop to fill this Column (y)
                for(int y = 0; y < uniqueCharacters.length; y++){
                    // SkipValue for this y,x position
                    int skipValue;

                    // First check if we have a match in this position (Unique char matches character in the pattern at this position)
                    if (uniqueCharacters[y].compareTo(searchStrArray[x]) == 0){
                        // 0 to indicate a match
                        skipValue = 0;
                    }
                    // Then check if we are on the first row (where everything but a match means skipping 1)
                    else if (x == 0){
                        skipValue = 1;
                    }
                    // Else, we have a mismatch and must do math
                    else{
                        // Create a specific context for this comparison by appending the unique character for this loop to the rowContext
                        String columnContext = rowContext + uniqueCharacters[y];
                        
                        // Value to store the number of skips that need to be made for this unique char
                        int skipCount = 0;
                        // Try to find a suffix of our columnContext that fits a prefix of the Pattern
                        // Compare decreasing prefix slices of the Pattern against decreasing suffix slices of the columnContext
                        // Loop terminates when it finds a skip value, or, when we must skip the entire length of the context
                        while(skipCount <= x){
                            // Create a suffix of our context
                            String tempContext = columnContext.substring(skipCount, columnContext.length());
                            // Create a prefix of our pattern
                            String tempPattern = pattern.substring(0, pattern.length() - skipCount);
                            // Check if they do not match
                            if (tempContext.compareTo(tempPattern) != 0){
                                //if so, increment our counter
                                //this will mean a smaller prefix-suffix slice comparison on the next iteration
                                skipCount++;
                            }
                            // otherwise, if there is a match, then we have found a skip value, so break
                            else
                                break;
                        }
                        // Store the result of the loop
                        skipValue = skipCount;
                    }
                    // Store the skip value into the array
                    System.err.println("Storing value " + skipValue + " at index " + x + ","+  y);
                    skipArray[x][y] = skipValue;
                }
            }

            // Now to print the array to Standard output
            try {
                System.err.println("Print code reached");
                // Writer
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
                // Print the pattern on the first line
                // Use the \t to create a faux table within the cmd window
                String out = "\t\t";
                for(int z = 0; z < searchStrArray.length; z++){
                    out+= searchStrArray[z] + " ";
                }
                writer.write(out);
                writer.newLine();
                // Now print the values of the skip array
                for(int y = 0; y < uniqueCharacters.length; y++){
                    // Print each row, starting with the Unique character
                    out = "\t" + uniqueCharacters[y] + "\t";
                    // The iterate over the row in the skipArray, using tabs as faux table again
                    for(int x = 0; x < searchStrArray.length; x++){
                        out += Integer.toString(skipArray[x][y]) + " ";
                    }
                    // And write it out
                    writer.write(out);
                    writer.newLine();
                }
                // Final row, the skip values for every character not in the pattern (No calculations needed here)
                // Write a '*' to indicatie 'anything else'
                out = "\t*\t";
                // Then write out the series of numbers 1 - n, where n is length of pattern
                for (int p = 0; p < searchStrArray.length; p++){
                    out+= Integer.toString(p+1) + " ";
                }
                // Final IO handling
                writer.write(out);
                writer.newLine();
                writer.flush();
                writer.close();
            } catch (Exception e) {
                System.err.println("Writer error");
                e.printStackTrace();
            }

        }
}