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
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val planCreateOCRBinding: FragmentPlanCreateOcrBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_plan_create_ocr,
            container,
            false
        )
        planCreateOCRBinding.planLogic = planViewModel.planLogic

        val register = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                val imageBitmap = it.data!!.extras!!
                    .get("data") as Bitmap
                planCreateOCRBinding.imageOcr.setImageBitmap(imageBitmap)
                val bitmap = scaleBitmapDown(imageBitmap, 1024)
                planViewModel.ocrBitmap = bitmap
            }
        }

        initObserver(register)
        return planCreateOCRBinding.root
    }

    private fun initObserver(
        register: ActivityResultLauncher<Intent>
    ) {
        planViewModel.uploadImgEvent.observe(viewLifecycleOwner, {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            register.launch(intent)
        })
        planViewModel.createPlanCompleteEvent.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })
        planViewModel.createPlanOcrFailEvent.observe(viewLifecycleOwner, {
            Toast.makeText(context, "약속 생성 실패!\n인식된 문자열 $it", Toast.LENGTH_LONG).show()
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


}