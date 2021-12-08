import kotlin.math.abs

fun main() {

    fun part1(crabs: List<Int>): Int {
        val median = crabs.sorted()[crabs.count() / 2]
        return crabs.sumOf { abs(it - median) }
    }

    fun part2(crabs: List<Int>): Int {
        val min = crabs.minOrNull()!!
        val max = crabs.maxOrNull()!!
        return (min..max).minOf { position ->
            crabs.sumOf { abs(it - position).triangular() }
        }
    }

    val input = readInput("Day07")
    val crabs = input[0].split(",").map { it.toInt() }
    println(part1(crabs))
    println(part2(crabs))
}