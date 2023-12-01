package interview

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Parse INPUT (<country>,<brand>,<soldCars>) to sum up data per country.
 */
const val INPUT = "DE:BMW:30000,GB:BMW:20001,DE:RR:1,GB:MINI:7001,IT:MINI:3002,IT:BMW:10000,DE:MINI:2009"

class CountrySales() {
    var countryName: String? = null
    var soldCars: Int = 0
}
fun String.toCountryAndSales(delimiter: Char): Pair<String, Int> = split(delimiter).let { Pair(it[0], it[2].toInt()) }

class SalesVolume() {
    private var salesPerCountry: MutableList<CountrySales> = mutableListOf()

    fun parseCountrySales(input: String): MutableList<CountrySales>? {
        if (input.isEmpty()) return null

        val data = input.split(',')
        val len = data.size
        var i = 0
        while (i < len) {
            val (country, soldCars) = data[i].toCountryAndSales(':')
            getOrCreateCountrySales(country).soldCars += soldCars
            i += 1
        }
        return salesPerCountry
    }

    private fun getOrCreateCountrySales(countryName: String) : CountrySales {
        for (c in salesPerCountry) {
            if (c.countryName == countryName) return c
        }
        val x = CountrySales()
        x.countryName = countryName
        salesPerCountry.add(x)
        return x
    }

    fun getSalesVolume(country: String) = getOrCreateCountrySales(country).soldCars

    fun numberOfCountries() = salesPerCountry.size
}

class ParseDataTest {
    @Test
    fun calcSalesVolume() {
        val sv = SalesVolume()
        sv.parseCountrySales(INPUT)
        assertEquals(3, sv.numberOfCountries())
        assertEquals(32010, sv.getSalesVolume("DE"))
    }
}
