package org.sehproject.sss.view

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sehproject.sss.R
import org.sehproject.sss.UserInfo
import org.sehproject.sss.databinding.*
import org.sehproject.sss.datatype.Memo
import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.MapViewModel
import org.sehproject.sss.viewmodel.PlanViewModel

class PlanDetailFragment : Fragment() {
    val planViewModel: PlanViewModel by lazy {
        ViewModelProvider(this).get(PlanViewModel::class.java)
    }
    private val mapViewModel: MapViewModel by lazy {
        ViewModelProvider(this).get(MapViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val planDetailBinding: FragmentPlanDetailBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_plan_detail, container, false)
        planDetailBinding.planLogic = planViewModel.planLogic
        planDetailBinding.mapLogic = mapViewModel.mapLogic
        val view = planDetailBinding.root
        val safeArgs: PlanDetailFragmentArgs by navArgs()
        planViewModel.setPlan(safeArgs.pid)

        initObserver(planDetailBinding, safeArgs.pid)

        return view
    }

    fun initObserver(planDetailBinding: FragmentPlanDetailBinding, pid: Int) {
        planViewModel.concatAdapterLiveData.value = 0
        val navController = findNavController()
        planViewModel.planLiveData.observe(viewLifecycleOwner, {
            planDetailBinding.plan = it

            if(planViewModel.planLiveData.value?.creator == UserInfo.userId) {
                planDetailBinding.editPlanFloatingActionButton.visibility = ViewGroup.VISIBLE
                planDetailBinding.deletePlanTypeFloatingActionButton.visibility = ViewGroup.VISIBLE
                planDetailBinding.completePlanFloatingActionButton.visibility = ViewGroup.VISIBLE
                planDetailBinding.kickOutPlanFloatingActionButton.visibility = ViewGroup.VISIBLE
            }
        })

        planViewModel.userListLiveData.observe(viewLifecycleOwner, {
            planViewModel.concatAdapterLiveData.value =
                planViewModel.concatAdapterLiveData.value?.plus(1)
        })

        planViewModel.memoListLiveData.observe(viewLifecycleOwner, {
            planViewModel.concatAdapterLiveData.value =
                planViewModel.concatAdapterLiveData.value?.plus(1)
        })

        planViewModel.concatAdapterLiveData.observe(viewLifecycleOwner, {
            if(it == 2) {
                planDetailBinding.recyclerMemoParticipant.adapter = ConcatAdapter(
                    MemoAdapter(planViewModel.memoListLiveData.value!!),
                    ParticipantAdapter(planViewModel.userListLiveData.value!!)
                )
            }
        })

        planViewModel.createMemoCompleteEvent.observe(viewLifecycleOwner, {
            planViewModel.concatAdapterLiveData.value =
                planViewModel.concatAdapterLiveData.value?.minus(1)
            planViewModel.setMemoList(pid)
        })

        planViewModel.deleteMemoCompleteEvent.observe(viewLifecycleOwner, {
            planViewModel.concatAdapterLiveData.value =
                planViewModel.concatAdapterLiveData.value?.minus(1)
            planViewModel.setMemoList(pid)
        })

        planViewModel.deletePlanEvent.observe(viewLifecycleOwner, {
            android.app.AlertDialog
                .Builder(context)
                .setMessage("약속을 삭제하시겠습니까?")
                .setPositiveButton("네") { dialogInterface: DialogInterface, i: Int ->
                    planViewModel.planLogic.onDeletePlanConfirmClick(it)
                }
                .setNegativeButton("아니오", null)
                .show()

        })

        planViewModel.deletePlanCompleteEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.action_planDetailFragment_to_planListFragment)
        })

        planViewModel.cancelPlanEvent.observe(viewLifecycleOwner, {
            android.app.AlertDialog
                .Builder(context)
                .setMessage("약속에 참석 취소하시겠습니까?")
                .setPositiveButton("네") { dialogInterface: DialogInterface, i: Int ->
                    planViewModel.planLogic.onCancelPlanConfirmClick(it)
                }
                .setNegativeButton("아니오", null)
                .show()
        })

        planViewModel.cancelPlanCompleteEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.action_planDetailFragment_to_planListFragment)
        })

        planViewModel.editPlanEvent.observe(viewLifecycleOwner, {
            val action = PlanDetailFragmentDirections.actionPlanDetailFragmentToPlanEditFragment(it)
            navController.navigate(action)
        })

        planViewModel.kickOutPlanEvent.observe(viewLifecycleOwner, {
            val action = PlanDetailFragmentDirections.actionPlanDetailFragmentToPlanInviteDialogFragment(planViewModel.isInvite, it)
            navController.navigate(action)
        })

        planViewModel.invitePlanEvent.observe(viewLifecycleOwner, {
            val action = PlanDetailFragmentDirections.actionPlanDetailFragmentToPlanInviteDialogFragment(planViewModel.isInvite, it)
            navController.navigate(action)
        })

        mapViewModel.trackEvent.observe(viewLifecycleOwner, {
            val action = PlanDetailFragmentDirections.actionPlanDetailFragmentToMapFragment(it)
            navController.navigate(action)
        })

        planViewModel.completePlanCompleteEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.action_planDetailFragment_to_planListFragment)
        })

        planViewModel.createMemoEvent.observe(viewLifecycleOwner, {
            val editText = EditText(context)

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("메모")
            builder.setView(editText)
            builder.setPositiveButton("입력", DialogInterface.OnClickListener { dialog, which ->
                planViewModel.planLogic.onCreateMemoDoneClick(editText.getText().toString())
            })
            builder.setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
                // planViewModel.planLogic.onCreateMemoExitClick()
            })
            builder.show()
        })
    }

    private inner class MemoHolder(val itemMemoBinding: ItemMemoBinding) : RecyclerView.ViewHolder(itemMemoBinding.root) {
        fun bind(memo: Memo) {
            itemMemoBinding.memo = memo
            itemMemoBinding.plan = planViewModel.planLiveData.value
            itemMemoBinding.planLogic = planViewModel.planLogic
            if(memo.writer == UserInfo.nickname)
                itemMemoBinding.buttonDeleteMemo.visibility = ViewGroup.VISIBLE
        }
    }

    private inner class MemoAdapter(val memos: List<Memo>) :
        RecyclerView.Adapter<MemoHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoHolder {
            val itemMemoBinding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MemoHolder(itemMemoBinding)
        }

        override fun getItemCount(): Int = memos.size

        override fun onBindViewHolder(holder: MemoHolder, position: Int) {
            val memo = memos[position]
            holder.bind(memo)
        }
    }

    private inner class ParticipantHolder(val itemParticipantBinding: ItemParticipantsBinding)
        : RecyclerView.ViewHolder(itemParticipantBinding.root) {
        fun bind(user: User) {
            itemParticipantBinding.user = user
        }
    }

    private inner class ParticipantAdapter(val users: List<User>) :
        RecyclerView.Adapter<ParticipantHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantHolder {
            val itemParticipantBinding = ItemParticipantsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ParticipantHolder(itemParticipantBinding)
        }

        override fun getItemCount(): Int = users.size

        override fun onBindViewHolder(holder: ParticipantHolder, position: Int) {
            val user = users[position]
            holder.bind(user)
        }
    }
}