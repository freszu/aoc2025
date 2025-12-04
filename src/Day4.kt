enum class MapElement {
    SPACE, ROLL
}

fun main() {
    fun parse(input: List<String>): Matrix<MapElement> = input.map { line ->
        line.map {
            when (it) {
                '.' -> MapElement.SPACE
                '@' -> MapElement.ROLL
                else -> error("Invalid char: $it")
            }
        }
    }

    fun part1(input: List<String>): Int {
        val matrix: Matrix<MapElement> = parse(input)

        return matrix.sequence()
            .filter { (_, element) -> element == MapElement.ROLL }
            .map { (position, _) -> matrix.adjacent(position).count { it == MapElement.ROLL } < 4 }
            .count { it }
    }

    tailrec fun removeRec(currentMatrix: Matrix<MapElement>, totalRemovedRollsCount: Int): Int {
        var removedRollsCount = 0
        val nextMatrix = currentMatrix.map2dIndexed { x, y, element ->
            if (element != MapElement.ROLL) return@map2dIndexed element
            val canBeTakenOut = currentMatrix.adjacent(Position(x, y)).count { it == MapElement.ROLL } < 4
            if (canBeTakenOut) {
                removedRollsCount++
                MapElement.SPACE
            } else {
                MapElement.ROLL
            }
        }

        return if (removedRollsCount == 0) {
            totalRemovedRollsCount
        } else {
            removeRec(nextMatrix, totalRemovedRollsCount + removedRollsCount)
        }
    }

    fun part2(input: List<String>): Int {
        val matrix: Matrix<MapElement> = parse(input)

        return removeRec(matrix, 0)
    }

    val testInput = readInput("Day4_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    val input = readInput("Day4")
    part1(input).println()
    part2(input).println()
}
