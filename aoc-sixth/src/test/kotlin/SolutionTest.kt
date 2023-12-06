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
            val result = testee.calculateMargin(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(288))
        }

        @Test
        fun itWorks() {
            // given
            val exampleData = FileUtil.loadData("input.txt")
            val testee = Solution()

            // when
            val result = testee.calculateMargin(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(588588))
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
            val result = testee.partTwo(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(71503))
        }

        @Test
        fun itWorks() {
            // given
            val exampleData = FileUtil.loadData("input.txt")
            val testee = Solution()

            // when
            val result = testee.partTwo(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(34655848))
        }
    }
}
