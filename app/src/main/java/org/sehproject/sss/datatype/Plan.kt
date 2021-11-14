package org.sehproject.sss.datatype

data class Plan(var pid: Int = -1,
                var name: String,
                var startTime: String,
                var endTime: String,
                var location: String,
                var category: String,
                var sync: Boolean,
                var participants: List<User>? = null) {
    init {
        participants = mutableListOf()
                }}
