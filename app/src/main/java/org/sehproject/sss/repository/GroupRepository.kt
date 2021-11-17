package org.sehproject.sss.repository

import org.sehproject.sss.ServerApi
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.*
import org.sehproject.sss.service.GroupService
import retrofit2.Call
import retrofit2.Callback
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
        groupService.requestCreateGroup(group.name, group.explanation, group.color, UserInfo.userId)
            .enqueue(object : Callback<GenericResponse> {
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
                    onResult(-1)
                }
            })
    }

    fun deleteGroup(gid: Int, onResult: (Int) -> Unit) {
        groupService.requestDeleteGroup(gid)
            .enqueue(object : Callback<GenericResponse> {
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
                    onResult(-1)
                }
            })
    }

    fun exitGroup(gid: Int, onResult: (Int) -> Unit) {
        groupService.requestExitGroup(gid, UserInfo.userId)
            .enqueue(object : Callback<GenericResponse> {
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
                    onResult(-1)
                }
            })
    }

    fun editGroup(group: Group, onResult: (Int) -> Unit) {
        groupService.requestEditGroup(group.gid!!, group.name, group.explanation, group.color)
            .enqueue(object : Callback<GenericResponse> {
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
                    onResult(-1)
                }
            })
    }

    fun inviteGroup(gid: Int, userIdList: List<String>, onResult: (Int) -> Unit) {
        groupService.requestInviteGroup(gid, userIdList)
            .enqueue(object : Callback<GenericResponse> {
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
                    onResult(-1)
                }
            })
    }

    fun kickOutGroup(gid: Int, userIdList: List<String>, onResult: (Int) -> Unit) {
        groupService.requestKickOutGroup(gid, userIdList)
            .enqueue(object : Callback<GenericResponse> {
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
                    onResult(-1)
                }
            })
    }

    fun getGroupList(onResult: (Int, List<Group>?) -> Unit) {
        groupService.requestGetGroupList(UserInfo.userId)
            .enqueue(object : Callback<GroupListResponse> {
                override fun onResponse(
                    call: Call<GroupListResponse>,
                    response: Response<GroupListResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        val groupList = response.body()?.groupList
                        onResult(0, groupList)
                    } else {
                        onResult(1, null)
                    }
                }

                override fun onFailure(call: Call<GroupListResponse>, t: Throwable) {
                    onResult(-1, null)
                }
            })
    }

    fun getGroup(gid: Int, onResult: (Int, Group?) -> Unit) {
        groupService.requestGetGroup(gid)
            .enqueue(object : Callback<GroupResponse> {
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
                    onResult(-1, null)
                }
            })
    }

    fun getParticipantList(gid: Int, onResult: (Int, List<User>?) -> Unit) {
        groupService.requestGetParticipantList(gid)
            .enqueue(object : Callback<UserListResponse> {
                override fun onResponse(
                    call: Call<UserListResponse>,
                    response: Response<UserListResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        val participantList = response.body()?.userList
                        onResult(0, participantList)
                    } else {
                        onResult(1, null)
                    }
                }

                override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                    onResult(-1, null)
                }
            })
    }

}