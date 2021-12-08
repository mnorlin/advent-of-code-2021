fun main() {

    fun part1(lines: List<Line>): Int {
        val straightLines = lines.filter { it.start.col == it.end.col || it.start.row == it.end.row }
        return straightLines.flatMap { it.line }.groupBy { it }.count { it.value.count() > 1 }
    }

    fun part2(lines: List<Line>): Int {
        return lines.flatMap { it.line }.groupBy { it }.count { it.value.count() > 1 }
    }

    val input = readInput("Day05")
    val lines = input.map { toLine(it) }
    //debug(lines)
    println(part1(lines))
    println(part2(lines))
}

fun toLine(input: String): Line {
    return input.split(" -> ").map { it.split(",") }.zipWithNext { a, b ->
        Line(
            Coordinate(col = a.first().toInt(), row = a.last().toInt()),
            Coordinate(col = b.first().toInt(), row = b.last().toInt())
        )
    }.first()
}

class Line(val start: Coordinate, val end: Coordinate) {
    var line: List<Coordinate>

    init {
        val firstRange = if (start.col < end.col) start.col..end.col else start.col downTo end.col
        val secondRange = if (start.row < end.row) start.row..end.row else start.row downTo end.row

        line = if (firstRange.count() == 1) secondRange.map { Coordinate(col = firstRange.first, row = it) }
        else if (secondRange.count() == 1) firstRange.map { Coordinate(col = it, row = secondRange.first) }
        else firstRange.zip(secondRange).map { Coordinate(col = it.first, row = it.second) }
    }
}

fun debug(lines: List<Line>) {
    val points = lines.flatMap { it.line }.groupBy { it }
    for (row in points.minOf { it.key.row }..points.maxOf { it.key.row }) {
        for (col in points.minOf { it.key.col }..points.maxOf { it.key.col }) {
            print(points[Coordinate(col = col, row = row)]?.count() ?: ".")
        }
        println()
    }
}