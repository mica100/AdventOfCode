package adventofcode

fun main() {
    val worldpopulation = 8*1000*1000000L

    val sqMeter = worldpopulation / 4

    val sqKilometer = sqMeter / (1000*1000)

    val hectare = sqMeter / (100*100)
    println("population = $worldpopulation, m^2 = $sqMeter, km^2 = $sqKilometer, $hectare ha")
}