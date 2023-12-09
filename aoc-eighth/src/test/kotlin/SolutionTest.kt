import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SolutionTest {

    @Nested
    inner class partOne {

        @Test
        fun itWorks_example1() {
            // given
            val exampleData = FileUtil.loadData("example1.txt")
            val testee = Solution()

            // when
            val result = testee.calculateSteps(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(2)
        }

        @Test
        fun itWorks_example2() {
            // given
            val exampleData = FileUtil.loadData("example2.txt")
            val testee = Solution()

            // when
            val result = testee.calculateSteps(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(6)
        }

        @Test
        fun itWorks() {
            // given
            val exampleData = FileUtil.loadData("input.txt")
            val testee = Solution()

            // when
            val result = testee.calculateSteps(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(17621)
        }
    }

    @Nested
    inner class partTwo {

        @Test
        fun itWorks_example() {
            // given
            val exampleData = FileUtil.loadData("example_PartTwo.txt")
            val testee = Solution()

            // when
            val result = testee.calculateStepsForSimultaneousApproach(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(6)
        }

        @Test
        fun itWorks() {
            // given
            val exampleData = FileUtil.loadData("input.txt")
            val testee = Solution()

            // when
            val result = testee.calculateStepsForSimultaneousApproach(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(20685524831999L)
        }
    }
}
