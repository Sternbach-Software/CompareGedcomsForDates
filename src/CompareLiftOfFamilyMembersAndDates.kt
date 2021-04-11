fun main() {

//    findDifferencesBetweenGEDCOMs()
}

private fun findDifferencesBetweenGEDCOMs(

        geniGEDCOM:MutableList<Triple<String, String, String>>,
        ancestryGEDCOM:MutableList<Triple<String, String, String>>
)

: MutableList<Triple<String, String, String>>

{
    val geniGEDCOM1: MutableList<Triple<String, String, String>> =geniGEDCOM
    val ancestryGEDCOM1: MutableList<Triple<String, String, String>> = ancestryGEDCOM
    val bigger = if (geniGEDCOM1.size > ancestryGEDCOM1.size) geniGEDCOM1 else ancestryGEDCOM1
    val smaller = if (bigger == geniGEDCOM1) ancestryGEDCOM1 else geniGEDCOM1
    val differences: MutableList<Triple<String, String, String>> = mutableListOf(Triple("", "", ""))

    findDifferencesBetweenSets(bigger, smaller, differences)
    return differences
}

private fun findDifferencesBetweenSets(bigger: MutableList<Triple<String, String, String>>, smaller: MutableList<Triple<String, String, String>>, differences: MutableList<Triple<String, String, String>>) {
    for (value in bigger) {
        var found = false
        for (s in smaller) {
            if (value == s) {
                found = true
                break
            }
        }
        if (!found) differences.plus(value)
    }
}