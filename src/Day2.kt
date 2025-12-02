fun main() {

    fun parse(input: String): List<LongRange> = input.split(',')
        .map {
            val (firstID, lastID) = it.split('-')
            firstID.toLong()..lastID.toLong()
        }

    fun isInvalid(id: Long): Boolean {
        val idStr = id.toString()
        if (idStr.length % 2 == 1) return false

        return (idStr.drop(idStr.length / 2) == idStr.dropLast(idStr.length / 2))
    }

    fun part1(input: String): Long {
        return parse(input)
            .sumOf { range -> range.filter { isInvalid(it) }.sum() }

    }

    fun isInvalidV2(id: Long): Boolean {
        val idStr = id.toString()

        for (i in 1..idStr.length / 2) {
            val p = idStr.take(i).repeat(idStr.length / i)
            if (p == idStr) return true
        }
        return false
    }

    fun part2(input: String): Long {
        return parse(input).sumOf { range ->
            range.filter { isInvalidV2(it) }.sum()
        }
    }

    val testInput = readInputAsString("Day2_test").trim()
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    val input = readInputAsString("Day2").trim()
    part1(input).println()
    part2(input).println()
}
