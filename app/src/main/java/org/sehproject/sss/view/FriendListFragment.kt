package org.sehproject.sss.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentFriendListBinding
import org.sehproject.sss.databinding.FriendListItemBinding
import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.FriendViewModel

class FriendListFragment : Fragment() {
    private val friendViewModel: FriendViewModel by lazy {
        ViewModelProvider(this).get(FriendViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val friendListBinding: FragmentFriendListBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_friend_list, container, false)
        friendListBinding.friendLogic = friendViewModel.friendLogic

        initObserver(friendListBinding.searchRecyclerView)

        friendViewModel.setFriendList()

        return friendListBinding.root
    }

    private fun initObserver(recyclerView: RecyclerView) {
        val navController = findNavController()
        friendViewModel.searchUserEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.userSearchFragment)
        })

        friendViewModel.friendList.observe(viewLifecycleOwner, {
            val adapter = UserAdapter(it)
            recyclerView.adapter = adapter
        })

        friendViewModel.listFriendEvent.observe(viewLifecycleOwner, {
            val action = FriendListFragmentDirections.actionFriendListFragmentToFriendProfileFragment(it)
            navController.navigate(action)
        })

        friendViewModel.deleteFriendEvent.observe(viewLifecycleOwner, {
            friendViewModel.setFriendList()
        })
        friendViewModel.blockFriendEvent.observe(viewLifecycleOwner, {
            friendViewModel.setFriendList()
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