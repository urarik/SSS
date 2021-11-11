package org.sehproject.sss.datatype

data class Plan(var pid: Int,
                var name: String,
                var date: String,
                var startTime: String,
                var endTime: String,
                var location: String,
                var category: String,
                var participants: List<User>)
