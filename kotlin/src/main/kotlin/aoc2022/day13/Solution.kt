package aoc2022.day13

import Solver
abstract class Packet() {
    companion object {
        fun parse(inp: MutableList<Char>): Packet? {
            val c = inp.removeFirst()
            return when(c) {
                '[' -> {
                    var nextEl = parse(inp)
                    val elements = emptyList<Packet>().toMutableList()
                    while (nextEl != null) {
                        elements.add(nextEl)
                        nextEl = parse(inp)
                    }
                    ListPacket(elements)
                }
                ']' -> {
                    if (inp.firstOrNull() == ',')
                        inp.removeFirst()
                    null
                }
                else -> {
                    var acc = c.toString()
                    while (inp.first().isDigit()) {
                        acc += inp.removeFirst();
                    }
                    if (inp.first() == ',')
                        inp.removeFirst()
                    IntPacket(acc.toInt())
                }
            }
        }

        fun parse(inp: String): Packet? = parse(inp.toMutableList())
    }

    abstract fun correctOrder(right: Packet): Boolean?
}

data class ListPacket(val list: List<Packet>) : Packet() {
    override fun correctOrder(right: Packet): Boolean? {
        if (right is ListPacket) {
            for (i in 0 until list.size) {
                if (right.list.size <= i) {
                    return false
                }
                val res = list[i].correctOrder(right.list[i])
                if (res != null) {
                    return res
                }
            }
            if (list.size < right.list.size) {
                return true
            }
            return null
        }
        return correctOrder(ListPacket(listOf(right)))
    }

}
data class IntPacket(val int: Int) : Packet() {
    override fun correctOrder(right: Packet): Boolean? {
        if (right is IntPacket) {
            if (int < right.int)
                return true
            else if (int > right.int)
                return false
            return null
        }
        return ListPacket(listOf(this)).correctOrder(right)
    }

}

object Day13Solver : Solver(2022, 13) {
    override fun solvePartOne(input: String): Int {
        val pairs: List<Pair<Int, Pair<Packet, Packet>>> = input.split("\n\n").mapIndexed { i, s ->
            val l = s.lines()
            Pair(i + 1, Pair(Packet.parse(l[0])!!, Packet.parse(l[1])!!))
        }

        return pairs.filter { it.second.first.correctOrder(it.second.second) ?: false }
            .sumOf { it.first }
    }

    override fun solvePartTwo(input: List<String>): Int {
        val packets = input.filter { !it.isEmpty() }.map { Packet.parse(it)!! }.toMutableList()
        val p1 = ListPacket(listOf(ListPacket(listOf(IntPacket(2)))))
        val p2 = ListPacket(listOf(ListPacket(listOf(IntPacket(6)))))
        packets.add(p1)
        packets.add(p2)
        val sorted = packets.sortedWith { a, b ->
            val res = a.correctOrder(b)
            if (res == null)
                0
            else if (res)
                -1
            else
                1
        }
        return (sorted.indexOf(p1) + 1) * (sorted.indexOf(p2) + 1)
    }
}

fun main() {
    Day13Solver.runPartOneTest()
    Day13Solver.runPartTwoTest()

    Day13Solver.solve()
}
