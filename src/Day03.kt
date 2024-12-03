import kotlin.reflect.typeOf

class Expression(expression: String) {
    private val values: Pair<Int, Int>

    val captureRegex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")

    override fun toString(): String {
        return "mul(${this.values.first}, ${this.values.second})"
    }

    init {
        val matches = captureRegex.findAll(expression)
        val (a, b) = matches.toList().flatMap {
            it.groupValues
        }.let {
            it.slice(1..<it.size)
        }.map {
            it.toInt()
        }
        this.values = Pair(a, b)
    }

    fun evaluate(): Int {
        return this.values.first * this.values.second
    }


}

fun main() {
    fun parseInput(input: String): List<Expression> {
        val pattern = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")
        val matches = pattern.findAll(input).toList().map {
            Expression(it.value)
        }
        return matches
    }

    fun part1(input: List<String>): Int {
        return input.map { parseInput(it) }.flatten().map { it.evaluate() }.sum()
    }

    fun part2(input: List<String>): Int {
        val captureRegex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")

        val pattern = Regex("(don't\\(\\))|(do\\(\\))|(mul\\((\\d{1,3}),(\\d{1,3})\\))")
        return input.map { pattern.findAll(it).toList() }.flatten().map { it.value }.map {
            if (captureRegex.matches(it)) {
                return@map Expression(it)
            } else {
                if (it == "do()") {
                    return@map true
                } else {
                    return@map false
                }
            }
        }
            .fold(Pair(0, true)) { acc, element ->
                if (!acc.second) {
                    if (element is Expression) {
                        return@fold Pair(acc.first, false)
                    } else {
                        val e = element as Boolean
                        return@fold Pair(acc.first, e)
                    }
                } else {
                    if (element is Expression) {
                        return@fold Pair(acc.first + element.evaluate(), acc.second)
                    } else {
                        val e = element as Boolean
                        return@fold Pair(acc.first, e)
                    }
                }
            }.first

    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)


    // Read the input from the `src/Day01_test.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}