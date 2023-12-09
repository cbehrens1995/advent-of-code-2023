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
            val result = testee.calculateTotalCalibrationValueByDigit(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(142))
        }

        @Test
        fun itWorks() {
            // given
            val exampleData = FileUtil.loadData("input.txt")
            val testee = Solution()

            // when
            val result = testee.calculateTotalCalibrationValueByDigit(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(54561))
        }
    }

    @Nested
    inner class partTwo {

        @Test
        fun itWorks_example() {
            // given
            val exampleData = FileUtil.loadData("example2.txt")
            val testee = Solution()

            // when
            val result = testee.calculateTotalCalibrationValueByRealDigit(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(281))
        }

        @Test
        fun itWorks() {
            // given
            val exampleData = FileUtil.loadData("input.txt")
            val testee = Solution()

            // when
            val result = testee.calculateTotalCalibrationValueByRealDigit(exampleData)

            // then
            Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(54076))
        }
    }
}
