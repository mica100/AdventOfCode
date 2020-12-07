package adventofcode.y2020

import org.junit.Test

import org.junit.Assert.*

class Day07KtTest {

    @Test
    fun testParseSubRule() {
        val pair = parseSubRule(" 1 mirrored maroon bag")

        assertTrue(pair != null)
        if (pair != null) {
            val (name, num) = pair
            assertEquals("mirrored maroon bag", name)
            assertEquals(1, num)
        }


        val pair2 = parseSubRule("no other bag")
        assertTrue(pair2 == null)
    }

    @Test
    fun testParseRule() {
        val rule = parseRule("dotted olive bags contain 1 mirrored maroon bag, 2 dotted red bags, 4 drab lime bags.")

        assertEquals("dotted olive bag", rule.name)
        assertEquals(3, rule.subRules.size)
        assertTrue(rule.canCarry("mirrored maroon bag"))

    }
}