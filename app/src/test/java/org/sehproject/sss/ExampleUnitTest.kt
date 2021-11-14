package org.sehproject.sss

import org.junit.Test

import org.junit.Assert.*
import org.sehproject.sss.utils.StringParser

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val stringParser = StringParser()

    @Test
    fun addition_isCorrect() {
        val result = stringParser.parse("회의 영남대 2021/11/15 13:00 2021/11/16 14:00 X X ")
        print(result)
    }
}