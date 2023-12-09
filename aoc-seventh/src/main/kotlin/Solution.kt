import java.math.BigInteger
import java.util.Optional
import java.util.function.Function

class Solution {
    fun calculateTotalWinnings(data: String): BigInteger {
        return data.lines()
            .map { getCardsBidPair(it) }
            .sortedWith { p1, p2 -> compareCards(p1.first, p2.first) }
            .withIndex()
            .sumOf { it.index.toBigInteger().add(BigInteger.ONE).multiply(it.value.second) }
    }

    fun getCardsBidPair(line: String): Pair<List<Char>, BigInteger> {
        val parts = line.split(" ")
        val bid = parts[1].toBigInteger()
        val cards = parts[0].toList()
        return Pair(cards, bid)
    }

    fun compareCards(cards: List<Char>, otherCards: List<Char>): Int {
        val isFiveOfKindCondition = checkCondition(cards, otherCards, this::isFiveOfAKind)
        if (isFiveOfKindCondition.isPresent) {
            return isFiveOfKindCondition.get()
        }

        val isFourOfKindCondition = checkCondition(cards, otherCards, this::isFourOfAKind)
        if (isFourOfKindCondition.isPresent) {
            return isFourOfKindCondition.get()
        }

        val isFullHouseCondition = checkCondition(cards, otherCards, this::isFullHouse)
        if (isFullHouseCondition.isPresent) {
            return isFullHouseCondition.get()
        }

        val isThreeOfAKindCondition = checkCondition(cards, otherCards, this::isThreeOfAKind)
        if (isThreeOfAKindCondition.isPresent) {
            return isThreeOfAKindCondition.get()
        }

        val isTwoPairsCondition = checkCondition(cards, otherCards, this::isTwoPairs)
        if (isTwoPairsCondition.isPresent) {
            return isTwoPairsCondition.get()
        }

        val isOnePairsCondition = checkCondition(cards, otherCards, this::isOnePair)
        if (isOnePairsCondition.isPresent) {
            return isOnePairsCondition.get()
        }

        return compareCardsFallback(cards, otherCards)
    }

    fun checkCondition(cards: List<Char>, otherCards: List<Char>, checkFunction: Function<List<Char>, Boolean>): Optional<Int> {
        if (checkFunction.apply(cards) && !checkFunction.apply(otherCards)) {
            return Optional.of(1)
        } else if (!checkFunction.apply(cards) && checkFunction.apply(otherCards)) {
            return Optional.of(-1)
        } else if (checkFunction.apply(cards) && checkFunction.apply(otherCards)) {
            return Optional.of(compareCardsFallback(cards, otherCards))
        }

        return Optional.empty<Int>()
    }

    fun checkConditionWithJokers(
        jokerReplacedCards: List<Char>,
        jokerReplacedOtherCards: List<Char>,
        originalCards: List<Char>,
        originalOtherCards: List<Char>,
        checkFunction: Function<List<Char>, Boolean>,
    ): Optional<Int> {
        if (checkFunction.apply(jokerReplacedCards) && !checkFunction.apply(jokerReplacedOtherCards)) {
            return Optional.of(1)
        } else if (!checkFunction.apply(jokerReplacedCards) && checkFunction.apply(jokerReplacedOtherCards)) {
            return Optional.of(-1)
        } else if (checkFunction.apply(jokerReplacedCards) && checkFunction.apply(jokerReplacedOtherCards)) {
            return Optional.of(compareCardsFallbackWithJokers(originalCards, originalOtherCards))
        }

        return Optional.empty<Int>()
    }

    private fun isFiveOfAKind(cards: List<Char>): Boolean {
        return cards.distinct().size == 1
    }

    private fun isFourOfAKind(cards: List<Char>): Boolean {
        return cards.groupingBy { it }
            .eachCount()
            .any { it.value == 4 }
    }

    private fun isFullHouse(cards: List<Char>): Boolean {
        val countByChar = cards.groupingBy { it }
            .eachCount()

        return countByChar.any { it.value == 3 } && countByChar.any { it.value == 2 }
    }

