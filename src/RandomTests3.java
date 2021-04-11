public class RandomTests3 {
    public static void main(String[] args) {
        System.out.println(subsequencesq("abc"));
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
