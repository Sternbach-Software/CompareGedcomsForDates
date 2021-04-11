import java.io.File

fun main() {
    findDifference()
    println()
}

fun findDifference() {
    val geniGEDCOM = "C:\\Users\\Shmuel\\IdeaProjects\\Compare Gedcoms for dates\\backup of gedcoms\\GeniExport-BloodTree.ged"
    val ancestryGEDCOM = "C:\\Users\\Shmuel\\IdeaProjects\\Compare Gedcoms for dates\\backup of gedcoms\\Sternbach-Blood Tree (1).ged"
    val differences = findDifferencesBetweenGEDCOMs(geniGEDCOM, ancestryGEDCOM)
    println(differences)
}


private fun buildListOfPeopleAndTheirDates(pathname: String): MutableList<Triple<String, String, String>> {
    val individualNameAndAssociatedDates = Triple<String, String, String>("", "", "")
    val listOfFamilyMembersAndDates: MutableList<Triple<String, String, String>> = mutableListOf(individualNameAndAssociatedDates)

//    val gEDCOMPeopleWithAllTheirAttributes = File(pathname).readText().split("(?=1 NAME .+)".toRegex())
    val gEDCOMPeopleWithAllTheirAttributes = File(pathname).readLines()
//    gEDCOMPeopleWithAllTheirAttributes.forEach {
//    for(it in gEDCOMPeopleWithAllTheirAttributes){

    var index = 0
    var nextInstanceOfNAME = nextInstanceOfNAME(index, gEDCOMPeopleWithAllTheirAttributes)
    index += nextInstanceOfNAME.index
    while (index < gEDCOMPeopleWithAllTheirAttributes.size) {
        val it = gEDCOMPeopleWithAllTheirAttributes[index]


        //TODO these regexes are not accurately finding the dates but rather returning null
        val name = nextInstanceOfNAME.name/*"(?<=\\d NAME ).+".toRegex().find(it)?.value.toString()*/
        val birthDate = {
            var miniIndex= index
            var birthDateAnswer = "null"
            miniWhile@while(miniIndex < gEDCOMPeopleWithAllTheirAttributes.size) {
                miniIndex++
                val gEDCOMAtMiniIndex = gEDCOMPeopleWithAllTheirAttributes[miniIndex]
                println(gEDCOMAtMiniIndex)
                if(gEDCOMAtMiniIndex.contains("NAME")) break@miniWhile
                if(gEDCOMAtMiniIndex.contains("BIRT") && gEDCOMPeopleWithAllTheirAttributes[miniIndex+1].contains("(?<=\\d DATE ).+".toRegex())) birthDateAnswer = "(?<=\\d DATE ).+".toRegex().find(gEDCOMAtMiniIndex)?.value.toString()
            }
            birthDateAnswer
        }/*"(?<=\\d BIRT \\d DATE ).+".toRegex().find(it)?.value.toString()*/
        val deathDate = {
            var miniIndex= index
            var deathDateAnswer = "null"
            miniWhile@while(miniIndex < gEDCOMPeopleWithAllTheirAttributes.size) {
                miniIndex++
                val gEDCOMAtMiniIndex = gEDCOMPeopleWithAllTheirAttributes[miniIndex]
                println(gEDCOMAtMiniIndex)

                if(gEDCOMAtMiniIndex.contains("NAME")) break@miniWhile
                if(gEDCOMAtMiniIndex.contains("DEAT") && gEDCOMPeopleWithAllTheirAttributes[miniIndex+1].contains("(?<=\\d DATE ).+".toRegex())) deathDateAnswer = "(?<=\\d DATE ).+".toRegex().find(gEDCOMAtMiniIndex)?.value.toString()
            }
            deathDateAnswer
        }/*"(?<=\\d DEAT \\d DATE ).+".toRegex().find(it)?.value.toString()*/
        nextInstanceOfNAME = nextInstanceOfNAME(index, gEDCOMPeopleWithAllTheirAttributes)
        index += nextInstanceOfNAME.index
        listOfFamilyMembersAndDates.add(Triple(name, birthDate.invoke(), deathDate.invoke()))
    }
    return listOfFamilyMembersAndDates
}

fun nextInstanceOfNAME(index: Int, gedcom: List<String>): InstanceOfName {
    var index1 = index+1
    val gedcomAtIndex = gedcom[index1]
    while(index1<gedcom.size){
    if(gedcomAtIndex.contains("NAME")) return InstanceOfName("(\\d NAME ).+".toRegex().find(gedcomAtIndex)?.value.toString(),index1)
        index1++
    }
    return InstanceOfName("null",index) /*TODO I smell a one-off error here*/
}

data class InstanceOfName(val name: String, val index: Int)

private operator fun Int.plusAssign(nextInstanceOfNAME: () -> Int) {
    this += nextInstanceOfNAME
}

private fun findDifferencesBetweenGEDCOMs(geniGEDCOM: String, ancestryGEDCOM: String): MutableList<Triple<String, String, String>> {

    val geniGEDCOM1: MutableList<Triple<String, String, String>> = buildListOfPeopleAndTheirDates(geniGEDCOM)
    val ancestryGEDCOM1: MutableList<Triple<String, String, String>> = buildListOfPeopleAndTheirDates(ancestryGEDCOM)
    val bigger = if (geniGEDCOM1.size > ancestryGEDCOM1.size) geniGEDCOM1 else ancestryGEDCOM1
    val smaller = if (bigger == geniGEDCOM1) ancestryGEDCOM1 else geniGEDCOM1
    var differences: MutableList<Triple<String, String, String>> = mutableListOf(Triple("", "", ""))

    findDifferencesBetweenSets(ancestryGEDCOM1, geniGEDCOM1, differences)
//    differences= geniGEDCOM1.minus(ancestryGEDCOM1) as MutableList<Triple<String, String, String>>
    differences.forEach {
        if ((it.second == "null" && it.third == "null")) {
            differences = differences.minus(it) as MutableList<Triple<String, String, String>>
        }
    }
    return differences
}

private fun findDifferencesBetweenSets(bigger: MutableList<Triple<String, String, String>>, smaller: MutableList<Triple<String, String, String>>, differences: MutableList<Triple<String, String, String>>) {
    bigger.removeIf { smaller.contains(it) }
//  bigger.removeIf{it in smaller}
    //^^^^^Not sure which versions to use, didn't test it out yet
    /*for (value in bigger) {
        var found = false
        for (s in smaller) {
            if (value.second == s.second&&value.third == s.third) {
                found = true
                break
            }
        }
        if (!found) differences.add(value)
    }*/
}