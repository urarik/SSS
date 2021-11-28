package org.sehproject.sss.utils

import org.sehproject.sss.datatype.Plan

class StringParser {
    fun parse(string: String): Plan {
        try {
            val tokens = string.split(",")
            val name: String = tokens[0].trim()
            val location: String = tokens[1].trim()
            val startTime: String = tokens[2].trim() + " " + tokens[3].trim()
            val endTime: String = tokens[4].trim() + " " + tokens[5].trim()
            val category: String = if (tokens[6].trim() == "X") "" else tokens[6].trim()
            return Plan(name=name,
                startTime = startTime,
                endTime = endTime,
                location = location,
                category = category)

        } catch(e: IndexOutOfBoundsException) {
            return Plan(pid = -1, name=string)
        }
    }
}