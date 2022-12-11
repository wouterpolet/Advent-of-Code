package aoc2015.day07

import readInputAsLines
import java.lang.NumberFormatException

val year = 2015
val day = 7

fun main() {
    fun part1(input: List<String>): Int {
        val map: MutableMap<String, List<String>> = HashMap()
        val known: MutableMap<String, Int> = HashMap()

        for (line in input) {
            val (value, res) = line.split(" -> ")
            val operands = value.split(' ')
            if (operands.size > 2) {
                val (e1, op, e2) = operands
                map[res] = listOf(op, e1, e2)
            } else if (operands.size > 1) {
                map[res] = operands + ""
            } else {
                map[res] = operands + "" + ""
            }
        }

        fun findBoi(value: String): Int {
            if (known.containsKey(value)) {
                return known[value]!!
            }
            try {
                known[value] = value.toInt()
                return value.toInt()
            } catch (e: NumberFormatException) {

            }
            val (op, e1, e2) = map[value]!!
            if (e1 == "") {
                known[value] = findBoi(op)
                return known[value]!!
            }
            known[value] = when (op) {
                "NOT" -> findBoi(e1).inv()
                "OR" -> findBoi(e1) or findBoi(e2)
                "AND" -> findBoi(e1) and findBoi(e2)
                "LSHIFT" -> findBoi(e1) shl e2.toInt()
                "RSHIFT" -> findBoi(e1) shr e2.toInt()
                else -> -1
            }
            return known[value]!!
        }

        return findBoi("a")
    }

    fun part2(input: List<String>): Int {
        val map: MutableMap<String, List<String>> = HashMap()
        val known: MutableMap<String, Int> = HashMap()

        for (line in input) {
            val (value, res) = line.split(" -> ")
            val operands = value.split(' ')
            if (operands.size > 2) {
                val (e1, op, e2) = operands
                map[res] = listOf(op, e1, e2)
            } else if (operands.size > 1) {
                map[res] = operands + ""
            } else {
                map[res] = operands + "" + ""
            }
        }

        map["b"] = listOf("3176", "", "")

        fun findBoi(value: String): Int {
            if (known.containsKey(value)) {
                return known[value]!!
            }
            try {
                known[value] = value.toInt()
                return value.toInt()
            } catch (e: NumberFormatException) {

            }
            val (op, e1, e2) = map[value]!!
            if (e1 == "") {
                known[value] = findBoi(op)
                return known[value]!!
            }
            known[value] = when (op) {
                "NOT" -> findBoi(e1).inv()
                "OR" -> findBoi(e1) or findBoi(e2)
                "AND" -> findBoi(e1) and findBoi(e2)
                "LSHIFT" -> findBoi(e1) shl e2.toInt()
                "RSHIFT" -> findBoi(e1) shr e2.toInt()
                else -> -1
            }
            return known[value]!!
        }

        return findBoi("a")
    }

//    val testInput = readInput(year, day, Input.Test)
//    check(part1(testInput) == 1)
//    check(part2(testInput) == 1)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
