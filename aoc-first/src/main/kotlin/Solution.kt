import java.math.BigInteger
import java.util.stream.Stream

class Solution {
    fun calculateTotalCalibrationValueByDigit(data: String): BigInteger {
        return data.lines().stream()
            .map { calculateCalibrationValueByDigit(it) }
            .reduce(BigInteger::add)
            .get()
    }

    fun calculateCalibrationValueByDigit(line: String): BigInteger {
        val characters = line.toList()
        val firstDigit = characters.stream()
            .filter { it.isDigit() }
            .map { it.toString() }
            .findFirst().get()

        val lastDigit = characters.reversed().stream()
            .filter { it.isDigit() }
            .map { it.toString() }
            .findFirst().get()

        return BigInteger(firstDigit + lastDigit)
    }

    fun calculateTotalCalibrationValueByRealDigit(data: String): BigInteger {
        return data.lines().stream()
            .map { calculateCalibrationValueByRealDigit(it) }
            .reduce(BigInteger::add)
            .get()
    }

    fun calculateCalibrationValueByRealDigit(line: String): BigInteger {
        val characters = line.toList()
        val reversedLine = line.reversed()
        val firstRealDigitOptional = RealDigit.entries.stream()
            .map { Pair(convertRealDigit(it), line.indexOf(string = it.name, startIndex = 0, ignoreCase = true)) }
            .filter { it.second >= 0 }
            .min({ o1, o2 -> o1.second.compareTo(o2.second) })

        val lastRealDigitOptional = RealDigit.entries.stream()
            .map { Pair(convertRealDigit(it), reversedLine.indexOf(string = it.name.reversed(), startIndex = 0, ignoreCase = true)) }
            .filter { it.second >= 0 }
            .min({ o1, o2 -> o1.second.compareTo(o2.second) })

        val firstDigit = characters.stream()
            .filter { it.isDigit() }
            .map { it.toString() }
            .findFirst()
            .map { Pair(it.toInt(), line.indexOf(string = it, startIndex = 0, ignoreCase = true)) }
            .filter { it.second >= 0 }

        val lastDigit = characters.reversed().stream()
            .filter { it.isDigit() }
            .map { it.toString() }
            .findFirst()
            .map { Pair(it.toInt(), reversedLine.indexOf(string = it, startIndex = 0, ignoreCase = true)) }
            .filter { it.second >= 0 }

        val firstValue = Stream.of(firstRealDigitOptional, firstDigit)
            .filter { it.isPresent }
            .map { it.get() }
            .min({ o1, o2 -> o1.second.compareTo(o2.second) })
            .map { it.first }
            .get()

        val lastValue = Stream.of(lastRealDigitOptional, lastDigit)
            .filter { it.isPresent }
            .map { it.get() }
            .min({ o1, o2 -> o1.second.compareTo(o2.second) })
            .map { it.first }
            .get()

        return BigInteger(firstValue.toString() + lastValue.toString())
    }

    enum class RealDigit {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
    }

    private fun convertRealDigit(realDigit: RealDigit): Int {
        return when (realDigit) {
            RealDigit.ONE -> 1
            RealDigit.TWO -> 2
            RealDigit.THREE -> 3
            RealDigit.FOUR -> 4
            RealDigit.FIVE -> 5
            RealDigit.SIX -> 6
            RealDigit.SEVEN -> 7
            RealDigit.EIGHT -> 8
            RealDigit.NINE -> 9
        }
    }
}
