package org.sehproject.sss.repository

import org.sehproject.sss.ServerApi
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.*
import org.sehproject.sss.service.GroupService
import org.sehproject.sss.service.PlanService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlanRepository {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ServerApi.DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var planService: PlanService = retrofit.create(PlanService::class.java)

    fun createPlan(plan: Plan, onResult: (Int) -> Unit) {
        planService.requestCreatePlan(plan.name, plan.name, plan.startTime, plan.endTime, plan.location, plan.category, UserInfo.userId)
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

    fun deletePlan(pid: Int, onResult: (Int) -> Unit) {
        planService.requestDeletePlan(pid)
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

    fun editPlan(plan: Plan, onResult: (Int) -> Unit) {
        planService.requestEditPlan(plan.name, plan.name, plan.startTime, plan.endTime, plan.location, plan.category)
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

    fun completePlan(pid: Int, onResult: (Int) -> Unit) {
        planService.requestCompletePlan(pid, UserInfo.userId)
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

    fun cancelPlan(pid: Int, onResult: (Int) -> Unit) {
        planService.requestCancelPlan(pid, UserInfo.userId)
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

    fun invitePlan(pid: Int, userIdList: List<String>, onResult: (Int) -> Unit)  {
        planService.requestInvitePlan(pid, userIdList)
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

    fun kickOutPlan(pid: Int, userIdList: List<String>, onResult: (Int) -> Unit)  {
        planService.requestKickOutPlan(pid, userIdList)
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

    fun createMemo(memo: Memo, onResult: (Int) -> Unit) {
        planService.requestCreateMemo(memo.pid, UserInfo.userId, memo.memo)
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

    fun deleteMemo(memoId: Int, onResult: (Int) -> Unit) {
        planService.requestDeleteMemo(memoId)
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

    fun makePlanPublic(pid: Int, onResult: (Int) -> Unit) {
        planService.requestMakePlanPublic(pid, UserInfo.userId)
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

    fun getImageOcr(image: Byte, onResult: (Int, String?) -> Unit) {
        planService.requestGetImageOcr(image)
            .enqueue(object : Callback<OcrResponse> {
                override fun onResponse(
                    call: Call<OcrResponse>,
                    response: Response<OcrResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        val recognizedStr = response.body()?.recognizedStr
                        onResult(0, recognizedStr)
                    } else {
                        onResult(1, null)
                    }
                }

                override fun onFailure(call: Call<OcrResponse>, t: Throwable) {
                    onResult(-1, null)
                }
            })
    }

    // isCurrent true : 현재, false : 과거
    fun getPlanList(isCurrent: Boolean, onResult: (Int, List<Plan>?) -> Unit) {
        planService.requestGetPlanList(UserInfo.userId, isCurrent)
            .enqueue(object : Callback<PlanListResponse> {
                override fun onResponse(
                    call: Call<PlanListResponse>,
                    response: Response<PlanListResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        val planList = response.body()?.planList
                        onResult(0, planList)
                    } else {
                        onResult(1, null)
                    }
                }

                override fun onFailure(call: Call<PlanListResponse>, t: Throwable) {
                    onResult(-1, null)
                }
            })
    }

    fun getPlan(pid: Int, onResult: (Int, Plan?) -> Unit) {
        planService.requestGetPlan(pid)
            .enqueue(object : Callback<PlanResponse> {
                override fun onResponse(
                    call: Call<PlanResponse>,
                    response: Response<PlanResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        val plan = response.body()?.plan
                        onResult(0, plan)
                    } else {
                        onResult(1, null)
                    }
                }

                override fun onFailure(call: Call<PlanResponse>, t: Throwable) {
                    onResult(-1, null)
                }
            })
    }

    fun getParticipantList(pid: Int, onResult: (Int, List<User>?) -> Unit) {
        planService.requestGetParticipantList(pid)
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

    fun getMemoList(pid: Int, onResult: (Int, List<Memo>?) -> Unit) {
        planService.requestGetMemoList(pid)
            .enqueue(object : Callback<MemoListResponse> {
                override fun onResponse(
                    call: Call<MemoListResponse>,
                    response: Response<MemoListResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        val memoList = response.body()?.memoList
                        onResult(0, memoList)
                    } else {
                        onResult(1, null)
                    }
                }

                override fun onFailure(call: Call<MemoListResponse>, t: Throwable) {
                    onResult(-1, null)
                }
            })
    }
}