package aoc2021.day10

import readInput
import java.util.*

val year = 2021
val day = 10

fun main() {

    val scoreMap = mapOf(Pair(')', 3), Pair(']', 57), Pair('}', 1197), Pair('>', 25137))
    val scoreClosingMap = mapOf(Pair(')', 1), Pair(']', 2), Pair('}', 3), Pair('>', 4))
    val opening = setOf('(', '[', '{', '<')
    val closing = setOf(')', ']', '}', '>')
    val openCloseMap = mapOf(Pair('(', ')'), Pair('[', ']'), Pair('{', '}'), Pair('<', '>'))

    fun part1(input: List<String>): Int {
//        val corrupted = input.filter {
//            val s1 = it.filter {
//                opening.contains(it)
//            }.length
//            val s2 = it.filter {
//                closing.contains(it)
//            }.length
//            s1 == s2
//        }
        return input.map {
            val s = LinkedList<Char>()
            var score = 0
            it.forEach {
                if (opening.contains(it)) {
                    s.push(it)
                } else if (closing.contains(it)) {
                    if (score == 0 && s.isNotEmpty() && openCloseMap[s.pop()]!! != it) {
                        score = scoreMap[it]!!
                    }
                }
            }
            score
        }.sum()
    }

    fun part2(input: List<String>): Long {
        val scores = input.filter {
            val s = LinkedList<Char>()
            var good = true
            it.forEach {
                if (opening.contains(it)) {
                    s.push(it)
                } else if (closing.contains(it)) {
                    if (good && s.isNotEmpty() && openCloseMap[s.pop()]!! != it) {
                        good = false
                    }
                }
            }
            good
        }.map {
            val s = LinkedList<Char>()
            it.forEach {
                if (opening.contains(it)) {
                    s.push(it)
                } else if (closing.contains(it)) {
                    s.pop()
                }
            }
            var score = 0L
            while (s.isNotEmpty()) {
                score *= 5
                score += scoreClosingMap[openCloseMap[s.pop()]!!]!!
            }
            score
        }.sorted()
        return scores[scores.size / 2]
    }

    val testInput = readInput(year, day, Input.Test)
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
