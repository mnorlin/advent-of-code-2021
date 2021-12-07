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
fun Int.flipBit(): Int = if(this == 1) 0 else 1

/**
 * Get triangular number for an Int
 */
fun Int.triangular(): Int = if (this == 0) 0 else (this downTo 0).reduce{sum, number -> sum + number}
/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)


data class Coordinate(val row: Int, val col: Int)