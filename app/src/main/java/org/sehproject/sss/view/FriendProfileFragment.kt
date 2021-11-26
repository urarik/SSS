package org.sehproject.sss.view

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import org.sehproject.sss.R
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.databinding.FragmentFriendProfileBinding
import org.sehproject.sss.databinding.ItemFriendPlanBinding
import org.sehproject.sss.databinding.ItemPlanBinding
import org.sehproject.sss.datatype.Plan
import org.sehproject.sss.logic.FriendLogic
import org.sehproject.sss.utils.ProfileViewModelFactory
import org.sehproject.sss.viewmodel.FriendViewModel
import org.sehproject.sss.viewmodel.PlanViewModel
import org.sehproject.sss.viewmodel.ProfileViewModel


class FriendProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by lazy {
        val appDatabase = AppDatabase.getInstance(requireContext())!!
        ViewModelProvider(this, ProfileViewModelFactory(appDatabase)).get(ProfileViewModel::class.java)
    }
    private val planViewModel: PlanViewModel by lazy {
        ViewModelProvider(this).get(PlanViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val friendProfileBinding: FragmentFriendProfileBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_friend_profile,
            container,
            false
        )
        friendProfileBinding.profileLogic = profileViewModel.profileLogic

        val safeArgs: FriendProfileFragmentArgs by navArgs()
        profileViewModel.setProfile(safeArgs.userId)
        Log.d("TAG", safeArgs.userId)

        planViewModel.getPlanList()

        initObserver(friendProfileBinding)

        return friendProfileBinding.root
    }

    fun initObserver(friendProfileBinding: FragmentFriendProfileBinding) {
        profileViewModel.profileLiveData.observe(viewLifecycleOwner, {
            friendProfileBinding.profile = it
        })

        profileViewModel.imageBitmapLiveData.observe(viewLifecycleOwner, {
            var imageView = friendProfileBinding.imageView
            imageView.setImageBitmap(profileViewModel.imageBitmapLiveData.value)
        })

        planViewModel.planListLiveData.observe(viewLifecycleOwner, {
            val adapter = PlanAdapter(it)
            friendProfileBinding.RecyclerViewPlanList.adapter = adapter
        })
    }

    private inner class PlanHolder(val itemFriendPlanBinding: ItemFriendPlanBinding) : RecyclerView.ViewHolder(itemFriendPlanBinding.root) {
        fun bind(plan: Plan) {
            itemFriendPlanBinding.plan = plan
            itemFriendPlanBinding.planLogic = planViewModel.planLogic
        }
    }

    private inner class PlanAdapter(val plans: List<Plan>) :
        RecyclerView.Adapter<PlanHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanHolder {
            val itemFriendPlanBinding = ItemFriendPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PlanHolder(itemFriendPlanBinding)
        }

        override fun getItemCount(): Int = plans.size

        override fun onBindViewHolder(holder: PlanHolder, position: Int) {
            val plan = plans[position]
            holder.bind(plan)
        }
    }
}