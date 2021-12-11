import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Transpose a Matrix
 */
fun List<IntArray>.transpose(): List<IntArray> {
    val rows = this.indices
    val cols = this.first().indices
    val transposed: List<IntArray> = List(cols.count()) { IntArray(rows.count()) }
    for (row in rows) {
        for (col in cols) {
            transposed[col][row] = this[row][col]
        }
    }
    return transposed
}

/**
 * Flip a binary Int
 */
fun Int.flipBit(): Int = if (this == 1) 0 else 1

/**
 * Get triangular number (1+2+3...) for an Int
 */
fun Int.triangular(): Int = if (this == 0) 0 else (this downTo 0).reduce { sum, number -> sum + number }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)


data class Coordinate(val row: Int, val col: Int)

class Matrix(matrix: List<IntArray>) {
    val cells: LinkedHashMap<Coordinate, Int> = LinkedHashMap()

    init {
        for (row in matrix.indices) {
            for (col in matrix.transpose().indices) {
                cells[Coordinate(row, col)] = matrix[row][col]
            }
        }
    }

    fun getValue(coordinate: Coordinate): Int {
        return cells[coordinate] ?: Int.MAX_VALUE
    }

    fun setValue(coordinate: Coordinate, newValue: Int) {
        cells[coordinate] = newValue
    }

    fun incrementCell(coordinate: Coordinate) {
        cells[coordinate] = cells[coordinate]!! + 1
    }

    fun getAdjacentCells(coordinate: Coordinate, noDiagonals: Boolean = false): Map<Coordinate, Int> {
        return this.getAdjacentCoordinates(coordinate, noDiagonals).associateWith { cells[it] ?: -1 }
    }

    fun getAdjacentCoordinates(coordinate: Coordinate, noDiagonals: Boolean = false): List<Coordinate> {
        return listOf(
            Coordinate(coordinate.row - 1, coordinate.col - 1),
            Coordinate(coordinate.row - 1, coordinate.col),
            Coordinate(coordinate.row - 1, coordinate.col + 1),

            Coordinate(coordinate.row, coordinate.col - 1),
            Coordinate(coordinate.row, coordinate.col + 1),

            Coordinate(coordinate.row + 1, coordinate.col - 1),
            Coordinate(coordinate.row + 1, coordinate.col),
            Coordinate(coordinate.row + 1, coordinate.col + 1),
        )
            .filterIndexed { index, _ ->
                if (noDiagonals) listOf(1, 3, 4, 6).contains(index) else true
            }.filter { cell ->
                cell.row >= 0 &&
                        cell.col >= 0 &&
                        cell.row <= cells.maxOfOrNull { it.key.row }!! &&
                        cell.col <= cells.maxOfOrNull { it.key.col }!!
            }
    }
}