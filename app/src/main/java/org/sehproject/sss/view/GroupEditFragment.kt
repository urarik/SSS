package org.sehproject.sss.view

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentGroupEditBinding
import org.sehproject.sss.viewmodel.GroupViewModel
import top.defaults.colorpicker.ColorPickerPopup

class GroupEditFragment : Fragment() {
    private val groupViewModel by lazy {
        ViewModelProvider(this).get(GroupViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val editGroupBinding: FragmentGroupEditBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_group_edit, container, false)
        val safeArgs: GroupEditFragmentArgs by navArgs()

        editGroupBinding.groupLogic = groupViewModel.groupLogic
        editGroupBinding.group = safeArgs.group

        initObserver(editGroupBinding)

        return editGroupBinding.root
    }
    private fun initObserver(editGroupBinding: FragmentGroupEditBinding) {
        val navController = findNavController()

        groupViewModel.inviteGroupEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.groupInviteFragment)
        })
        groupViewModel.setColorEvent.observe(viewLifecycleOwner, {
            ColorPickerPopup.Builder(context)
                .initialColor(Color.BLUE)
                .enableBrightness(true)
                .enableAlpha(true)
                .okTitle("선택")
                .cancelTitle("취소")
                .showIndicator(true)
                .showValue(true)
                .build()
                .show(view, object :ColorPickerPopup.ColorPickerObserver(){
                    override fun onColorPicked(color: Int) {
                        it.color = color
                        val view = editGroupBinding.imageEditGroupColor
                        view.background.colorFilter = BlendModeColorFilter(
                            Color.parseColor("#"+Integer.toHexString(color)),
                            BlendMode.SRC_ATOP)
                    }

                })
        })

        groupViewModel.createGroupCompleteEvent.observe(viewLifecycleOwner, {
            navController.popBackStack()
        })
        groupViewModel.editGroupCompleteEvent.observe(viewLifecycleOwner, {
            navController.popBackStack()
        })
        groupViewModel.editGroupFailEvent.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }
}