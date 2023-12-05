import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

private const val EXAMPLE_DATA = "test_data.txt"

private const val PART_1_DATA = "part1.txt"

class Solution1Test {

    @Test
    fun itWorks_Example() {
        // given
        val testData = FileUtil.loadData(EXAMPLE_DATA)
        val testee = Solution1()

        // when
        val result = testee.calculateEngines(testData)

        // given
        Assertions.assertThat(result).isEqualByComparingTo(BigDecimal(4361))
    }

    @Test
    fun itWorks_Part1() {
        // given
        val testData = FileUtil.loadData(PART_1_DATA)
        val testee = Solution1()

        // when
        val result = testee.calculateEngines(testData)

        // given
        Assertions.assertThat(result).isEqualByComparingTo(BigDecimal(557705))
    }
}
