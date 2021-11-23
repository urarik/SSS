package org.sehproject.sss.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.google.gson.*
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentPlanCreateOcrBinding
import org.sehproject.sss.utils.StringParser
import org.sehproject.sss.viewmodel.PlanViewModel
import java.io.ByteArrayOutputStream

class PlanCreateOCRFragment : Fragment() {
    val planViewModel: PlanViewModel by lazy {
        ViewModelProvider(this).get(PlanViewModel::class.java)
    }
    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val planCreateOCRBinding: FragmentPlanCreateOcrBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_plan_create_ocr, container, false)
        planCreateOCRBinding.planLogic = planViewModel.planLogic
        val view = planCreateOCRBinding.root


        val register = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {

                val imageBitmap = it.data!!.extras!!
                    .get("data") as Bitmap
                planCreateOCRBinding.imageOcr.setImageBitmap(imageBitmap)
                val bitmap = scaleBitmapDown(imageBitmap, 1024)

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
                        } else {
                            val annotation = task.result!!.asJsonArray[0].asJsonObject["fullTextAnnotation"].asJsonObject
                            val string = annotation["text"].asString
                            val parser = StringParser()
                            val plan = parser.parse(string)
                            planViewModel.createPlanOcrDoneEvent.value = plan
                        }
                    }
            }
        }

        initObserver(planCreateOCRBinding, register)
        return view
    }

    private fun initObserver(
        planCreateOcrBinding: FragmentPlanCreateOcrBinding,
        register: ActivityResultLauncher<Intent>
    ) {
        planViewModel.uploadImgEvent.observe(viewLifecycleOwner, {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            register.launch(intent)
        })
        planViewModel.createPlanOcrDoneEvent.observe(viewLifecycleOwner, {
            //TODO
        })
    }

    private fun scaleBitmapDown(bitmap: Bitmap, maxDimension: Int): Bitmap {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height
        var resizedWidth = maxDimension
        var resizedHeight = maxDimension
        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension
            resizedWidth =
                (resizedHeight * originalWidth.toFloat() / originalHeight.toFloat()).toInt()
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension
            resizedHeight =
                (resizedWidth * originalHeight.toFloat() / originalWidth.toFloat()).toInt()
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension
            resizedWidth = maxDimension
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false)
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

}