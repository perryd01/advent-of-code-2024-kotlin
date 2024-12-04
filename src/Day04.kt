fun main() {
    fun fill(input: List<String>, len: Int = 5): List<String> {
        val width = input[0].count()
        val emptyRow = ".".repeat(width + (2 * len)).toString()

        return input.map {
            ".".repeat(len).toString() + it + ".".repeat(len)
        }.toMutableList().apply {
            repeat(len) { this.addFirst(emptyRow) }
            repeat(len) { this.addLast(emptyRow) }
        }
    }


    fun part1(input: List<String>): Int {
        var matches = 0

        fun List<String>.findVariations(x: Int, y: Int): Int {

            var counter = 0

            val positions = listOf(
                1 to 0,
                -1 to 0,
                0 to 1,
                0 to -1,
                1 to 1,
                -1 to -1,
                1 to -1,
                -1 to 1
            )

            fun get(x: Int, y: Int): Char? {
                return this.getOrNull(y)?.getOrNull(x)
            }

            positions.forEach {
                val (px, py) = it
                if (get(x, y) == 'X' && get(x + px * 1, y + py * 1) == 'M' && get(x + px * 2, y + py * 2) == 'A' && get(
                        x + px * 3,
                        y + py * 3
                    ) == 'S'
                ) {
                    counter++
                }
            }


            return counter
        }


        val width = input[0].length
        val height = input.size

        for (j in 0..<height) {
            for (i in 0..<width) {
                matches += input.findVariations(j, i)
            }
        }


        return matches
    }

    fun part2(input: List<String>): Int {
        var matches = 0
        val width = input[0].length
        val height = input.size

        fun List<String>.findVariations(x: Int, y: Int): Int {
            fun get(x: Int, y: Int): Char? {
                return this.getOrNull(y)?.getOrNull(x)
            }

            val positionLayouts: List<List<Pair<Pair<Int, Int>, Char>>> = listOf(
                listOf(
                    Pair(Pair(0, 0), 'A'),
                    Pair(Pair(1, 1), 'S'),
                    Pair(Pair(-1, 1), 'M'),
                    Pair(Pair(-1, -1), 'M'),
                    Pair(Pair(1, -1), 'S')
                ),
                listOf(
                    Pair(Pair(0, 0), 'A'),
                    Pair(Pair(1, 1), 'M'),
                    Pair(Pair(-1, 1), 'S'),
                    Pair(Pair(-1, -1), 'S'),
                    Pair(Pair(1, -1), 'M')
                ),
                listOf(
                    Pair(Pair(0, 0), 'A'),
                    Pair(Pair(1, 1), 'S'),
                    Pair(Pair(-1, 1), 'S'),
                    Pair(Pair(-1, -1), 'M'),
                    Pair(Pair(1, -1), 'M')
                ),
                listOf(
                    Pair(Pair(0, 0), 'A'),
                    Pair(Pair(1, 1), 'M'),
                    Pair(Pair(-1, 1), 'M'),
                    Pair(Pair(-1, -1), 'S'),
                    Pair(Pair(1, -1), 'S')
                )
            )

            return positionLayouts.count { pos ->
                val matched = pos.all {
                    val (coordinate, letter) = it
                    val (dx, dy) = coordinate

                    get(x + dx, y + dy) == letter
                }



                return@count matched
            }
        }

        for (j in 0..<height) {
            for (i in 0..<width) {
                matches += input.findVariations(j, i)
            }
        }

        return matches
    }


    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    part1(testInput).println()
    part2(testInput).println()


    // Read the input from the `src/Day01_test.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

