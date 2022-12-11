import kotlin.io.path.Path

fun generateYear(year: Int) {
    val template = Path("kotlin", "src", "main", "kotlin", "template").toFile()
    for (day in 1..25) {
        val dayString = "day" + if (day < 10) "0$day" else day
        val dayFile = Path("kotlin", "src", "main", "kotlin", "aoc$year", dayString).toFile()
        if (!dayFile.exists()) {
            template.copyRecursively(dayFile)
            val solutionFile = Path("kotlin", "src", "main", "kotlin", "aoc$year", dayString, "Solution.kt").toFile()
            val original = solutionFile.readText()
            solutionFile.writeText(
                original
                    .replace("package template", "package aoc$year.$dayString")
                    .replace("Day00Solver", "Day${if (day < 10) "0$day" else day}Solver")
                    .replace("Solver(0, 0)", "Solver($year, $day)")
            )
        } else {
            println("Day $day of year $year already exists!")
        }
    }
}

fun main() {
    generateYear(2022)
}