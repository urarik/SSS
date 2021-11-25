package org.sehproject.sss.repository

import android.graphics.Bitmap
import android.util.Base64
import com.google.android.gms.tasks.Task
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Event
import com.google.firebase.functions.FirebaseFunctions
import com.google.gson.*
import org.sehproject.sss.ServerApi
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.*
import org.sehproject.sss.service.PlanService
import org.sehproject.sss.utils.CallbackWithRetry
import org.sehproject.sss.utils.CreateEventTask
import org.sehproject.sss.utils.StringParser
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream

class PlanRepository {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ServerApi.DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var planService: PlanService = retrofit.create(PlanService::class.java)

    fun createPlan(plan: Plan, onResult: (Int) -> Unit) {
        val createPlanCall = planService.requestCreatePlan(
            plan.name,
            plan.name,
            plan.startTime,
            plan.endTime,
            plan.location,
            plan.category,
            UserInfo.userId
        )
        createPlanCall.enqueue(object : CallbackWithRetry<GenericResponse>(createPlanCall) {
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

    fun deletePlan(pid: Int, onResult: (Int) -> Unit) {
        val deletePlanCall = planService.requestDeletePlan(pid)
        deletePlanCall.enqueue(object : CallbackWithRetry<GenericResponse>(deletePlanCall) {
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

    fun editPlan(plan: Plan, onResult: (Int) -> Unit) {
        val editPlanCall = planService.requestEditPlan(
            plan.name,
            plan.name,
            plan.startTime,
            plan.endTime,
            plan.location,
            plan.category
        )
        editPlanCall.enqueue(object : CallbackWithRetry<GenericResponse>(editPlanCall) {
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

    fun completePlan(pid: Int, onResult: (Int) -> Unit) {
        val completePlanCall = planService.requestCompletePlan(pid)
        completePlanCall.enqueue(object : CallbackWithRetry<GenericResponse>(completePlanCall) {
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

    fun cancelPlan(pid: Int, onResult: (Int) -> Unit) {
        val cancelPlanCall = planService.requestCancelPlan(pid, UserInfo.userId)
        cancelPlanCall.enqueue(object : CallbackWithRetry<GenericResponse>(cancelPlanCall) {
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

    fun invitePlan(pid: Int, userIdList: List<String>, onResult: (Int) -> Unit) {
        val invitePlanCall = planService.requestInvitePlan(pid, userIdList)
        invitePlanCall.enqueue(object : CallbackWithRetry<GenericResponse>(invitePlanCall) {
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

    fun acceptPlan(pid: Int, isAccept: Boolean, onResult: (Int) -> Unit) {
        val acceptPlanCall = planService.requestAcceptPlan(pid, UserInfo.userId, isAccept)
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

    fun kickOutPlan(pid: Int, userIdList: List<String>, onResult: (Int) -> Unit) {
        val kickOutPlanCall = planService.requestKickOutPlan(pid, userIdList)
        kickOutPlanCall.enqueue(object : CallbackWithRetry<GenericResponse>(kickOutPlanCall) {
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

    fun createMemo(memo: Memo, onResult: (Int) -> Unit) {
        val createMemoCall = planService.requestCreateMemo(memo.pid, UserInfo.userId, memo.memo)
        createMemoCall.enqueue(object : CallbackWithRetry<GenericResponse>(createMemoCall) {
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

    fun deleteMemo(pid: Int, onResult: (Int) -> Unit) {
        val deleteMemoCall = planService.requestDeleteMemo(pid, UserInfo.userId)
        deleteMemoCall.enqueue(object : CallbackWithRetry<GenericResponse>(deleteMemoCall) {
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

    fun setPlanVisibility(pid: Int, visibility: Boolean, onResult: (Int) -> Unit) {
        val makePlanPublicCall = planService.requestSetPlanVisibility(pid, visibility)
        makePlanPublicCall.enqueue(object : CallbackWithRetry<GenericResponse>(makePlanPublicCall) {
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

    fun getImageOcr(bitmap: Bitmap, onResult: (Int, String?) -> Unit) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        val base64encoded = Base64.encodeToString(imageBytes, Base64.NO_WRAP)

        // Create json request to cloud vision
        val request = JsonObject()
        // Add image to request
        val image = JsonObject()
        image.add("content", JsonPrimitive(base64encoded))
        request.add("image", image)
        //Add features to the request
        val feature = JsonObject()
        feature.add("type", JsonPrimitive("TEXT_DETECTION"))
        // Alternatively, for DOCUMENT_TEXT_DETECTION:
        // feature.add("type", JsonPrimitive("DOCUMENT_TEXT_DETECTION"))
        val features = JsonArray()
        features.add(feature)
        request.add("features", features)

        val imageContext = JsonObject()
        val languageHints = JsonArray()
        languageHints.add("ko")
        imageContext.add("languageHints", languageHints)
        request.add("imageContext", imageContext)

        annotateImage(request.toString())
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    onResult(1, null)
                } else {
                    val annotation = task.result!!.asJsonArray[0].asJsonObject["fullTextAnnotation"].asJsonObject
                    val string = annotation["text"].asString
                    onResult(0, string)
                }
            }
    }

    private fun annotateImage(requestJson: String): Task<JsonElement> {
        val functions = FirebaseFunctions.getInstance()

        return functions
            .getHttpsCallable("annotateImage")
            .call(requestJson)
            .continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then result will throw an Exception which will be
                // propagated down.
                val result = task.result?.data
                JsonParser.parseString(Gson().toJson(result))
            }
    }

    // isCurrent true : 현재, false : 과거
    fun getPlanList(isCurrent: Boolean, onResult: (Int, List<Plan>?) -> Unit) {
        val getPlanListCall = planService.requestGetPlanList(UserInfo.userId, isCurrent)
        getPlanListCall.enqueue(object : CallbackWithRetry<PlanListResponse>(getPlanListCall) {
            override fun onResponse(
                call: Call<PlanListResponse>,
                response: Response<PlanListResponse>
            ) {
                val code = response.body()?.code
                if (code == 0) {
                    val planList = response.body()?.plan
                    onResult(0, planList)
                } else {
                    onResult(1, null)
                }
            }

            override fun onFailure(call: Call<PlanListResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1, null)
                }
            }
        })
    }

    fun getPlan(pid: Int, onResult: (Int, Plan?) -> Unit) {
        val getPlanCall = planService.requestGetPlan(pid)
        getPlanCall.enqueue(object : CallbackWithRetry<PlanResponse>(getPlanCall) {
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
                super.onFailure {
                    onResult(-1, null)
                }
            }
        })
    }

    fun getParticipantList(pid: Int, onResult: (Int, List<User>?) -> Unit) {
        val getParticipantListCall = planService.requestGetParticipantList(pid)
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

    fun getMemoList(pid: Int, onResult: (Int, List<Memo>?) -> Unit) {
        val getMemoListCall = planService.requestGetMemoList(pid)
        getMemoListCall.enqueue(object : CallbackWithRetry<MemoListResponse>(getMemoListCall) {
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
                super.onFailure {
                    onResult(-1, null)
                }
            }
        })
    }

    fun syncCalendar(mService: Calendar, event: Event) {
        CreateEventTask(mService, event).execute()
    }
}