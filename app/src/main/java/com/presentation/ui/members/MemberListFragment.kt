package com.presentation.ui.members

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.presentation.ui.members.adapters.MembersAdapter
import com.presentation.ui.members.viewmodels.MembersViewModel
import com.presentation.ui.states.MemberUIState
import com.app.mmustgdsc.databinding.FragmentMemberListBinding


class MemberListFragment : Fragment() {
    private var _binding : FragmentMemberListBinding? = null
    private val binding get() = _binding!!

    private val membersViewModel : MembersViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMemberListBinding.inflate(inflater, container, false)

        binding.membersRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        loadMembers()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadMembers() {
        membersViewModel.memberDataList.observe(viewLifecycleOwner) { memberState ->
            when(memberState) {
                is MemberUIState.Failure -> {
                    binding.membersLoader.visibility = View.GONE
                    showSnackBar("Could not get leads.\n${memberState.message}")
                }
                MemberUIState.Loading -> binding.membersLoader.visibility = View.VISIBLE
                MemberUIState.StandBy -> Unit
                is MemberUIState.Success -> {
                    binding.membersLoader.visibility = View.GONE
                    binding.membersRecyclerView.adapter = memberState.data?.let { MembersAdapter(it) }
                }
            }
        }
    }

    private fun showSnackBar(message : String)  {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Cancel") {
            snackbar.dismiss()
        }
        snackbar.show()
    }
}