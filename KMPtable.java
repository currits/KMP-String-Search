import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class KMPtable{
        public static void main(String[] args){
            //Get the search string
            String search = args[0];
            System.err.println("Search Term: " + search);
            //Split string into an array using negative lookahead
            String[] searchStrArray = search.split("(?!^)");
            System.err.println("Search array length: " + searchStrArray.length);
            //Store the unique characters in a new string
            String uniqueCharStr = "";
            for(int i = 0; i < searchStrArray.length; i++){
                if (!uniqueCharStr.contains(searchStrArray[i])){
                    uniqueCharStr += searchStrArray[i];
                }
            }
            System.err.println("Unique chars string: " + uniqueCharStr);
            //Split the unique characters into an array
            String[] uniqueCharacters = uniqueCharStr.split("(?!^)");
            System.err.println("Unique char array length: " + uniqueCharacters.length);
            //2D array, x axis search string, y axis unique characters

            int[][] skipArray = new int[searchStrArray.length][uniqueCharacters.length];
            
            // Three! Nested loops to populate the skip array
            // Filling Column first, then Row
            for(int x = 0; x < searchStrArray.length; x++){
                //create strings to use for this rows calculation
                //One for the context of what we have matched so far
                String rowContext = search.substring(0, x);
                //and another for the expected pattern up to this point
                String pattern = search.substring(0, x+1);
                for(int y = 0; y < uniqueCharacters.length; y++){
                    //skipValue to store, default 0 to indicate match
                    int skipValue;
                    //check if the character of this row matches the character of this column
                    if (uniqueCharacters[y].compareTo(searchStrArray[x]) == 0){
                        skipValue = 0;
                    }
                    //else, check if we on the first row (where everything but a match means skipping 1)
                    else if (x == 0){
                        skipValue = 1;
                    }
                    //else, we have a mismatch
                    else{
                        //create specific context for this comparison
                        //append the unique character for this loop to the rowContext
                        String columnContext = rowContext + uniqueCharacters[y];
                        //try to find a suffix of our match that fits a prefix of the pattern
                        //compare decreasing prefix slices of the pattern against decreasing suffix slices of the context
                        //value to store the number of skips that need to be made for
                        int skipCount = 0;
                        while(skipCount <= x){
                            //create a suffix of our context
                            String tempContext = columnContext.substring(skipCount, columnContext.length());
                            //create a prefix of our pattern
                            String tempPattern = pattern.substring(0, pattern.length() - skipCount);
                            //check if they do not match
                            if (tempContext.compareTo(tempPattern) != 0){
                                //if so, increment our counter
                                //this will mean a smaller prefix-suffix slice comparison on the next iteration
                                skipCount++;
                            }
                            //otherwise, if there is a match, then we have found a skip value, so break
                            else
                                break;
                        }
                        skipValue = skipCount;
                    }
                    //store the skip value
                    System.err.println("Storing value " + skipValue + " at index " + x + ","+  y);
                    skipArray[x][y] = skipValue;
                }
            }

            //now printing
            try {
                System.err.println("Print code reached");
                //Writer
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
                //Print the pattern
                String out = "\t\t";
                for(int z = 0; z < searchStrArray.length; z++){
                    out+= searchStrArray[z] + " ";
                }
                writer.write(out);
                writer.newLine();
                for(int y = 0; y < uniqueCharacters.length; y++){
                    out = "\t" + uniqueCharacters[y] + "\t";
                    for(int x = 0; x < searchStrArray.length; x++){
                        out += Integer.toString(skipArray[x][y]) + " ";
                    }
                    writer.write(out);
                    writer.newLine();
                }
                out = "\t*\t";
                for (int p = 0; p < searchStrArray.length; p++){
                    out+= Integer.toString(p+1) + " ";
                }
                writer.write(out);
                writer.newLine();
                writer.flush();
                writer.close();
            } catch (Exception e) {
                System.err.println("Writer error");
                e.printStackTrace();
            }

        }


        public void theGraveyard(){
                        //nested for loops, to calculate each row of the skip array
                        // for(int y = 0; y < uniqueChracters.length - 1; y++){
                        //     //to store the working context of the search string
                        //     String context = "";
            
                        //     for(int x = 0; x < searchStrArray.length; x++){
                        //         //skip value to store, set to zero
                        //         int skipValue = 0;
                        //         //if mismatch, we must compute a non-zero skip value
                        //         if (searchStrArray[x].compareTo(uniqueChracters[y]) != 0){
                        //             //if the unique char occurs previosuly in the string, we must find the specific value we can skip over
                        //             if (context.contains(uniqueChracters[y])){
                        //                 if (context.indexOf(uniqueChracters[y]) == 0){
                        //                     skipValue = context.length() - 1;
                        //                 }
                        //                 else{
                                            
                        //                 }
            
            
                        //             }
                        //             //otherwise, we must skip over the whole x length we have reached so far
                        //             else{
                        //                 skipValue = x;
                        //             }
                        //         }
                        //         //store the skip value
                        //         skipArray[x][y] = skipValue;
                        //         //add the 'matched' character to our context (effectively a rebuilding of the search string as we go)
                        //         context += searchStrArray[x];
                        //     }
                        // }
        }
}