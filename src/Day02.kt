import kotlin.math.abs

fun main() {

    fun isSafe(report: List<Int>): Boolean {
        val isAscending = report.zipWithNext().all { (a, b) -> a < b }
        val isDescending = report.zipWithNext().all { (a, b) -> a > b }

        if (!isAscending && !isDescending) {
            return false
        }
        return report.zipWithNext().all { (a, b) -> abs(a - b) in 1..3 }
    }

    fun part1(input: List<String>): Int {


        return input.map { it.split(" ") }.map { it.map { e -> e.toInt() } }.map { isSafe(it) }.count { it }
    }

    fun part2(input: List<String>): Int {
        fun isSafeWithPop(report: List<Int>): Boolean {
            if(isSafe(report)){
                return true
            }

            report.forEachIndexed { index, i ->
                var popped = report.toMutableList().apply {
                    removeAt(index)
                }
                if (isSafe(popped)){
                    return true
                }
            }

            return false
        }


        return input.map { it.split(" ") }.map { it.map { it.toInt() } }.map { isSafeWithPop(it) }.count { it }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01_test.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()

}