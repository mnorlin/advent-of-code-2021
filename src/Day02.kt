fun main() {
    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        toCommands(input).forEach { command ->
            when (command.type) {
                "forward" -> horizontal += command.value
                "up" -> depth -= command.value
                "down" -> depth += command.value
            }
        }
        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0
        toCommands(input).forEach { command ->
            when (command.type) {
                "forward" -> {
                    horizontal += command.value
                    depth += command.value * aim
                }
                "up" -> aim -= command.value
                "down" -> aim += command.value
            }
        }
        return horizontal * depth
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

fun toCommands(input: List<String>): List<Command<String, Int>> {
    return input.flatMap { it.split(" ").zipWithNext { a, b -> Command(a, b.toInt()) } }
}

data class Command<T, V>(val type: T, val value: V)