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
            val result = testee.calculateLowestLocation(data)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(35))
        }

        @Test
        fun itWorks() {
            // given
            val data = FileUtil.loadData("part1.txt")
            val testee = Solution()

            // when
            val result = testee.calculateLowestLocation(data)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(836040384))
        }
    }

    @Nested
    inner class oartTwo {

        @Test
        fun itWorks_example() {
            // given
            val data = FileUtil.loadData("example.txt")
            val testee = Solution()

            // when
            val result = testee.calculateLowestLocationFromSeedRange(data)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(46))
        }

        @Test
        fun itWorks() {
            // given
            val data = FileUtil.loadData("part1.txt")
            val testee = Solution()

            // when
            val result = testee.calculateLowestLocationFromSeedRange(data)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(836040384))
        }
    }
}