    private fun isThreeOfAKind(cards: List<Char>): Boolean {
        val countByChar = cards.groupingBy { it }
            .eachCount()

        return countByChar.any { it.value == 3 } && countByChar.filter { it.value == 1 }.count() == 2
    }

    private fun isTwoPairs(cards: List<Char>): Boolean {
        return cards.groupingBy { it }
            .eachCount()
            .filter { it.value == 2 }
            .count() == 2
    }

    private fun isOnePair(cards: List<Char>): Boolean {
        return cards.groupingBy { it }
            .eachCount()
            .filter { it.value == 2 }
            .count() == 1
    }

    fun compareCardsFallback(cards: List<Char>, otherCards: List<Char>): Int {
        for ((index, card) in cards.withIndex()) {
            val otherCard = otherCards[index]
            if (otherCard == card) {
                continue
            }

            val cardValue = extractCardValue(card)
            val otherCardValue = extractCardValue(otherCard)

            if (cardValue > otherCardValue) {
                return 1
            }

            return -1
        }

        return 0
    }

    fun compareCardsFallbackWithJokers(cards: List<Char>, otherCards: List<Char>): Int {
        for ((index, card) in cards.withIndex()) {
            val otherCard = otherCards[index]
            if (otherCard == card) {
                continue
            }

            val cardValue = extractCardValueWithJokers(card)
            val otherCardValue = extractCardValueWithJokers(otherCard)

            if (cardValue > otherCardValue) {
                return 1
            }

            return -1
        }

        return 0
    }

    private fun extractCardValue(card: Char): Int {
        if (card.isDigit()) {
            return card.toString().toInt()
        }

        return PictureCardValue.valueOf(card.toString()).value
    }

    private fun extractCardValueWithJokers(card: Char): Int {
        if (card.isDigit()) {
            return card.toString().toInt()
        }

        return PictureCardValue.valueOf(card.toString()).jokerVariationValue
    }

    fun calculateTotalWinningsWithJokers(data: String): BigInteger {
        return data.lines()
            .map { getCardsBidPair(it) }
            .sortedWith { p1, p2 -> compareCardsWithJokers(p1.first, p2.first) }
            .withIndex()
            .sumOf { it.index.toBigInteger().add(BigInteger.ONE).multiply(it.value.second) }
    }

