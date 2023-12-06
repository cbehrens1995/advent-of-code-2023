import org.apache.commons.lang3.StringUtils
import java.math.BigInteger
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Collectors

class Solution {

    fun calculateMargin(data: String): BigInteger {
        val filteredLines = data.lines().stream()
            .map { it.split(":") }
            .map { it.getOrNull(1) }
            .filter { StringUtils.isNotBlank(it) }
            .map { it!!.split(" ") }
            .toList()

        val timeToDistancePairs = ArrayList<Pair<BigInteger, BigInteger>>()
        val times = filteredLines.get(0).stream()
            .filter { StringUtils.isNotBlank(it) }
            .map { BigInteger(it) }
            .toList()

        val distances = filteredLines.get(1).stream()
            .filter { StringUtils.isNotBlank(it) }
            .map { BigInteger(it) }
            .toList()

        for ((index, time) in times.withIndex()) {
            val timeToDistancePair = Pair(time, distances.get(index))
            timeToDistancePairs.add(timeToDistancePair)
        }

        return timeToDistancePairs.stream()
            .map { pair -> calculateWinnableOptions(pair.first, pair.second) }
            .reduce { t, u -> t.multiply(u) }.get()
    }

    fun partTwo(data: String): BigInteger {
        val filteredLines = data.lines().stream()
            .map { it.split(":") }
            .map { it.getOrNull(1) }
            .filter { StringUtils.isNotBlank(it) }
            .map { it!!.split(" ") }
            .map { it.stream().collect(Collectors.joining()) }
            .map { BigInteger(it) }
            .toList()

        return calculateWinnableOptions(filteredLines.get(0), filteredLines.get(1))
//
    }

    fun calculateWinnableOptions(time: BigInteger, distance: BigInteger): BigInteger {
        val winnableOptions = AtomicInteger()
        for (i in 0..time.toInt()) {
            val remainingTime = time.minus(i.toBigInteger())
            val speed = i.toBigInteger()

            val distanceTravelled = speed.multiply(remainingTime)

            if (distanceTravelled > distance) {
                winnableOptions.incrementAndGet()
            }
        }

        return winnableOptions.get().toBigInteger()
    }
}
