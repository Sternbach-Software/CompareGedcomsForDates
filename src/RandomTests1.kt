import java.io.File
import java.util.regex.Pattern

fun main() {
    //Random Geni sample
    //1 NAME Lea /Asia/
    //...
    //...
    //1 BIRT
    //2 DATE ABT 1911
    //1 DEAT
    //2 DATE 1963
    val individualNameAndAssociatedDates= Triple<String,String,String>("","","")
    val listOfFamilyMembersAndDates: MutableList<Triple<String,String,String>> = mutableListOf(individualNameAndAssociatedDates)

    val geniGEDCOMPeopleWithAllTheirAttributes= File("C:\\Users\\Shmuel\\IdeaProjects\\Compare Gedcoms for dates\\src\\Geni export-BloodTree1.ged").readText().split("(?=1 NAME .+)".toRegex())
    geniGEDCOMPeopleWithAllTheirAttributes.forEach{
        //TODO these regexes are not accurately finding the dates but rather returning null
        val name= "(?<=\\d NAME ).+".toRegex().find(it)?.value.toString()
        val birthDate="(?<=\\d BIRT \\d DATE ).+".toRegex().find(it)?.value.toString()
        val deathDate="(?<=\\d DEAT \\d DATE ).+".toRegex().find(it)?.value.toString()
        listOfFamilyMembersAndDates.add(Triple(name,birthDate,deathDate))
    }
    println(listOfFamilyMembersAndDates)
    val ancestryGedcom= File("src/Ancestry Sternbach-Blood Tree (1).ged").readLines()
}