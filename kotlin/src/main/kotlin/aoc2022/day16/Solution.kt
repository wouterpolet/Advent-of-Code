package aoc2022.day16

import Solver
import java.util.PriorityQueue
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

    fun getOptimalSmart(pathReversed: Map<String, List<Pair<String, Int>>>, rates: Map<String, Int>): Int {
        var minute = 1
        val acc = mutableListOf<Map<String, Map<Set<String>, Int>>>()
        val valves = pathReversed.keys
        acc.add(mapOf(Pair("AA", mapOf(Pair(emptySet(), 0)))))

        while (minute < 30) {
            val newAccEntry = mutableMapOf<String, Map<Set<String>, Int>>()
            for (newValve in valves) {
                if (rates[newValve]!! == 0) continue
                val options = mutableMapOf<Set<String>, Int>()
                for (prevValve in pathReversed[newValve]!!) {
                    if (minute - prevValve.second - 1 >= 0 && acc[minute - prevValve.second - 1].contains(prevValve.first)) {
                        for (prev in acc[minute - prevValve.second - 1][prevValve.first]!!) {
                            if (!prev.key.contains(newValve)) {
                                val newKey = prev.key + setOf(newValve)
                                val newValue = prev.value + (30 - minute) * rates[newValve]!!
                                if (!options.contains(newKey) || options[newKey]!! < newValue) {
                                    options[newKey] = newValue
                                }
                            }
                        }
                    }
                }
                if (!options.isEmpty())
                    newAccEntry[newValve] = options
            }
            acc.add(newAccEntry)
            minute++
        }

        return acc.maxOfOrNull {
            it.maxOfOrNull {
                it.value.maxOfOrNull {
                    it.value
                } ?: -1
            } ?: -1
        } ?: -1
    }

    fun getOptimalWithElephant(pathReversed: Map<String, List<Pair<String, Int>>>, rates: Map<String, Int>): Int {
        var minute = 1
        val acc = mutableListOf<Map<Pair<String, String>, Map<Set<String>, Int>>>()
        val valves = pathReversed.keys
        acc.add(mapOf(Pair(Pair("AA", "AA"), mapOf(Pair(emptySet(), 0)))))

        while (minute < 26) {
            val newAccEntry = mutableMapOf<Pair<String, String>, MutableMap<Set<String>, Int>>()
            for (newValve in valves) {
                if (rates[newValve]!! == 0) continue
                for (prevValve in pathReversed[newValve]!!) {
                    if (minute - prevValve.second - 1 < 0) continue
                    // Only me open valve
                    val prevMyPairs = acc[minute - prevValve.second - 1].filter { it.key.first == prevValve.first }
                    for (prevPair in prevMyPairs) {
                        val newPair = Pair(newValve, prevPair.key.second)
                        if (!newAccEntry.contains(newPair)) newAccEntry[newPair] = mutableMapOf()
                        for (prevSet in prevPair.value) {
                            val newSet = prevSet.key + setOf(newValve)
                            val newValue = prevSet.value + (30 - minute) * rates[newValve]!!
                            if (!newAccEntry[newPair]!!.contains(newSet) || newAccEntry[newPair]!![newSet]!! < newValue) {
                                newAccEntry[newPair]!![newSet] = newValue
                            }
                        }
                    }

                    // Only elephant open valve
                    val prevElephantPairs =
                        acc[minute - prevValve.second - 1].filter { it.key.second == prevValve.first }
                    for (prevPair in prevElephantPairs) {
                        val newPair = Pair(prevPair.key.first, newValve)
                        if (!newAccEntry.contains(newPair)) newAccEntry[newPair] = mutableMapOf()
                        for (prevSet in prevPair.value) {
                            val newSet = prevSet.key + setOf(newValve)
                            val newValue = prevSet.value + (30 - minute) * rates[newValve]!!
                            if (!newAccEntry[newPair]!!.contains(newSet) || newAccEntry[newPair]!![newSet]!! < newValue) {
                                newAccEntry[newPair]!![newSet] = newValue
                            }
                        }
                    }

                    // Me and elephant open valve
                    for (newElephantValve in valves) {
                        if (newElephantValve == newValve || rates[newElephantValve]!! == 0) continue
                        val newPair = Pair(newValve, newElephantValve)
                        for (prevElephantValve in pathReversed[newElephantValve]!!) {
                            if (minute - prevElephantValve.second - 1 < 0) continue
                            val prevPair = Pair(prevValve.first, prevElephantValve.first)
                            if (!acc[minute - prevValve.second - 1].contains(prevPair)) continue

                            if (!newAccEntry.contains(newPair)) newAccEntry[newPair] = mutableMapOf()
                            for (prevSet in acc[minute - prevValve.second - 1][prevPair]!!) {
                                val newSet = prevSet.key + setOf(newValve, newElephantValve)
                                val newValue =
                                    prevSet.value + (30 - minute) * (rates[newValve]!! + rates[newElephantValve]!!)
                                if (!newAccEntry[newPair]!!.contains(newSet) || newAccEntry[newPair]!![newSet]!! < newValue) {
                                    newAccEntry[newPair]!![newSet] = newValue
                                }
                            }
                        }
                    }
                }
            }
            acc.add(newAccEntry)
            minute++
        }

        return acc.maxOfOrNull {
            it.maxOfOrNull {
                it.value.maxOfOrNull {
                    it.value
                } ?: -1
            } ?: -1
        } ?: -1
    }

    fun getOptimalWithWrongElephant(pathReversed: Map<String, List<Pair<String, Int>>>, rates: Map<String, Int>): Int {
        var minute = 1
        val valves = pathReversed.keys
        var acc = mapOf(Pair(Pair("AA", "AA"), mapOf(Pair(emptySet<String>(), 0))))

        while (minute < 26) {
            val newAcc = mutableMapOf<Pair<String, String>, MutableMap<Set<String>, Int>>()

            // Both moving
            for (newMyValve in valves) {
                val possibleMyPrev = pathReversed[newMyValve]!!.filter { it.second == 1 }.map { it.first }
                for (newElephantValve in valves) {
                    val newComb = Pair(newMyValve, newElephantValve)
                    val possibleElephantPrev = pathReversed[newElephantValve]!!.filter { it.second == 1 }.map { it.first }
                    for (myPrev in possibleMyPrev) {
                        for (elephantPrev in possibleElephantPrev) {
                            // This prev combination existed, so it's a valid new state
                            val prevComb = Pair(myPrev, elephantPrev)
                            if (acc.contains(prevComb) && acc[prevComb]!!.isNotEmpty()) {
                                if (!newAcc.contains(newComb)) {
                                    newAcc[newComb] = mutableMapOf()
                                }
                                for (prevOpen in acc[prevComb]!!) {
                                    if (!newAcc[newComb]!!.contains(prevOpen.key) || newAcc[newComb]!![prevOpen.key]!! < prevOpen.value) {
                                        newAcc[newComb]!![prevOpen.key] = prevOpen.value
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Me moving, elephant openening
            for (newMyValve in valves) {
                val possibleMyPrev = pathReversed[newMyValve]!!.filter { it.second == 1 }.map { it.first }
                for (currElephant in valves) {
                    if (rates[currElephant]!! == 0) continue
                    val newComb = Pair(newMyValve, currElephant)
                    for (myPrev in possibleMyPrev) {
                        val prevComb = Pair(myPrev, currElephant)
                        if (acc.contains(prevComb) && acc[prevComb]!!.isNotEmpty()) {
                            if (!newAcc.contains(newComb)) {
                                newAcc[newComb] = mutableMapOf()
                            }
                            for (prevOpen in acc[prevComb]!!) {
                                if (prevOpen.key.contains(currElephant)) continue
                                if (!newAcc[newComb]!!.contains(prevOpen.key) || newAcc[newComb]!![prevOpen.key]!! < prevOpen.value) {
                                    newAcc[newComb]!![prevOpen.key + setOf(currElephant)] = prevOpen.value + (30 - minute) * rates[currElephant]!!
                                }
                            }
                        }
                    }
                }
            }

            // Me opening, elephant moving
            for (newElephantValve in valves) {
                val possibleElephantPrev = pathReversed[newElephantValve]!!.filter { it.second == 1 }.map { it.first }
                for (currMe in valves) {
                    if (rates[currMe]!! == 0) continue
                    val newComb = Pair(currMe, newElephantValve)
                    for (elephantPrev in possibleElephantPrev) {
                        val prevComb = Pair(currMe, elephantPrev)
                        if (acc.contains(prevComb) && acc[prevComb]!!.isNotEmpty()) {
                            if (!newAcc.contains(newComb)) {
                                newAcc[newComb] = mutableMapOf()
                            }
                            for (prevOpen in acc[prevComb]!!) {
                                if (prevOpen.key.contains(currMe)) continue
                                if (!newAcc[newComb]!!.contains(prevOpen.key) || newAcc[newComb]!![prevOpen.key]!! < prevOpen.value) {
                                    newAcc[newComb]!![prevOpen.key + setOf(currMe)] = prevOpen.value + (30 - minute) * rates[currMe]!!
                                }
                            }
                        }
                    }
                }
            }

            // Both opening
            for (currMe in valves) {
                for (currElephant in valves) {
                    val comb = Pair(currMe, currElephant)
                    if (rates[currMe]!! == 0 || rates[currElephant]!! == 0) continue
                    if (acc.contains(comb) && acc[comb]!!.isNotEmpty()) {
                        if (!newAcc.contains(comb)) {
                            newAcc[comb] = mutableMapOf()
                        }
                        for (prevOpen in acc[comb]!!) {
                            if (prevOpen.key.contains(currMe) || prevOpen.key.contains(currElephant)) continue
                            if (!newAcc[comb]!!.contains(prevOpen.key) || newAcc[comb]!![prevOpen.key]!! < prevOpen.value) {
                                newAcc[comb]!![prevOpen.key + setOf(currMe, currElephant)] = prevOpen.value + (30 - minute) * (rates[currMe]!! + rates[currElephant]!!)
                            }
                        }
                    }
                }
            }

            acc = newAcc
            minute++
        }

        return acc.maxOfOrNull {
            it.value.maxOfOrNull {
                it.value
            } ?: -1
        } ?: -1
    }

    fun getOptimalWithForwardElephant(paths: Map<String, List<Pair<String, Int>>>, rates: Map<String, Int>): Int {
        val minutes = 26
        val acc: MutableMap<Int, MutableMap<Pair<String, String>, MutableMap<Set<String>, Int>>> = mutableMapOf()
        acc[1] = mutableMapOf(Pair(Pair("AA", "AA"), mutableMapOf(Pair(emptySet(), 0))))

        for (minute in 1 until minutes) {
            for (entry in acc[minute]?.entries ?: emptyList()) {
                val prevSpots = entry.key
                val prevValues = entry.value
/*
                // Do me
                for (newValve in paths[prevSpots.first]!!) {
                    if (rates[newValve.first]!! != 0 && minute + newValve.second < minutes) {
                        val newTime = minute + newValve.second + 1
                        if (!acc.containsKey(newTime)) acc[newTime] = mutableMapOf()
                        val newSpot = Pair(newValve.first, prevSpots.second)
                        for (prevValue in prevValues) {
                            if (prevValue.key.contains(newValve.first)) continue
                            if (!acc[newTime]!!.contains(newSpot)) acc[newTime]!![newSpot] = mutableMapOf()
                            val newSet = prevValue.key + setOf(newValve.first)
                            val newValue = prevValue.value + (minutes - minute - newValve.second) * rates[newValve.first]!!
                            if (!acc[newTime]!![newSpot]!!.contains(newSet) || acc[newTime]!![newSpot]!![newSet]!! < newValue) {
                                acc[newTime]!![newSpot]!![newSet] = newValue
                            }
                        }
                    }
                }

                // Do elephant
                for (newValve in paths[prevSpots.second]!!) {
                    if (rates[newValve.first]!! != 0 && minute + newValve.second < minutes) {
                        val newTime = minute + newValve.second + 1
                        if (!acc.containsKey(newTime)) acc[newTime] = mutableMapOf()
                        val newSpot = Pair(prevSpots.first, newValve.first)
                        for (prevValue in prevValues) {
                            if (prevValue.key.contains(newValve.first)) continue
                            if (!acc[newTime]!!.contains(newSpot)) acc[newTime]!![newSpot] = mutableMapOf()
                            val newSet = prevValue.key + setOf(newValve.first)
                            val newValue = prevValue.value + (minutes - minute - newValve.second) * rates[newValve.first]!!
                            if (!acc[newTime]!![newSpot]!!.contains(newSet) || acc[newTime]!![newSpot]!![newSet]!! < newValue) {
                                acc[newTime]!![newSpot]!![newSet] = newValue
                            }
                        }
                    }
                }
*/
                // Do both
                for (newMyValve in paths[prevSpots.first]!!) {
                    if (rates[newMyValve.first]!! == 0 || minute + newMyValve.second < minutes) continue
                    for (newElephantValve in paths[prevSpots.second]!!) {
                        if (newMyValve == newElephantValve
                            || minute + newElephantValve.second < minutes
                            || rates[newElephantValve.first]!! == 0
                            || newMyValve.second != newElephantValve.second) continue
                        val newTime = minute + newMyValve.second + 1
                        if (!acc.containsKey(newTime)) acc[newTime] = mutableMapOf()
                        val newSpot = Pair(newMyValve.first, newElephantValve.first)
                        for (prevValue in prevValues) {
                            if (prevValue.key.contains(newMyValve.first) || prevValue.key.contains(newElephantValve.first)) continue
                            if (!acc[newTime]!!.contains(newSpot)) acc[newTime]!![newSpot] = mutableMapOf()
                            val newSet = prevValue.key + setOf(newMyValve.first, newElephantValve.first)
                            val newValue = prevValue.value + (minutes - minute - newMyValve.second) * (rates[newMyValve.first]!! + rates[newElephantValve.first]!!)
                            if (!acc[newTime]!![newSpot]!!.contains(newSet) || acc[newTime]!![newSpot]!![newSet]!! < newValue) {
                                acc[newTime]!![newSpot]!![newSet] = newValue
                            }
                        }
                    }
                }
            }
        }
        return acc.maxOfOrNull { it.value.maxOfOrNull { it.value.maxOfOrNull { it.value } ?: -1 } ?: -1 } ?: -1
    }

    fun getOptimalWithForwardElephantSteps(paths: Map<String, List<Pair<String, Int>>>, rates: Map<String, Int>): Int {
        val minutes = 26
        var acc: MutableMap<Pair<String, String>, MutableMap<Set<String>, Int>> = mutableMapOf(Pair(Pair("AA", "AA"), mutableMapOf(Pair(emptySet(), 0))))
        for (minute in 1 until minutes) {
            println("Processing minute $minute")
            val newAcc: MutableMap<Pair<String, String>, MutableMap<Set<String>, Int>> = mutableMapOf()
            for (entry in acc.entries) {
                val prevSpots = entry.key
                val prevValues = entry.value
                for (newMyValve in paths[prevSpots.first]!!.filter { it.second == 1 }.map { it.first }) {
                    // Both moving
                    for (newElephantValve in paths[prevSpots.second]!!.filter { it.second == 1 }.map { it.first }) {
                        val newPair = Pair(newMyValve, newElephantValve)
                        if (!newAcc.contains(newPair)) newAcc[newPair] = mutableMapOf()
                        for (prev in prevValues) {
                            if ((newAcc[newPair]!![prev.key] ?: -1) < prev.value) {
                                newAcc[newPair]!![prev.key] = prev.value
                            }
                        }
                    }
                    // Me moving
                    if (rates[prevSpots.second]!! > 0) {
                        val newPair = Pair(newMyValve, prevSpots.second)
                        if (!newAcc.contains(newPair)) newAcc[newPair] = mutableMapOf()
                        for (prev in prevValues) {
                            if (prev.key.contains(prevSpots.second)) continue
                            val newSet = prev.key + setOf(prevSpots.second)
                            val newValue = prev.value + (minutes - minute) * rates[prevSpots.second]!!
                            if ((newAcc[newPair]!![newSet] ?: -1) < newValue) {
                                newAcc[newPair]!![newSet] = newValue
                            }
                        }
                    }
                }
                for (newElephant in paths[prevSpots.second]!!.filter { it.second == 1 }.map { it.first }) {
                    // Elephant moving
                    if (rates[prevSpots.first]!! > 0) {
                        val newPair = Pair(prevSpots.first, newElephant)
                        if (!newAcc.contains(newPair)) newAcc[newPair] = mutableMapOf()
                        for (prev in prevValues) {
                            if (prev.key.contains(prevSpots.first)) continue
                            val newSet = prev.key + setOf(prevSpots.first)
                            val newValue = prev.value + (minutes - minute) * rates[prevSpots.first]!!
                            if ((newAcc[newPair]!![newSet] ?: -1) < newValue) {
                                newAcc[newPair]!![newSet] = newValue
                            }
                        }
                    }
                }
                // Neither moving
                if (rates[prevSpots.first]!! > 0 && rates[prevSpots.second]!! > 0) {
                    if (prevSpots.first == prevSpots.second) continue
                    if (!newAcc.contains(prevSpots)) newAcc[prevSpots] = mutableMapOf()
                    for (prev in prevValues) {
                        if (prev.key.contains(prevSpots.first) || prev.key.contains(prevSpots.second)) continue
                        val newSet = prev.key + setOf(prevSpots.first, prevSpots.second)
                        val newValue = prev.value + (minutes - minute) * (rates[prevSpots.first]!! + rates[prevSpots.second]!!)
                        if ((newAcc[prevSpots]!![newSet] ?: -1) < newValue) {
                            newAcc[prevSpots]!![newSet] = newValue
                        }
                    }
                }
            }
            acc = newAcc
        }
        return acc.values.maxOfOrNull { it.maxOfOrNull { it.value } ?: -1 } ?: -1
    }

    fun getOptimalWithForwardElephantFast(paths: Map<String, List<Pair<String, Int>>>, rates: Map<String, Int>): Int {
        val duration = 26
        val acc: MutableMap<Set<Pair<String, Int>>, MutableMap<Set<String>, Int>> = mutableMapOf(Pair(setOf(Pair("AA", 0)), mutableMapOf(Pair(emptySet(), 0))))
        val qPrio = PriorityQueue<Set<Pair<String, Int>>> { p1, p2 ->
            p1.minOf { it.second }.compareTo(p2.minOf { it.second })
        }

        qPrio.add(setOf(Pair("AA", 0)))
        var result = -1
        var maxMinute = 0

        while (!qPrio.isEmpty()) {
            val prevSpots = qPrio.poll()
            if (prevSpots.minOf { it.second } > maxMinute) {
                maxMinute = prevSpots.minOf { it.second }
                println("Starting minute $maxMinute")
            }
            acc.keys.toList().forEach {
                if (it.minOf { it.second } < prevSpots.minOf { it.second })
                    acc.remove(it)
            }
            val prevSets = acc[prevSpots]!!
            for (prevValve in prevSpots) {
                for (nextValve in paths[prevValve.first]!!) {
                    if (nextValve.second + prevValve.second >= duration) continue
                    if (prevSpots.any { it.first == nextValve.first }) continue
                    val newValve = Pair(nextValve.first, nextValve.second + prevValve.second + 1)
                    val newValves = (if (prevSpots.size == 1) prevSpots else prevSpots - prevValve) + newValve

                    if (!acc.contains(newValves)) acc[newValves] = mutableMapOf()

                    for ((prevOpenValves, prevScore) in prevSets) {
                        if (prevOpenValves.contains(newValve.first)) continue
                        val newScore = prevScore +
                                (duration - newValve.second) * rates[newValve.first]!!
                        val newSet = prevOpenValves + newValve.first
                        if ((acc[newValves]!![newSet] ?: -1) < newScore) {
                            acc[newValves]!![newSet] = newScore
                            if (newScore > result)
                                result = newScore
                            if (!qPrio.contains(newValves)) qPrio.add(newValves)
                        }

                    }
                }
            }


//            val (myPrevValve, elephantPrevValve) = prevSpots
//            val prevSets = acc[prevSpots]!!
            /*for (newValve in paths[myPrevValve.first]!!) {
                if (rates[newValve.first]!! == 0
                    || newValve.second + myPrevValve.second >= duration) continue
                val newValvesMe = Pair(
                    Pair(newValve.first, newValve.second + myPrevValve.second + 1),
                    elephantPrevValve
                )

                if (!acc.contains(newValvesMe)) acc[newValvesMe] = mutableMapOf()

                for ((prevOpenValves, prevScore) in prevSets) {
                    if (prevOpenValves.contains(newValve.first)) continue
                    val newScoreMe = prevScore +
                            (duration - newValvesMe.first.second) * rates[newValve.first]!!
                    val newSet = prevOpenValves + setOf(newValve.first)
                    if ((acc[newValvesMe]!![newSet] ?: -1) < newScoreMe) {
                        acc[newValvesMe]!![newSet] = newScoreMe
                        if (newScoreMe > result)
                            result = newScoreMe
                        if (!qPrio.contains(newValvesMe)) qPrio.add(newValvesMe)
                    }

                }
            }
            for (newValve in paths[elephantPrevValve.first]!!) {
                if (rates[newValve.first]!! == 0
                    || newValve.second + elephantPrevValve.second >= duration) continue

                val newValvesElephant = Pair(
                    myPrevValve,
                    Pair(newValve.first, newValve.second + elephantPrevValve.second + 1),
                )

                if (!acc.contains(newValvesElephant)) acc[newValvesElephant] = mutableMapOf()

                for ((prevOpenValves, prevScore) in prevSets) {
                    if (prevOpenValves.contains(newValve.first)) continue
                    val newScoreElephant = prevScore +
                            (duration - newValvesElephant.second.second) * rates[newValve.first]!!
                    val newSet = prevOpenValves + setOf(newValve.first)
                    if ((acc[newValvesElephant]!![newSet] ?: -1) < newScoreElephant) {
                        acc[newValvesElephant]!![newSet] = newScoreElephant
                        if (newScoreElephant > result)
                            result = newScoreElephant
                        if (!qPrio.contains(newValvesElephant)) qPrio.add(newValvesElephant)
                    }
                }
            }*/
        }

        return result
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
        val pathsRev: MutableMap<String, List<Pair<String, Int>>> = mutableMapOf()

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
        for (dest in tunnels.keys) {
            val l = mutableListOf<Pair<String, Int>>()
            for (origin in tunnels.keys) {
                if (origin != dest) {
                    val res = findPath(dest, origin, tunnels)
                    if (res != null)
                        l.add(Pair(origin, res))
                }
            }
            pathsRev[dest] = l
        }

        return getOptimalSmart(pathsRev, rates)
    }

    override fun solvePartTwo(input: List<String>): Int {
        val parse = "Valve ([A-Z]+) has flow rate=(\\d+); tunnels? leads? to valves? ([,A-Z ]+)".toRegex()
        val tunnels: MutableMap<String, List<String>> = mutableMapOf()
        val rates: MutableMap<String, Int> = mutableMapOf()
        val paths: MutableMap<String, List<Pair<String, Int>>> = mutableMapOf()
        val pathsRev: MutableMap<String, List<Pair<String, Int>>> = mutableMapOf()

        input.forEach {
            val (valve, flow, dest) = parse.matchEntire(it)!!.destructured
            rates[valve] = flow.toInt()
            tunnels[valve] = dest.split(", ")
        }
        for (origin in tunnels.keys) {
            val l = mutableListOf<Pair<String, Int>>()
            for (dest in tunnels.keys) {
                if (rates[dest]!! == 0) continue
                if (origin != dest) {
                    val res = findPath(origin, dest, tunnels)
                    if (res != null)
                        l.add(Pair(dest, res))
                }
            }
            paths[origin] = l
        }
        for (dest in tunnels.keys) {
            val l = mutableListOf<Pair<String, Int>>()
            for (origin in tunnels.keys) {
                if (origin != dest) {
                    val res = findPath(dest, origin, tunnels)
                    if (res != null)
                        l.add(Pair(origin, res))
                }
            }
            pathsRev[dest] = l
        }

        return getOptimalWithForwardElephantFast(paths, rates)
    }
}

fun main() {
    Day16Solver.runPartOneTest()
    Day16Solver.runPartTwoTest()

    Day16Solver.solve()
}
