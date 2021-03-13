package adventofcode.y2020

import org.junit.Test

import org.junit.Assert.*

class Day16KtTest {

    private fun getTestParser(): Parser {
        val lines = """
                class: 1-3 or 5-7
                row: 6-11 or 33-44
                seat: 13-40 or 45-50
                
                your ticket:
                7,1,14
                
                nearby tickets:
                7,3,47
                40,4,50
                55,2,20
                38,6,12
            """.trimIndent().lines()

        return Parser(lines)
    }

    @Test
    fun testParser_parseRule() {

        val parser = Parser(listOf<String>())

        val rule = parser.parseRule("class: 1-3 or 5-7")
        assertNotNull(rule)
        if (rule != null) {
            assertEquals("class", rule.name)
            assertEquals(2, rule.cond.size)
            val ir1 = rule.cond[0]
            assertEquals(1, ir1.first)
            assertEquals(3, ir1.last)

            assertEquals(false, rule.isValid(0))
            assertEquals(true, rule.isValid(1))
            assertEquals(true, rule.isValid(2))
            assertEquals(true, rule.isValid(3))
            assertEquals(false, rule.isValid(4))
            assertEquals(true, rule.isValid(7))
            assertEquals(false, rule.isValid(8))
        }
    }

    @Test
    fun testParser_parseTicket() {
        val parser = Parser(listOf<String>())

        val ticket = parser.parseTicket("1,2,3,4")
        assertNotNull(ticket)
        if (ticket != null) {
            assertEquals(4, ticket.data.size)
            assertEquals(1, ticket.data[0])
        }
    }

    @Test
    fun testParser() {
        val parser = getTestParser()
        assertEquals(3, parser.rules.size)
        assertEquals(4, parser.ticketList.size)
        assertEquals(7, parser.ticketList[0].data[0])
        assertNotNull(parser.ownTicket)
    }

    @Test
    fun testChecker_getInvalidTicketNumber() {
        val parser = Parser(listOf<String>())
        val rule = parser.parseRule("class: 1-3 or 5-7")

        assertNotNull(rule)
        val checker = Checker(listOf<Rules>(rule!!))

        val ticket = parser.parseTicket("1,2,3,4,6,8")
        assertNotNull(ticket)
        if (ticket != null) {
            assertEquals(4, checker.getInvalidTicketNumber(ticket))
        }
    }

    @Test
    fun testChecker_getInvalidSum() {
        val parser = getTestParser()
        val checker = Checker(parser.rules)

        assertEquals(71, checker.getInvalidSum(parser.ticketList))
    }

    @Test
    fun testChecker_pos() {
        val lines = """
            class: 0-1 or 4-19
            row: 0-5 or 8-19
            seat: 0-13 or 16-19
    
            your ticket:
            11,12,13
    
            nearby tickets:
            3,9,18
            15,1,5
            5,14,9""".trimIndent().lines()
        val parser = Parser(lines)
        val checker = Checker(parser.rules)

        val validTickets = checker.getValidTickets(parser.ticketList).toMutableList()
        validTickets.add(parser.ownTicket!!)
        assertEquals(4, validTickets.size)

        checker.resetRulePos()
        val checksum = checker.getInvalidSum(validTickets)
        assertEquals(0, checksum)

        val ruleList = checker.getListOfRules()
        for (r in ruleList) {
            print(r.name + ",")
        }

    }
}