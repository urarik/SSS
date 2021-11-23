package org.sehproject.sss.view.Friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentFriendListBinding
import org.sehproject.sss.databinding.FriendListItemBinding
import org.sehproject.sss.databinding.UserSerachItemBinding
import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.FriendViewModel

class FriendListFragment : Fragment() {
    private val friendViewModel: FriendViewModel by lazy {
        ViewModelProvider(this).get(FriendViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val friendListBinding: FragmentFriendListBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_friend_list, container, false)
        friendListBinding.friendLogic = friendViewModel.friendLogic

        friendViewModel.getFriendList()
        initObserver(friendListBinding.searchRecyclerView)

        return friendListBinding.root
    }

    private fun initObserver(recyclerView: RecyclerView) {
        friendViewModel.searchUserEvent.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.userSearchFragment)
        })

        friendViewModel.friendList.observe(viewLifecycleOwner, {
            val adapter = UserAdapter(it)
            recyclerView.adapter = adapter
        })
    }

    private inner class UserHolder(val friendListItemBinding: FriendListItemBinding) : RecyclerView.ViewHolder(friendListItemBinding.root) {
        fun bind(user: User) {
            friendListItemBinding.user = user
            friendListItemBinding.friendLogic = friendViewModel.friendLogic
        }
    }

    private inner class UserAdapter(val users: List<User>) :
        RecyclerView.Adapter<UserHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
            val friendListItemBinding = FriendListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return UserHolder(friendListItemBinding)
        }

        override fun getItemCount(): Int = users.size

        override fun onBindViewHolder(holder: UserHolder, position: Int) {
            val user = users[position]
            holder.bind(user)
        }
    }
}