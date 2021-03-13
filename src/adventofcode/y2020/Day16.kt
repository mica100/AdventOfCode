package adventofcode.y2020

import adventofcode.readInput
import java.lang.NumberFormatException

class Ticket(val data: List<Int>)

class Rules(val name: String, val cond: List<IntRange>) {
    val errorPosition = mutableSetOf<Int>()

    fun isValid(nr: Int): Boolean {
        for (c in cond) {
            if (c.contains(nr)) return true
        }
        return false
    }

    fun failed(pos: Int) = errorPosition.add(pos)

    fun getValidPos(numberOfRules: Int): List<Int> {
        val l = mutableListOf<Int>()
        for (i in 0 until numberOfRules) if (i !in errorPosition) l.add(i)
        return l
    }
}

class Checker(private val rules: List<Rules>) {

    fun getInvalidTicketNumber(ticket: Ticket): Int? {
        for ((index, nr) in ticket.data.withIndex()) {                   // check every number
            var validRule = false
            for (rule in rules) {
                if (rule.isValid(nr)) {      // if number in range, check next rule
                    validRule = true
                    //break
                } else rule.failed(index)
            }
            if (!validRule) return nr
        }
        return null
    }

    fun getInvalidSum(ticketList: List<Ticket>): Int {
        var sum = 0
        for (ticket in ticketList) {
            val nr = getInvalidTicketNumber(ticket)
            if (nr != null) sum += nr
        }
        return sum
    }

    fun getValidTickets(ticketList: List<Ticket>): List<Ticket> = ticketList.filter { getInvalidTicketNumber(it) == null }

    fun resetRulePos() {
        for (r in rules) r.errorPosition.clear()
    }

    fun getListOfRules() : List<Rules> {
        var variants = mutableSetOf<MutableMap<Int, Rules>>()
        val numberOfVariants = rules.size
        variants.add(mutableMapOf())
        val sortedRules = rules.sortedBy { it.getValidPos(numberOfVariants).size }
        for (r in sortedRules) {
            val posList = r.getValidPos(numberOfVariants)
            if (posList.isEmpty()) {
                println("Error: getListOfRules list of valid pos empty! Rule: ${r.name}")
            } else {
                print("Check rule ${r.name} with ${posList.size} posS ...")
                val newVariants = mutableSetOf<MutableMap<Int, Rules>>()
                for (m in variants) {
                    for (p in posList) {
                        if (! m.containsKey(p)) { // only make a copy, if we can put the rule at this pos
                            val newMap = HashMap(m)
                            newMap[p] = r
                            newVariants.add(newMap)
                        }
                    }
                }
                variants = newVariants
            }
            println("currently ${variants.size} new variants")
        }
        val l = mutableListOf<Rules>()
        for (v in variants) {
            if (v.size >= rules.size) {
                println("Map size ${v.size}, ${v.toString()}")
                for (i in 0 until rules.size) {
                    val r = v[i]
                    if (r != null) l.add(r) else {
                        println("Error: getListOfRules no rule at index $i")
                    }
                }
            }
        }
        return l
    }

    fun getOwnTicketProduct(ticket: Ticket, rules: List<Rules>): Long {
        var prod = 1L

        print("1")
        for ((idx, r) in rules.withIndex()) {
            if (r.name.startsWith("departure")) {
                val nr = ticket.data[idx]
                print(" * ${nr}")
                prod *= nr
            }
        }
        println("Ticket product is ${prod}")
        return prod
    }
}

class Parser(lines: List<String>) {
    val rules = mutableListOf<Rules>()
    var ownTicket: Ticket? = null
    val ticketList = mutableListOf<Ticket>()

    init {
        parse(lines)
    }

    fun parse(lines: List<String>) {
        var section = "inRules"

        for (line in lines) {
            if (line.isNotBlank()) {
                when (section) {
                    "inRules" -> parseRule(line)?.let { rules.add(it) }
                    "your ticket:" -> ownTicket = parseTicket(line)
                    "nearby tickets:" -> parseTicket(line)?.let { ticketList.add(it) }
                    else -> section = line
                }
            } else section = ""
        }
    }

    fun parseRule(line: String) : Rules? {
        val (name, condStr) = line.split(":")
        val condList = mutableListOf<IntRange>()

        for (item in condStr.split(" ")) {
            if (item.matches(Regex("[0-9]+-[0-9]+")) ) {
                val (from,to) = item.split("-")
                val ir = IntRange(from.toInt(), to.toInt())
                condList.add(ir)
            } else if ((item == "or") or item.isEmpty()) {
                // all ok
            } else {
                println("parseRule: Can't parse $line")
                return null
            }
        }
        return Rules(name, condList)
    }

    fun parseTicket(line: String): Ticket? {
        try {
            if (line.isNotBlank()) {
                return Ticket(line.split(',').map { it.toInt() })
            }
        } catch (e: NumberFormatException) {
            println("parseTicket: Can't parse $line")
            e.printStackTrace()
        }
        return null
    }
}


fun main() {
    val inputLines = readInput(16, 2020)
    val parser = Parser(inputLines)

    val checker = Checker(parser.rules)

    val sum = checker.getInvalidSum(parser.ticketList)
    println("error sum: $sum")

    // solution 2: count the errors on each position -> if error == 0 it's the correct position
    val validTickets = checker.getValidTickets(parser.ticketList).toMutableList()
    validTickets.add(parser.ownTicket!!)
    checker.resetRulePos()
    val checksum = checker.getInvalidSum(validTickets)
    println("checksum is $checksum")

    val ruleList = checker.getListOfRules()
    for (r in ruleList) {
        print(r.name + ",")
    }
    println("")

    val prod = checker.getOwnTicketProduct(parser.ownTicket!!, ruleList)
    println("prod is $prod")
}