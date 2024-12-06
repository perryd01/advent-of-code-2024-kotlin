import kotlin.math.floor

data class Vertex(
    val vValue: Int,
)

data class Edge(
    val source: Vertex,
    val destination: Vertex
)

class AdjacencyList {

    val adjacencyMap = mutableMapOf<Vertex, ArrayList<Edge>>()

    fun createVertex(vValue: Int): Vertex {
        val vertex = Vertex(vValue)

        if (adjacencyMap.contains(vertex)) {
            return vertex
        }
        adjacencyMap[vertex] = arrayListOf()
        return vertex
    }

    fun addDirectedEdge(source: Vertex, destination: Vertex) {
        val edge = Edge(source, destination)
        adjacencyMap[source]?.add(edge)
    }

    fun isUpdateOrderCorrect(order: List<Int>): Boolean {
        for (i in order.indices) {
            val current = order[i]
            val rest = order.slice(i + 1..<order.size)

            val currentVertex = this.adjacencyMap.get(Vertex(current))!!
            val destinations = currentVertex.map { it.destination.vValue }

            val noBackRef = rest.all { destinations.contains(it) }
            if (!noBackRef) {
                return false
            }
        }
        return true
    }
}


fun main() {

    fun <T> List<T>.getMiddle(): T {
        return this[floor(this.size / 2.0).toInt()]
    }

    fun <T> MutableList<T>.swap(i: Int, j: Int) {
        val temp = this[i]
        this[i] = this[j]
        this[j] = temp
    }

    fun parse(input: List<String>): Pair<AdjacencyList, List<List<Int>>> {
        val emptyIndex = input.indexOf("")
        val left = input.subList(0, emptyIndex).map {
            val (left, right) = it.split("|")
            return@map Pair(left, right)
        }.map {
            return@map Pair(it.first.toInt(), it.second.toInt())
        }
        val right = input.subList(emptyIndex + 1, input.size).map { rows ->

            rows.split(",").map {
                it.toInt()
            }
        }

        val adjacencyList = AdjacencyList()
        left.forEach {
            val vLeft = adjacencyList.createVertex(it.first)
            val vRight = adjacencyList.createVertex(it.second)
            adjacencyList.addDirectedEdge(vLeft, vRight)
        }

        return Pair(adjacencyList, right)
    }

    fun part1(rawInput: List<String>): Int {
        val (orders, updates) = parse(rawInput)

        return updates.filter {
            orders.isUpdateOrderCorrect(it)
        }.sumOf { it.getMiddle() }
    }

    fun part2(rawInput: List<String>): Int {
        val (orders, updates) = parse(rawInput)

        val invalid = updates.filter {
            !orders.isUpdateOrderCorrect(it)
        }.map {
            it.toMutableList()
        }

        val a = invalid.map { update ->
            for (i in update.indices) {
                for (j in i + 1 until update.size) {
                    val sVertex = Vertex(update[j])

                    val asd = orders.adjacencyMap[sVertex]?.filter {
                        return@filter it.source.vValue == update[j] && it.destination.vValue == update[i]
                    }?.size

                    if (asd != null) {
                        if (asd.toInt() == 1) {
                            update.swap(j, i)
                        }
                    }
                }
            }
            update
        }.sumOf { it.getMiddle() }

        return a
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    part1(testInput).println()
    part2(testInput).println()


    // Read the input from the `src/Day01_test.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}