package adventofcode.y2024

class ParserHelper(
    val operationList: MutableList<Pair<Int, Int>> = mutableListOf(),
    var currentElement: String = "",
    var enableMul: Boolean = true,
)

class Day03(testData: String, enableConditions: Boolean = false) {
    val operationList = testData.fold(ParserHelper()) { parser, ch ->
        if (ch == 'm') {
            parser.currentElement = ""
        }
        //println("${parser.currentElement} + $ch, ${parser.enableMul}")
        parser.currentElement += ch

        when {
            enableConditions && parser.currentElement.endsWith("do()") -> parser.enableMul = true
            enableConditions && parser.currentElement.endsWith("don't()") -> parser.enableMul = false
            parser.currentElement.matches(Regex("mul\\([0-9]+,[0-9]+\\)")) -> {
                if (parser.enableMul) {
                    parser.currentElement.removePrefix("mul(").removeSuffix(")").split(",").let {
                        if (it.size == 2) {
                            val first = it[0].toIntOrNull()
                            val second = it[1].toIntOrNull()
                            if (first != null && second != null) {
                                parser.operationList.add(Pair(first, second))
                                //println("add pair $it")
                            }
                        }
                    }
                }
                parser.currentElement = ""
            }
        }
        parser
    }.operationList

    fun multiplyResult() =
        operationList.sumOf { it.first.toLong() * it.second }
}
