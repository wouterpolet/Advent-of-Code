import kotlin.io.path.Path
import kotlin.io.path.exists

fun generateYear(year: Int) {
    val folderPath = Path("src", "main", "kotlin", "aoc$year")
    if (folderPath.exists()) {
        println("Year $year already exists!")
        return
    }
    val template = Path("src", "main", "kotlin", "template").toFile()
    for (day in 1..25) {
        val dayString = "day" + if (day < 10) "0$day" else day
        val dayFile = Path("src", "main", "kotlin", "aoc$year", dayString).toFile()
        template.copyRecursively(dayFile)
        val solutionFile = Path("src", "main", "kotlin", "aoc$year", dayString, "Solution.kt").toFile()
        val original = solutionFile.readText()
        solutionFile.writeText(
            original
                .replace("package template", "package aoc$year.$dayString")
                .replace("val year = 0", "val year = $year")
                .replace("val day = 0", "val day = $day")
        )
    }
}

fun main() {
    generateYear(2015)
}