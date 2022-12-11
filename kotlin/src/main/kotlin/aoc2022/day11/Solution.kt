package aoc2022.day11

import Solver
import java.lang.Integer.parseInt
import java.lang.Long.parseLong
import java.lang.NumberFormatException

val year = 2022
val day = 11

data class Monkey(
    val id: Int,
    var inspect: Int,
    val items: MutableList<Long>,
    val op: (Long) -> Long,
    val divBy: Long,
    val monkeyT: Int,
    val monkeyF: Int
) {
    companion object {
        fun parse(inp: String): Monkey {
            val lines = inp.lines().iterator()
            val id = parseInt(lines.next().split(" ")[1].removeSuffix(":"))
            val items = lines.next().split(": ")[1].split(", ").map { parseLong(it) }.toMutableList()
            val exp = lines.next().split(" = ")[1].split(" ")
            val op: (Long) -> Long = when (exp[1]) {
                "*" -> { { try { parseLong(exp[0]) } catch (e: NumberFormatException) { it } * try { parseLong(exp[2]) } catch (e: NumberFormatException) { it } } }
                "+" -> { { try { parseLong(exp[0]) } catch (e: NumberFormatException) { it } + try { parseLong(exp[2]) } catch (e: NumberFormatException) { it } } }
                else -> throw Exception()
            }
            val divBy = parseLong(lines.next().split(" ").last())
            val monkeyT = parseInt(lines.next().split(" ").last())
            val monkeyF = parseInt(lines.next().split(" ").last())
            return Monkey(
                id,
                0,
                items,
                op,
                divBy,
                monkeyT,
                monkeyF
            )
        }
    }

    fun round(monkeys: List<Monkey>) {
        for (item in items) {
            inspect++
            var newVal: Long = op(item) / 3
            monkeys.get(if(newVal % divBy == 0L) monkeyT else monkeyF).items.add(newVal)
        }
        items.clear()
    }

    fun roundTwo(monkeys: List<Monkey>) {
        for (item in items) {
            inspect++
            var newVal = op(item)
            newVal %= monkeys.map { it.divBy }.reduce { acc, l -> acc * l }
            monkeys.get(if(newVal % divBy == 0L) monkeyT else monkeyF).items.add(newVal)
        }
        items.clear()
    }
}

object Day11Solver : Solver(2022, 11) {
    override fun solvePartOne(input: String): Int {
        val monkeys = input.split("\n\n").map { Monkey.parse(it) }

        for (_it in 1..20) {
            monkeys.forEach { it.round(monkeys) }
        }

        return monkeys.map { it.inspect }.sortedDescending().take(2).reduce { acc, i -> acc * i }
    }

    override fun solvePartTwoLong(input: String): Long {
        val monkeys = input.split("\n\n").map { Monkey.parse(it) }

        for (_it in 1..10000) {
            monkeys.forEach { it.roundTwo(monkeys) }
        }

        val res = monkeys.map { it.inspect }.sortedDescending().take(2)
        return res[0].toLong() * res[1].toLong()
    }
}

fun main() {
    Day11Solver.solve()
}
