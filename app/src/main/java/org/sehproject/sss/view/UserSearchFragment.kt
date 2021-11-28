package org.sehproject.sss.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentFriendListBinding
import org.sehproject.sss.databinding.FragmentUserSearchBinding
import org.sehproject.sss.databinding.ItemPlanBinding
import org.sehproject.sss.databinding.UserSerachItemBinding
import org.sehproject.sss.datatype.Plan
import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.FriendViewModel
import android.content.Context.INPUT_METHOD_SERVICE
import android.opengl.Visibility
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.getSystemService




class UserSearchFragment : Fragment() {
    val friendViewModel: FriendViewModel by lazy {
        ViewModelProvider(this).get(FriendViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userSearchBinding: FragmentUserSearchBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_user_search, container, false)
        userSearchBinding.friendLogic = friendViewModel.friendLogic

        initObserver(userSearchBinding.searchRecyclerView)

        return userSearchBinding.root
    }

    fun initObserver(recyclerView: RecyclerView) {
        friendViewModel.userList.observe(viewLifecycleOwner, {
            val adapter = UserAdapter(it)
            recyclerView.adapter = adapter

            //dismiss keyboard
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (null != requireActivity().currentFocus) imm.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.applicationWindowToken, 0
            )
        })

        friendViewModel.addFriendEvent.observe(viewLifecycleOwner, {
            it.visibility = ViewGroup.GONE
        })

        friendViewModel.addFriendFailEvent.observe(viewLifecycleOwner, {
            Toast.makeText(context, "이미 친구인 사용자입니다.", Toast.LENGTH_SHORT).show()
        })
    }

    private inner class UserHolder(val userSearchItemBinding: UserSerachItemBinding) : RecyclerView.ViewHolder(userSearchItemBinding.root) {
        fun bind(user: User) {
            userSearchItemBinding.user = user
            userSearchItemBinding.friendLogic = friendViewModel.friendLogic
        }
    }

    private inner class UserAdapter(val users: List<User>) :
        RecyclerView.Adapter<UserHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
            val userSearchItemBinding = UserSerachItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return UserHolder(userSearchItemBinding)
        }

        override fun getItemCount(): Int = users.size

        override fun onBindViewHolder(holder: UserHolder, position: Int) {
            val user = users[position]
            holder.bind(user)
        }
    }
}