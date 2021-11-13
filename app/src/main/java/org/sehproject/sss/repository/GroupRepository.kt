package org.sehproject.sss.repository

import org.sehproject.sss.datatype.Friend

class GroupRepository {

    fun getFriendList(userId: String, onResult: (List<Friend>) -> Unit) {
        val list = mutableListOf<Friend>()
        list.add(Friend("a", "keum", false, true))
        list.add(Friend("b", "kim", true, false))
        list.add(Friend("c", "kang", false, true))
        list.add(Friend("d", "oh", false, true))
        list.add(Friend("e", "song", true, false))
        list.add(Friend("f", "jun", false, true))
        list.add(Friend("g", "koo", false, false))
        list.add(Friend("h", "tang", true, false))
        list.add(Friend("i", "tong", true, true))
        list.add(Friend("j", "gooo", true, false))

        onResult(list)
    }
}