fun main() {

    fun part1(input: List<String>): Int {
        val (_, zeros) = input.map {
            val dir = it.first()
            val num = it.substring(1).toInt()
            dir to num
        }
            .fold(50 to 0) { (pos, zeroStopsCounter), (dir, move) ->
                val newPos = when (dir) {
                    'R' -> pos + move
                    'L' -> pos - move
                    else -> error("Invalid direction")
                }
                if (newPos % 100 == 0) newPos to zeroStopsCounter + 1 else newPos to zeroStopsCounter
            }
        return zeros
    }

    fun part2(input: List<String>): Int {
        return input.map {
            val dir = it.first()
            val num = it.substring(1).toInt()
            dir to num
        }
            .fold(50 to 0) { (pos, zeroStopsCounter), (dir, move) ->
                val dir = when (dir) {
                    'R' -> 1
                    'L' -> -1
                    else -> error("Invalid direction")
                }
                var pos = pos
                var zeroStopsCounter = zeroStopsCounter
                repeat(move) {
                    pos += dir
                    pos %= 100
                    if (pos == 0) zeroStopsCounter++
                }
                pos to zeroStopsCounter
            }.second
    }

    val testInput = readInput("Day1_test")
    check(part1(testInput) == 3)
    check(part2(testInput).alsoPrintln() == 6)

    val input = readInput("Day1")
    part1(input).println()
    part2(input).println()
}
