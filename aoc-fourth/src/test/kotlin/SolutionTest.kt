import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger

class SolutionTest {

    @Nested
    inner class partOne {

        @Test
        fun itWorks_example() {
            // given
            val exampleData = FileUtil.loadData("example.txt")
            val testee = Solution()

            // when
            val result = testee.calculatePoints(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigDecimal(13))
        }

        @Test
        fun itWorks() {
            // given
            val exampleData = FileUtil.loadData("part1.txt")
            val testee = Solution()

            // when
            val result = testee.calculatePoints(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigDecimal(23673))
        }
    }

    @Nested
    inner class partTwo {
        @Test
        fun itWorks_example() {
            // given
            val exampleData = FileUtil.loadData("example.txt")
            val testee = Solution()

            // when
            val result = testee.calculateScratchCards(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(30))
        }

        @Test
        fun itWorks() {
            // given
            val exampleData = FileUtil.loadData("part1.txt")
            val testee = Solution()

            // when
            val result = testee.calculateScratchCards(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(12263631))
        }
    }
}
