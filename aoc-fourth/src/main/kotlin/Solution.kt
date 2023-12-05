import org.apache.commons.lang3.StringUtils
import java.math.BigDecimal
import java.math.BigInteger
import java.util.stream.Collectors

class Solution {

    fun calculatePoints(data: String): BigDecimal {
        val cards = data.lines()

        val points = ArrayList<BigDecimal>()
        for (card in cards) {
            val countOfMatchingNumbers = countMatchingNumbers(card)

            if (countOfMatchingNumbers > 0) {
                val winningPoint = BigDecimal.TWO.pow(countOfMatchingNumbers - 1)
                points.add(winningPoint)
            }
        }

        return points.sumOf { it }
    }

    private fun countMatchingNumbers(card: String): Int {
        val lineWithoutCardBeginning = card.split(":").get(1)
        val numbersSplit = lineWithoutCardBeginning.split("|")

        val winningNumbers = numbersSplit.get(0).split(" ").stream()
            .filter { StringUtils.isNotBlank(it) }
            .collect(Collectors.toSet())

        val ownNumbers = numbersSplit.get(1).split(" ").stream()
            .filter { StringUtils.isNotBlank(it) }
            .collect(Collectors.toSet())

        val countOfMatchingNumbers = winningNumbers.intersect(ownNumbers).size
        return countOfMatchingNumbers
    }

    fun calculateScratchCards(data: String): BigInteger {
        val cards = data.lines()

        val countByCards = HashMap<Int, BigInteger>()
        for (i in 1..cards.size) {
            countByCards[i] = BigInteger.ONE
        }

        for ((index, card) in cards.withIndex()) {
            val currentCardNumber = index + 1
            val countMatchingNumbers = countMatchingNumbers(card)

            if (countMatchingNumbers > 0) {
                val multiplier = countByCards.get(currentCardNumber)

                for (i in 1..countMatchingNumbers) {
                    countByCards.compute(currentCardNumber + i) { _, it -> BigInteger.ONE.multiply(multiplier).add(it) }
                }
            }
        }

        return countByCards.values.sumOf { it }
    }
}
