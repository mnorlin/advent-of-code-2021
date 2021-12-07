fun main() {

    fun part1(input: List<Int>): Int {
        return input.zipWithNext { a, b -> b > a }.count { it }
    }

    fun part2(input: List<Int>): Int {
        return input.windowed(3, 1) { x -> x.sum() }
            .zipWithNext { a, b -> b > a }
            .count { it }
    }

    val input = readInput("Day01")
    println(part1(input.map { it.toInt() }))
    println(part2(input.map { it.toInt() }))
}
