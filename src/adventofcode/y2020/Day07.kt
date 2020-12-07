package adventofcode.y2020

import adventofcode.readInput

data class Rule(private val parsedName: String) {
    val subRules = mutableMapOf<String, Int>()
    val name: String = parsedName.replace("bags","bag").strip()

    init {
        assert(! ruleList.containsKey(name))

        ruleList[name] = this
    }

    companion object {
        val ruleList = mutableMapOf<String, Rule>()

        fun getOrCreate(name: String): Rule {
            val key = name.replace("bags","bag")
            return ruleList.getOrDefault(key, Rule(name))
        }
        fun getRule(name: String): Rule? {
            val key = name.replace("bags","bag")
            return ruleList[key]
        }
    }

    fun addRule(ruleName: String, count: Int = 1) {
        subRules[ruleName] = count
    }

    fun canCarry(bags: String): Boolean {
        val bag = bags.replace("bags", "bag")

        for ( name in subRules.keys) {
            if (name == bag) {
                return true
            } else {
                val rule = ruleList[name]
                if (rule != null) if (rule.canCarry(bag)) return true
            }
        }

        return false
    }

    fun numberOfBags(): Int {
        var count = 1 // itself
        for ( (name, multiply) in subRules) {
            val rule = ruleList[name]
            if (rule != null) {
                count += multiply*rule.numberOfBags()
            }
        }
        return count
    }
}

fun parseSubRule(input: String): Pair<String, Int>? {
    if (input.isEmpty() || input.isBlank() || input.strip() == "no other bag") return null

    val re = Regex("( *[0-9]+) (.*)")
    val num = input.replace(re,"$1").strip().toInt()
    val name = input.replace(re,"$2")

    return Pair(name, num)
}

fun parseRule(input: String): Rule {
    val (name, value) = input.split("contain")

    val rule = Rule.getOrCreate(name)

    val modifySubRules = value.replace('.',',').replace("bags", "bag")

    for (subRule in modifySubRules.split(',')) {
        val parsed = parseSubRule(subRule)
        if (parsed != null) rule.addRule(parsed.first, parsed.second)
    }
    return rule
}

fun main() {
    readInput(7, 2020).forEach { parseRule(it) }

    var count = 0
    for ((name, rule) in Rule.ruleList) {
        if (rule.canCarry("shiny gold bag")) count++
    }

    val rule = Rule.ruleList["shiny gold bag"]
    val c = rule?.numberOfBags() ?: 0
    println("rule: ${Rule.ruleList.size}, answers: $count, answer2: ${c-1}")
}