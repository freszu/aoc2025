fun main() {

    fun parse(input: String): Pair<List<LongRange>, Set<Long>> {
        val (rangesRaw, availableRaw) = input.split("\n\n")

        val ranges = rangesRaw.lines().map {
            val (start, end) = it.split("-")
            start.toLong()..end.toLong()
        }
        val available = availableRaw.lines().map { it.toLong() }.toSet()

        return ranges to available
    }

    fun part1(input: String): Int {
        val (ranges, available) = parse(input)

        return available.count {
            ranges.any { range -> it in range }
        }
    }

    fun LongRange.overlaps(other: LongRange): Boolean =
        first in other || last in other || other.first in this || other.last in this

    fun LongRange.union(other: LongRange): LongRange = minOf(first, other.first)..maxOf(last, other.last)

    fun part2(input: String): Long {
        val (ranges, _) = parse(input)

        return ranges.sortedBy { it.first }.fold(emptyList<LongRange>()) { acc, next ->
            val previous = acc.lastOrNull() ?: return@fold listOf(next)

            if (previous.overlaps(next)) {
                acc.dropLast(1).plusElement(previous.union(next))
            } else {
                acc.plusElement(next)
            }
        }
            .sumOf { it.last + 1 - it.first }
    }

    val testInput = readInputAsString("Day5_test").trim()
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

    val input = readInputAsString("Day5").trim()
    part1(input).println()
    part2(input).println()
}
