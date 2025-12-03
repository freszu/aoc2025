fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line -> line.map { it.digitToInt() } }.sumOf { bank ->
            val bankMax = bank.max()
            val maxIndex = bank.indexOf(bankMax)
            val splitBank = bank.drop(maxIndex + 1)

            if (splitBank.isEmpty()) {
                val newMax = (bank.dropLast(1)).max()
                newMax * 10 + bankMax
            } else {
                val splitBankMax = splitBank.max()
                bankMax * 10 + splitBankMax
            }.alsoPrintln()
        }
    }

    fun maxJoltage(bank: List<Int>, n: Int): List<Int> {
        if (n == 0) return emptyList()
        val best = bank.dropLast(n - 1).max()
        val bestIndex = bank.indexOf(best)
        val newBank = bank.drop(bestIndex + 1)
        return listOf(best) + maxJoltage(newBank, n - 1)
    }

    fun part2(input: List<String>): Long {
        return input.map { line -> line.map { it.digitToInt() } }
            .sumOf { bank ->
                maxJoltage(bank, 12).fold(0L) { acc, i -> acc * 10 + i }
            }
    }

    val testInput = readInput("Day3_test")
    check(part1(testInput) == 357)
    check(part2(testInput).alsoPrintln() == 3121910778619L)

    val input = readInput("Day3")
    part1(input).println()
    part2(input).println()
}
