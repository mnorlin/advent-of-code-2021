fun main() {

    fun part1(boards: List<Board>, drawn: List<Int>): Int? {
        for (number in drawn.withIndex()) {
            boards.forEach { it.mark(number.value) }
            val winnerBoards = boards.filter { it.isWinner() }
            if (winnerBoards.isNotEmpty()) {
                return winnerBoards.first().getUnmarked().sum() * number.value
            }
        }
        return null
    }

    fun part2(boards: List<Board>, drawn: List<Int>): Int? {
        for (number in drawn.withIndex()) {
            boards.forEach { it.mark(number.value) }
            val loserBoards = boards.filter { !it.isWinner() }
            if (loserBoards.count() == 1) {
                return part1(loserBoards, drawn.drop(number.index + 1))
            }
        }
        return null
    }

    val input = readInput("Day04")

    val drawn = input[0].split(",").map { it.toInt() }
    val boards = input.drop(2).windowed(5, 6).map { matrices ->
        matrices.map { row ->
            row.trim().split("\\s+".toRegex()).map { cell -> cell.toInt() }
        }
    }.map { Board(it) }

    println(part1(boards, drawn))
    boards.forEach{it.clear()}
    println(part2(boards, drawn))
}

class Board(private val board: List<List<Int>>) {
    private val marked = mutableListOf<Int>()
    private val markedCoordinates = mutableListOf<Coordinate>()

    fun mark(number: Int) {
        if (isWinner()) return
        val row = board.indexOfFirst { it.contains(number) }
        if (row < 0) return

        marked.add(number)
        val col = board[row].indexOf(number)
        markedCoordinates.add(Coordinate(row, col))
    }

    fun isWinner(): Boolean {
        val horizontalWin = markedCoordinates.groupBy { it.row }.filter { (_, b) -> b.count() == 5 }.isNotEmpty()
        val verticalWin = markedCoordinates.groupBy { it.col }.filter { (_, b) -> b.count() == 5 }.isNotEmpty()

        return horizontalWin or verticalWin
    }

    fun getUnmarked(): IntArray {
        return board.flatMap { it.toList() }.filter { !marked.contains(it) }.toIntArray()
    }

    fun clear() {
        marked.clear()
        markedCoordinates.clear()
    }
}