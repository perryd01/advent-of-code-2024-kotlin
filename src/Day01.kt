import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        return input.map { it.split(" ") }
            .map { row -> row.filter { it.isNotBlank() } }
            .map { it[0].toInt() to it[1].toInt() }
            .unzip()
            .toList()
            .map { it.sorted() }
            .let {
                it[0].zip(it[1]) { a, b -> abs(a - b) }
            }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val x = input.map { it.split(" ") }
            .map { row -> row.filter { it.isNotBlank() } }
            .map { it[0].toInt() to it[1].toInt() }
            .unzip()
            .toList()
            .let {
                it[0].map {
                    e -> e * it[1].filter { v -> v == e }.size
                }
            }.sum()

        return x
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01_test.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
