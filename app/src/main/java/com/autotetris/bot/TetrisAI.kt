
package com.autotetris.bot

data class Board(
    val cells: Array<IntArray>
) {
    val width = 10
    val height = 20
}

data class Piece(
    val rotations: List<List<Pair<Int, Int>>>
)

data class Move(
    val x: Int,
    val rotation: Int,
    val score: Double
)

object TetrisAI {

    private const val WIDTH = 10
    private const val HEIGHT = 20

    fun bestMove(
        board: Board,
        piece: Piece
    ): Move? {

        var best: Move? = null

        for (r in piece.rotations.indices) {
            val shape = piece.rotations[r]

            val maxX = shape.maxOf { it.first }

            for (x in 0..(WIDTH - maxX - 1)) {

                val simulated = board.cells.map {
                    it.clone()
                }.toTypedArray()

                var y = 0

                while (canPlace(simulated, shape, x, y + 1)) {
                    y++
                }

                if (!canPlace(simulated, shape, x, y)) {
                    continue
                }

                for ((px, py) in shape) {
                    simulated[y + py][x + px] = 1
                }

                clearLines(simulated)

                val score = evaluate(simulated)

                if (best == null || score > best.score) {
                    best = Move(x, r, score)
                }
            }
        }

        return best
    }

    private fun canPlace(
        board: Array<IntArray>,
        shape: List<Pair<Int, Int>>,
        x: Int,
        y: Int
    ): Boolean {
        return shape.all { (px, py) ->
            val bx = x + px
            val by = y + py

            bx in 0 until WIDTH &&
            by in 0 until HEIGHT &&
            board[by][bx] == 0
        }
    }

    private fun clearLines(
        board: Array<IntArray>
    ) {
        val remaining = board.filter {
            row -> row.any { it == 0 }
        }

        val cleared = HEIGHT - remaining.size

        for (i in 0 until cleared) {
            board[i] = IntArray(WIDTH)
        }

        for (i in remaining.indices) {
            board[cleared + i] = remaining[i].clone()
        }
    }

    private fun evaluate(
        board: Array<IntArray>
    ): Double {

        var holes = 0
        var aggregateHeight = 0
        var bumpiness = 0
        val heights = IntArray(WIDTH)

        for (x in 0 until WIDTH) {
            var found = false

            for (y in 0 until HEIGHT) {
                if (board[y][x] != 0) {
                    if (!found) {
                        heights[x] = HEIGHT - y
                        found = true
                    }
                } else if (found) {
                    holes++
                }
            }

            aggregateHeight += heights[x]
        }

        for (x in 0 until WIDTH - 1) {
            bumpiness += kotlin.math.abs(
                heights[x] - heights[x + 1]
            )
        }

        return -0.51066 * aggregateHeight -
            0.35663 * holes -
            0.18448 * bumpiness
    }
}
