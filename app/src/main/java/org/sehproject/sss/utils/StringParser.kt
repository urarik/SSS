package org.sehproject.sss.utils

import org.sehproject.sss.datatype.Plan

class StringParser {
    fun parse(string: String): Plan {
        val tokens = string.split(", ")
        val name: String = tokens[0]
        val location: String = tokens[1]
        val startTime: String = tokens[2] + " " + tokens[3]
        val endTime: String = tokens[4] + " " + tokens[5]
        val category: String = if(tokens[6] == "X") "" else tokens[6]
        val calender: Boolean = tokens[7] == "O"

        return Plan(name=name,
            startTime = startTime,
            endTime = endTime,
            location = location,
            category = category,
            sync = calender)
    }
}