import java.util.Collections

fun main() {

    fun part1(timers: List<Int>): Int {
        return (1..80).fold(timers) { sum, _ ->
            sum.flatMap { if (it == 0) listOf(8, 6) else listOf(it - 1) }
        }.count()
    }

    fun part2(timers: List<Int>): Long {
        val numbers = MutableList(9) { 0L }
        timers.forEach { numbers[it]++ }
        repeat(256) {
            Collections.rotate(numbers, -1)
            numbers[6] += numbers[8]
        }
        return numbers.sum()
    }

    val input = readInput("Day06")
    val timers = input[0].split(",").map { it.toInt() }
    println(part1(timers))
    println(part2(timers))
}
