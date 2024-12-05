package adventofcode.y2024

typealias Page = Int
data class Rule(val a: Page, val b: Page)
typealias Update = List<Page>

class Day05(inputData: List<String>) {
    private val splitIndex = inputData.indexOf("")
    val rules: List<Rule> = inputData.take(splitIndex).map { line -> line.split("|").let { Rule(it[0].toInt(), it[1].toInt()) } }
    val updates: List<List<Page>> = inputData.drop(splitIndex+1).map { line -> line.split(',').map { it.toInt() } }

    fun getRules(pages: List<Page>): List<Rule> = rules.filter { pages.contains(it.a) || pages.contains(it.b) }

    fun isOrdered(update: Update): Boolean {
        return getRules(update).all { rule -> !update.contains(rule.a) || !update.contains(rule.b) || update.indexOf(rule.a) < update.indexOf(rule.b) }
    }
    fun failedRules(update: Update): List<Rule> {
        return getRules(update).filter { rule -> update.contains(rule.a) && update.contains(rule.b) && update.indexOf(rule.a) > update.indexOf(rule.b) }
    }

    fun middlePage(update: Update): Int = update[(update.size -1) / 2]

    fun sumMiddlePages(): Int =
        updates.mapIndexed { index, update ->
            if (isOrdered(update)) {
                middlePage(update)
            } else 0
        }.sum()

//    fun sumMiddlePagesFromIncorrect(): Int =
//        updates.mapIndexed { index, update ->
//            if (!isOrdered(update)) {
//                val updateNew = update.toMutableList()
//                var previousErrors:  List<Rule> = emptyList()
//                for (attemp in 0..50) {
//                    val failedRules = failedRules(updateNew)
//                    if (failedRules.isEmpty()) {
//                        println("$index $attemp fixed!")
//                        break
//                    }
//                    if (failedRules == previousErrors) {
//                        println("$index $attemp failed: no changes on errors")
//                        break
//                    }
//                    previousErrors = failedRules
//
//                    println("$index $attemp: failed: $updateNew, rules: ${failedRules.size} $failedRules")
//                    failedRules.forEach { rule ->
//                        val index1 = updateNew.indexOf(rule.a)
//                        val index2 = updateNew.indexOf(rule.b)
//                        val element = updateNew[index1]
//                        updateNew.remove(element)
//                        updateNew.add(index2, element)
//                    }
//
//                }
//                middlePage(updateNew)
//            } else 0
//        }.sum()

}
