import kotlin.system.measureNanoTime

fun main() {
    var list2 = listOf("a", "b", "c", "d", "e", "f")
    var list1 = listOf("a", "b", "c")
    var timeA=mutableListOf(0L)
    var timeB=mutableListOf(0L)
    for(i in 1..100) {
        val b = measureNanoTime {
            println(list2.minus(list1))
        }
        val a = measureNanoTime {
            findDifferencesBetweenSets(list2, list1)
        }
        timeA.add(a)
        timeB.add(b)
    }
    println(timeA)
    println(timeB)
}
private fun findDifferencesBetweenSets(bigger: List<String>, smaller: List<String>) {
    val differences=mutableListOf("")
    for (value in bigger) {
        var found = false
        for (s in smaller) {
            if (value == s) {
                found = true
                break
            }
        }
        if (!found) differences+=value
    }
    println(differences)
}