    fun calculateJokerReplacedCards(cards: List<Char>): List<Char> {
        val jokerCount = cards.stream()
            .map { it.toString() }
            .filter { it.contains("J") }
            .count()

        val nonJokerCards = cards.stream()
            .map { it.toString() }
            .filter { !it.contains("J") }
            .map { it.single() }
            .distinct()
            .toList()
        if (jokerCount == 5L) {
            return listOf("A", "A", "A", "A", "A").stream()
                .map { it.single() }
                .toList()
        }

        if (jokerCount == 4L) {
            val originalCard = nonJokerCards[0]
            return listOf(originalCard, originalCard, originalCard, originalCard, originalCard)
        }

        if (jokerCount == 3L) {
            if (nonJokerCards.count() == 1) {
                val originalCard = nonJokerCards[0]
                return listOf(originalCard, originalCard, originalCard, originalCard, originalCard)
            }

            val firstNonJokerCard = nonJokerCards[0]
            val secondNonJokerCard = nonJokerCards[1]
            return listOf(firstNonJokerCard, secondNonJokerCard, secondNonJokerCard, secondNonJokerCard, secondNonJokerCard)
        }

        if (jokerCount == 2L) {
            if (nonJokerCards.count() == 1) {
                val originalCard = nonJokerCards[0]
                return listOf(originalCard, originalCard, originalCard, originalCard, originalCard)
            }

            if (nonJokerCards.count() == 2) {
                val pairChar = cards.groupingBy { it }
                    .eachCount()
                    .filter { it.value == 2 }
                    .map { it.key }[0]
                val otherChar = nonJokerCards.filter { it != pairChar }.first()
                return listOf(pairChar, pairChar, pairChar, pairChar, otherChar)
            }

            return listOf(nonJokerCards[0], nonJokerCards[0], nonJokerCards[0], nonJokerCards[1], nonJokerCards[2])
        }

        if (jokerCount == 1L) {
            if (nonJokerCards.count() == 1) {
                val originalCard = nonJokerCards[0]
                return listOf(originalCard, originalCard, originalCard, originalCard, originalCard)
            }

            if (nonJokerCards.count() == 2) {
                val nonJokerCardsByCount = cards
                    .map { it.toString() }
                    .filter { !it.contains("J") }
                    .map { it.single() }
                    .groupingBy { it }
                    .eachCount()

                if (nonJokerCardsByCount.containsValue(1)) {
                    val singleCard = nonJokerCardsByCount.filter { it.value == 1 }.keys.first()
                    val tripleCard = nonJokerCardsByCount.filter { it.value == 3 }.keys.first()
                    return listOf(tripleCard, tripleCard, tripleCard, tripleCard, singleCard)
                }

                if (nonJokerCardsByCount.containsValue(2)) {
                    val firstPairCard = nonJokerCardsByCount.filter { it.value == 2 }.keys.first()
                    val otherCard = nonJokerCardsByCount.filter { it.key != firstPairCard }.keys.first()
                    return listOf(firstPairCard, firstPairCard, firstPairCard, otherCard, otherCard)
                }
            }

            if (nonJokerCards.count() == 3) {
                val pairChar = cards.groupingBy { it }
                    .eachCount()
                    .filter { it.value == 2 }
                    .map { it.key }[0]

                val nonPairs = cards.groupingBy { it }
                    .eachCount()
                    .filter { it.value != 2 }
                    .map { it.key }
                return listOf(pairChar, pairChar, pairChar, nonPairs[0], nonPairs[1])
            }

            if (nonJokerCards.count() == 4) {
                return listOf(nonJokerCards[0], nonJokerCards[0], nonJokerCards[1], nonJokerCards[2], nonJokerCards[3])
            }
        }

        return cards
    }

    fun compareCardsWithJokers(cards: List<Char>, otherCards: List<Char>): Int {
        val jokerReplacedCards = calculateJokerReplacedCards(cards)
        val jokerReplacedOtherCards = calculateJokerReplacedCards(otherCards)
        val isFiveOfKindCondition = checkConditionWithJokers(jokerReplacedCards, jokerReplacedOtherCards, cards, otherCards, this::isFiveOfAKind)
        if (isFiveOfKindCondition.isPresent) {
            return isFiveOfKindCondition.get()
        }

        val isFourOfKindCondition = checkConditionWithJokers(jokerReplacedCards, jokerReplacedOtherCards, cards, otherCards, this::isFourOfAKind)
        if (isFourOfKindCondition.isPresent) {
            return isFourOfKindCondition.get()
        }

        val isFullHouseCondition = checkConditionWithJokers(jokerReplacedCards, jokerReplacedOtherCards, cards, otherCards, this::isFullHouse)
        if (isFullHouseCondition.isPresent) {
            return isFullHouseCondition.get()
        }

        val isThreeOfAKindCondition = checkConditionWithJokers(jokerReplacedCards, jokerReplacedOtherCards, cards, otherCards, this::isThreeOfAKind)
        if (isThreeOfAKindCondition.isPresent) {
            return isThreeOfAKindCondition.get()
        }

        val isTwoPairsCondition = checkConditionWithJokers(jokerReplacedCards, jokerReplacedOtherCards, cards, otherCards, this::isTwoPairs)
        if (isTwoPairsCondition.isPresent) {
            return isTwoPairsCondition.get()
        }

        val isOnePairsCondition = checkConditionWithJokers(jokerReplacedCards, jokerReplacedOtherCards, cards, otherCards, this::isOnePair)
        if (isOnePairsCondition.isPresent) {
            return isOnePairsCondition.get()
        }

        return compareCardsFallbackWithJokers(cards, otherCards)
    }

    enum class PictureCardValue(val value: Int, val jokerVariationValue: Int) {
        A(14, 14), K(13, 13), Q(12, 12), J(11, 0), T(10, 10)
    }
}
