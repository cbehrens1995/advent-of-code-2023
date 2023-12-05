import org.apache.commons.lang3.StringUtils
import java.math.BigDecimal
import java.util.Optional

class Solution1 {

    fun calculateEngines(data: String): BigDecimal {
        var currentLine: String
        var previousLine = String()
        var nextLine: String

        val parts = ArrayList<BigDecimal>()
        val lines = data.lines()

        for ((lineNumber, line) in lines.withIndex()) {
            currentLine = line
            nextLine = if (lineNumber < lines.size - 1) {
                lines[lineNumber + 1]
            } else {
                String()
            }

            val adjacentIndexSet = HashSet<Int>()
            val partIndexSet = HashSet<Int>()
            for ((index, character) in currentLine.toCharArray().withIndex()) {
                if (character.isDigit()) {
                    processDigit(index, currentLine, adjacentIndexSet, partIndexSet)
                } else if (adjacentIndexSet.isNotEmpty()) {
                    processSymbol(previousLine, currentLine, nextLine, adjacentIndexSet, partIndexSet, parts)
                    continue
                }
            }

            if (adjacentIndexSet.isNotEmpty()) {
                processSymbol(previousLine, currentLine, nextLine, adjacentIndexSet, partIndexSet, parts)
            }

            previousLine = line
        }

        return parts.sumOf { it }
    }

    private fun processSymbol(
        previousLine: String,
        currentLine: String,
        nextLine: String,
        adjacentIndexSet: HashSet<Int>,
        partIndexSet: HashSet<Int>,
        parts: ArrayList<BigDecimal>,
    ) {
        val startingIndex = adjacentIndexSet.min()
        var endingIndex = adjacentIndexSet.max()

        var partOptional: Optional<BigDecimal> = Optional.empty()
        if (!StringUtils.isBlank(previousLine)) {
            partOptional = getPartIfPresent(previousLine, startingIndex, endingIndex, currentLine, partIndexSet)
        }

        if (!StringUtils.isBlank(nextLine) && partOptional.isEmpty) {
            partOptional = getPartIfPresent(nextLine, startingIndex, endingIndex, currentLine, partIndexSet)
        }

        if (!StringUtils.isBlank(currentLine) && partOptional.isEmpty) {
            partOptional = getPartIfPresent(currentLine, startingIndex, endingIndex, currentLine, partIndexSet)
        }

        partOptional.ifPresent { parts.add(it) }
        adjacentIndexSet.clear()
        partIndexSet.clear()
        return
    }

    private fun processDigit(
        index: Int,
        currentLine: String,
        adjacentIndexSet: HashSet<Int>,
        partIndexSet: HashSet<Int>,
    ) {
        adjacentIndexSet.add(index)
        partIndexSet.add(index)
        if (index > 0) {
            adjacentIndexSet.add(index - 1)
        }

        if (index < currentLine.length) {
            adjacentIndexSet.add(index + 1)
        }
    }

    private fun getPartIfPresent(
        lineToCheck: String,
        startingIndex: Int,
        endingIndex: Int,
        currentLine: String,
        partIndexSet: Set<Int>,
    ): Optional<BigDecimal> {
        var endingIndex2 = endingIndex
        if (endingIndex < 140) {
            endingIndex2 = endingIndex + 1
        }

        val hasSymbol = lineToCheck.subSequence(startingIndex, endingIndex2).chars()
            .mapToObj { it.toChar() }
            .filter { !it.isDigit() }
            .map { it.toString() }
            .filter { !it.contains(".") }
            .findAny()

        if (hasSymbol.isPresent) {
            val part = currentLine.subSequence(partIndexSet.min(), partIndexSet.max() + 1).toString().toBigDecimal()
            return Optional.of(part)
        }

        return Optional.empty()
    }
}
