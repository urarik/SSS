package org.sehproject.sss

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.databinding.FragmentFriendProfileBinding
import org.sehproject.sss.databinding.FragmentProfileEditBinding
import org.sehproject.sss.logic.ProfileLogic
import org.sehproject.sss.utils.ProfileViewModelFactory
import org.sehproject.sss.view.FriendProfileFragmentArgs
import org.sehproject.sss.view.GroupDetailFragmentArgs
import org.sehproject.sss.viewmodel.PlanViewModel
import org.sehproject.sss.viewmodel.ProfileViewModel
import java.io.File
import java.io.FileInputStream
import android.provider.OpenableColumns




class ProfileEditFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by lazy {
        val appDatabase = AppDatabase.getInstance(requireContext())!!
        ViewModelProvider(this, ProfileViewModelFactory(appDatabase)).get(ProfileViewModel::class.java)
    }
    private lateinit var imageView: ImageView
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { results: Uri? ->
        imageView.setImageURI(results)
        val uri = results!!
        val stream = FileInputStream(context!!.contentResolver.openFileDescriptor(uri, "r")!!.fileDescriptor)
        profileViewModel.imageUri = uri
        profileViewModel.imageStream = stream

    }
    fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = context!!.getContentResolver().query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val profileEditBinding: FragmentProfileEditBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_profile_edit,
            container,
            false
        )
        profileEditBinding.profileLogic = profileViewModel.profileLogic

        val safeArgs: ProfileEditFragmentArgs by navArgs()
        profileViewModel.setProfile(safeArgs.userId)
        Log.d("TAG", safeArgs.userId)

        imageView = profileEditBinding.imageEditProfile

        initObserver(profileEditBinding)
        return profileEditBinding.root
    }

    private fun initObserver(profileEditBinding: FragmentProfileEditBinding) {
        val navController = findNavController()
        profileViewModel.editProfileCompleteEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.action_profileEditFragment_to_profileFragment)
        })

        profileViewModel.profileLiveData.observe(viewLifecycleOwner, {
            profileEditBinding.profile = it
        })

        profileViewModel.uploadImageEvent.observe(viewLifecycleOwner, {
            launcher.launch("image/*")
        })

        profileViewModel.imageBitmapLiveData.observe(viewLifecycleOwner, {
            var imageView = profileEditBinding.imageEditProfile
            imageView.setImageBitmap(profileViewModel.imageBitmapLiveData.value)
        })
    }
}