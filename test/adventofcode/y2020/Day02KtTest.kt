package adventofcode.y2020

import org.junit.Test

import org.junit.Assert.*

class Day02KtTest {

    @Test
    fun fromInput() {
        val pol = createPolicy("1-3 c")
        assertEquals(pol.min, 1)
        assertEquals(pol.max, 3)
        assertEquals(pol.char, 'c')
    }

    @Test
    fun testPassword() {
        val password = createPassword("2-5 a: anton albert")
        assertTrue(password.validate())
    }
}