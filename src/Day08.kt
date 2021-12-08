fun main() {

    fun part1(input: List<List<String>>): Int {
        val sizeOfInterest = listOf(2, 3, 4, 7)
        return input.flatten().count { sizeOfInterest.contains(it.count()) }
    }

    fun part2(wires: List<Wiring>): Int {
        var sum = 0
        for (wire in wires) {
            val circuit = Circuit()
            circuit.parseInputWires(wire.input)
            sum += wire.output.map { circuit.calculateDigit(it) }.joinToString("").toInt()
        }
        return sum
    }

    val input = readInput("Day08")
    val inputWires = input.map { it.split(" | ")[0] }.map { it.split(" ").map { it.toCharArray() } }
    val outputWires = input.map { it.split(" | ")[1] }.map { it.split(" ") }
    val wiring = inputWires.zip(outputWires).map{Wiring(it.first, it.second)}
    println(part1(outputWires))
    println(part2(wiring))
}

data class Wiring(val input: List<CharArray>, val output: List<String>)

/*

 0000
1    2
1    2
 3333
4    5
4    5
 6666

*/
class DigitSegment(val digit: Int, val segments: Set<Int>)

class Circuit {
    var archetype = List(7) { ('a'..'g').toList() } // A representation of the segment wiring

    private val digits = listOf(
        DigitSegment(0, setOf(0, 1, 2, 4, 5, 6)),
        DigitSegment(1, setOf(2, 5)),
        DigitSegment(2, setOf(0, 2, 3, 4, 6)),
        DigitSegment(3, setOf(0, 2, 3, 5, 6)),
        DigitSegment(4, setOf(1, 2, 3, 5)),
        DigitSegment(5, setOf(0, 1, 3, 5, 6)),
        DigitSegment(6, setOf(0, 1, 3, 4, 5, 6)),
        DigitSegment(7, setOf(0, 2, 5)),
        DigitSegment(8, setOf(0, 1, 2, 3, 4, 5, 6)),
        DigitSegment(9, setOf(0, 1, 2, 3, 5, 6))
    )

    fun parseInputWires(wires: List<CharArray>) {
        archetype = wires.fold(archetype) { model, wire ->
            // The possible segments these wires could be connected to
            val possibleSegments =
                digits.filter { it.segments.count() == wire.count() }.map { it.segments }
                    .reduce { commonSegments, digitSegment -> commonSegments.intersect(digitSegment) }

            // We remove the letters that are not in the corresponding segments
            model.mapIndexed { segment, possibleWires -> if (possibleSegments.contains(segment)) possibleWires.filter { wire.contains(it) } else possibleWires }
        }

        // There are still segments with multiple possible wires, loop until wire is mapped 1:1 with segment
        while (archetype.flatten().count() != 7) {
            val finishedWires = archetype.filter { it.count() == 1 }.flatten();
            archetype = archetype.map { model -> if (model.count() != 1) model.filter { !finishedWires.contains(it) } else model }
        }
    }

    fun calculateDigit(input: String): Int {
        val segments = input.map{archetype.flatten().indexOf(it)}.toSet()
        return digits.find{it.segments == segments}?.digit?:-1
    }
}