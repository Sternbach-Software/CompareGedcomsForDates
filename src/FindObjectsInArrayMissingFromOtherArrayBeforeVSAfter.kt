import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object FindObjectsInArrayMissingFromOtherArrayBeforeVSAfter {
    @Throws(IOException::class, InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        //   File rejected = new File("C:\\TD_Library\\validate_process\\Rejected");
        //   final JPanel panel = new JPanel();
        //   JOptionPane.showMessageDialog(panel, "Some shiurim have been rejected by the system. This either means that they have already \nbeen uploaded or that they were improperly formatted. You can find the shiurim in question\nat the top of the folder C:\\TD_Library\\validate_process\\Rejected. If they have already been uploaded, prepended to their filename you will find \"DUPLICATE-UPDATED-(ShiurID# already in database)\". \nYou can also contact ssternbach@torahdownloads.com or\nnmond@torahdownloads.com for help.", "Error", JOptionPane.ERROR_MESSAGE);
        //   System.out.println(rejected);

//TODO shown above is the formatted message box for rejected shiurim. below is the stuff before vs after
        val numberOfRejectedShiurim = 0
        val rejected = ArrayList<File>()
        val before = File(System.getProperty("user.dir")).listFiles()
        val after = File(System.getProperty("user.dir")).listFiles()
        for (value in after) {
            var found = false
            for (s in before) {
                if (value == s) {
                    found = true
                    break
                }
            }
            if (!found) rejected.add(value)
        }
        val rejected1 = arrayOfNulls<File>(rejected.size)
        rejected.toArray(rejected1)
        for (file in rejected1) {
            if (!file!!.isFile) continue
            val temp = Files.move(
                Paths.get(file.toString()),
                Paths.get(System.getProperty("user.dir") + file.name)
            )
        }
        //TODO test if this^ works(moves back files)
        //TODO add in mp3 erasure code
        //TODO arrange the joptionpanes all the right way and make sure the logic flows right
        //if you wouldn't mind, just walk through the code one more time to make sure everything lines up
        /*String[] setA = {"1", "2", "3", "4", "5", "hello", "yes", "14"};
      String[] setB = {"1", "2"};
        ArrayList<String> rejected= new ArrayList<>();
      for (String value : setA) {
          boolean found = false;
          for (String s : setB) {
              if (value.equals(s)) {
                  found = true;
                  break;
              }
          }
          if (!found) {
//                numberOfRejectedShiurim+=1;
              System.out.println(value + " is not in setB");
              rejected.add(value);
          }
          System.out.println(rejected);
      }*/
    }
}