package aoc2021.day04

import readInputAsLines

val year = 2021
val day = 4

fun main() {

    fun checkDone(board: List<List<Int>>, taken: List<Int>): Boolean {
        for (c in 0 until board[0].size) {
            var done = true
            for (r in 0 until board.size) {
                if (!taken.contains(board[r][c])) {
                    done = false
                    break
                }
            }
            if (done) return true
        }
        for (r in 0 until board.size) {
            var done = true
            for (c in 0 until board[0].size) {
                if (!taken.contains(board[r][c])) {
                    done = false
                    break
                }
            }
            if (done) return true
        }
        return false;
    }

    fun calcDone(board: List<List<Int>>, taken: List<Int>): Int {
        var s = 0
        for (r in 0 until board.size) {
            for (c in 0 until board[0].size) {
                if (!taken.contains(board[r][c])) {
                    s += board[r][c]
                }
            }
        }
        return s
    }

    fun part1(input: List<String>): Int {
        val numbers = input.first().split(',')
        val textBoards = input.joinToString("\n").split("\n\n").drop(1)
        val boards = textBoards.map { it.split('\n').map { it.split(" +".toRegex()).filter { it.isNotEmpty() }.map { it.toInt() } } }
        val taken = listOf<Int>().toMutableList()
        for (num in numbers) {
            taken.add(num.toInt())
            for (b in boards) {
                if (checkDone(b, taken)) {
                    return num.toInt() * calcDone(b, taken)
                }
            }
        }
        return -1
    }



    fun part2(input: List<String>): Int {
        var done = 0
        val numbers = input.first().split(',')
        val textBoards = input.joinToString("\n").split("\n\n").drop(1)
        val boards = textBoards.map { it.split('\n').map { it.split(" +".toRegex()).filter { it.isNotEmpty() }.map { it.toInt() } } }.toMutableList()
        val target = boards.size
        val taken = listOf<Int>().toMutableList()
        for (num in numbers) {
            taken.add(num.toInt())
            val doneBoards = listOf<List<List<Int>>>().toMutableList()
            for (b in boards) {
                if (checkDone(b, taken)) {
                    done++
                    doneBoards.add(b)
                }
                if (done == target) {
                    return num.toInt() * calcDone(b, taken)
                }
            }
            boards.removeAll(doneBoards)
        }
        return -1
    }

    val testInput = readInputAsLines(year, day, Input.Test)
    check(part1(testInput) == 4512)
    println(part2(testInput))
    check(part2(testInput) == 1924)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
