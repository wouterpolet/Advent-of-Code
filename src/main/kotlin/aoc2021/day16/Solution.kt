package aoc2021.day16

import readInput

val year = 2021
val day = 16

sealed class Packet(open val version: Int) {
    companion object {
        fun parse(msg: String): Pair<Int, Packet> {
            val type = msg.substring(3..5).toInt(2)
            if (type == 4) {
                return Literal.parse(msg)
            }
            return Operator.parse(msg)
        }
    }

    abstract fun sumVersion(): Int

    abstract fun calculate(): Long
}

data class Literal(override val version: Int, val value: Long): Packet(version) {
    companion object {
        fun parse(msg: String): Pair<Int, Literal> {
            val version = msg.substring(0..2).toInt(2)
            var i = 6
            var bitString = ""
            while (msg[i] != '0') {
                bitString += msg.substring(i+1..i+4)
                i += 5
            }
            bitString += msg.substring(i+1..i+4)
            val value = bitString.toLong(2)
            return Pair(i + 5, Literal(version, value))
        }
    }

    override fun sumVersion(): Int {
        return version
    }

    override fun calculate(): Long {
        return value
    }
}

data class Operator(override val version: Int, val op: Int, val subPackets: List<Packet>): Packet(version) {
    companion object {
        fun parse(msg: String): Pair<Int, Operator> {
            val version = msg.substring(0..2).toInt(2)
            val op =  msg.substring(3..5).toInt(2)
            val lengthType = msg[6].digitToInt()
            val packets = emptyList<Packet>().toMutableList()
            var lengthSum = 0
            if (lengthType == 0) {
                val length = msg.substring(7..7+14).toInt(2)
                while (lengthSum < length) {
                    val (l, p) = Packet.parse(msg.substring(22 + lengthSum))
                    lengthSum += l
                    packets.add(p)
                }
            } else {
                val numPackets = msg.substring(7..7+10).toInt(2)
                for (i in 1..numPackets) {
                    val (l, p) = Packet.parse(msg.substring(18 + lengthSum))
                    lengthSum += l
                    packets.add(p)
                }
            }
            return Pair(22 - lengthType * 4 + lengthSum, Operator(version, op, packets))
        }
    }

    override fun sumVersion(): Int {
        return version + subPackets.map { it.sumVersion() }.sum()
    }

    override fun calculate(): Long {
        val results = subPackets.map(Packet::calculate)
        return when (op) {
            0 -> results.sum()
            1 -> results.reduce(Long::times)
            2 -> results.minOrNull()!!
            3 -> results.maxOrNull()!!
            5 -> if (results[0] > results[1]) 1L else 0L
            6 -> if (results[0] < results[1]) 1L else 0L
            7 -> if (results[0] == results[1]) 1L else 0L
            else -> throw RuntimeException()
        }
    }
}

fun hexToString(h: Char): String {
    val binary = h.digitToInt(16).toString(2).toMutableList()
    while (binary.size < 4) {
        binary.add(0, '0')
    }
    return binary.joinToString("")
}

fun main() {
    fun part1(input: List<String>): Int {
        val hex = input.first()
        val binary =
            hex.map { hexToString(it) }.joinToString("")
        val (_, packet) = Packet.parse(binary)
        return packet.sumVersion()
    }

    fun part2(input: List<String>): Long {
        val hex = input.first()
        val binary =
            hex.map { hexToString(it) }.joinToString("")
        val (_, packet) = Packet.parse(binary)
        return packet.calculate()
    }

    val testInput = readInput(year, day, Input.Test)
//    check(part1(testInput) == 16)
//    check(part2(testInput) == 1)

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
