package org.sehproject.sss

import com.google.api.client.util.DateTime
import org.junit.Test

import org.junit.Assert.*
import org.sehproject.sss.utils.StringParser
import java.sql.Date
import java.text.SimpleDateFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val stringParser = StringParser()

    @Test
    fun addition_isCorrect() {
        //val result = stringParser.parse("회의, 영남대, 2021-11-15, 13:00, 2021-11-16, 14:00, X, X")
        //print(result)

        val format = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val parsed = format.parse("2021-10-12 12:33:50")

        val dateTime = DateTime(parsed)
        print(dateTime)
    }
}