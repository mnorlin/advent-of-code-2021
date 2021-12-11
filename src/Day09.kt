fun main() {

    fun traverseWell(matrix: Matrix, coordinate: Coordinate): List<Coordinate> {
        val toTraverse = matrix.getAdjacentCells(coordinate, true).filter { it.value < 9 }
        toTraverse.forEach { matrix.setValue(it.key, Int.MAX_VALUE) }
        if (toTraverse.isEmpty()) {
            return listOf(coordinate)
        }
        return listOf(toTraverse.flatMap { traverseWell(matrix, it.key) }, listOf(coordinate)).flatten()
    }

    fun part1(matrix: Matrix): Int {
        return matrix.cells.filter {
            (matrix.getAdjacentCells(it.key, true).minOfOrNull { cell -> cell.value } ?: Int.MAX_VALUE) > it.value
        }.map { it.value }.sumOf { it + 1 }
    }

    fun part2(matrix: Matrix): Int {
        val minimums = matrix.cells.filter {
            (matrix.getAdjacentCells(it.key, true).minOfOrNull { cell -> cell.value } ?: Int.MAX_VALUE) > it.value
        }.map { it.key }

        val test = minimums.map { traverseWell(matrix, it) }

        return test.map { it.count() }.sorted().takeLast(3).map { it - 1 }.reduce { prod, value -> prod * value }
    }

    val input = readInput("Day09t").map { row ->
        row.chunked(1).map { cell -> cell.toInt() }
    }.map { it.toIntArray() }

    println(part1(Matrix(input)))
    println(part2(Matrix(input)))
}