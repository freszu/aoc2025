typealias Matrix<T> = List<List<T>>
typealias MutableMatrix<T> = MutableList<MutableList<T>>
typealias XY = Position
data class Position(val x: Int, val y: Int)

enum class Direction8 {
    LEFT, LEFT_UP, UP, RIGHT_UP, RIGHT, RIGHT_DOWN, DOWN, LEFT_DOWN;
}

enum class Direction4 {
    LEFT, UP, RIGHT, DOWN;

    companion object {
        fun toDirection8(direction4: Direction4): Direction8 = when (direction4) {
            LEFT -> Direction8.LEFT
            UP -> Direction8.UP
            RIGHT -> Direction8.RIGHT
            DOWN -> Direction8.DOWN
        }
    }
}


fun <T> Matrix<T>.nicePrint() = joinToString("\n")
operator fun <T> Matrix<T>.get(position: Position): T = this[position.y][position.x]
operator fun <T> MutableMatrix<T>.set(position: Position, value: T) {
    this[position.y][position.x] = value
}

fun <T> Matrix<T>.getOrNull(x: Int, y: Int) = this.getOrNull(y)?.getOrNull(x)
fun <T> Matrix<T>.getOrNull(position: Position) = getOrNull(position.x, position.y)

inline fun <T, R> Matrix<T>.map2d(transform: (T) -> R) = this.map { it.map(transform) }

inline fun <T, R> Matrix<T>.map2dIndexed(transform: (x: Int, y: Int, T) -> R) = mapIndexed { y, rows ->
    rows.mapIndexed { x, t -> transform(x, y, t) }
}

inline fun <T, R> Matrix<T>.map2dNotNull(transform: (T) -> R?): List<List<R>> {
    return map { row -> row.mapNotNull(transform) }
}

fun <T> Matrix<T>.adjacent(position: Position): Sequence<T> {
    return position.adjacent().map { this@adjacent.getOrNull(it) }.filterNotNull()
}

fun Position.adjacent(): Sequence<Position> {
    val (x, y) = this

    return sequenceOf(
        Position(x - 1, y),
        Position(x - 1, y - 1),
        Position(x, y - 1),
        Position(x + 1, y - 1),
        Position(x + 1, y),
        Position(x + 1, y + 1),
        Position(x, y + 1),
        Position(x - 1, y + 1)
    )
}

fun <T> Matrix<T>.sequence(): Sequence<Pair<Position, T>> {
    val matrix = this
    return sequence {
        matrix.forEachIndexed { y, ts ->
            ts.forEachIndexed { x, t ->
                yield(Position(x, y) to t)
            }
        }
    }
}
