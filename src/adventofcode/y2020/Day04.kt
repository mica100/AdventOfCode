package adventofcode.y2020

import adventofcode.readInput

fun isValidHeight(hgt: String?): Boolean {
    if (hgt == null) return false

    if (hgt.endsWith("cm")) {
        val num = hgt.substring(0, hgt.length-2)
        if (num.toInt() in 150..193) {
            return true
        }
    }
    if (hgt.endsWith("in")) {
        val num = hgt.substring(0, hgt.length-2)
        if (num.toInt() in 59..76) {
            return true
        }
    }
    return false
}

fun isValidColor(input: String?): Boolean {
    if (input == null) return false

    return (input[0] == '#' && input.substring(1).matches(Regex("[a-f0-9]+")))
}

fun isValidEyeColor(input: String?): Boolean {
    if (input == null) return false

    return (input in arrayListOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth"))
}

fun isValidPid(input: String?): Boolean {
    if (input == null) return false

    return input.length == 9 && input.matches(Regex("[0-9]+") )
}

class Passport() {
    var byr: Int? = null
    var iyr: Int? = null
    var eyr: Int? = null
    var hgt: String? = null
    var hcl: String? = null
    var ecl: String? = null
    var pid: String? = null
    var cid: String? = null

    fun isValid(): Boolean {
        val byr = this.byr
        if (byr != null && byr in 1920..2002) {
            val iyr = this.iyr
            if (iyr != null && iyr in 2010..2020) {
                val eyr = this.eyr
                if (eyr != null && eyr in 2020..2030) {
                    if (isValidHeight(hgt)) {
                        if (isValidColor(hcl) ) {
                            if (isValidEyeColor(ecl)) {
                                if (isValidPid(pid) ) {
                                    return true
                                } else {
                                    println("invalid pid")
                                }
                            } else {
                                println("invalid ecl")
                            }
                        } else {
                            println("invalid hcl")
                        }
                    } else {
                        println("invalid hgt")
                    }
                } else {
                    println("invalid eyr")
                }
            } else {
                println("invalid iyr")
            }
        } else {
            println("invalid byr")
        }
        return false
    }

    fun setValue(input: String) {
        //println("Input: $input")
        val in1 = input.strip()
        if (in1.contains(":")) {
            val (key, value) = in1.split(":")
            setValue(key, value)
        }
    }

    fun setValue(key: String, value: String) {
        when(key) {
            "byr" -> byr = value.toInt()
            "iyr" -> iyr = value.toInt()
            "eyr" -> eyr = value.toInt()
            "hgt" -> hgt = value
            "hcl" -> hcl = value
            "ecl" -> ecl = value
            "pid" -> pid = value
            "cid" -> cid = value
        }
    }

    override fun toString(): String {
        return super.toString() + " byr:" + this.byr + " iyr:" + this.iyr + " eyr:" + this.eyr + " hgt:" + this.hgt +
                " hcl:" + this.hcl + " ecl:" + this.ecl + " pid:" + this.pid
    }
}

class PassportReader() {
    private val passportList = mutableListOf<Passport>()
    private var currentPassport = Passport()
    var validPassports = 0

    fun readLine(input: String, linenr: Int) {
        if (input.isBlank() || input.isEmpty()) {
            if (currentPassport.isValid()) validPassports++ else println("$linenr: ${currentPassport.toString()}")
            //passportList.add(currentPassport)
            currentPassport = Passport()
        } else {
            input.split(" ", ",").forEach { currentPassport.setValue(it) }
        }
    }
}

fun main() {
    val pwr = PassportReader()
    var line = 0
    readInput(4, 2020).forEach { pwr.readLine(it, line++) }
    pwr.readLine("", line++) // last line may not be an empty one

    println("answer: count=${pwr.validPassports}")
}