fun main() {

    fun part1(matrix: List<IntArray>): Int {
        val majority = matrix.transpose().map { mostCommon(it) }
        val gamma = majority.joinToString("")
        val epsilon = majority.map { it.flipBit() }.joinToString("")

        return gamma.toInt(2) * epsilon.toInt(2);
    }

    fun part2(matrix: List<IntArray>): Int {
        val gamma = mutableListOf<Int>()
        val epsilon = mutableListOf<Int>()
        for (col in matrix.transpose().indices) {
            val filterGamma = matrix.filter { it.take(gamma.count()) == gamma }
            val filterEpsilon = matrix.filter { it.take(epsilon.count()) == epsilon }

            if (filterGamma.count() == 1) gamma.addAll(filterGamma.first().drop(gamma.count()))
            else gamma.add(mostCommon(matrix.filter { it.take(gamma.count()) == gamma }.transpose()[col]))

            if (filterEpsilon.count() == 1) epsilon.addAll(filterEpsilon.first().drop(epsilon.count()))
            else epsilon.add(mostCommon(matrix.filter { it.take(epsilon.count()) == epsilon }
                .transpose()[col]).flipBit())
        }

        return gamma.joinToString("").toInt(2) * epsilon.joinToString("").toInt(2)
    }

    val input = readInput("Day03")
    val matrix = toMatrix(input)
    println(part1(matrix))
    println(part2(matrix))
}

fun toMatrix(input: List<String>): List<IntArray> {
    return input.map { it.chunked(1).map { cell -> cell.toInt() }.toIntArray() }
}

fun mostCommon(column: IntArray): Int {
    return if (2 * column.sum() >= column.count()) 1 else 0
}