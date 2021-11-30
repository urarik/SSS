package org.sehproject.sss


import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.databinding.FragmentProfileEditBinding
import org.sehproject.sss.utils.ProfileViewModelFactory
import org.sehproject.sss.viewmodel.ProfileViewModel
import java.io.FileInputStream

class ProfileEditFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by lazy {
        val appDatabase = AppDatabase.getInstance(requireContext())!!
        ViewModelProvider(this, ProfileViewModelFactory(appDatabase)).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileEditBinding: FragmentProfileEditBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_profile_edit,
            container,
            false
        )
        profileEditBinding.profileLogic = profileViewModel.profileLogic
        val safeArgs: ProfileEditFragmentArgs by navArgs()

        val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { results: Uri? ->
            profileEditBinding.imageEditProfile.setImageURI(results)
            val uri = results!!
            val stream = FileInputStream(context!!.contentResolver.openFileDescriptor(uri, "r")!!.fileDescriptor)
            val name = getFileName(results)

            profileViewModel.imageUri = uri
            profileViewModel.imageLength = getFileLength(uri)
            profileViewModel.imageStream = stream
            profileViewModel.imageExtension = name.substring(name.lastIndexOf("."))
        }
        initObserver(profileEditBinding, launcher)

        profileViewModel.setProfile(safeArgs.userId)

        return profileEditBinding.root
    }

    private fun initObserver(profileEditBinding: FragmentProfileEditBinding,
                             launcher: ActivityResultLauncher<String>
    ) {
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
            val imageView = profileEditBinding.imageEditProfile
            imageView.setImageBitmap(profileViewModel.imageBitmapLiveData.value)
        })
    }

    private fun getFileLength(uri: Uri): Int {
        val returnCursor: Cursor? =
            context!!.getContentResolver().query(uri, null, null, null, null)
        val sizeIndex = returnCursor!!.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        return returnCursor.getLong(sizeIndex).toInt()
    }
    private fun getFileName(uri: Uri): String {
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
}