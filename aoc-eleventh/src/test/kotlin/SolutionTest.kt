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
            val data = FileUtil.loadData("example.txt")
            val testee = Solution()

            // when
            val result = testee.calculateSumOfAllPaths(data)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(374))
        }

        @Test
        fun itWorks() {
            // given
            val data = FileUtil.loadData("input.txt")
            val testee = Solution()

            // when
            val result = testee.calculateSumOfAllPaths(data)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(9799681))
        }
    }

    @Nested
    inner class partTwo {

        @Test
        fun itWorks_example_one() {
            // given
            val data = FileUtil.loadData("example.txt")
            val testee = Solution()

            // when
            val result = testee.calculateSumOfAllPathsExpendedUniverse(data, BigInteger.TWO)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(374))
        }

        @Test
        fun itWorks_example_ten() {
            // given
            val data = FileUtil.loadData("example.txt")
            val testee = Solution()

            // when
            val result = testee.calculateSumOfAllPathsExpendedUniverse(data, BigInteger.TEN)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(1030))
        }

        @Test
        fun itWorks_example_hundred() {
            // given
            val data = FileUtil.loadData("example.txt")
            val testee = Solution()

            // when
            val result = testee.calculateSumOfAllPathsExpendedUniverse(data, BigInteger.TEN.multiply(BigInteger.TEN))

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(8410))
        }

        @Test
        fun itWorks() {
            // given
            val data = FileUtil.loadData("input.txt")
            val testee = Solution()

            // when
            val result = testee.calculateSumOfAllPathsExpendedUniverse(data, BigInteger.valueOf(1000000L))

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(513171773355))
        }
    }
}
