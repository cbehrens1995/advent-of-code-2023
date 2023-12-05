import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.StandardCharsets

object FileUtil {

    @Throws(IOException::class)
    fun loadData(input: String?): String {
        val classLoader = FileUtil::class.java.classLoader
        val file = File(classLoader.getResource(input).file)
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8)
    }
}
