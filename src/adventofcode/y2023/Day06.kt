package adventofcode.y2023

data class RaceRule(val id: Int, val maxTime: Long, val minDistance: Long) {
    fun getDistance(buttonPress: Long): Long {
        val movingTime = maxTime - buttonPress
        return movingTime * buttonPress
    }

    fun getValidDistance(): Int = (1 until maxTime).count { buttonPressTime ->
        getDistance(buttonPressTime) > minDistance
    }
}

fun String.splitField() = substringAfter(':').trim().split(Regex(" +"))

class Day06(inputData: List<String>) {

    val races: List<RaceRule> by lazy {
        var id = 0
        inputData.zipWithNext { e1, e2 ->
            e1.splitField().zip(e2.splitField()) { a: String, b: String ->
                RaceRule(id++, a.toLong(), b.toLong())
            }
        }.flatten().onEach(::println)
    }

    val singleRace = inputData.map { it.substringAfter(":").replace(Regex(" +"),"").toLong() }
        .let { RaceRule(0, it[0], it[1]) }

    fun getSolution1() = races.map {it.getValidDistance() }.reduce { acc, i -> acc * i }

    fun getSolution2() = singleRace.getValidDistance()

}
