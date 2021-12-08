import kotlin.math.abs

fun main() {

    fun part1(crabs: List<Int>): Int {
        val median = crabs.sorted()[crabs.count() / 2]
        return crabs.sumOf { abs(it - median) }
    }

    fun part2(crabs: List<Int>): Int {
        val max = crabs.maxOrNull()
        return (0..(max ?: 0)).map {
            position -> crabs.sumOf { abs(it - position).triangular() }
        }.minOrNull() ?: 0
    }

    val input = readInput("Day07")
    val crabs = input[0].split(",").map { it.toInt() }
    println(part1(crabs))
    println(part2(crabs))
}