enum class Direction(val dir: Pair<Int, Int>) {
    UP(Pair(0, 1)), RIGHT(Pair(1, 0)), DOWN(Pair(0, -1)), LEFT(Pair(-1, 0))
}

class GameState(
    val map: List<List<Char>>,
    var guardPos: Pair<Int, Int>,
    var obstacles: MutableList<Pair<Int, Int>>,
    var facing: Direction = Direction.UP,
) {
    val mapSize: Pair<Int, Int> = Pair(map[0].size, map.size)
    val coveredPositions: MutableSet<Pair<Int, Int>> = mutableSetOf(guardPos)
    val rotateEvent: MutableList<Pair<Pair<Int, Int>, Direction>> = mutableListOf()

    var covered = 0

    private fun rotate() {
        this.facing = when (this.facing) {
            Direction.UP -> Direction.RIGHT
            Direction.RIGHT -> Direction.DOWN
            Direction.DOWN -> Direction.LEFT
            Direction.LEFT -> Direction.UP
        }
        this.rotateEvent.add(this.guardPos to this.facing)
    }

    fun isInsideMap(pos: Pair<Int, Int>): Boolean {
        val (x, y) = pos
        return x in 0..<this.mapSize.first && y in 0..<this.mapSize.second
    }


    fun nextStep(): Pair<Int, Int> {
        return (this.guardPos.first + this.facing.dir.first to this.guardPos.second + this.facing.dir.second)
    }

    fun step(): Unit {
        if (this.nextStep() in this.obstacles) {
            this.rotate()
        }

        covered++

        this.guardPos = this.nextStep()
        this.coveredPositions.add(this.guardPos)
    }
}

fun parse(input: List<String>): GameState {
    val obstacles = mutableListOf<Pair<Int, Int>>()
    var guardPos: Pair<Int, Int>? = null

    val m = input.map {
        it.toList()
    }.reversed()

    for (j in m.indices) {
        for (i in m[j].indices) {
            val currentChar = m[j][i]
            if (currentChar == '#') {
                obstacles.add(Pair(i, j))
            }

            if (currentChar == '^') {
                guardPos = Pair(i, j)
            }

        }
    }

    return GameState(
        m.toList(), guardPos!!, obstacles
    )


}


fun main() {
    fun part1(input: List<String>): Int {
        val state = parse(input)

        while (state.isInsideMap(state.nextStep())) {
            state.step()
        }

        val covered = state.coveredPositions

        return covered.size
    }

    fun part2(input: List<String>): Int {
        return 0
    }


    val testInput = readInput("Day06_test")
    //check(part1(testInput) == 41)
    //check(part2(testInput) == 123)

    //part1(testInput).println()
    part2(testInput).println()


    // Read the input from the `src/Day01_test.txt` file.
    val input = readInput("Day06")
    //part1(input).println()
    part2(input).println()
}