package adventofcode.y2019

/**
 * https://adventofcode.com/2019/day/1
 */

fun main() {
    var fuelSum = 0
    do {
        val inputStr = readLine() ?: ""
        if (inputStr == "") break
        fuelSum += calcFuel(inputStr.toInt())
    } while (true)

    print(fuelSum)
}

fun calcFuel(mass: Int) : Int {
    if (mass <= 0) return 0

    val third : Float = mass / 3.0f
    val round : Int = third.toInt()
    var fuel = round - 2

    if(fuel > 0) {
        fuel += calcFuel(fuel)
    } else {
        fuel = 0
    }
    return fuel
}