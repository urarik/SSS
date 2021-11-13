package org.sehproject.sss.view.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentInviteFriendBinding
import org.sehproject.sss.databinding.ItemUserBinding
import org.sehproject.sss.datatype.Friend
import org.sehproject.sss.viewmodel.GroupViewModel
import org.sehproject.sss.viewmodel.PlanViewModel

open class DialogFragment(protected var groupViewModel: GroupViewModel? = null, protected var planViewModel: PlanViewModel? = null)
    :DialogFragment(){
    protected lateinit var adapter: UserAdapter

    protected inner class UserHolder(val itemUserBinding: ItemUserBinding) : RecyclerView.ViewHolder(itemUserBinding.root) {

        fun bind(user: Friend) {
            itemUserBinding.user = user
            itemUserBinding.itemUser.setOnClickListener {
                Log.d("TAG", "123")
                groupViewModel?.let {
                    groupViewModel!!.groupLogic.onItemClick(itemUserBinding.user as Friend)
                }?: let {
                    planViewModel!!.planLogic.onItemClick(itemUserBinding.user as Friend)
                }
                it.setBackgroundColor(resources.getColor(R.color.gray)) // gray
            }
        }
    }

    protected inner class UserAdapter(val users: List<Friend>) :
        RecyclerView.Adapter<UserHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
            val itemCuisinesBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return UserHolder(itemCuisinesBinding)
        }

        override fun getItemCount(): Int = users.size

        override fun onBindViewHolder(holder: UserHolder, position: Int) {
            val user = users[position]
            holder.bind(user)
        }

    }
}