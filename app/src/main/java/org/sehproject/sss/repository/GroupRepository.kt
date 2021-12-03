package org.sehproject.sss.repository

import android.util.Log
import org.sehproject.sss.ServerApi
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.*
import org.sehproject.sss.service.GroupService
import org.sehproject.sss.utils.CallbackWithRetry
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GroupRepository {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ServerApi.DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var groupService: GroupService = retrofit.create(GroupService::class.java)

    fun createGroup(group: Group, onResult: (Int) -> Unit) {
        val createGroupCall = groupService.requestCreateGroup(
            group.name,
            group.explanation,
            group.color,
            UserInfo.userId
        )
        createGroupCall.enqueue(object : CallbackWithRetry<GenericResponse>(createGroupCall) {
            override fun onResponse(
                call: Call<GenericResponse>,
                response: Response<GenericResponse>
            ) {
                val code = response.body()?.code
                if (code == 0) {
                    onResult(0)
                } else {
                    onResult(1)
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1)
                }
            }
        })
    }

    fun deleteGroup(gid: Int, onResult: (Int) -> Unit) {
        val deleteGroupCall = groupService.requestDeleteGroup(gid)
        deleteGroupCall.enqueue(object : CallbackWithRetry<GenericResponse>(deleteGroupCall) {
            override fun onResponse(
                call: Call<GenericResponse>,
                response: Response<GenericResponse>
            ) {
                val code = response.body()?.code
                if (code == 0) {
                    onResult(0)
                } else {
                    onResult(1)
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1)
                }
            }
        })
    }

    fun exitGroup(gid: Int, onResult: (Int) -> Unit) {
        val exitGroupCall = groupService.requestExitGroup(gid, UserInfo.userId)
        exitGroupCall.enqueue(object : CallbackWithRetry<GenericResponse>(exitGroupCall) {
            override fun onResponse(
                call: Call<GenericResponse>,
                response: Response<GenericResponse>
            ) {
                val code = response.body()?.code
                if (code == 0) {
                    onResult(0)
                } else {
                    onResult(1)
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1)
                }
            }
        })
    }

    fun editGroup(group: Group, onResult: (Int) -> Unit) {
        val editGroupCall =
            groupService.requestEditGroup(group.gid!!, group.name, group.explanation, group.color)
        editGroupCall.enqueue(object : CallbackWithRetry<GenericResponse>(editGroupCall) {
            override fun onResponse(
                call: Call<GenericResponse>,
                response: Response<GenericResponse>
            ) {
                val code = response.body()?.code
                if (code == 0) {
                    onResult(0)
                } else {
                    onResult(1)
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1)
                }
            }
        })
    }

    fun inviteGroup(gid: Int, userIdList: List<String>, onResult: (Int) -> Unit) {
        val inviteGroupCall = groupService.requestInviteGroup(gid, UserInfo.userId, userIdList)
        inviteGroupCall.enqueue(object : CallbackWithRetry<GenericResponse>(inviteGroupCall) {
            override fun onResponse(
                call: Call<GenericResponse>,
                response: Response<GenericResponse>
            ) {
                val code = response.body()?.code
                if (code == 0) {
                    onResult(0)
                } else {
                    onResult(1)
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1)
                }
            }
        })
    }

    fun acceptGroup(gid: Int, isAccept: Boolean, onResult: (Int) -> Unit) {
        val acceptPlanCall = groupService.requestAcceptGroup(gid, UserInfo.userId, isAccept)
        acceptPlanCall.enqueue(object : CallbackWithRetry<GenericResponse>(acceptPlanCall) {
            override fun onResponse(
                call: Call<GenericResponse>,
                response: Response<GenericResponse>
            ) {
                val code = response.body()?.code
                if (code == 0) {
                    onResult(0)
                } else {
                    onResult(1)
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1)
                }
            }
        })
    }

    fun kickOutGroup(gid: Int, userIdList: List<String>, onResult: (Int) -> Unit) {
        val kickOutGroupCall = groupService.requestKickOutGroup(gid, userIdList)
        kickOutGroupCall.enqueue(object : CallbackWithRetry<GenericResponse>(kickOutGroupCall) {
            override fun onResponse(
                call: Call<GenericResponse>,
                response: Response<GenericResponse>
            ) {
                val code = response.body()?.code
                if (code == 0) {
                    onResult(0)
                } else {
                    onResult(1)
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1)
                }
            }
        })
    }

    fun getGroupList(onResult: (Int, List<Group>?) -> Unit) {
        val getGroupListCall = groupService.requestGetGroupList(UserInfo.userId)
        getGroupListCall.enqueue(object :
            CallbackWithRetry<GroupListResponse>(getGroupListCall) {
            override fun onResponse(
                call: Call<GroupListResponse>,
                response: Response<GroupListResponse>
            ) {
                val code = response.body()?.code
                if (code == 0) {
                    val groupList = response.body()?.group
                    onResult(0, groupList)
                } else {
                    onResult(1, null)
                }
            }

            override fun onFailure(call: Call<GroupListResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1, null)
                }
            }
        })
    }

    fun getGroup(gid: Int, onResult: (Int, Group?) -> Unit) {
        val getGroupCall = groupService.requestGetGroup(gid)
        getGroupCall.enqueue(object : CallbackWithRetry<GroupResponse>(getGroupCall) {
            override fun onResponse(
                call: Call<GroupResponse>,
                response: Response<GroupResponse>
            ) {
                val code = response.body()?.code
                if (code == 0) {
                    val group = response.body()?.group
                    onResult(0, group)
                } else {
                    onResult(1, null)
                }
            }

            override fun onFailure(call: Call<GroupResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1, null)
                }
            }
        })
    }

    fun getParticipantList(gid: Int, onResult: (Int, List<User>?) -> Unit) {
        val getParticipantListCall = groupService.requestGetParticipantList(gid)
        getParticipantListCall.enqueue(object :
            CallbackWithRetry<UserListResponse>(getParticipantListCall) {
            override fun onResponse(
                call: Call<UserListResponse>,
                response: Response<UserListResponse>
            ) {
                val code = response.body()?.code
                if (code == 0) {
                    val participantList = response.body()?.user
                    onResult(0, participantList)
                } else {
                    onResult(1, null)
                }
            }

            override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1, null)
                }
            }
        })
    }
}