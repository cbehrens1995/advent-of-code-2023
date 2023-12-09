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
            val result = testee.sumOfExtrapolatedValuesForward(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(114))
        }

        @Test
        fun itWorks() {
            // given
            val exampleData = FileUtil.loadData("input.txt")
            val testee = Solution()

            // when
            val result = testee.sumOfExtrapolatedValuesForward(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(2075724761))
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
            val result = testee.sumOfExtrapolatedValuesBackwards(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(2))
        }

        @Test
        fun itWorks() {
            // given
            val exampleData = FileUtil.loadData("input.txt")
            val testee = Solution()

            // when
            val result = testee.sumOfExtrapolatedValuesBackwards(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(1072))
        }
    }
}
