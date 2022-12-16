package aoc2022.day16

import Solver
import kotlin.math.min

object Day16Solver : Solver(2022, 16) {

    fun getOptimal(minutes: Int,
                   opened: Set<String>,
                   location: String,
                   pressure: Int,
                   tunnels: Map<String, List<String>>,
                   rates: Map<String, Int>): Int {
        if (minutes <= 0)
            return pressure
        val options = mutableListOf<Int>()
        val newPressure = pressure + opened.sumOf { rates[it]!! }
        if (!opened.contains(location) && rates[location]!! != 0) {
            val newOpened = opened + setOf(location)
            options.add(getOptimal(minutes - 1, newOpened, location, newPressure, tunnels, rates))
        }
        for (dest in tunnels[location]!!) {
            options.add(getOptimal(minutes - 1, opened, dest, newPressure, tunnels, rates))
        }
        return options.maxOrNull()!!
    }

    fun getOptimalGreedy(minutes: Int,
                         opened: Set<String>,
                         location: String,
                         pressure: Int,
                         paths: Map<String, List<Pair<String, Int>>>,
                         rates: Map<String, Int>): Int {
        if (minutes <= 0)
            return pressure
        val newPressure = pressure + opened.sumOf { rates[it]!! }
        // Go to valve that gives max
        val best = paths[location]!!.maxByOrNull {
            if (it.second >= minutes)
                -1
            else
                (minutes - it.second) * rates[it.first]!!
        }!!
        if (best.second == -1) println("might be breaking")
        // Open that valve
        val newOpened = opened + setOf(best.first)
        return getOptimalGreedy(
            minutes - best.second - 1,
            newOpened,
            best.first,
            newPressure,
            paths,
            rates
        )
    }

    fun findPath(origin: String, dest: String, tunnels: Map<String, List<String>>): Int? {
        val q = mutableListOf<Pair<String, Int>>()
        val visited = mutableSetOf<String>()
        q.add(Pair(origin, 0))
        visited.add(origin)
        while (!q.isEmpty()) {
            val curr = q.removeFirst()
            if (curr.first == dest) {
                return curr.second
            }
            tunnels[curr.first]!!.forEach {
                if (!visited.contains(it)) {
                    q.add(Pair(it, curr.second + 1))
                    visited.add(it)
                }
            }
        }

        return null
    }

    override fun solvePartOne(input: List<String>): Int {
        val parse = "Valve ([A-Z]+) has flow rate=(\\d+); tunnels? leads? to valves? ([,A-Z ]+)".toRegex()
        val tunnels: MutableMap<String, List<String>> = mutableMapOf()
        val rates: MutableMap<String, Int> = mutableMapOf()
        val paths: MutableMap<String, List<Pair<String, Int>>> = mutableMapOf()

        input.forEach {
            val (valve, flow, dest) = parse.matchEntire(it)!!.destructured
            rates[valve] = flow.toInt()
            tunnels[valve] = dest.split(", ")
        }
        for (origin in tunnels.keys) {
            val l = mutableListOf(Pair(origin, 0))
            for (dest in tunnels.keys) {
                if (origin != dest) {
                    val res = findPath(origin, dest, tunnels)
                    if (res != null)
                        l.add(Pair(dest, res))
                }
            }
            paths[origin] = l
        }

        return getOptimalGreedy(30, setOf(), "AA", 0, paths, rates)
    }

    override fun solvePartTwo(input: List<String>): Int {
         return -1
    }
}

fun main() {
    Day16Solver.runPartOneTest()
//    Day16Solver.runPartTwoTest()

    Day16Solver.solve()
}
