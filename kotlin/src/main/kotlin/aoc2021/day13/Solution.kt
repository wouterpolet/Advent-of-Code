package aoc2021.day13

import readInputAsLines

val year = 2021
val day = 13

fun main() {
    fun printPaper(paper: List<List<Boolean>>) {
        paper.forEach {
            it.forEach { print(if (it) "█" else " ") }
            println()
        }
        println()
    }

    fun part1(input: List<String>): Int {
        val dots = input.takeWhile { it.isNotBlank() }.map { it.split(',').map { it.toInt() } }
        val foldings = input.reversed().takeWhile { it.isNotBlank() }.map { it.split(" ").last().split("=") }.map { (a, b) -> Pair(a, b.toInt()) }.reversed()
        val paper = List(dots.maxOf { it.last() } + 1) { List(dots.maxOf { it.first() } + 1) { false }.toMutableList() }.toMutableList()
        dots.forEach { (x, y) -> paper[y][x] = true }

        var xSize = paper.first().size
        var ySize = paper.size

        val (dir, coord) = foldings.first()
        if (dir == "x") {
            for (y in 0 until ySize) {
                for (x in 0 until coord) {
                    paper[y][x] = paper[y][x] || paper[y][xSize - x - 1]
                }
            }
            xSize /= 2
        } else {
            for (y in 0 until coord) {
                for (x in 0 until xSize) {
                    paper[y][x] = paper[y][x] || paper[ySize - y - 1][x]
                }
            }
            ySize /= 2
        }

        var sum = 0
        for (y in 0 until ySize) {
            for (x in 0 until xSize) {
                if (paper[y][x]) {
                    sum++
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val dots = input.takeWhile { it.isNotBlank() }.map { it.split(',').map { it.toInt() } }
        val foldings = input.reversed().takeWhile { it.isNotBlank() }.map { it.split(" ").last().split("=") }.map { (a, b) -> Pair(a, b.toInt()) }.reversed()
        var paper = List(dots.maxOf { it.last() } + 1) { List(dots.maxOf { it.first() } + 1) { false }.toMutableList() }
        dots.forEach { (x, y) -> paper[y][x] = true }

        for ((dir, coord) in foldings) {
            if (dir == "x") {
                val xSize = coord
                val ySize = paper.size
                val newPaper = List(ySize) { List(xSize) { false }.toMutableList() }
                for (y in 0 until ySize) {
                    for (x in 0 until xSize) {
                        newPaper[y][x] = paper[y][x] || (2 * coord - x < paper[0].size && paper[y][2 * coord - x])
                    }
                }
                paper = newPaper
            } else {
                val xSize = paper[0].size
                val ySize = coord
                val newPaper = List(ySize) { List(xSize) { false }.toMutableList() }
                for (y in 0 until ySize) {
                    for (x in 0 until xSize) {
                        newPaper[y][x] = paper[y][x] || (2 * coord - y < paper.size && paper[2 * coord - y][x])
                    }
                }
                paper = newPaper
            }
        }

        printPaper(paper)
        return -1
    }

    val testInput = readInputAsLines(year, day, Input.Test)
    check(part1(testInput) == 17)
//    check(part2(testInput) == 1)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
