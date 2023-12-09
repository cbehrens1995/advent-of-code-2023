import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
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
            val result = testee.calculateTotalWinnings(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(6440))
        }

        @Test
        fun itWorks() {
            // given
            val exampleData = FileUtil.loadData("input.txt")
            val testee = Solution()

            // when
            val result = testee.calculateTotalWinnings(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(247961593))
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
            val result = testee.calculateTotalWinningsWithJokers(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(5905))
        }

        @Test
        fun itWorks() {
            // given
            val exampleData = FileUtil.loadData("input.txt")
            val testee = Solution()

            // when
            val result = testee.calculateTotalWinningsWithJokers(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(248750699))
        }
    }
}
