fun main() {

    fun part1(matrix: Matrix): Int {
        var flashes = 0
        repeat(100){
            matrix.cells.forEach { matrix.incrementCell(it.key) }
            while(matrix.cells.filter {it.value > 9}.isNotEmpty()) {
                val flashing = matrix.cells.filter{it.value > 9}
                flashing.forEach{ matrix.setValue(it.key, 0) }
                flashing.flatMap { matrix.getAdjacentCoordinates(it.key) }.filter { matrix.getValue(it) > 0 }.forEach{matrix.incrementCell(it)}
                flashes += flashing.count()
            }
        }
        return flashes
    }

    fun part2(matrix: Matrix): Int {
        repeat(1000) { step ->
            matrix.cells.forEach { matrix.incrementCell(it.key) }
            while(matrix.cells.filter {it.value > 9}.isNotEmpty()) {
                val goingToFlash = matrix.cells.filter{it.value > 9}
                goingToFlash.forEach{ matrix.setValue(it.key, 0) }
                goingToFlash.flatMap { matrix.getAdjacentCoordinates(it.key) }.filter { matrix.getValue(it) > 0 }.forEach{matrix.incrementCell(it)}
                if (matrix.cells.count() == matrix.cells.filter { it.value == 0 }.count()) return step + 1
            }
        }
        return -1
    }

    val input = readInput("Day11").map { row ->
        row.chunked(1).map { cell -> cell.toInt() }
    }.map { it.toIntArray() }
    println(part1(Matrix(input)))
    println(part2(Matrix(input)))
}