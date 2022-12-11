package aoc2021.day18

import combinations
import readInputAsLines

val year = 2021
val day = 18

sealed class SnailFish(var parent: SnailPair?) {
    companion object {
        fun parse(str: String): SnailFish = parse(str, null)

        fun parse(str: String, parent: SnailPair?): SnailFish =
            if (str.first() == '[') SnailPair.parse(str, parent) else SnailNumber.parse(str, parent)
    }

    fun reduce() {
        while (this.reduceExplode() || this.reduceSplit()) continue
    }

    fun depth(): Int = 1 + (parent?.depth() ?: -1)

    abstract fun magnitude(): Int

    abstract fun reduceSplit(): Boolean
    abstract fun reduceExplode(): Boolean

    abstract fun rightMost(): SnailNumber
    abstract fun leftMost(): SnailNumber

    fun copy() = copy(null)
    abstract fun copy(parent: SnailPair?): SnailFish
}

class SnailPair(parent: SnailPair?, var left: SnailFish, var right: SnailFish) : SnailFish(parent) {
    companion object {
        fun parse(str: String, parent: SnailPair?): SnailPair {
            var count = 0
            var comma = -1
            str.forEachIndexed { index, c ->
                when (c) {
                    '[' -> count++
                    ']' -> count--
                    ',' -> if (count == 1) comma = index
                }
            }
            val result = SnailPair(parent, SnailNumber(null, 0), SnailNumber(null, 0))
            result.left = SnailFish.parse(str.substring(1 until comma), result)
            result.right = SnailFish.parse(str.substring(comma + 1 until str.length - 1), result)
            return result
        }
    }

    override fun reduceExplode(): Boolean {
        if (depth() >= 4) {
            val leftNum = (left as SnailNumber).value
            val rightNum = (right as SnailNumber).value

            val left = parent?.oneToLeft(this)
            val right = parent?.oneToRight(this)

            left?.value = left?.value!! + leftNum
            right?.value = right?.value!! + rightNum

            if (parent?.left === this) {
                parent!!.left = SnailNumber(parent, 0)
            } else {
                parent?.right = SnailNumber(parent, 0)
            }
            return true
        }
        return left.reduceExplode() || right.reduceExplode()
    }

    override fun reduceSplit(): Boolean = left.reduceSplit() || right.reduceSplit()

    fun oneToLeft(from: SnailFish): SnailNumber? {
        if (left !== from) {
            return left.rightMost()
        } else {
            return parent?.oneToLeft(this)
        }
    }

    fun oneToRight(from: SnailFish): SnailNumber? {
        if (right !== from) {
            return right.leftMost()
        } else {
            return parent?.oneToRight(this)
        }
    }

    override fun magnitude() = 3 * left.magnitude() + 2 * right.magnitude()

    override fun leftMost() = left.leftMost()

    override fun rightMost() = right.rightMost()

    override fun equals(other: Any?) =
        (this === other) || ((other is SnailPair) && left == other.left && right == other.right)

    override fun toString() = "[$left,$right]"

    override fun hashCode() = 31 * left.hashCode() + right.hashCode()

    override fun copy(parent: SnailPair?): SnailFish {
        val res = SnailPair(parent, left, right)
        res.left = left.copy(res)
        res.right = right.copy(res)
        return res
    }
}

class SnailNumber(parent: SnailPair?, var value: Int) : SnailFish(parent) {
    companion object {
        fun parse(str: String, parent: SnailPair?): SnailNumber = SnailNumber(parent, str.toInt())
    }

    override fun reduceExplode() = false

    override fun reduceSplit(): Boolean {
        if (value >= 10) {
            val p = parent!!
            val n = SnailPair(parent, this, this)
            n.left = SnailNumber(n, value / 2)
            n.right = SnailNumber(n, value - (n.left as SnailNumber).value)
            if (p.left === this) {
                p.left = n
            } else {
                p.right = n
            }
            return true
        }
        return false
    }

    override fun magnitude() = value

    override fun leftMost() = this

    override fun rightMost() = this

    override fun equals(other: Any?) =
        (this === other) || ((other is SnailNumber) && value == other.value)

    override fun toString() = value.toString()

    override fun hashCode() = value

    override fun copy(parent: SnailPair?) = SnailNumber(parent, value)
}

fun main() {
    fun part1(input: List<String>): Int {
        val fishes = input.map(SnailFish::parse)
        val summed = fishes.reduce { acc, snailFish ->
            val sum = SnailPair(null, acc, snailFish)
            acc.parent = sum
            snailFish.parent = sum
            sum.reduce()
            sum
        }
        return summed.magnitude()
    }

    fun part2(input: List<String>): Int {
        val fishes = input.map(SnailFish::parse)
        return fishes.combinations().map { (x, y) ->
            val sum = SnailPair(null, x.copy(), y.copy())
            sum.left.parent = sum
            sum.right.parent = sum
            sum.reduce()
            sum.magnitude()
        }.maxOrNull() ?: -1
    }

    val testInput = readInputAsLines(year, day, Input.Test)
    check(part1(testInput) == 4140)
    check(part2(testInput) == 3993)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
