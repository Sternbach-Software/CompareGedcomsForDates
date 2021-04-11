import java.util.Arrays;

public class RandomTests2 {
    public static void main(String[] args) {
    help();
    }
    public static void help(){
        System.out.println();
        System.out.println(Arrays.toString(subsequencesAfter("", "abc").split(",")));
        System.out.println(Arrays.toString(subsequencesq("abc").split(",")));
        	   }
private static String subsequencesAfter(String partialSubsequence, String word) {
	    if (word.isEmpty()) {
	        // base case
	        return partialSubsequence;
	    } else {
	        // recursive step
	        return subsequencesAfter(partialSubsequence, word.substring(1))
	             + ","
	             + subsequencesAfter(partialSubsequence + word.charAt(0), word.substring(1));
	    }

    }
    public static String subsequencesq(String word) {
        if (word.isEmpty()) {
            return ""; // base case
        } else {
            char firstLetter = word.charAt(0);
            String restOfWord = word.substring(1);

            String subsequencesOfRest = subsequencesq(restOfWord);

            String result = "";
            for (String subsequence : subsequencesOfRest.split(",", -1)) {
                result += "," + subsequence;
                result += "," + firstLetter + subsequence;
            }
            result = result.substring(1); // remove extra leading comma
            return result;
        }
    }

}
