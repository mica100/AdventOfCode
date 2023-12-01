package interview_clean

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Parse INPUT (<country>,<brand>,<soldCars>) to sum up data per country.
 */
const val INPUT = "DE:BMW:30000,GB:BMW:20001,DE:RR:1,GB:MINI:7001,IT:MINI:3002,IT:BMW:10000,DE:MINI:2009"


enum class Brand {
    BMW, MINI, RR, UNKNOWN,
}


data class SalesNumber(val country: String, val brand: Brand, val soldCars: Int)


class SalesVolume(private val salesNumbers: List<SalesNumber>) {
    fun getSalesVolume(country: String) = salesNumbers.filter { it.country == country }.sumOf { it.soldCars }
    fun getSalesVolume(brand: Brand) = salesNumbers.filter { it.brand == brand }.sumOf { it.soldCars }
    fun numberOfCountries() = salesNumbers.distinctBy { it.country }.size
}

// Parse functions
fun String.toSalesVolume() = SalesVolume(split(',').map { it.toSalesNumber() })

fun String.toSalesNumber() = split(':').let { SalesNumber(it[0], it[1].toBrand(), it[2].toInt()) }

fun String.toBrand() = try {
    Brand.valueOf(this)
} catch (e: IllegalArgumentException) {
    Brand.UNKNOWN
}


class ParseDataTest2 {
    @Test
    fun calcBrand() {
        val sv = INPUT.toSalesVolume()
        assertEquals(3, sv.numberOfCountries())
        assertEquals(32010, sv.getSalesVolume("DE"))
    }
}
