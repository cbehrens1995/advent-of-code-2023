import java.math.BigInteger
import java.util.Optional

class Solution {

    fun sumOfExtrapolatedValuesForward(data: String): BigInteger {
        return sumOfExtrapolatedValues(data, true)
    }

    fun sumOfExtrapolatedValuesBackwards(data: String): BigInteger {
        return sumOfExtrapolatedValues(data, false)
    }

    fun sumOfExtrapolatedValues(data: String, isExtrapolationForward: Boolean): BigInteger {
        val extrapolatedValues = data.lines()
            .map { calculateExtrapolatedValue(it, isExtrapolationForward) }

        return extrapolatedValues.sumOf { it }
    }

    private fun calculateExtrapolatedValue(line: String, isExtrapolationForward: Boolean): BigInteger {
        val values = line.split(" ")
            .map { it.toBigInteger() }
            .toList()

        return calculateExtrapolation(values, isExtrapolationForward)
    }

    private fun calculateExtrapolation(previousLine: List<BigInteger>, isExtrapolationForward: Boolean): BigInteger {
        val nextLine = ArrayList<BigInteger>()
        var previousValueOptional: Optional<BigInteger> = Optional.empty()
        for (value in previousLine) {
            if (previousValueOptional.isEmpty) {
                previousValueOptional = Optional.of(value)
                continue
            }

            nextLine.add(value.subtract(previousValueOptional.get()))
            previousValueOptional = Optional.of(value)
        }

        if (isExtrapolationForward) {
            return calculateExtrapolationForward(previousLine, nextLine)
        }

        return calculateExtrapolationBackward(previousLine, nextLine)
    }

    private fun calculateExtrapolationForward(
        previousLine: List<BigInteger>,
        nextLine: ArrayList<BigInteger>,
    ): BigInteger {
        val lastValueInLine = previousLine.last()
        if (nextLine.stream()
                .allMatch { it == BigInteger.ZERO }
        ) {
            return lastValueInLine
        }

        return calculateExtrapolation(nextLine, true) + lastValueInLine
    }

    private fun calculateExtrapolationBackward(
        previousLine: List<BigInteger>,
        nextLine: ArrayList<BigInteger>,
    ): BigInteger {
        val firstValue = previousLine.first()
        if (nextLine.stream()
                .allMatch { it == BigInteger.ZERO }
        ) {
            return firstValue
        }

        return firstValue - calculateExtrapolation(nextLine, false)
    }
}
