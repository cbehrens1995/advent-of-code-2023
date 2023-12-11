import java.math.BigInteger

private const val GALAXY_STRING = "#"

class Solution {
    fun calculateSumOfAllPaths(data: String): BigInteger {
        val expendedUniverse = getExpendedUniverse(data)

        val galaxyCoordinates = determineGalaxyCoordinates(expendedUniverse)

        val pathLengths = ArrayList<BigInteger>()
        for ((index, galaxyCoordinate) in galaxyCoordinates.withIndex()) {
            if (index < galaxyCoordinates.size - 1) {
                val otherCoordinates = galaxyCoordinates.subList(index + 1, galaxyCoordinates.size)
                for (otherCoordinate in otherCoordinates) {
                    val xCoordinate = galaxyCoordinate.first
                    val otherXCoordinate = otherCoordinate.first
                    val xLength = Math.abs(xCoordinate - otherXCoordinate).toBigInteger()

                    val yCoordinate = galaxyCoordinate.second
                    val otherYCoordinate = otherCoordinate.second
                    val yLength = Math.abs(yCoordinate - otherYCoordinate).toBigInteger()

                    pathLengths.add(xLength + yLength)
                }
            }
        }

        return pathLengths.sumOf { it }
    }

    private fun determineGalaxyCoordinates(expendedUniverse: List<List<Char>>): ArrayList<Pair<Int, Int>> {
        val galaxyCoordinates = ArrayList<Pair<Int, Int>>()
        for ((yCoordinate, chars) in expendedUniverse.withIndex()) {
            for ((xCoordinate, char) in chars.withIndex()) {
                if (char.toString() == GALAXY_STRING) {
                    val coordinate = Pair(xCoordinate, yCoordinate)
                    galaxyCoordinates.add(coordinate)
                }
            }
        }

        return galaxyCoordinates
    }

    private fun getExpendedUniverse(data: String): List<List<Char>> {
        val yExpendedUniverse = ArrayList<List<Char>>()

        for (line in data.lines()) {
            val chars = line.toCharArray().toList()
            yExpendedUniverse.add(chars)

            if (!chars.contains(GALAXY_STRING.toCharArray()[0])) {
                yExpendedUniverse.add(chars)
            }
        }

        val horizontalSize = data.lines()[0].length
        val verticalLinesToExpand = ArrayList<Int>()
        for (i in 0..horizontalSize - 1) {
            val hasGalaxyInVerticalLine = data.lines()
                .map { it.toCharArray()[i] }
                .any { it.toString() == GALAXY_STRING }
            if (!hasGalaxyInVerticalLine) {
                verticalLinesToExpand.add(i)
            }
        }

        val expendedUniverse = ArrayList<List<Char>>()

        for (line in yExpendedUniverse) {
            val newLine = line.toMutableList()
            for ((index, verticalLineToExpend) in verticalLinesToExpand.withIndex()) {
                val shiftValue = index + verticalLineToExpend

                newLine.add(shiftValue, ".".toCharArray()[0])
            }
            expendedUniverse.add(newLine)
        }

        return expendedUniverse
    }

    fun calculateSumOfAllPathsExpendedUniverse(data: String, multiplier: BigInteger): BigInteger? {
        val originalUniverse = data.lines()
            .map { it.toCharArray().toList() }
            .toList()

        val galaxyCoordinates = determineGalaxyCoordinates(originalUniverse)

        val yExpansions = ArrayList<BigInteger>()
        for ((yCoordinate, chars) in originalUniverse.withIndex()) {
            if (!chars.contains(GALAXY_STRING.toCharArray()[0])) {
                yExpansions.add(yCoordinate.toBigInteger())
            }
        }

        val horizontalSize = data.lines()[0].length
        val xExpansions = ArrayList<BigInteger>()
        for (i in 0..horizontalSize - 1) {
            val hasGalaxyInVerticalLine = data.lines()
                .map { it.toCharArray()[i] }
                .any { it.toString() == GALAXY_STRING }
            if (!hasGalaxyInVerticalLine) {
                xExpansions.add(i.toBigInteger())
            }
        }

        val pathLengths = ArrayList<BigInteger>()
        for ((index, galaxyCoordinate) in galaxyCoordinates.withIndex()) {
            if (index < galaxyCoordinates.size - 1) {
                val otherCoordinates = galaxyCoordinates.subList(index + 1, galaxyCoordinates.size)
                for (otherCoordinate in otherCoordinates) {
                    val xCoordinate = galaxyCoordinate.first.toBigInteger()
                    val otherXCoordinate = otherCoordinate.first.toBigInteger()
                    val xLength = calculateLength(xExpansions, xCoordinate, otherXCoordinate, multiplier.subtract(BigInteger.ONE))

                    val yCoordinate = galaxyCoordinate.second.toBigInteger()
                    val otherYCoordinate = otherCoordinate.second.toBigInteger()
                    val yLength = calculateLength(yExpansions, yCoordinate, otherYCoordinate, multiplier.subtract(BigInteger.ONE))

                    pathLengths.add(xLength.add(yLength))
                }
            }
        }

        return pathLengths.sumOf { it }
    }

    private fun calculateLength(
        expansions: ArrayList<BigInteger>,
        coordinate: BigInteger,
        otherCoordinate: BigInteger,
        expansionValue: BigInteger,
    ): BigInteger {
        val addition = expansionValue.multiply(expansions.count { it < coordinate }.toBigInteger())
        val extendedCoordinate = coordinate.add(addition)
        val otherAddition = expansions.count { it < otherCoordinate }.toBigInteger()
        val otherExtendedCoordinate = otherCoordinate.add(expansionValue.multiply(otherAddition))

        return (extendedCoordinate.subtract(otherExtendedCoordinate)).abs()
    }
}
